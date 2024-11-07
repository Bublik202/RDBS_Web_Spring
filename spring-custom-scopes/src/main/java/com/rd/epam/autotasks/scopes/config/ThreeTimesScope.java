package com.rd.epam.autotasks.scopes.config;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;

public class ThreeTimesScope implements Scope {
	private Map<String, Runnable> destructionCallbacks = Collections.synchronizedMap(new HashMap<>());	
	private final Map<String, Integer> beanCountMap = Collections.synchronizedMap(new HashMap<>());
	private final Map<String, Object> beanInstances = Collections.synchronizedMap(new HashMap<>());	

	@Override
	public Object get(String name, ObjectFactory<?> objectFactory) {		
		int count = beanCountMap.getOrDefault(name, 0);
		if(count < 3) {			
			beanCountMap.put(name, count + 1);		
			return beanInstances.computeIfAbsent(name, k -> objectFactory.getObject());			
		} else {
			 beanCountMap.put(name, 1);			 
			 beanInstances.put(name, objectFactory.getObject());	
			
			 return beanInstances.get(name);	
		}
		
	}
	
	@Override
	public Object remove(String name) {	
		destructionCallbacks.remove(name);
		return beanInstances.remove(name);
	}
	
	@Override
	public void registerDestructionCallback(String name, Runnable callback) {
		destructionCallbacks.put(name, callback);
	}

	@Override
	public Object resolveContextualObject(String key) {
		return null;
	}

	@Override
	public String getConversationId() {
		return "threeTimes";
	}
}
