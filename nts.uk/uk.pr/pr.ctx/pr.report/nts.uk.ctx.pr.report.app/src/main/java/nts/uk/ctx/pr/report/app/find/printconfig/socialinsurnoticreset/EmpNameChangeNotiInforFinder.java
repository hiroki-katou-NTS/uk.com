package nts.uk.ctx.pr.report.app.find.printconfig.socialinsurnoticreset;

import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.EmpNameChangeNotiInfor;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.EmpNameChangeNotiInforRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

@Stateless
/**
* 社員氏名変更届情報: Finder
*/
public class EmpNameChangeNotiInforFinder
{

    @Inject
    private EmpNameChangeNotiInforRepository finder;

    public EmpNameChangeNotiInfor getEmpNameChangeNotiInforById(String employeeId, String cid){
        Optional<EmpNameChangeNotiInfor> empNameChangeNotiInfor = finder.getEmpNameChangeNotiInforById(employeeId,cid);
        return empNameChangeNotiInfor.isPresent() ? empNameChangeNotiInfor.get() : null;
    }

}
