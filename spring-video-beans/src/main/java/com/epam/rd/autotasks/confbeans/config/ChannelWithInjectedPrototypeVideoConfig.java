package com.epam.rd.autotasks.confbeans.config;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.epam.rd.autotasks.confbeans.video.Channel;
import com.epam.rd.autotasks.confbeans.video.Video;

@Configuration
public class ChannelWithInjectedPrototypeVideoConfig extends Channel{
	private static LocalDateTime release = LocalDateTime.now();

    @Bean
    @Scope("prototype")
    public Video videoPrototype() {    	
    	release = release.plusDays(1);
        return new Video("Cat Failure Compilation", release);
    }

    @Override
    public Stream<Video> videos() {
        Channel channel = new Channel();
        for (int i = 0; i < 7; i++) {
            channel.addVideo(videoPrototype());
        }
        return channel.videos();
    }
}
