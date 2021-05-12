package cz.cvut.fel.zecmarce.semanticcriteriaapi.criteriavssoql.model;


import cz.cvut.kbss.jopa.model.metamodel.Identifier;
import cz.cvut.kbss.jopa.model.metamodel.SingularAttribute;
import cz.cvut.kbss.jopa.model.metamodel.StaticMetamodel;

import java.net.URI;

@StaticMetamodel(IdentifiableEntity.class)
public abstract class IdentifiableEntity_ {
    public static volatile Identifier<IdentifiableEntity, URI> uri;
}
