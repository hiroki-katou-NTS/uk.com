package nts.uk.ctx.pr.yearend.app.find.yearendadjustment.insurancecompany.lifeinsurance;

import nts.uk.ctx.pr.yearend.dom.yearendadjustment.insurancecompany.lifeInsurance.LifeInsuranceRepository;
import nts.uk.shr.com.context.AppContexts;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
/**
* 生命保険情報: Finder
*/
public class LifeInsuranceFinder
{

    @Inject
    private LifeInsuranceRepository finder;

    public List<LifeInsuranceDto> getAllLifeInsurance(){
        return finder.getAllLifeInsurance().stream().map(item -> LifeInsuranceDto.fromDomain(item))
                .collect(Collectors.toList());
    }

    public List<LifeInsuranceDto> getLifeInsuranceBycId(){
        String cid = AppContexts.user().companyId();
        return finder.getLifeInsuranceBycId(cid).stream().map(item -> LifeInsuranceDto.fromDomain(item))
                .collect(Collectors.toList());
    }


}
