/***
 * Author: Chandan_Sharma
 */
package com.INDIN_Dev.model;

import com.fasterxml.jackson.databind.util.JSONPObject;
import jdk.nashorn.internal.parser.JSONParser;
import org.neo4j.driver.v1.*;
import org.neo4j.driver.v1.exceptions.DatabaseException;

import javax.json.Json;
import javax.json.JsonObject;
import javax.xml.ws.Response;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class IModel implements AutoCloseable{

    private Driver driver;

    public IModel(String uri, String user, String password){
        try {
            driver = GraphDatabase.driver(uri, AuthTokens.basic(user, password));
        }
        catch (DatabaseException e){
            System.out.println("Database Exception1" + e.getMessage());
        }
    }


    public String generateScenarioReport(String function,
                                           String appDomain,
                                           String s_capHostAgents,
                                           float scalabilityWeight,
                                           float timeBehaviourWeight,
                                           float reusabilityWeight) throws IOException {

        JsonObject jsonObject = null;
        String str = "";
        boolean b_capHostAgents = false;

        if(s_capHostAgents.equalsIgnoreCase("Yes")){
            b_capHostAgents = true;
        }


        StatementResult result = null;
        try(Session session = driver.session(AccessMode.READ)){
            result = session.run("WITH "+ b_capHostAgents+ " AS debug\n" +
                    "MATCH (a)-[control:WEIGHT]->(f:Function), " +
                    "(a)-->(d:Domain), " +
                    "(a)-[h:WEIGHT]->(t:PerformanceEfficiency), " +
                    "(a)-[timeB:WEIGHT]->(s:PerformanceEfficiency), " +
                    "(a)-[reuse:WEIGHT]->(s1:Maintenance), " +
                    "(a)-[scale:WEIGHT]->(s2:PerformanceEfficiency) " +
                    " where f.name = \""+ function + "\"" +
                    " AND d.name = \""+ appDomain + "\""+
                    " AND t.name = \"Capacity To Host Agents\""+
                    " AND s.name = \"Time Behaviour\""+
                    " AND s1.name = \"Reusability\"" +
                    " AND s2.name = \"Scalability\""+
                    " RETURN a.name AS name,a.implementationType AS Type, a.apiClient AS API, a.channel AS Channel , a.broker AS Broker, " +
                    "CASE " +
                    "WHEN debug = false \n" +
                    "\tTHEN CASE\n" +
                    "\t\tWHEN h.value = true\n" +
                    "\t\tTHEN\n" +
                    " (timeB.value * "+ timeBehaviourWeight +
                    " + "+reusabilityWeight+
                    "* reuse.value + "+ scalabilityWeight +" * scale.value) * 0\n" +
                    "\t\tWHEN h.value = false\n" +
                    "\t\tTHEN \n" +
                    "(timeB.value * "+ timeBehaviourWeight +
                    " + "+reusabilityWeight+ "* reuse.value + "
                    + scalabilityWeight +" * scale.value)" +
                    "\t\tEND\n" +
                    "    ELSE  round(((timeB.value * "+ timeBehaviourWeight +
                    " + "+reusabilityWeight+ "* reuse.value + "
                    + scalabilityWeight +" * scale.value))*1000)/1000   \n" +
                    "END AS Score,\n" +
                    "\n" +
                    "CASE \n" +
                    "WHEN debug = false \n" +
                    "\tTHEN CASE\n" +
                    "\t\tWHEN h.value = true\n" +
                    "\t\tTHEN\n" +
                    " \t\t\t(timeB.value * "+ timeBehaviourWeight +
                    " + "+reusabilityWeight+ "* reuse.value + "
                    + scalabilityWeight +" * scale.value) * (control.value / 5.0) * 0\n" +
                    "\t\tWHEN h.value = false\n" +
                    "\t\tTHEN \n" +
                    "round(((timeB.value * "+ timeBehaviourWeight +
                    " + "+reusabilityWeight+ "* reuse.value + "
                    + scalabilityWeight +" * scale.value) * (control.value / 5.0))*1000)/1000\n" +
                    "\t\tEND\n" +
                    "    ELSE  round(((timeB.value * "+ timeBehaviourWeight +
                    " + "+reusabilityWeight+ "* reuse.value + "
                    + scalabilityWeight +" * scale.value) * (control.value / 5.0))*1000)/1000 \n" +
                    "END AS SFinal ORDER BY SFinal DESC, name ASC"

            );
        }

        for(Record record:result.list()){
            jsonObject = Json.createObjectBuilder(record.asMap()).build();
            str += jsonObject.toString() + ",";
        }

        str = "[ " + str.substring(0,str.length()-1) + "]";


        return str;
    }

    public List<String> searchFunction(){
        List<String> functionString = new ArrayList<>();
        StatementResult result = null;
        try(Session session = driver.session(AccessMode.READ)) {
            result = session.run("MATCH (x:Function) RETURN x.name");
        }
        for(Record record: result.list()){
            functionString.add(record.get("x.name")
                    .toString()
                    .substring(1,(record.get("x.name").size()+1)));
        }
        return functionString;
    }

    public List<String> searchAppDomain(){
        List<String> functionString = new ArrayList<>();
        StatementResult result = null;
        try(Session session = driver.session(AccessMode.READ)) {
            result = session.run("MATCH (x:Domain) RETURN x.name");
        }
        for(Record record: result.list()){
            functionString.add(record.get("x.name")
                    .toString()
                    .substring(1,(record.get("x.name").size()+1)));
        }
        return functionString;
    }

    @Override
    public void close() throws Exception {
        driver.close();
    }
}