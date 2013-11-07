package com.example.dictionary.config;

import com.example.dictionary.CommandParameters;
import com.example.dictionary.TranslationService;
import com.example.dictionary.file.FileService;
import com.example.dictionary.model.TranslationProcess;
import com.example.dictionary.repositories.InMemoryRepository;
import com.example.dictionary.repositories.Repository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

public class GenericTestConfiguration {

	@Bean
	@Qualifier("jpa")
	public Repository repository() {
		return new InMemoryRepository();
	}

    @Bean
    @Qualifier("jpaTxMgr")
    public PlatformTransactionManager txManager() {
        return new PlatformTransactionManager() {
            @Override
            public TransactionStatus getTransaction(TransactionDefinition definition) throws TransactionException {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public void commit(TransactionStatus status) throws TransactionException {
            }

            @Override
            public void rollback(TransactionStatus status) throws TransactionException {
            }
        };
    }
	
	@Bean
	public LocalValidatorFactoryBean validator() {
		return new LocalValidatorFactoryBean();
	}
	
	@Bean
	@Scope(BeanDefinition.SCOPE_PROTOTYPE)
	public TranslationProcess translationProcess(CommandParameters params) {
		return new TranslationProcess(params);
	}

	@Bean
	public TranslationService service() {
		return new TranslationService();
	}

    @Bean
    public FileService fileService() {
        return new FileService();
    }

}
