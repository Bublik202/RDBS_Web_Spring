package com.epam.rd.autotasks.confbeans.config;

import java.time.LocalDateTime;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.epam.rd.autotasks.confbeans.video.Channel;
import com.epam.rd.autotasks.confbeans.video.Video;

@Configuration
public class ChannelWithPhantomVideoStudioConfig {
	private int num = 1;
	
	@Bean
    @Scope("prototype")
    public Video videoPrototype() {			
		return new Video("Cat & Curious " + num,
				LocalDateTime.of(2001, 10, 18, 10, 00).plusYears(2 * (num++-1)));
	}		
	
	@Bean
	public Channel channel() {
		Channel channel = new Channel();		
		for (int i = 0; i < 8; i++) {
			channel.addVideo(videoPrototype());
		}
		return channel;		
	}
}
