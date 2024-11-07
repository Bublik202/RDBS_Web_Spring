package com.rd.epam.autotasks.scopes.config;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;

public class JustASecondScope implements Scope {
	private final Map<String, Object> beanInstances = Collections.synchronizedMap(new HashMap<>());
	private final Map<String, Long> expireTime = Collections.synchronizedMap(new HashMap<>());
	
	@Override
	public Object get(String name, ObjectFactory<?> objectFactory) {
		long currentTime = System.currentTimeMillis();
        long expirationTime = expireTime.getOrDefault(name, 0L);
		
		if(currentTime > expirationTime || !beanInstances.containsKey(name)) {
			expireTime.put(name, currentTime + TimeUnit.SECONDS.toMillis(1));
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
		return "justASecond";
	}
}
