package nts.uk.ctx.pr.core.dom.emprsdttaxinfo;

import nts.arc.time.YearMonth;

import java.util.Optional;
import java.util.List;

/**
 * 社員住民税納付先情報
 */
public interface EmployeeResidentTaxPayeeInfoRepository {

    List<EmployeeResidentTaxPayeeInfo> getEmpRsdtTaxPayeeInfo(List<String> listSId, YearMonth periodYM);

    void add(List<EmployeeResidentTaxPayeeInfo> domain);

    void update(List<EmployeeResidentTaxPayeeInfo> domain);

    void remove(String sid, String histId);

}
