package nts.uk.ctx.sys.assist.app.find.salary;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.uk.ctx.sys.assist.dom.salary.EmpInsurStanDateRepository;

@Stateless
/**
* 雇用保険基準日
*/
public class EmpInsurStanDateFinder
{

    @Inject
    private EmpInsurStanDateRepository finder;

    public List<EmpInsurStanDateDto> getAllEmpInsurStanDate(){
        return finder.getAllEmpInsurStanDate().stream().map(item -> EmpInsurStanDateDto.fromDomain(item))
                .collect(Collectors.toList());
    }

}
