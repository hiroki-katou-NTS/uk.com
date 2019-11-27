package nts.uk.ctx.pr.core.dom.wageprovision.processdatecls;

import java.util.Optional;
import java.util.List;

/**
* 給与社会保険徴収月
*/
public interface SalaryInsuColMonRepository
{

    List<SalaryInsuColMon> getAllSalaryInsuColMon();

    Optional<SalaryInsuColMon> getSalaryInsuColMonById(int processCateNo, String cid);

    void add(SalaryInsuColMon domain);

    void update(SalaryInsuColMon domain);

    void remove(int processCateNo, String cid);

}
