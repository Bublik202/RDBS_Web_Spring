package com.rd.epam.autotasks.scopes.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;

public class ThreadScope implements Scope {
	
	private Map<String, Object> beanInstances = Collections.synchronizedMap(new HashMap<>());
	private List<Thread> threads = Collections.synchronizedList(new ArrayList<>());
	
	@Override
	public Object get(String name, ObjectFactory<?> objectFactory) {
		
		Thread thread = Thread.currentThread();
		if(threads.contains(thread)) {
			return beanInstances.get(name);
		}else {
			threads.add(thread);
			beanInstances.put(name, objectFactory.getObject());
		}
		
		return beanInstances.get(name);
	}

	@Override
	public Object remove(String name) {
		return null;
	}

	@Override
	public void registerDestructionCallback(String name, Runnable callback) {
	}

	@Override
	public Object resolveContextualObject(String key) {
		return null;
	}

	@Override
	public String getConversationId() {
		return "thread";
	}
}
