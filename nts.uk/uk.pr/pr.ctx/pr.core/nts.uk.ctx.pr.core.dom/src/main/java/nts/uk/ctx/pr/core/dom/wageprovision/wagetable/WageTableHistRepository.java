package nts.uk.ctx.pr.core.dom.wageprovision.wagetable;

import java.util.List;

public interface WageTableHistRepository {
    List<WageTableHist> getAll(String cid);
}
