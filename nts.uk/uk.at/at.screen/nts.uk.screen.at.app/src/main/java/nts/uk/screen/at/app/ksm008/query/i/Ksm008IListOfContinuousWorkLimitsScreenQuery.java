package nts.uk.screen.at.app.ksm008.query.i;

import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.consecutiveworktime.MaxDaysOfContinuousWorkTimeCompany;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.consecutiveworktime.MaxDaysOfContinuousWorkTimeCompanyRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Screen KSM008I : 会社の就業時間帯の連続勤務上限リストを取得す
 *
 * @author Md Rafiqul Islam
 */
@Stateless
public class Ksm008IListOfContinuousWorkLimitsScreenQuery {

    @Inject
    MaxDaysOfContinuousWorkTimeCompanyRepository maxDaysOfContinuousWorkTimeCompanyRepository;

    /*public List<MaxDaysOfContinuousWorkTimeListOrgDto> get() {

        List<MaxDaysOfContinuousWorkTimeCompany> MaxDaysOfContinuousWorkTimeCompanyList = maxDaysOfContinuousWorkTimeCompanyRepository.getAll(AppContexts.user().companyId());
        return MaxDaysOfContinuousWorkTimeCompanyList.stream()
                .collect(
                        Collectors.mapping(

                                p -> new MaxDaysOfContinuousWorkTimeListOrgDto(
                                        p.getCode().v(),
                                        p.getName().v(),
                                        new WorkingHoursDTO(
                                                p.getMaxDay().getWorkTimeCodes()
                                                        .stream()
                                                        .map(item -> item.v())
                                                        .collect(Collectors.toList()),
                                                p.getMaxDay().getMaxDay().v()
                                        )
                                ),
                                Collectors.toList()));
    }*/
}
