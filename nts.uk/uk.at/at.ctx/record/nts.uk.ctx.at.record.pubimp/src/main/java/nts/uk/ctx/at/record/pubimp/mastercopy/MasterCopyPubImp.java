package nts.uk.ctx.at.record.pubimp.mastercopy;

import nts.uk.ctx.at.record.infra.repository.mastercopy.handler.JpaMasterCopyDataRepoImp;
import nts.uk.ctx.at.record.pub.mastercopy.MasterCopyPub;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * @author locph
 */
@Stateless
public class MasterCopyPubImp implements MasterCopyPub {
    @Inject
    JpaMasterCopyDataRepoImp repoImp;

    @Override
    public void doCopy(String companyId, int copyMethod) {
        repoImp.doCopy(companyId, copyMethod);
    }
}
