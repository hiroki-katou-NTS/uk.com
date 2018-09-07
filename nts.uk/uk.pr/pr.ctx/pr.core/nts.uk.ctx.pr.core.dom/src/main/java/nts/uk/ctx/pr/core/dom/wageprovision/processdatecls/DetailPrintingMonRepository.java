package nts.uk.ctx.pr.core.dom.wageprovision.processdatecls;

import java.util.Optional;
import java.util.List;

/**
* 明細印字月
*/
public interface DetailPrintingMonRepository
{

    List<DetailPrintingMonth> getAllDetailPrintingMon();

    Optional<DetailPrintingMonth> getDetailPrintingMonById(int processCateNo, String cid);

    void add(DetailPrintingMonth domain);

    void update(DetailPrintingMonth domain);

    void remove(int processCateNo, String cid);

}
