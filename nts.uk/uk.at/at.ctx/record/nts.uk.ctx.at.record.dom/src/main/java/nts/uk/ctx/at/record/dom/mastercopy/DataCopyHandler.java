package nts.uk.ctx.at.record.dom.mastercopy;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.command.CommandProxy;
import nts.arc.layer.infra.data.query.QueryProxy;

import javax.persistence.EntityManager;

/**
 * @author locph
 *
 * The Interface CopyHandler.
 */
public abstract class DataCopyHandler extends JpaRepository {
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
