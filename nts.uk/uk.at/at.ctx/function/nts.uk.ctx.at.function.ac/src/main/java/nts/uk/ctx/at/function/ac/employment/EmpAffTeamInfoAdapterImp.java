package nts.uk.ctx.at.function.ac.employment;

import nts.uk.ctx.at.function.dom.adapter.employment.EmpAffTeamInfoAdapter;
import nts.uk.ctx.at.function.dom.adapter.employment.EmpTeamInfoImport;
import nts.uk.ctx.at.schedule.pub.schedule.workschedule.employeeinfo.EmpAffTeamInfoPub;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class EmpAffTeamInfoAdapterImp implements EmpAffTeamInfoAdapter {

    @Inject
    private EmpAffTeamInfoPub employmentHistoryPub;


    /**
     * @param listEmpId
     * @return List<社員所属チーム情報Imported>
     */
    @Override
    public List<EmpTeamInfoImport> get(List<String> listEmpId) {
        return employmentHistoryPub.get(listEmpId).stream()
                .map(x -> new EmpTeamInfoImport(
                        x.getEmployeeID(),
                        x.getScheduleTeamCd(),
                        x.getScheduleTeamName()
                )).collect(Collectors.toList());
    }
}
