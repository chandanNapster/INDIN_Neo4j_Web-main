//--NODES IN THE DATABASE

//--IMPLEMENTATION
//--Keep the label names as CAMEL case as show in the script below, this makes NEO4J to pick up the name property 
//--and display node names in the GUI correctly..... Still have to figure out why.... Probably have to look in the code
//--of NEO4J.
CREATE  (T1:Hybrid{implementationType:"Tightly",name:"HT:1", apiClient:"Java", channel:"Modbus"}),
		(T2:OnDevice{implementationType:"Tightly", name:"OT:1", apiClient:"Java", channel:"Modbus"}),
        (L1:Hybrid{implementationType:"Loosely", name:"HL:1", apiClient:"Apache Milo", channel:"OPC-UA"}),
        (L2:Hybrid{implementationType:"Loosely", name:"HL:2", apiClient:"Apache Paho", channel:"MQTT", broker:"Eclipse Mosquito"}),
        (L3:OnDevice{implementationType:"Loosely", name:"OL:1",apiClient:"Apache Milo", channel:"OPC-UA"}),
        (L4:OnDevice{implementationType:"Loosely", name:"OL:2",apiClient:"Apache Paho", channel:"MQTT", broker:"Eclipse Mosquito"})
//--CATEGORIES
CREATE	(Monitoring:Function{name:"Monitoring"}),
        (Control:Function{name:"Control"}),
        (Simulation:Function{name :"Simulation"}),
        (Energy:Domain{name:"Energy"}),
        (FactoryAutomation:Domain{name:"Factory Automation"}),
        (BuildingAutomation:Domain{name:"Building Automation"}),
        (CapacityToHostAgents:PerformanceEfficiency{name:"Capacity To Host Agents"}),
        (TimeBehaviour:PerformanceEfficiency{name:"Time Behaviour"}),
        (Reusability:Maintenance{name:"Reusability"}),
		(Scalability:PerformanceEfficiency{name:"Scalability"})

//--EDGES IN THE DATABASE

CREATE  (T1)-[v1:WEIGHT{value:4}]->(Monitoring),
		(T1)-[v2:WEIGHT{value:1}]->(Control),
        (T1)-[v3:WEIGHT{value:2}]->(Simulation),
        (T1)-[v4:WEIGHT{value:3}]->(Energy),
        (T1)-[v5:WEIGHT{value:5}]->(FactoryAutomation),
        (T1)-[v6:WEIGHT{value:3}]->(BuildingAutomation),
        (T1)-[v7:WEIGHT{value:false}]->(CapacityToHostAgents),
        (T1)-[v8:WEIGHT{value:3}]->(TimeBehaviour),
        (T1)-[v9:WEIGHT{value:3}]->(Reusability),
		(T1)-[v55:WEIGHT{value:2}]->(Scalability),
       
        (T2)-[v10:WEIGHT{value:4}]->(Monitoring),
		(T2)-[v11:WEIGHT{value:4}]->(Control),
        (T2)-[v12:WEIGHT{value:1}]->(Simulation),
        (T2)-[v13:WEIGHT{value:3}]->(Energy),
        (T2)-[v14:WEIGHT{value:4}]->(FactoryAutomation),
        (T2)-[v15:WEIGHT{value:3}]->(BuildingAutomation),
        (T2)-[v16:WEIGHT{value:true}]->(CapacityToHostAgents),
        (T2)-[v17:WEIGHT{value:5}]->(TimeBehaviour),
        (T2)-[v18:WEIGHT{value:2}]->(Reusability),
		(T2)-[v56:WEIGHT{value:2}]->(Scalability),
        
        (L1)-[v19:WEIGHT{value:5}]->(Monitoring),
		(L1)-[v20:WEIGHT{value:1}]->(Control),
        (L1)-[v21:WEIGHT{value:5}]->(Simulation),
        (L1)-[v22:WEIGHT{value:4}]->(Energy),
        (L1)-[v23:WEIGHT{value:5}]->(FactoryAutomation),
        (L1)-[v24:WEIGHT{value:4}]->(BuildingAutomation),
        (L1)-[v25:WEIGHT{value:false}]->(CapacityToHostAgents),
        (L1)-[v26:WEIGHT{value:3}]->(TimeBehaviour),
        (L1)-[v27:WEIGHT{value:5}]->(Reusability),
		(L1)-[v57:WEIGHT{value:4}]->(Scalability),
        
        (L2)-[v28:WEIGHT{value:5}]->(Monitoring),
		(L2)-[v29:WEIGHT{value:1}]->(Control),
        (L2)-[v30:WEIGHT{value:5}]->(Simulation),
        (L2)-[v31:WEIGHT{value:3}]->(Energy),
        (L2)-[v32:WEIGHT{value:4}]->(FactoryAutomation),
        (L2)-[v33:WEIGHT{value:5}]->(BuildingAutomation),
        (L2)-[v34:WEIGHT{value:false}]->(CapacityToHostAgents),
        (L2)-[v35:WEIGHT{value:2}]->(TimeBehaviour),
        (L2)-[v36:WEIGHT{value:5}]->(Reusability),
		(L2)-[v58:WEIGHT{value:4}]->(Scalability),
        
        (L3)-[v37:WEIGHT{value:5}]->(Monitoring),
		(L3)-[v38:WEIGHT{value:3}]->(Control),
        (L3)-[v39:WEIGHT{value:3}]->(Simulation),
        (L3)-[v40:WEIGHT{value:3}]->(Energy),
        (L3)-[v41:WEIGHT{value:4}]->(FactoryAutomation),
        (L3)-[v42:WEIGHT{value:4}]->(BuildingAutomation),
        (L3)-[v43:WEIGHT{value:true}]->(CapacityToHostAgents),
        (L3)-[v44:WEIGHT{value:4}]->(TimeBehaviour),
        (L3)-[v45:WEIGHT{value:5}]->(Reusability),
		(L3)-[v59:WEIGHT{value:3}]->(Scalability),
        
        (L4)-[v46:WEIGHT{value:5}]->(Monitoring),
		(L4)-[v47:WEIGHT{value:3}]->(Control),
        (L4)-[v48:WEIGHT{value:3}]->(Simulation),
        (L4)-[v49:WEIGHT{value:2}]->(Energy),
        (L4)-[v50:WEIGHT{value:4}]->(FactoryAutomation),
        (L4)-[v51:WEIGHT{value:4}]->(BuildingAutomation),
        (L4)-[v52:WEIGHT{value:true}]->(CapacityToHostAgents),
        (L4)-[v53:WEIGHT{value:4}]->(TimeBehaviour),
        (L4)-[v54:WEIGHT{value:5}]->(Reusability),
		(L4)-[v60:WEIGHT{value:3}]->(Scalability)