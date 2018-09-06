package nts.uk.ctx.at.record.pubimp.mastercopy;

import nts.uk.ctx.at.record.dom.mastercopy.CopyErAlWorkRecordRepository;
import nts.uk.ctx.at.record.pub.mastercopy.CopyRecordPub;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * @author locph
 */
@Stateless
public class CopyRecordPubImp implements CopyRecordPub {
    @Inject
    CopyErAlWorkRecordRepository repoImp;

    @Override
    public void doCopy(String companyId, int copyMethod) {
        repoImp.doCopy(companyId, copyMethod);
    }
}
