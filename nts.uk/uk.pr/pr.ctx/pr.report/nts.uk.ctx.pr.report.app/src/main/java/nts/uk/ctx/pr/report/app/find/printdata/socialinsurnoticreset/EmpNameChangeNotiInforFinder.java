package nts.uk.ctx.pr.report.app.find.printdata.socialinsurnoticreset;

import nts.uk.ctx.pr.report.dom.printdata.socinsurnoticreset.EmpNameChangeNotiInforRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
/**
* 社員氏名変更届情報: Finder
*/
public class EmpNameChangeNotiInforFinder
{

    @Inject
    private EmpNameChangeNotiInforRepository finder;

    public List<EmpNameChangeNotiInforDto> getAllEmpNameChangeNotiInfor(){
        return finder.getAllEmpNameChangeNotiInfor().stream().map(item -> EmpNameChangeNotiInforDto.fromDomain(item))
                .collect(Collectors.toList());
    }

}
