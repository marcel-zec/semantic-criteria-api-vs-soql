package cz.cvut.fel.zecmarce.semanticcriteriaapi.criteriavssoql.config;

import com.github.ledsoft.jopa.spring.transaction.DelegatingEntityManager;


import cz.cvut.kbss.jopa.model.EntityManager;
import cz.cvut.kbss.jopa.model.query.TypedQuery;
import cz.cvut.kbss.jopa.model.query.criteria.CriteriaQuery;
import cz.cvut.kbss.jopa.sessions.CriteriaBuilder;

public class WrappedDelegatingEntityManager extends DelegatingEntityManager implements EntityManager {
    @Override
    public <T> TypedQuery<T> createQuery(CriteriaQuery<T> criteriaQuery) {
        return getTransactionalDelegate().createQuery(criteriaQuery);
    }

    @Override
    public CriteriaBuilder getCriteriaBuilder() {
        return getTransactionalDelegate().getCriteriaBuilder();
    }
}
