package nts.uk.ctx.pr.shared.app.find.socialinsurance.employeesociainsur.empcomofficehis;

import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomofficehis.EmpCorpHealthOffHisRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
/**
* 社員社保事業所所属履歴: Finder
*/
public class EmpCorpHealthOffHisFinder
{

    @Inject
    private EmpCorpHealthOffHisRepository finder;

    public List<EmpCorpHealthOffHisDto> getAllEmpCorpHealthOffHis(){
        return finder.getAllEmpCorpHealthOffHis().stream().map(item -> EmpCorpHealthOffHisDto.fromDomain(item))
                .collect(Collectors.toList());
    }

}
