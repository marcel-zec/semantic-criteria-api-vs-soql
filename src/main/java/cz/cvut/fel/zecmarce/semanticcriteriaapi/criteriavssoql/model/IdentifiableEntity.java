package cz.cvut.fel.zecmarce.semanticcriteriaapi.criteriavssoql.model;

import cz.cvut.kbss.jopa.model.annotations.Id;
import cz.cvut.kbss.jopa.model.annotations.MappedSuperclass;

import java.io.Serializable;
import java.net.URI;

@MappedSuperclass
abstract public class IdentifiableEntity implements Serializable {

    @Id
    protected URI uri;

    public URI getUri() {
        return uri;
    }

    public void setUri(URI uri) {
        this.uri = uri;
    }
}
