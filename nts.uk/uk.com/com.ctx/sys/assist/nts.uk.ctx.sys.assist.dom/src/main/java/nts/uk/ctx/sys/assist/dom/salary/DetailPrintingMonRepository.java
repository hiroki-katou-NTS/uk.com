package nts.uk.ctx.sys.assist.dom.salary;

import java.util.Optional;
import java.util.List;

/**
* 明細印字月
*/
public interface DetailPrintingMonRepository
{

    List<DetailPrintingMon> getAllDetailPrintingMon();

    Optional<DetailPrintingMon> getDetailPrintingMonById(int processCateNo, String cid);

    void add(DetailPrintingMon domain);

    void update(DetailPrintingMon domain);

    void remove(int processCateNo, String cid);

}
