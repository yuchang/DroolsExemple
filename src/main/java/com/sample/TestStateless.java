//
//package com.sample;
//
//import java.util.ArrayList;
//import java.util.Collection;
//
//import org.drools.KnowledgeBase;
//import org.drools.KnowledgeBaseConfiguration;
//import org.drools.KnowledgeBaseFactory;
//import org.drools.builder.KnowledgeBuilder;
//import org.drools.builder.KnowledgeBuilderFactory;
//import org.drools.builder.ResourceType;
//import org.drools.command.CommandFactory;
//import org.drools.definition.KnowledgePackage;
//import org.drools.io.ResourceFactory;
//import org.drools.logger.KnowledgeRuntimeLogger;
//import org.drools.logger.KnowledgeRuntimeLoggerFactory;
//import org.drools.runtime.StatelessKnowledgeSession;
//
//public class TestStateless {
//    public static final void main(String[] args) {
//    	
//    	KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
//    	kbuilder.add(ResourceFactory.newClassPathResource("TestStateless.drl",TestStateless.class), ResourceType.DRL);
//    	
//    	Collection<KnowledgePackage> kpackage = kbuilder.getKnowledgePackages();
//    	
//    	KnowledgeBaseConfiguration kbConf = KnowledgeBaseFactory.newKnowledgeBaseConfiguration();
//    	kbConf.setProperty("org.drools.sequential", "false");
//    	
//    	KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase(kbConf);
//    	kbase.addKnowledgePackages(kpackage);
//    	
//    	StatelessKnowledgeSession statelessKSession=kbase.newStatelessKnowledgeSession();
//    	KnowledgeRuntimeLogger logger = KnowledgeRuntimeLoggerFactory.newFileLogger(statelessKSession, "TestStateless");
//    	
//    	ArrayList<Message> list=new ArrayList<Message>();
//        Message message = new Message();
//        message.setMessage("Hello World!!!");
//        message.setStatus(Message.HELLO);
//    	list.add(message);
//    	statelessKSession.execute(list);
//    	
////    	list.add(CommandFactory.newInsert(new Object()));
////    	list.add(CommandFactory.newInsert(new Object()));
////    	list.add(CommandFactory.newSetGlobal("key1", new Object()));
////    	list.add(CommandFactory.newSetGlobal("key2", new Object()));
////    	statelessKSession.execute(CommandFactory.newBatchExecution(list));
//
//    	logger.close();
//    }
//}
