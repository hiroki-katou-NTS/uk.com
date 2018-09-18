package nts.uk.ctx.pereg.infra.repository.mastercopy.handler;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pereg.dom.mastercopy.CopyPerInfoRepository;

import javax.ejb.Stateless;

/**
 * @author locph
 */
@Stateless
public class JpaCopyPerInfoRepoImp extends JpaRepository implements CopyPerInfoRepository {
    @Override
    public void personalInfoDefEvent(String companyId, int copyMethod) {
        new PersonalInfoDefCopyHandler(this, copyMethod, companyId).doCopy();
    }

    @Override
    public void newLayoutEvent(String companyId, int copyMethod) {
        new PpemtNewLayoutDataCopyHandler(copyMethod, companyId, getEntityManager()).doCopy();
    }

    @Override
    public void personalInfoItemGroupEvent(String companyId, int copyMethod) {
        new PpemtPInfoItemGroupDataCopyHandler(copyMethod, companyId, getEntityManager()).doCopy();
    }

    @Override
    public void personalInfoSelectItemEvent(String companyId, int copyMethod) {
        new PerInfoSelectionItemCopyHandler(this, copyMethod, companyId).doCopy();

    }
}
