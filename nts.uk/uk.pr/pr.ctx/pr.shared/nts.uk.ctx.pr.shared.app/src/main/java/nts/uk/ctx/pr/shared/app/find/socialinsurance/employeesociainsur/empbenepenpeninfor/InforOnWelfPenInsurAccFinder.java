package nts.uk.ctx.pr.shared.app.find.socialinsurance.employeesociainsur.empbenepenpeninfor;

import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor.InforOnWelfPenInsurAccRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;


@Stateless
/**
* 厚生年金保険取得時情報: Finder
*/
public class InforOnWelfPenInsurAccFinder
{

    @Inject
    private InforOnWelfPenInsurAccRepository finder;

    public List<InforOnWelfPenInsurAccDto> getAllInforOnWelfPenInsurAcc(){
        return finder.getAllInforOnWelfPenInsurAcc().stream().map(item -> InforOnWelfPenInsurAccDto.fromDomain(item))
                .collect(Collectors.toList());
    }

}
