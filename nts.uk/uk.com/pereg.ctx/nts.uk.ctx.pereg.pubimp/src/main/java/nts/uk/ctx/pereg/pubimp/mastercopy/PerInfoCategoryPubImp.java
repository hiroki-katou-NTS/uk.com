package nts.uk.ctx.pereg.pubimp.mastercopy;

import nts.uk.ctx.pereg.dom.mastercopy.MasterCopyDataRepository;
import nts.uk.ctx.pereg.pub.mastercopy.PerInfoCategoryPub;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * @author locph
 */
@Stateless
public class PerInfoCategoryPubImp implements PerInfoCategoryPub {

    @Inject
    MasterCopyDataRepository repoImp;

    @Override
    public void doCopyA(String companyId, int copyMethod) {
        repoImp.doCopyA(companyId,copyMethod);
    }

    @Override
    public void doCopyB(String companyId, int copyMethod) {
        repoImp.doCopyB(companyId,copyMethod);
    }

    @Override
    public void doCopyC(String companyId, int copyMethod) {
        repoImp.doCopyC(companyId,copyMethod);
    }
}
