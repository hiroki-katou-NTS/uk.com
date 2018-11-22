package nts.uk.ctx.pr.core.pub.emprsdttaxinfo;

import nts.arc.time.YearMonth;

import java.util.List;

public interface EmployeeResidentTaxPayeeInfoPub {

    List<EmployeeResidentTaxPayeeInfoExport> getEmpRsdtTaxPayeeInfo(List<String> listSId, YearMonth periodYM);
}
