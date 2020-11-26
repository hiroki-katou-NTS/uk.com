package nts.uk.screen.at.app.ksm008.query.b;

import nts.uk.ctx.at.schedule.dom.schedule.alarm.worktogether.together.WorkTogether;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.worktogether.together.WorkTogetherRepository;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfo;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfoRepository;
import nts.uk.ctx.bs.person.dom.person.info.Person;
import nts.uk.ctx.bs.person.dom.person.info.PersonRepository;
import nts.uk.screen.at.app.ksm008.query.b.dto.PersonInfoDto;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * UKDesign.UniversalK.就業.KSM_スケジュールマスタ.KSM008 スケジュールのアラームチェック設定.B: 同時出勤指定.メニュー別OCD. 同時出勤指定明細を表示する
 *
 * @author 3Si/hai.tt
 */

@Stateless
public class DisplaySimultanAtdDesginDtl {

    @Inject
    private WorkTogetherRepository workTogetherRepository;

    @Inject
    private EmployeeDataMngInfoRepository employeeDataMngInfoRepository;

    @Inject
    private PersonRepository personRepository;

    public List<PersonInfoDto> getSimultaneousAttendaceDesignDtl(String sid) {
        Optional<WorkTogether> workTogether = workTogetherRepository.get(sid);
        if (workTogether.isPresent()) {
            List<EmployeeDataMngInfo> empMngInfos = employeeDataMngInfoRepository.findBySidNotDel(workTogether.get().getEmpMustWorkTogetherLst());
            List<Person> personInfos = personRepository.getPersonByPersonIds(empMngInfos.stream().map(EmployeeDataMngInfo::getPersonId).collect(Collectors.toList()));

            return empMngInfos.stream().map(i -> {
                PersonInfoDto info = new PersonInfoDto();
                info.setEmployeeID(i.getEmployeeId());
                info.setEmployeeCode(i.getEmployeeCode().v());
                Optional<Person> targetPerson = personInfos.stream().filter(x -> x.getPersonId().equals(i.getPersonId())).findFirst();
                targetPerson.ifPresent(z -> info.setBusinessName(z.getPersonNameGroup().getBusinessName().v()));
                return info;
            }).collect(Collectors.toList());
        }
        return null;
    }

}
