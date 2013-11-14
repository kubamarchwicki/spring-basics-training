package com.example.dictionary.validation;

import com.example.dictionary.CommandParameters;
import com.example.dictionary.TranslationProcess;
import com.example.dictionary.commands.Command;
import com.example.dictionary.commands.SaveWordsCommand;
import com.example.dictionary.config.GenericTestConfiguration;
import com.example.dictionary.model.DictionaryWord;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.validation.ConstraintViolation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

@ContextConfiguration(classes = SaveCommandValidationTest.Config.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class SaveCommandValidationTest {

	@Autowired
	BeanFactory factory;

	@Test
	public void emptyTranslationList() {
		TranslationProcess process = TranslationProcess.fromCommandParameters(new CommandParameters("save 0"));
		process.setWords(new ArrayList<DictionaryWord>());
		
		SaveWordsCommand service = (SaveWordsCommand) factory.getBean(
				"saveWordsCommand", process);

		Set<ConstraintViolation<? extends Command>> errors = service.getErrors();
		assertThat(errors.size(), is(equalTo(1)));
		assertThat(errors.iterator().next().getMessage(), is(equalTo("{com.example.dictionary.validation.ValidSearchParameters.message}")));
	}

	@Test
	public void nullTranslationList() {
		TranslationProcess process = TranslationProcess.fromCommandParameters(new CommandParameters("save 0"));
		process.setWords(null);
		
		SaveWordsCommand service = (SaveWordsCommand) factory.getBean(
				"saveWordsCommand", process);

		Set<ConstraintViolation<? extends Command>> errors = service.getErrors();
		assertThat(errors.size(), is(equalTo(1)));
		assertThat(errors.iterator().next().getMessage(), is(equalTo("{com.example.dictionary.validation.ValidSearchParameters.message}")));
	}

	
	@Test
	public void validCommand() {
		TranslationProcess process = TranslationProcess.fromCommandParameters(new CommandParameters("save 0"));
		process.setWords(Arrays.asList(DictionaryWord.fromPolishWord("polishWord").withEnglishWord("englishWord").build()));
		
		SaveWordsCommand service = (SaveWordsCommand) factory.getBean(
				"saveWordsCommand", process);

		Set<ConstraintViolation<? extends Command>> errors = service.getErrors();
		assertThat(errors.isEmpty(), is(true));
	}

	@Configuration
	public static class Config extends GenericTestConfiguration {

		@Bean 
		@Scope(value=BeanDefinition.SCOPE_PROTOTYPE)
		public SaveWordsCommand saveWordsCommand(TranslationProcess process) {
			return new SaveWordsCommand(process);
		}
		
	}
	
}
