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
    public void personalInfoDefCopy(String companyId, int copyMethod) {
        repo.personalInfoDefCopy(companyId,copyMethod);
    }
}
