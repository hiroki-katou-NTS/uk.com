package nts.uk.ctx.at.record.pubimp.mastercopy;

import nts.uk.ctx.at.record.dom.mastercopy.DataCopyHandler;
import nts.uk.ctx.at.record.infra.repository.mastercopy.handler.KwrmtErAlWorkRecordCopyHandler;
import nts.uk.ctx.at.record.pub.mastercopy.MasterCopyPub;

/**
 * @author locph
 */
public class MasterCopyPubImp implements MasterCopyPub {
    @Override
    public void doCopy(String companyId, int copyMethod) {
        DataCopyHandler copyHandler = new KwrmtErAlWorkRecordCopyHandler(copyMethod, companyId);
        copyHandler.doCopy();
    }
}
