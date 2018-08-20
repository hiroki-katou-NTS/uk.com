package nts.uk.ctx.sys.assist.ac.mastercopy;

import nts.uk.ctx.sys.assist.dom.mastercopy.PersonalInfoDataCopyAdapter;

import javax.ejb.Stateless;

/**
 * @author locph
 */
@Stateless
public class PersonalInfoCopyAdapterImp implements PersonalInfoDataCopyAdapter {

    @Override
    public void copy(String companyId, int value) {
//        masterCopyPub.copy(companyId, copyMethod);
    }
}
