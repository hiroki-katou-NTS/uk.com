package nts.uk.ctx.pr.transfer.dom.adapter.core.emprsdttaxinfo;

import nts.arc.time.YearMonth;

import java.util.List;

public interface EmployeeResidentTaxPayeeInfoAdapter {

    List<EmployeeResidentTaxPayeeInfoImport> getEmpRsdtTaxPayeeInfo(List<String> listSId, YearMonth periodYM);
}
