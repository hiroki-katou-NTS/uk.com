package nts.uk.ctx.pr.shared.app.find.socialinsurance.employeesociainsur.empbenepenpeninfor;

import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor.EmpWelfarePenInsQualiInforRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
/**
* 社員厚生年金保険資格情報: Finder
*/
public class EmpWelfarePenInsQualiInforFinder
{

    @Inject
    private EmpWelfarePenInsQualiInforRepository finder;

    public List<EmpWelfarePenInsQualiInforDto> getAllEmpWelfarePenInsQualiInfor(){
        return finder.getAllEmpWelfarePenInsQualiInfor().stream().map(item -> EmpWelfarePenInsQualiInforDto.fromDomain(item))
                .collect(Collectors.toList());
    }

}
