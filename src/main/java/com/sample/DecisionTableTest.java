package com.sample;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.DecisionTableConfiguration;
import org.drools.builder.DecisionTableInputType;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderErrors;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.logger.KnowledgeRuntimeLogger;
import org.drools.logger.KnowledgeRuntimeLoggerFactory;
import org.drools.runtime.StatefulKnowledgeSession;

public class DecisionTableTest {

    public static final void main(String[] args) {
        try {
            KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
            
            DecisionTableConfiguration config = KnowledgeBuilderFactory.newDecisionTableConfiguration();
            config.setInputType(DecisionTableInputType.XLS);
            
            kbuilder.add(ResourceFactory.newClassPathResource("Essai.xls"), ResourceType.DTABLE, config);
            
            KnowledgeBuilderErrors errors = kbuilder.getErrors();
            if (errors.size() > 0) {
                for (KnowledgeBuilderError error: errors) {
                    System.err.println(error);
                }
                throw new IllegalArgumentException("Could not parse knowledge.");
            }
            
            KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
            kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());
            
            StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();
            KnowledgeRuntimeLogger logger = KnowledgeRuntimeLoggerFactory.newFileLogger(ksession, "test");
          
            Customer cus1 = new Customer();
            cus1.setName("Yuchang");
            cus1.setAge(12);

            Customer cus2 = new Customer();
            cus2.setName("Jonathan");
            cus2.setAge(27);            
            
            ksession.insert(cus1);
            ksession.insert(cus2);
            ksession.fireAllRules();
            
            logger.close();
            
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }


}
