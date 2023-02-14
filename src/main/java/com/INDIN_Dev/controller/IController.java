package com.INDIN_Dev.controller;

import com.INDIN_Dev.model.IModel;
import com.sun.deploy.util.SystemUtils;
import org.neo4j.driver.v1.exceptions.DatabaseException;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

@RestController
//@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/enterVar")

public class IController {

    private static IModel model;
    private String jsonString = null;




    @GetMapping("{capacity}/{function}/{domain}/{scalability}/{time_behaviour}/{reusability}")
    public String getHelloMessage2(@PathVariable("capacity") String capacity,
                                     @PathVariable("function") String function,
                                     @PathVariable("domain") String domain,
                                     @PathVariable("scalability") String scalability,
                                     @PathVariable("time_behaviour") String time_behaviour,
                                     @PathVariable("reusability") String reusability) throws IOException {
        /**
         * MODEL INITIALIZATION
         */

        System.out.println( System.getProperty("os.name"));

        float f_scalability = Integer.parseInt(scalability) / 100.0f;
        float f_time_behaviour = Integer.parseInt(time_behaviour) / 100.0f;
        float f_reusability = Integer.parseInt(reusability) / 100.0f;

        System.out.println(f_scalability +"::"+ f_time_behaviour +"::"+ f_reusability);


        try {
            model = new IModel("bolt://localhost:11005",
                    "neo4j",
                    "chandan");
        }
        catch(DatabaseException e){
            System.out.println(e.getCause());
        }
        /**
         * VIEW INITIALIZATION
         */

        try {
            jsonString = model.generateScenarioReport(function,
                    domain,
                    capacity,
                    f_scalability,
                    f_time_behaviour,
                    f_reusability);
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        return jsonString;
    }



}
