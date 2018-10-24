/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pereg.dom.mastercopy;

import java.util.Collections;
import java.util.Map;

import javax.persistence.EntityManager;

import nts.arc.layer.infra.data.command.CommandProxy;
import nts.arc.layer.infra.data.query.QueryProxy;

/**
 * The Class DataCopyHandler.
 */
public abstract class DataCopyHandler {
    
    /** The replace all. */
    protected final int REPLACE_ALL = 1;
    
    /** The add new. */
    protected final int ADD_NEW = 2;
    
    /** The do nothing. */
    protected final int DO_NOTHING = 0;
    
    /** The entity manager. */
    protected EntityManager entityManager;

    /** The copy method. */
    protected int copyMethod;

    /** The company id. */
    protected String companyId;

    /** The query proxy. */
    protected QueryProxy queryProxy;

    /** The command proxy. */
    protected CommandProxy commandProxy;
    
    /** The transfer id map. */
    protected Map<String, String> transferIdMap = Collections.emptyMap();

    /**
     * Do copy.
     */
    public abstract Map<String, String> doCopy();
}
