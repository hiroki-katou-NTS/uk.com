package nts.uk.ctx.pr.yearend.app.find.yearendadjustment.insurancecompany.insurancetype;

import nts.uk.ctx.pr.yearend.dom.yearendadjustment.insurancecompany.insuranceType.InsuranceTypeRepository;
import nts.uk.shr.com.context.AppContexts;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
/**
* 保険種類情報: Finder
*/
public class InsuranceTypeFinder
{

    @Inject
    private InsuranceTypeRepository finder;

    public List<InsuranceTypeDto> getAllInsuranceType(String lifeInsuranceCode){
        String cid = AppContexts.user().companyId();
        return finder.getInsuranceTypeBycId(cid, lifeInsuranceCode).stream().map(item -> InsuranceTypeDto.fromDomain(item))
                .collect(Collectors.toList());
    }

}
