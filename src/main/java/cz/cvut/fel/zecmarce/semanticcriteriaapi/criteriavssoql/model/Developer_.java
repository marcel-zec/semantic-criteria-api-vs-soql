package cz.cvut.fel.zecmarce.semanticcriteriaapi.criteriavssoql.model;

import cz.cvut.kbss.jopa.model.annotations.OWLAnnotationProperty;
import cz.cvut.kbss.jopa.model.annotations.OWLDataProperty;
import cz.cvut.kbss.jopa.model.metamodel.SingularAttribute;
import cz.cvut.kbss.jopa.model.metamodel.StaticMetamodel;
import cz.cvut.kbss.jopa.vocabulary.RDFS;

import java.net.URI;
import java.time.LocalDate;
import java.util.Set;

@StaticMetamodel(Developer.class)
public abstract class Developer_ extends IdentifiableEntity_{
    public static volatile SingularAttribute<Developer,String> name;
    public static volatile SingularAttribute<Developer,String> comment;
    public static volatile SingularAttribute<Developer,Integer> employeeCount;
}
