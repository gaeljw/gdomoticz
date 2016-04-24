package fr.gaeljw.gdomoticz.service;

import com.mongodb.BasicDBObject;
import fr.gaeljw.gdomoticz.model.mongo.TemperatureAggregation;
import org.bson.BSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
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

        // db.temperatures.aggregate([
        // {$match:{dateTime:{$gte:ISODate("2016-04-24T17:00:00")}}},
        // {$sort:{dateTime:1}},
        // {$group:{_id:"$idDevice",temperatures:{$push:{date:"$dateTime", temperature:"$temperature"}}}}
        // ])

        BSONObject groupTemperatures = new BasicDBObject().append("date", "$dateTime").append("temperature", "$temperature");
        Aggregation aggregation = newAggregation(
                match(Criteria.where("dateTime").gte(yesterday)),
                sort(Sort.Direction.ASC, "dateTime"),
                group("idDevice").push(groupTemperatures).as("temperatures")
        );

        AggregationResults<TemperatureAggregation> temperatures =
                mongoTemplate.aggregate(aggregation, "temperatures", TemperatureAggregation.class);

        return temperatures.getMappedResults();
    }

}
