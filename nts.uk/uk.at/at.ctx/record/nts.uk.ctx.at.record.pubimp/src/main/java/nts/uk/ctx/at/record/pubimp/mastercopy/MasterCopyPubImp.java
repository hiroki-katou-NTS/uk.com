package nts.uk.ctx.at.record.pubimp.mastercopy;

import nts.uk.ctx.at.record.dom.mastercopy.CopyMethod;
import nts.uk.ctx.at.record.dom.mastercopy.DataCopyHandler;
import nts.uk.ctx.at.record.infra.repository.mastercopy.handler.KwrmtErAlWorkRecordCopyHandler;
import nts.uk.ctx.at.record.pub.mastercopy.MasterCopyPub;

import javax.ejb.Stateless;

/**
 * @author locph
 */
@Stateless
public class MasterCopyPubImp implements MasterCopyPub {
    @Override
    public void doCopy(int copyMethod, String companyId) {
        DataCopyHandler copyHandler = new KwrmtErAlWorkRecordCopyHandler(copyMethod, companyId);
        copyHandler.doCopy();
    }
}
