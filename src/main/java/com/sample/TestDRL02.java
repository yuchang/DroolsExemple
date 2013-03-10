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

public class TestDRL02 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
        try {
        	
            KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        
            kbuilder.add(ResourceFactory.newClassPathResource("TestDRL02.drl", TestDRL02.class), ResourceType.DRL);
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
            KnowledgeRuntimeLogger logger = KnowledgeRuntimeLoggerFactory.newFileLogger(ksession, "TestDRL02");
            
            Customer customer = new Customer();
            customer.setAge(25);
            customer.setCity("Lille");
            customer.setName("Yuchang");
            customer.setGender("Homme");
            
            Order order1 = new Order();
            order1.setCustomer(customer);
            order1.setId(1234);
            order1.setName("Commande Industrielle");
            order1.setPrice(123.45);
            
            Order order2 = new Order();
            order2.setCustomer(customer);
            order2.setId(4321);
            order2.setName("Commande Commerciale");
            order2.setPrice(543.21); 
            
            Order order3 = new Order();
            order3.setCustomer(null);
            order3.setId(0);
            order3.setName("Commande Inconnue");
            order3.setPrice(0);             
            
            ArrayList<Order> orders = new ArrayList<Order>();
            orders.add(order1);
            orders.add(order2);
            customer.setOrders(orders);
            
            ksession.setGlobal("orderNames", new String[]{"Commande Industrielle", "Commande Commerciale", "Commande Inconnue"});
            ksession.setGlobal("orderNames2", new String[]{});
            
            ksession.insert(customer);
            ksession.insert(order1);
            //ksession.insert(order3);
            
            ksession.fireAllRules();
            
            ksession.dispose();         
            logger.close();
            
        } catch (Throwable t) {
            t.printStackTrace();
        }      
	}
	
    public static void afficherMsg(String msg){
    	System.out.println("(Msg bis) " + msg);
    }

}
