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
    public void personalInfoDefCopy(String companyId, int copyMethod) {
        new PersonalInfoDefCopyHandler(this, copyMethod, companyId).doCopy();
        new PerInfoSelectionItemCopyHandler(this, copyMethod, companyId).doCopy();
        new PpemtNewLayoutDataCopyHandler(copyMethod, companyId, getEntityManager()).doCopy();
        new PpemtPInfoItemGroupDataCopyHandler(copyMethod, companyId, getEntityManager()).doCopy();

    }
}
