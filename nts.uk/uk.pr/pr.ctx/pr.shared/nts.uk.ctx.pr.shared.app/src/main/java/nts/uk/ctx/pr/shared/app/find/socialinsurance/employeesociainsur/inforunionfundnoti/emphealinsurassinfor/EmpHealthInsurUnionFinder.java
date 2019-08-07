package nts.uk.ctx.pr.shared.app.find.socialinsurance.employeesociainsur.inforunionfundnoti.emphealinsurassinfor;

import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.inforunionfundnoti.emphealinsurassinfor.EmpHealthInsurUnionRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
/**
* emphealinsurassinfor: Finder
*/
public class EmpHealthInsurUnionFinder
{

    @Inject
    private EmpHealthInsurUnionRepository finder;

    public List<EmpHealthInsurUnionDto> getAllEmpHealthInsurUnion(){
        return finder.getAllEmpHealthInsurUnion().stream().map(item -> EmpHealthInsurUnionDto.fromDomain(item))
                .collect(Collectors.toList());
    }

}
