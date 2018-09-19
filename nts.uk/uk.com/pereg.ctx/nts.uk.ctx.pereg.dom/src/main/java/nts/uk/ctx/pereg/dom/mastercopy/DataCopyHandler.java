package nts.uk.ctx.pereg.dom.mastercopy;

/**
 * @author locph
 */

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.command.CommandProxy;
import nts.arc.layer.infra.data.query.QueryProxy;

import javax.persistence.EntityManager;

/**
 * The Interface CopyHandler.
 */
public abstract class DataCopyHandler {
    protected final int REPLACE_ALL = 1;
    protected final int ADD_NEW = 2;
    protected final int DO_NOTHING = 0;
    /**
     *
     */
    protected EntityManager entityManager;

    /**
     *
     */
    protected int copyMethod;

    /**
     *
     */
    protected String companyId;

    /**
     *
     */
    protected QueryProxy queryProxy;

    /**
     *
     */
    protected CommandProxy commandProxy;

    /**
     * Do copy.
     */
    public abstract void doCopy();
}
