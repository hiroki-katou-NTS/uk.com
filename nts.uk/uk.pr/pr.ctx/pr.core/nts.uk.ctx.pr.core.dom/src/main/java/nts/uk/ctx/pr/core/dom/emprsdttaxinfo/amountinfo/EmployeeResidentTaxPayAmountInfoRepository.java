package nts.uk.ctx.pr.core.dom.emprsdttaxinfo.amountinfo;

import java.util.Optional;
import java.util.List;

/**
* 社員住民税納付額情報
*/
public interface EmployeeResidentTaxPayAmountInfoRepository
{

    List<EmployeeResidentTaxPayAmountInfo> getAllEmployeeResidentTaxPayAmountInfo();

    Optional<EmployeeResidentTaxPayAmountInfo> getEmployeeResidentTaxPayAmountInfoById(String sid, int year);

    void add(EmployeeResidentTaxPayAmountInfo domain);

    void update(EmployeeResidentTaxPayAmountInfo domain);

    void remove(String sid, int year);

}
