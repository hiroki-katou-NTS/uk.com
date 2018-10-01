package nts.uk.ctx.at.record.infra.repository.mastercopy.handler;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.mastercopy.CopyErAlWorkRecordRepository;

import javax.ejb.Stateless;

/**
 * @author locph
 */
@Stateless
public class JpaCopyErAlWorkRecordRepoImp extends JpaRepository implements CopyErAlWorkRecordRepository {
    @Override
    public void doCopy(String companyId, int copyMethod) {
        new KwrmtErAlWorkRecordCopyHandler(this, copyMethod, companyId).doCopy();
    }
}
