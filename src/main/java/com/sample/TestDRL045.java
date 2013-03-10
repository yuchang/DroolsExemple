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
import org.drools.definition.type.FactType;
import org.drools.io.ResourceFactory;
import org.drools.logger.KnowledgeRuntimeLogger;
import org.drools.logger.KnowledgeRuntimeLoggerFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.rule.QueryResults;
import org.drools.runtime.rule.QueryResultsRow;

public class TestDRL045 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
        try {
        	
            KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        
            kbuilder.add(ResourceFactory.newClassPathResource("TestDRL045.drl", TestDRL045.class), ResourceType.DRL);
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
            KnowledgeRuntimeLogger logger = KnowledgeRuntimeLoggerFactory.newFileLogger(ksession, "TestDRL045");
            
            Order order = new Order();
			order.setName("Commande Individuelle bis");

			FactType addressFact = kbase.getFactType("com.sample", "Address"); 
			Object add = addressFact.newInstance();
			addressFact.set(add, "city", "Villeneuve D'ascq");
			addressFact.set(add, "addressName", "Cité Scientifique");

			FactType personFact = kbase.getFactType("com.sample", "Person");
			Object obj = personFact.newInstance();
			personFact.set(obj, "name", "Yuchang");
			personFact.set(obj, "order", order);
			personFact.set(obj, "address", add);

			ksession.insert(obj);      
            
            ksession.fireAllRules();
            
             
            ksession.dispose();         
            logger.close();
            
        }catch (Throwable t) {
            t.printStackTrace();
        }
        
	}
	
}
