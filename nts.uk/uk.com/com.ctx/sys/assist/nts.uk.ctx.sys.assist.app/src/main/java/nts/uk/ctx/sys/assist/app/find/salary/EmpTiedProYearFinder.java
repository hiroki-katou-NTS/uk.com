package nts.uk.ctx.sys.assist.app.find.salary;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.uk.ctx.sys.assist.dom.salary.EmpTiedProYearRepository;

@Stateless
/**
* 処理年月に紐づく雇用
*/
public class EmpTiedProYearFinder
{

    @Inject
    private EmpTiedProYearRepository finder;

    public List<EmpTiedProYearDto> getAllEmpTiedProYear(){
        return finder.getAllEmpTiedProYear().stream().map(item -> EmpTiedProYearDto.fromDomain(item))
                .collect(Collectors.toList());
    }

}
