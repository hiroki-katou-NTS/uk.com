package nts.uk.ctx.pr.core.dom.emprsdttaxinfo;

import java.util.List;
import java.util.Optional;

import nts.arc.time.YearMonth;

/**
 * 社員住民税納付先情報
 */
public interface EmployeeResidentTaxPayeeInfoRepository {

    List<EmployeeResidentTaxPayeeInfo> getEmpRsdtTaxPayeeInfo(List<String> listSId, YearMonth periodYM);
    
    List<EmployeeResidentTaxPayeeInfo> getEmpRsdtTaxPayeeInfo(List<String> listSId);

    List<PayeeInfo> getListPayeeInfo(List<String> listHistId);

    void updatePayeeInfo(PayeeInfo payeeInfo);

}
