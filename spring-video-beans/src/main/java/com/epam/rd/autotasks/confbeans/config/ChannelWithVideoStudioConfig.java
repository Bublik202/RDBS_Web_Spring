package com.epam.rd.autotasks.confbeans.config;

import java.time.LocalDateTime;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.epam.rd.autotasks.confbeans.video.Channel;
import com.epam.rd.autotasks.confbeans.video.Video;
import com.epam.rd.autotasks.confbeans.video.VideoStudio;

@Configuration
public class ChannelWithVideoStudioConfig {
	@Bean
	public VideoStudio studio() {
		return new VideoStudio() {
			int num = 1;
			@Override
			public Video produce() {
				return new Video("Cat & Curious " + num,
						LocalDateTime.of(2001, 10, 18, 10, 00).plusYears(2 * (num++-1)));
			}
		};
	}
	
	@Bean
	public Channel channel() {
		Channel channel = new Channel();		
		for (int i = 0; i < 8; i++) {
			channel.addVideo(studio().produce());
		}
		return channel;		
	}
}
