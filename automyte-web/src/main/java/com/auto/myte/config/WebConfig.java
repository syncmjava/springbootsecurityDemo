package com.auto.myte.config;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.auto.myte.common.interceptor.AuthSessionInterceptor;
import com.auto.myte.common.interceptor.ErrorInterceptor;

@EnableWebMvc
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

	private static final String[] CLASSPATH_RESOURCE_LOCATIONS = { "classpath:/WEB-INF/static/",
			"classpath:/resources/", "classpath:/static/", "classpath:/public/" };

	@Bean(name = "multipartResolver")
	public CommonsMultipartResolver getResolver() throws IOException {
		CommonsMultipartResolver resolver = new CommonsMultipartResolver();

		// Set the maximum allowed size (in bytes) for each individual file.
		resolver.setMaxUploadSizePerFile(5242880);// 5MB

		return resolver;
	}

	/**
	 * filter 設定
	 * 
	 */
	// @Bean
	// public FilterRegistrationBean filterRegistration() {
	//
	// FilterRegistrationBean registration = new FilterRegistrationBean();
	//// registration.setFilter(new SecurityFilter());
	//// registration.addUrlPatterns("/*");
	//// registration.addInitParameter("paramName", "paramValue");
	//// registration.setName("LoginFilter");
	//// registration.setOrder(1);
	// return registration;
	// }

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// addPathPatterns ルート追加
		registry.addInterceptor(new ErrorInterceptor()).addPathPatterns("/**");
		registry.addInterceptor(new AuthSessionInterceptor()).addPathPatterns("/**");
		super.addInterceptors(registry);
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**").addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS);
		super.addResourceHandlers(registry);
	}

	

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
    }
}
