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
    public void personalInfoDefCopy(String companyId, int copyMethod) {
        perInfoCategoryPub.personalInfoDefCopy(companyId, copyMethod);
    }
}
