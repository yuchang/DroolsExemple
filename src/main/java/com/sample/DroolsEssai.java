package com.sample;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseConfiguration;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderErrors;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.definition.KnowledgePackage;
import org.drools.definition.type.FactType;
import org.drools.io.ResourceFactory;
import org.drools.logger.KnowledgeRuntimeLogger;
import org.drools.logger.KnowledgeRuntimeLoggerFactory;

import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.rule.AgendaFilter;
import org.drools.runtime.rule.QueryResults;
import org.drools.runtime.rule.QueryResultsRow;

public class DroolsEssai {

    public static final void main(String[] args) {
        try {
        	
            KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
            
            System.setProperty("drools.dateformat", "dd-MM-yyyy");
            
            kbuilder.add(ResourceFactory.newClassPathResource("Test.drl", DroolsEssai.class), ResourceType.DRL);
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
            KnowledgeRuntimeLogger logger = KnowledgeRuntimeLoggerFactory.newFileLogger(ksession, "Test");

            //Test N.1
//            Message message = new Message();
//            message.setMessage("Hello World");
//            message.setStatus(Message.HELLO);
//            ksession.insert(message);
            
            //Test N.2
//            Customer customer = new Customer();
//            customer.setAge(25);
//            customer.setCity("Lille");
//            customer.setName("Yuchang");
//            customer.setGender("Homme");
//            
//            Order order1 = new Order();
//            order1.setCustomer(customer);
//            order1.setId(1234);
//            order1.setName("Commande Industrielle");
//            order1.setPrice(123.45);
//            
//            Order order2 = new Order();
//            order2.setCustomer(customer);
//            order2.setId(4321);
//            order2.setName("Commande Commerciale");
//            order2.setPrice(543.21); 
//            
//            Order order3 = new Order();
//            order3.setCustomer(null);
//            order3.setId(0);
//            order3.setName("Commande Inconnue");
//            order3.setPrice(0);             
//            
//            ArrayList<Order> orders = new ArrayList<Order>();
//            orders.add(order1);
//            orders.add(order2);
//            customer.setOrders(orders);
//            
//            ksession.setGlobal("orderNames", new String[]{"Commande Industrielle", "Commande Commerciale", "Commande Inconnue"});
//            ksession.setGlobal("orderNames2", new String[]{});
//            
//            ksession.insert(customer);
//            ksession.insert(order1);
//            //ksession.insert(order3);
            
            //Test N.3
//            ksession.getAgenda().getAgendaGroup("AG01").setFocus();
//            AgendaFilter filter = new TestAgendaFilter("*");
//            ksession.fireAllRules(filter); 
            
//            ksession.insert(new Customer());
            
            //Test N.4
//            ksession.insert(generateCustomer("Yuchang", 25, 21, "Homme"));
//            ksession.insert(generateCustomer("Jonathan", 30, 11, "Homme"));
//            ksession.insert(generateCustomer("Adrien", 24, 12, "Homme"));
            
//            Order order = new Order();
//            order.setName("Commande Individuelle bis");
//            
//            FactType addressFact = kbase.getFactType("com.sample", "Address"); //1er paramètre, à quoi ça sert? Package Name
//            Object add = addressFact.newInstance();
//            addressFact.set(add, "city", "Villeneuve D'ascq");
//            addressFact.set(add, "addressName", "Cité Scientifique");
//            
//            FactType personFact = kbase.getFactType("com.sample", "Person");
//            Object obj = personFact.newInstance();
//            personFact.set(obj, "name", "Yuchang");
//            personFact.set(obj, "order", order);
//            personFact.set(obj, "address", add);
//            
//            ksession.insert(obj);
        
            ksession.fireAllRules();
            
            QueryResults qr=ksession.getQueryResults("query fact count");
            System.out.println("customer objet count:"+qr.size()); 
            
            //QueryResults queryResults = ksession.getQueryResults("query test");
//            QueryResults queryResults = ksession.getQueryResults("query test 2", new Object[]{new Integer(30), "Femme"});
//            for(QueryResultsRow qrr : queryResults){
//            	Customer customer = (Customer)qrr.get("customer");
//            	System.out.println("customer name : "+customer.getName());
//            }
            
            System.out.println("current thread id:"+Thread.currentThread().getId());
            
            ksession.dispose();         
            logger.close();
            
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
    
    public static void afficherMsg(String msg){
    	System.out.println("(Msg bis) " + msg);
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
