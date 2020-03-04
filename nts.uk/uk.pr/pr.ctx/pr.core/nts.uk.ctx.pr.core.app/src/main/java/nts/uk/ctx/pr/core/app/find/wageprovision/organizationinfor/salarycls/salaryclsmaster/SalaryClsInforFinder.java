package nts.uk.ctx.pr.core.app.find.wageprovision.organizationinfor.salarycls.salaryclsmaster;

import nts.uk.ctx.pr.core.dom.wageprovision.organizationinfor.salarycls.salaryclsmaster.SalaryClassificationInformationRepository;
import nts.uk.shr.com.context.AppContexts;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
/**
* 給与分類情報: Finder
*/
public class SalaryClsInforFinder
{

    @Inject
    private SalaryClassificationInformationRepository finder;

    public List<SalaryClsInforDto> getAllSalaryClassificationInformation(){
        return finder.getAllSalaryClassificationInformation(AppContexts.user().companyId()).stream().map(item -> SalaryClsInforDto.fromDomain(item))
                .collect(Collectors.toList());
    }

    public SalaryClsInforDto getSalaryClassificationInformationById(String salaryClassificationCode ) {
        return finder.getSalaryClassificationInformationById(AppContexts.user().companyId(), salaryClassificationCode).map(SalaryClsInforDto::fromDomain).orElse(null);
    }

}
