package cz.cvut.fel.zecmarce.semanticcriteriaapi.criteriavssoql.config;

import com.github.ledsoft.jopa.spring.transaction.DelegatingEntityManager;
import com.github.ledsoft.jopa.spring.transaction.JopaTransactionManager;
import cz.cvut.fel.zecmarce.semanticcriteriaapi.criteriavssoql.persistence.PersistenceFactory;
import cz.cvut.kbss.jopa.model.EntityManagerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@Import(PersistenceFactory.class)
public class PersistenceConfig {

    @Bean
    public DelegatingEntityManager entityManager() {
        return new WrappedDelegatingEntityManager();
    }

    @Bean(name = "txManager")
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf,
                                                         DelegatingEntityManager emProxy) {
        return new JopaTransactionManager(emf, emProxy);
    }
}
