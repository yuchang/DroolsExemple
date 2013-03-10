package com.sample;

import java.util.ArrayList;
import java.util.Collection;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseConfiguration;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderErrors;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.definition.KnowledgePackage;
import org.drools.io.ResourceFactory;
import org.drools.logger.KnowledgeRuntimeLogger;
import org.drools.logger.KnowledgeRuntimeLoggerFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.rule.QueryResults;
import org.drools.runtime.rule.QueryResultsRow;

public class TestDRL044 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
        try {
        	
            KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        
            kbuilder.add(ResourceFactory.newClassPathResource("TestDRL044.drl", TestDRL044.class), ResourceType.DRL);
            Collection<KnowledgePackage> kpackage = kbuilder.getKnowledgePackages();
            
            KnowledgeBuilderErrors errors = kbuilder.getErrors();
            if (errors.size() > 0) {
                for (KnowledgeBuilderError error: errors) {
                    System.err.println(error);
                }
                throw new IllegalArgumentException("Could not parse knowledge.");
            }
            
        	KnowledgeBaseConfiguration kbConf = KnowledgeBaseFactory.newKnowledgeBaseConfiguration();
        	kbConf.setProperty("org.drools.sequential", "false");
            
            KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase(kbConf);
            kbase.addKnowledgePackages(kpackage);

            StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();
            KnowledgeRuntimeLogger logger = KnowledgeRuntimeLoggerFactory.newFileLogger(ksession, "TestDRL044");
              
            ksession.insert(generateCustomer("Yuchang", 25, 21, "Homme"));
            ksession.insert(generateCustomer("Jonathan", 30, 11, "Homme"));
            ksession.insert(generateCustomer("Adrien", 24, 12, "Homme"));
            
            ksession.fireAllRules();
            
            QueryResults qr=ksession.getQueryResults("query fact count");
            System.out.println("customer objet count:"+qr.size()); 
            
            //QueryResults queryResults = ksession.getQueryResults("query test");
            QueryResults queryResults = ksession.getQueryResults("query test 2", new Object[]{new Integer(30), "Homme"});
            for(QueryResultsRow qrr : queryResults){
            	Customer customer = (Customer)qrr.get("customer");
            	System.out.println("customer name : "+customer.getName());
            }            
             
            ksession.dispose();         
            logger.close();
            
        }catch (Throwable t) {
            t.printStackTrace();
        }
        
	}
	
    public static Customer generateCustomer(String name, int age, int orderSize, String gender){
    	Customer customer = new Customer();
    	customer.setAge(age);
    	customer.setName(name);
    	customer.setGender(gender);
    	ArrayList<Order> ls = new ArrayList<Order>();
    	for(int i=0; i<orderSize; i++){
    		ls.add(new Order());
    	}
    	customer.setOrders(ls);
    	return customer;
    }

}
