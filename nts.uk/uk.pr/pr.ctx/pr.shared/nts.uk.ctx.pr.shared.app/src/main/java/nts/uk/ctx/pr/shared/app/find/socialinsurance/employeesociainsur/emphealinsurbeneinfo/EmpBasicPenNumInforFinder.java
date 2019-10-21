package nts.uk.ctx.pr.shared.app.find.socialinsurance.employeesociainsur.emphealinsurbeneinfo;

import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.EmpBasicPenNumInfor;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.EmpBasicPenNumInforRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
/**
* 社員基礎年金番号情報: Finder
*/
public class EmpBasicPenNumInforFinder {

    @Inject
    private EmpBasicPenNumInforRepository finder;

    public Optional<EmpBasicPenNumInfor> getEmpBasicPenNumInforById(String employeeId){
        return finder.getEmpBasicPenNumInforById(employeeId);
    }

}
