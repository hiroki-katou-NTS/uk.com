package nts.uk.ctx.pr.transfer.ac.core.emprsdttaxinfo.amountinfo;

import nts.uk.ctx.pr.core.pub.emprsdttaxinfo.amountinfo.EmployeeResidentTaxPayAmountInfoPub;
import nts.uk.ctx.pr.core.pub.emprsdttaxinfo.amountinfo.MonthlyResidentTaxPayAmountExport;
import nts.uk.ctx.pr.transfer.dom.adapter.core.emprsdttaxinfo.amountinfo.EmployeeResidentTaxPayAmountInfoAdapter;
import nts.uk.ctx.pr.transfer.dom.adapter.core.emprsdttaxinfo.amountinfo.EmployeeResidentTaxPayAmountInfoImport;
import nts.uk.ctx.pr.transfer.dom.adapter.core.emprsdttaxinfo.amountinfo.MonthlyResidentTaxPayAmountImport;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class EmployeeResidentTaxPayAmountInfoAdapterImpl implements EmployeeResidentTaxPayAmountInfoAdapter {
    @Inject
    private EmployeeResidentTaxPayAmountInfoPub employeeResidentTaxPayAmountInfoPub;

    @Override
    public List<EmployeeResidentTaxPayAmountInfoImport> getListEmpRsdtTaxPayAmountInfo(List<String> listSId, int year) {
        return employeeResidentTaxPayAmountInfoPub.getListEmpRsdtTaxPayAmountInfo(listSId, year).stream().map(x -> {
            EmployeeResidentTaxPayAmountInfoImport export = new EmployeeResidentTaxPayAmountInfoImport();
            export.setSid(x.getSid());
            export.setYear(x.getYear());
            export.setInputAtr(x.getInputAtr());

            MonthlyResidentTaxPayAmountExport amount = x.getMonthlyPaymentAmount();
            MonthlyResidentTaxPayAmountImport amountImport = new MonthlyResidentTaxPayAmountImport();
            amountImport.setAmountJanuary(amount.getAmountJanuary());
            amountImport.setAmountFebruary(amount.getAmountFebruary());
            amountImport.setAmountMarch(amount.getAmountMarch());
            amountImport.setAmountApril(amount.getAmountApril());
            amountImport.setAmountMay(amount.getAmountMay());
            amountImport.setAmountJune(amount.getAmountJune());
            amountImport.setAmountJuly(amount.getAmountJuly());
            amountImport.setAmountAugust(amount.getAmountAugust());
            amountImport.setAmountSeptember(amount.getAmountSeptember());
            amountImport.setAmountOctober(amount.getAmountOctober());
            amountImport.setAmountNovember(amount.getAmountNovember());
            amountImport.setAmountDecember(amount.getAmountDecember());

            export.setMonthlyPaymentAmount(amountImport);
            return export;
        }).collect(Collectors.toList());
    }
}
