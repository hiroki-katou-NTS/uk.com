package nts.uk.ctx.pereg.infra.repository.mastercopy.handler;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pereg.dom.mastercopy.MasterCopyDataRepository;

import javax.ejb.Stateless;

/**
 * @author locph
 */
@Stateless
public class JpaMasterCopyDataRepoImp extends JpaRepository implements MasterCopyDataRepository {
    @Override
    public void doCopy(String companyId, int copyMethod) {
        new PersonalInfoDefCopyHandler(this, copyMethod, companyId).doCopy();
//        new PpemtNewLayoutDataCopyHandler(copyMethod, companyId, getEntityManager()).doCopy();
//        new PersonalInfoDefCopyHandler(this, copyMethod, companyId).doCopy();
    }
}
