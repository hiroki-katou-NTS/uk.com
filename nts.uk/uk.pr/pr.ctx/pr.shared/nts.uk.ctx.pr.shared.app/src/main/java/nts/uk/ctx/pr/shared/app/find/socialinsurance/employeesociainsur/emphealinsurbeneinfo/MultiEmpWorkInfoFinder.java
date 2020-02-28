package nts.uk.ctx.pr.shared.app.find.socialinsurance.employeesociainsur.emphealinsurbeneinfo;

import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.MultiEmpWorkInfo;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.MultiEmpWorkInfoRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;


@Stateless
/**
* 社員二以上事業所勤務情報: Finder
*/
public class MultiEmpWorkInfoFinder {

    @Inject
    private MultiEmpWorkInfoRepository finder;

    public Optional<MultiEmpWorkInfo> getMultiEmpWorkInfoById(String employeeId){
        return finder.getMultiEmpWorkInfoById(employeeId);
    }

}
