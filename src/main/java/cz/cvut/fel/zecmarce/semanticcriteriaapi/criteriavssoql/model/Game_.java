package cz.cvut.fel.zecmarce.semanticcriteriaapi.criteriavssoql.model;

import cz.cvut.kbss.jopa.model.metamodel.SingularAttribute;
import cz.cvut.kbss.jopa.model.metamodel.StaticMetamodel;


import java.time.LocalDate;

@StaticMetamodel(Game.class)
public abstract class Game_ extends IdentifiableEntity_{
    public static volatile SingularAttribute<Game,String> name;
    public static volatile SingularAttribute<Game,String> comment;
    public static volatile SingularAttribute<Game,Developer> developer;
    public static volatile SingularAttribute<Game,LocalDate> releaseDate;
}
