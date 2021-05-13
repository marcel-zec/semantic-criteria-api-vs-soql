package cz.cvut.fel.zecmarce.semanticcriteriaapi.criteriavssoql;

import cz.cvut.fel.zecmarce.semanticcriteriaapi.criteriavssoql.model.*;
import cz.cvut.fel.zecmarce.semanticcriteriaapi.criteriavssoql.persistence.GameRepository;
import cz.cvut.kbss.jopa.model.EntityManager;
import cz.cvut.kbss.jopa.model.query.TypedQuery;
import cz.cvut.kbss.jopa.model.query.criteria.CriteriaQuery;
import cz.cvut.kbss.jopa.model.query.criteria.ParameterExpression;
import cz.cvut.kbss.jopa.model.query.criteria.Predicate;
import cz.cvut.kbss.jopa.model.query.criteria.Root;
import cz.cvut.kbss.jopa.sessions.CriteriaBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ComparisonService implements ApplicationListener<ApplicationReadyEvent> {
    private static final Logger LOG = LoggerFactory.getLogger(ComparisonService.class);

    private final EntityManager em;
    private final GameRepository gr;
    private int failed = 0;
    private int amount = 0;

    @Autowired
    public ComparisonService(EntityManager em, GameRepository gr) {
        this.em = em;
        this.gr = gr;
    }

    @Transactional
    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        amount++;
        if (!comparison1()) failed++;

        amount++;
        if (!comparison2()) failed++;

        amount++;
        if (!comparison3()) failed++;

        amount++;
        if (!comparison4()) failed++;

        amount++;
        if (!comparison5()) failed++;

        amount++;
        if (!comparison6()) failed++;

        amount++;
        if (!comparison7()) failed++;

        System.out.println("\nAMOUNT OF COMPARISON: " + amount);
        System.out.println("FAILED: " + failed);
        System.out.println("PASSED: " + (amount - failed) + "\n");
    }

    private boolean comparison1(){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Game> query = cb.createQuery(Game.class);
        Root<Game> game = query.from(Game.class);
        query.select(game);
        TypedQuery<Game> typedQuery = em.createQuery(query);
        final List<Game> criteriaResults = typedQuery.getResultList();

        final List<Game> soqlResults = em.createQuery("SELECT g FROM Game g", Game.class).getResultList();

        boolean result = compare(criteriaResults,soqlResults, "simply all");
        System.out.println("Criteria result: " + criteriaResults);
        System.out.println("SOQL result:     " + soqlResults);
        System.out.println("----------------------------------------------");
        return result;
    }

    private boolean comparison2(){
        final String parameterValue = "Stronghold 3";

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Game> query = cb.createQuery(Game.class);
        Root<Game> game = query.from(Game.class);
        ParameterExpression<String> name = cb.parameter(String.class);
        query.select(game).where(cb.equal(game.getAttr(Game_.name), name));
        TypedQuery<Game> typedQuery = em.createQuery(query).setParameter(name,parameterValue,"en");
        final Game criteriaResults = typedQuery.getSingleResult();

        final Game soqlResults = em.createQuery("SELECT g FROM Game g WHERE g.name = :name", Game.class).setParameter("name", parameterValue, "en").getSingleResult();

        boolean result = compare(criteriaResults,soqlResults, "simple where");
        System.out.println("Criteria result: " + criteriaResults);
        System.out.println("SOQL result:     " + soqlResults);
        System.out.println("----------------------------------------------");
        return result;
    }

    private boolean comparison3(){
        final String parameterValue = "Stronghold 3";

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Game> query = cb.createQuery(Game.class);
        Root<Game> game = query.from(Game.class);
        ParameterExpression<String> name = cb.parameter(String.class);
        query.select(game).where(cb.not(cb.equal(game.getAttr(Game_.name), name)));
        TypedQuery<Game> typedQuery = em.createQuery(query).setParameter(name,parameterValue,"en");
        final List<Game> criteriaResults = typedQuery.getResultList();

        final List<Game> soqlResults = em.createQuery("SELECT g FROM Game g WHERE NOT g.name = :name", Game.class).setParameter("name", parameterValue, "en").getResultList();

        boolean result = compare(criteriaResults,soqlResults, "where equal negated");
        System.out.println("Criteria result: " + criteriaResults);
        System.out.println("SOQL result:     " + soqlResults);
        System.out.println("----------------------------------------------");
        return result;
    }

    private boolean comparison4(){
        final Integer amountValue = 15;

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Game> query = cb.createQuery(Game.class);
        Root<Game> game = query.from(Game.class);
        ParameterExpression<Integer> amount = cb.parameter(Integer.class);
        query.select(game).where(cb.lessThanOrEqual(game.getAttr(Game_.developer).getAttr(Developer_.employeeCount), amount));
        TypedQuery<Game> typedQuery = em.createQuery(query).setParameter(amount,amountValue);
        final List<Game> criteriaResults = typedQuery.getResultList();

        final List<Game> soqlResults = em.createQuery("SELECT g FROM Game g WHERE g.developer.employeeCount <= :amount", Game.class).setParameter("amount", amountValue).getResultList();

        boolean result = compare(criteriaResults,soqlResults, "path joins + <");
        System.out.println("Criteria result: " + criteriaResults);
        System.out.println("SOQL result:     " + soqlResults);
        System.out.println("----------------------------------------------");
        return result;
    }

    private boolean comparison5(){
        final Integer parameterValue = 50;

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Integer> query = cb.createQuery(Integer.class);
        Root<Developer> developer = query.from(Developer.class);
        ParameterExpression<Integer> amount = cb.parameter(Integer.class);
        query.select(cb.count(developer)).where(cb.greaterThanOrEqual(developer.getAttr(Developer_.employeeCount), amount));
        TypedQuery<Integer> typedQuery = em.createQuery(query).setParameter(amount, parameterValue);
        final Integer criteriaResults = typedQuery.getSingleResult();

        final Integer soqlResults = em.createQuery("SELECT COUNT(d) FROM Developer d WHERE d.employeeCount >= :amount", Integer.class).setParameter("amount", parameterValue).getSingleResult();

        boolean result = compare(criteriaResults,soqlResults, "count + >=");
        System.out.println("Criteria result: " + criteriaResults);
        System.out.println("SOQL result:     " + soqlResults);
        System.out.println("----------------------------------------------");
        return result;
    }

    private boolean comparison6(){
        final String nameParameterValue = "Studio";
        final Integer amountParameterValue = 500;

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Developer> query = cb.createQuery(Developer.class);
        Root<Developer> developer = query.from(Developer.class);
        ParameterExpression<String> name = cb.parameter(String.class);
        ParameterExpression<Integer> amount = cb.parameter(Integer.class);
        Predicate nameRestriction = cb.like(developer.getAttr(Developer_.name), name);
        Predicate amountRestriction = cb.lessThan(developer.getAttr(Developer_.employeeCount),amount);
        query.select(developer).where(nameRestriction,amountRestriction);
        TypedQuery<Developer> typedQuery = em.createQuery(query).setParameter(name, nameParameterValue).setParameter(amount, amountParameterValue);
        final List<Developer> criteriaResults = typedQuery.getResultList();

        final List<Developer> soqlResults = em.createQuery("SELECT d FROM Developer d WHERE d.employeeCount < :amount AND d.name LIKE :name", Developer.class)
                .setParameter("amount", amountParameterValue).setParameter("name",nameParameterValue).getResultList();

        boolean result = compare(criteriaResults,soqlResults, "like + and");
        System.out.println("Criteria result: " + criteriaResults);
        System.out.println("SOQL result:     " + soqlResults);
        System.out.println("----------------------------------------------");
        return result;
    }

    private boolean comparison7(){
        final Integer value1 = 1000;
        final Integer value2 = 10;

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Developer> query = cb.createQuery(Developer.class);
        Root<Developer> developer = query.from(Developer.class);
        ParameterExpression<Integer> param1 = cb.parameter(Integer.class);
        ParameterExpression<Integer> param2 = cb.parameter(Integer.class);
        Predicate firstRestricion = cb.greaterThanOrEqual(developer.getAttr(Developer_.employeeCount), param1);
        Predicate secondRestriction = cb.lessThanOrEqual(developer.getAttr(Developer_.employeeCount), param2);
        Predicate restrictions = cb.or(firstRestricion,secondRestriction);
        query.select(developer).where(restrictions)
                .orderBy(cb.desc(developer.getAttr(Developer_.employeeCount)),cb.asc(developer.getAttr(Developer_.name)));
        TypedQuery<Developer> typedQuery = em.createQuery(query)
                .setParameter(param1, value1).setParameter(param2, value2);
        final List<Developer> criteriaResults = typedQuery.getResultList();

        final List<Developer> soqlResults = em.createQuery("SELECT d FROM Developer d WHERE d.employeeCount >= :param1 OR d.employeeCount <= :param2 ORDER BY d.employeeCount DESC, d.name ASC" , Developer.class)
                .setParameter("param1", value1).setParameter("param2",value2).getResultList();

        boolean result = compareWithOrder(criteriaResults,soqlResults, "or + order");
        System.out.println("Criteria result: " + criteriaResults);
        System.out.println("SOQL result:     " + soqlResults);
        System.out.println("----------------------------------------------");
        return result;
    }

    private boolean compare(List<? extends IdentifiableEntity> x, List<? extends IdentifiableEntity> y, String methodName){
        System.out.println("\n----- " + methodName + " -----");
        if (x.size() == y.size()){
            for (IdentifiableEntity e: x) {
                if (!y.stream().anyMatch(item -> item.getUri().equals(e.getUri()))){
                    System.out.println("--- DIFFERENT RESULTS -----------------------");
                    return false;
                }
            }
            System.out.println("--- SAME RESULTS ----------------------------");
            return true;
        } else {
            System.out.println("--- DIFFERENT RESULTS -----------------------");
            return false;
        }
    }

    private boolean compareWithOrder(List<? extends IdentifiableEntity> x, List<? extends IdentifiableEntity> y, String methodName){
        System.out.println("\n----- " + methodName + " -----");
        if (x.size() == y.size()){
            for (int i = 0; i < x.size(); i++) {
                if (!x.get(i).getUri().equals(y.get(i).getUri())){
                    System.out.println("--- DIFFERENT RESULTS -----------------------");
                    return false;
                }
            }
            System.out.println("--- SAME RESULTS ----------------------------");
            return true;
        } else {
            System.out.println("--- DIFFERENT RESULTS -----------------------");
            return false;
        }
    }

    private boolean compare(IdentifiableEntity x, IdentifiableEntity y, String methodName){
        System.out.println("\n----- " + methodName + " -----");
        if (x.getUri().equals(y.getUri())){
            System.out.println("--- SAME RESULTS ----------------------------");
            return true;
        } else {
            System.out.println("--- DIFFERENT RESULTS -----------------------");
            return false;
        }

    }

    private boolean compare(Integer x, Integer y, String methodName){
        System.out.println("\n----- " + methodName + " -----");
        if (x.equals(y)){
            System.out.println("--- SAME RESULTS ----------------------------");
            return true;
        } else {
            System.out.println("--- DIFFERENT RESULTS -----------------------");
            return false;
        }
    }


}
