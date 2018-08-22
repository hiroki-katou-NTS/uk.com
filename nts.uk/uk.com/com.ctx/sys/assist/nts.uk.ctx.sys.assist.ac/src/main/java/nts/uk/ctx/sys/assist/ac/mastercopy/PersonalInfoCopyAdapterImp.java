package nts.uk.ctx.sys.assist.ac.mastercopy;

import nts.uk.ctx.pereg.pub.mastercopy.PerInfoCategoryPub;
import nts.uk.ctx.sys.assist.dom.mastercopy.PersonalInfoDataCopyAdapter;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * @author locph
 */
@Stateless
public class PersonalInfoCopyAdapterImp implements PersonalInfoDataCopyAdapter {
    @Inject
    PerInfoCategoryPub perInfoCategoryPub;
    

    @Override
    public void copyA(String companyId, int copyMethod) {
        perInfoCategoryPub.doCopyA(companyId, copyMethod);
    }

    @Override
    public void copyB(String companyId, int copyMethod) {
        perInfoCategoryPub.doCopyB(companyId, copyMethod);
    }

    @Override
    public void copyC(String companyId, int copyMethod) {
        perInfoCategoryPub.doCopyC(companyId, copyMethod);
    }
}
