package nts.uk.ctx.at.record.dom.mastercopy;

/**
 * @author locph
 *
 * The Interface CopyHandler.
 */
public interface DataCopyHandler {
    int REPLACE_ALL = 1;
    int ADD_NEW = 2;
    int DO_NOTHING = 0;
    /**
     * Do copy.
     */
    void doCopy();
}
