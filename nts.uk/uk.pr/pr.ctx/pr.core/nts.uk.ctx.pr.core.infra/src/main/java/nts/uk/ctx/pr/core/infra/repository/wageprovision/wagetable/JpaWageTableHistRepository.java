package nts.uk.ctx.pr.core.infra.repository.wageprovision.wagetable;

import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.WageTableHist;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.WageTableHistRepository;

import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class JpaWageTableHistRepository implements WageTableHistRepository {
    @Override
    public List<WageTableHist> getAll(String cid) {
        return null;
    }
}
