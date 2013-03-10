package com.sample;

import java.util.Collection;

import org.drools.KnowledgeBase;
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

public class DroolsDSL {
	public static void main(String[] args) {
		
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
		kbuilder.add(ResourceFactory.newClassPathResource("DroolsDSL.dsl",DroolsDSL.class),ResourceType.DSL);
		kbuilder.add(ResourceFactory.newClassPathResource("TestDSL.dslr",DroolsDSL.class),ResourceType.DSLR);
		
		Collection<KnowledgePackage> kpackage = kbuilder.getKnowledgePackages();
		
	    KnowledgeBuilderErrors errors = kbuilder.getErrors();

	    if (errors.size() > 0) {
			for (KnowledgeBuilderError error : errors) {
				System.err.println(error);
			}
			throw new IllegalArgumentException("Could not parse knowledge.");
	    }
	    
	    KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
	    kbase.addKnowledgePackages(kpackage);
	    
	    StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();
	    KnowledgeRuntimeLogger logger = KnowledgeRuntimeLoggerFactory.newFileLogger(ksession, "DSL_Test");
	    
	    Customer customer = new Customer();
	    customer.setAge(25);
	    customer.setCity("Lille");
	    customer.setName("Yuchang");
	    customer.setGender("Homme");
	    
	    ksession.insert(customer);
	    
	    ksession.fireAllRules();
	    ksession.dispose();
	    logger.close();
	}

}
