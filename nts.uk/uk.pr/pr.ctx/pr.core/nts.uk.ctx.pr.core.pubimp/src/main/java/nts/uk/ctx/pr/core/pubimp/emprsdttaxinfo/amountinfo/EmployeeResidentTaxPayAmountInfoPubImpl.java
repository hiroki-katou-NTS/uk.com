package nts.uk.ctx.pr.core.pubimp.emprsdttaxinfo.amountinfo;

import nts.uk.ctx.pr.core.dom.emprsdttaxinfo.amountinfo.EmployeeResidentTaxPayAmountInfoRepository;
import nts.uk.ctx.pr.core.dom.emprsdttaxinfo.amountinfo.MonthlyResidentTaxPayAmount;
import nts.uk.ctx.pr.core.pub.emprsdttaxinfo.amountinfo.EmployeeResidentTaxPayAmountInfoExport;
import nts.uk.ctx.pr.core.pub.emprsdttaxinfo.amountinfo.EmployeeResidentTaxPayAmountInfoPub;
import nts.uk.ctx.pr.core.pub.emprsdttaxinfo.amountinfo.MonthlyResidentTaxPayAmountExport;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class EmployeeResidentTaxPayAmountInfoPubImpl implements EmployeeResidentTaxPayAmountInfoPub{
    @Inject
    private EmployeeResidentTaxPayAmountInfoRepository empRsdtTaxPayAmountInfoRepo;

    @Override
    public List<EmployeeResidentTaxPayAmountInfoExport> getListEmpRsdtTaxPayAmountInfo(List<String> listSId, int year) {
        return empRsdtTaxPayAmountInfoRepo.getListEmpRsdtTaxPayAmountInfo(listSId, year).stream().map(x -> {
            EmployeeResidentTaxPayAmountInfoExport export = new EmployeeResidentTaxPayAmountInfoExport();
            export.setSid(x.getSid());
            export.setYear(x.getYear());
            export.setInputAtr(x.getInputAtr().value);

            MonthlyResidentTaxPayAmount amount = x.getMonthlyPaymentAmount();
            MonthlyResidentTaxPayAmountExport amountExport = new MonthlyResidentTaxPayAmountExport();
            amountExport.setAmountJanuary(amount.getAmountJanuary().v());
            amountExport.setAmountFebruary(amount.getAmountFebruary().v());
            amountExport.setAmountMarch(amount.getAmountMarch().v());
            amountExport.setAmountApril(amount.getAmountApril().v());
            amountExport.setAmountMay(amount.getAmountMay().v());
            amountExport.setAmountJune(amount.getAmountJune().v());
            amountExport.setAmountJuly(amount.getAmountJuly().v());
            amountExport.setAmountAugust(amount.getAmountAugust().v());
            amountExport.setAmountSeptember(amount.getAmountSeptember().v());
            amountExport.setAmountOctober(amount.getAmountOctober().v());
            amountExport.setAmountNovember(amount.getAmountNovember().v());
            amountExport.setAmountDecember(amount.getAmountDecember().v());

            export.setMonthlyPaymentAmount(amountExport);
            return export;
        }).collect(Collectors.toList());
    }
}
