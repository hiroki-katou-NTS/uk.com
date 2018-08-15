package nts.uk.ctx.at.record.pub.mastercopy;

import nts.uk.ctx.at.record.dom.mastercopy.CopyMethod;

/**
 * @author locph
 */
public interface MasterCopyPub {
    /**
     * Do copy.
     */
    void doCopy(int copyMethod, String companyId);
}
