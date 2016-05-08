package fr.gaeljw.gdomoticz.service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import fr.gaeljw.gdomoticz.model.mongo.TemperatureAggregation;
import fr.gaeljw.gdomoticz.model.mongo.TemperatureMinMax;
import org.bson.BSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperationContext;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Service
public class MongoService {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<TemperatureAggregation> getLastDayTemperatures() {
        Date yesterday = Date.from(ZonedDateTime.now().minusDays(1).toInstant());

//         db.temperatures.aggregate([
//         {$match:{dateTime:{$gte:ISODate("2016-04-24T17:00:00")}, show:{$ne:false} }},
//         {$sort:{dateTime:1}},
//         {$group:{_id:"$nameDevice",temperatures:{$push:{date:"$dateTime", temperature:"$temperature"}}}}
//         ])

        DBObject match = new BasicDBObject().append("$match",
                new BasicDBObject()
                        .append("dateTime", new BasicDBObject("$gte", yesterday))
                        .append("show", new BasicDBObject("$ne", false)));
        BSONObject groupTemperatures = new BasicDBObject().append("date", "$dateTime").append("temperature", "$temperature");
        Aggregation aggregation = newAggregation(
                new AggregationOperation() {
                    @Override
                    public DBObject toDBObject(AggregationOperationContext context) {
                        return match;
                    }
                },
                sort(Sort.Direction.ASC, "dateTime"),
                group("nameDevice").push(groupTemperatures).as("temperatures")
        );

        AggregationResults<TemperatureAggregation> temperatures =
                mongoTemplate.aggregate(aggregation, "temperatures", TemperatureAggregation.class);

        return temperatures.getMappedResults();
    }

    public List<TemperatureMinMax> getLastMonthTemperatures() {

//         db.temperatures.aggregate([
//         {$match:{dateTime:{$gte:ISODate("2016-03-01")}, show:{$ne:false} }},
//         {$project:{nameDevice:1,temperature:1,date:{$dateToString:{format:"%Y-%m-%d", date:"$dateTime"}}}},
//         {$group:{_id:{nameDevice:"$nameDevice", date:"$date"}, min:{$min:"$temperature"}, max:{$max:"$temperature"}, moy:{$avg:"$temperature"}}},
//         {$sort:{"_id.date":1}},
//         {$group:{_id:"$_id.nameDevice", points:{$push:{date:"$_id.date", min:"$min", max:"$max", moy:"$moy"}}}}
//         ])

        Date lastMonth = Date.from(ZonedDateTime.now().minusMonths(1).toInstant());

        final DBObject match = new BasicDBObject().append("$match",
                new BasicDBObject()
                        .append("dateTime", new BasicDBObject("$gte", lastMonth))
                        .append("show", new BasicDBObject("$ne", false)));

        final DBObject dateToString = new BasicDBObject("$project",
                new BasicDBObject("nameDevice", 1)
                        .append("temperature", 1)
                        .append("date",
                                new BasicDBObject("$dateToString",
                                        new BasicDBObject()
                                                .append("format", "%Y-%m-%d")
                                                .append("date", "$dateTime"))));

        BSONObject push = new BasicDBObject().append("date", "$_id.date")
                .append("min", "$min")
                .append("max", "$max")
                .append("moy", "$moy");

        Aggregation aggregation = newAggregation(
                new AggregationOperation() {
                    @Override
                    public DBObject toDBObject(AggregationOperationContext context) {
                        return match;
                    }
                },
                new AggregationOperation() {
                    @Override
                    public DBObject toDBObject(AggregationOperationContext context) {
                        return dateToString;
                    }
                },
                group("nameDevice", "date")
                        .min("temperature").as("min")
                        .max("temperature").as("max")
                        .avg("temperature").as("moy"),
                sort(Sort.Direction.ASC, "_id.date"),
                group("_id.nameDevice").push(push).as("points")
        );

        AggregationResults<TemperatureMinMax> temperatures =
                mongoTemplate.aggregate(aggregation, "temperatures", TemperatureMinMax.class);

        return temperatures.getMappedResults();

    }

}
