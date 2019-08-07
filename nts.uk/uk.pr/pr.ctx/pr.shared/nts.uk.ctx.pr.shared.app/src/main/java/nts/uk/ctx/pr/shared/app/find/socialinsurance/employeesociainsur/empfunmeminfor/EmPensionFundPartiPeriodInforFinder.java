package nts.uk.ctx.pr.shared.app.find.socialinsurance.employeesociainsur.empfunmeminfor;

import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empfunmeminfor.EmPensionFundPartiPeriodInforRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
/**
* 厚生年金基金加入期間情報: Finder
*/
public class EmPensionFundPartiPeriodInforFinder
{

    @Inject
    private EmPensionFundPartiPeriodInforRepository finder;

    public List<EmPensionFundPartiPeriodInforDto> getAllEmPensionFundPartiPeriodInfor(){
        return finder.getAllEmPensionFundPartiPeriodInfor().stream().map(item -> EmPensionFundPartiPeriodInforDto.fromDomain(item))
                .collect(Collectors.toList());
    }

}
