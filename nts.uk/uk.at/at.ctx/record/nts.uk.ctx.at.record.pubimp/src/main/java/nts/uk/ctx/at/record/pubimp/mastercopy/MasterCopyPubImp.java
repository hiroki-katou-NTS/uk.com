package nts.uk.ctx.at.record.pubimp.mastercopy;

import nts.uk.ctx.at.record.dom.mastercopy.MasterCopyDataRepository;
import nts.uk.ctx.at.record.pub.mastercopy.MasterCopyPub;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * @author locph
 */
@Stateless
public class MasterCopyPubImp implements MasterCopyPub {
    @Inject
    MasterCopyDataRepository repoImp;

    @Override
    public void doCopy(String companyId, int copyMethod) {
        repoImp.doCopy(companyId, copyMethod);
    }
}
