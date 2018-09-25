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
    public void personalInfoDefEvent(String companyId, int copyMethod) {
        perInfoCategoryPub.personalInfoDefEvent(companyId, copyMethod);
    }

    @Override
    public void newLayoutEvent(String companyId, int copyMethod) {
        perInfoCategoryPub.newLayoutEvent(companyId, copyMethod);
    }

    @Override
    public void personalInfoItemGroupEvent(String companyId, int copyMethod) {
        perInfoCategoryPub.personalInfoItemGroupEvent(companyId, copyMethod);
    }

    @Override
    public void personalInfoSelectItemEvent(String companyId, int copyMethod) {
        perInfoCategoryPub.personalInfoSelectItemEvent(companyId, copyMethod);
    }
}
