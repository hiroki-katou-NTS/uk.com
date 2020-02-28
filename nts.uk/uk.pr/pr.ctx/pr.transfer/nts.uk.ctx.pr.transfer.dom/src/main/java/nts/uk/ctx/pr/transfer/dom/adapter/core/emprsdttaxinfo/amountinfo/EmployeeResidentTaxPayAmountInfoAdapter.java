package nts.uk.ctx.pr.transfer.dom.adapter.core.emprsdttaxinfo.amountinfo;

import java.util.List;

public interface EmployeeResidentTaxPayAmountInfoAdapter {

    List<EmployeeResidentTaxPayAmountInfoImport> getListEmpRsdtTaxPayAmountInfo(List<String> listSId, int year);
}
