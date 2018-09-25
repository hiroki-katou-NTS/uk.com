package nts.uk.ctx.pereg.pubimp.mastercopy;

import nts.uk.ctx.pereg.dom.mastercopy.CopyPerInfoRepository;
import nts.uk.ctx.pereg.pub.mastercopy.PerInfoCategoryPub;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * @author locph
 */
@Stateless
public class PerInfoCategoryPubImp implements PerInfoCategoryPub {

    @Inject
    CopyPerInfoRepository repo;

    @Override
    public void personalInfoDefEvent(String companyId, int copyMethod) {
        repo.personalInfoDefEvent(companyId,copyMethod);
    }

    @Override
    public void newLayoutEvent(String companyId, int copyMethod) {
        repo.newLayoutEvent(companyId,copyMethod);
    }

    @Override
    public void personalInfoItemGroupEvent(String companyId, int copyMethod) {
        repo.personalInfoItemGroupEvent(companyId,copyMethod);
    }

    @Override
    public void personalInfoSelectItemEvent(String companyId, int copyMethod) {
        repo.personalInfoSelectItemEvent(companyId,copyMethod);
    }
}
