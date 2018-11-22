package nts.uk.ctx.pr.core.app.find.wageprovision.organizationinformation.salaryclassification.salaryclassificationmaster;

import nts.uk.ctx.pr.core.dom.wageprovision.organizationinformation.salaryclassification.salaryclassificationmaster.SalaryClassificationInformationRepository;
import nts.uk.shr.com.context.AppContexts;
import sun.awt.AppContext;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
/**
* 給与分類情報: Finder
*/
public class SalaryClassificationInformationFinder
{

    @Inject
    private SalaryClassificationInformationRepository finder;

    public List<SalaryClassificationInformationDto> getAllSalaryClassificationInformation(){
        return finder.getAllSalaryClassificationInformation(AppContexts.user().companyId()).stream().map(item -> SalaryClassificationInformationDto.fromDomain(item))
                .collect(Collectors.toList());
    }

    public SalaryClassificationInformationDto getSalaryClassificationInformationById(String salaryClassificationCode ) {
        return finder.getSalaryClassificationInformationById(AppContexts.user().companyId(), salaryClassificationCode).map(SalaryClassificationInformationDto::fromDomain).orElse(null);
    }

}
