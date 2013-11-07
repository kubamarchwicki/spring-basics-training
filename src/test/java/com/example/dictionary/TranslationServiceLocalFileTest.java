package com.example.dictionary;

import com.example.dictionary.TranslationServiceLocalFileTest.JavaConfiguration;
import com.example.dictionary.config.GenericTestConfiguration;
import com.example.dictionary.model.DictionaryWord;
import com.example.dictionary.model.TranslationProcess;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Properties;

import static org.junit.Assert.*;

@ContextConfiguration(classes = JavaConfiguration.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class TranslationServiceLocalFileTest {

	@Autowired
	TranslationService service;

	@Autowired
	BeanFactory factory;
	
	@Test
	public void bookTranslationTest() {
		TranslationProcess process = (TranslationProcess) factory.getBean(
				"translationProcess", new CommandParameters("search book"));
		process = service.getDictionaryWords(process);

		List<DictionaryWord> dictionaryWords = process.getWords();
		
		assertEquals(24, dictionaryWords.size());
		assertEquals("książka", dictionaryWords.get(1).getPolishWord());
	}
	
	
	@Configuration
	public static class JavaConfiguration extends GenericTestConfiguration {

		@Bean
		public PropertySourcesPlaceholderConfigurer serverConfiguration() {
			Properties props = new Properties();
			props.setProperty("urlStringTemplate", JavaConfiguration.class.getResource("/words/book.html").toExternalForm());
			
			PropertySourcesPlaceholderConfigurer p = new PropertySourcesPlaceholderConfigurer();
			p.setProperties(props);
			return p;
		}
		
	}
}
