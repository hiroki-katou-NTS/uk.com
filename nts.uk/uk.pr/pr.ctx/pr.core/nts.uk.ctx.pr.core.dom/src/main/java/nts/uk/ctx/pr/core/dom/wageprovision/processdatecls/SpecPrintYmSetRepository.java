package nts.uk.ctx.pr.core.dom.wageprovision.processdatecls;

import java.util.Optional;
import java.util.List;

/**
* 明細書印字年月設定
*/
public interface SpecPrintYmSetRepository
{

    List<SpecPrintYmSet> getAllSpecPrintYmSet();

    Optional<SpecPrintYmSet> getSpecPrintYmSetById(String cid, int processCateNo);

    void add(SpecPrintYmSet domain);

    void update(SpecPrintYmSet domain);
}
