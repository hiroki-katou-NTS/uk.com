package nts.uk.ctx.pr.core.dom.emprsdttaxinfo.amountinfo;

import java.util.Optional;
import java.util.List;

/**
* 社員住民税納付額情報
*/
public interface EmployeeResidentTaxPayAmountInfoRepository
{
    Optional<EmployeeResidentTaxPayAmountInfo> getEmpRsdtTaxPayAmountInfoById(String sid, int year);

    List<EmployeeResidentTaxPayAmountInfo> getListEmpRsdtTaxPayAmountInfo(List<String> listSId, int year);

    void add(EmployeeResidentTaxPayAmountInfo domain);

    void update(EmployeeResidentTaxPayAmountInfo domain);

    void remove(String sid, int year);

}
