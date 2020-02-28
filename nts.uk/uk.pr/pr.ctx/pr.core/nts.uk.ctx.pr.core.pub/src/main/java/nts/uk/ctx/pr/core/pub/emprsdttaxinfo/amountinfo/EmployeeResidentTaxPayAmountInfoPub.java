package nts.uk.ctx.pr.core.pub.emprsdttaxinfo.amountinfo;

import java.util.List;

public interface EmployeeResidentTaxPayAmountInfoPub {

    List<EmployeeResidentTaxPayAmountInfoExport> getListEmpRsdtTaxPayAmountInfo(List<String> listSId, int year);
}
