package com.sample;
 
import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderErrors;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.logger.KnowledgeRuntimeLogger;
import org.drools.logger.KnowledgeRuntimeLoggerFactory;
import org.drools.runtime.StatefulKnowledgeSession;

public class ProcessTest {

    public static final void main(String[] args) {
        try {
        	
            KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
            kbuilder.add(ResourceFactory.newClassPathResource("EmployeeRule.drl"), ResourceType.DRL);
            kbuilder.add(ResourceFactory.newClassPathResource("ruleflow.rf"), ResourceType.DRF);
             
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
            KnowledgeRuntimeLogger logger = KnowledgeRuntimeLoggerFactory.newFileLogger(ksession, "RF_Test");
            
            Employee employee = new Employee();
            employee.setEduInfo("master");
            employee.setResume("tech");
            employee.setAnnualExam("good");
            employee.setAwardPunish("award");
            
            ksession.insert(employee);
            
            ksession.startProcess("com.sample.ruleflow");
            ksession.fireAllRules();
            
            System.out.println("BasicSalary: " + employee.getBasicSalary());
            System.out.println("DutySalary: " + employee.getDutySalary());
            System.out.println("Bonus : " + employee.getBonus());
            System.out.println("Rate : " + employee.getPercent());
            System.out.printf("TotalSalary: %.0f" , employee.getTotalSalary());           
  
            logger.close();
            
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}