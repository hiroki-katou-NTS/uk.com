package nts.uk.screen.at.app.ksm005.find;

import nts.uk.ctx.at.schedule.dom.shift.WeeklyWorkDay.WeeklyWorkDayPattern;
import nts.uk.ctx.at.schedule.dom.shift.WeeklyWorkDay.WeeklyWorkDayRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.ctx.at.shared.pub.worktime.worktimeset.WorkTimeSettingPub;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

@Stateless
public class WeeklyWorkScreenProcessor {

    @Inject
    private WorkTypeRepository workTypeRepository;

    @Inject
    private WeeklyWorkDayRepository weeklyWorkDayRepository;

    @Inject
    private WorkTimeSettingPub workTimeSettingPub;

    public WeeklyWorkDto findDataBentoMenu(RequestPrams requestPrams) {

        String cid = AppContexts.user().companyId();
        WeeklyWorkDayPattern weeklyWorkDayPattern = weeklyWorkDayRepository.getWeeklyWorkDayPatternByCompanyId(cid);

        List<String> listWorkTypeCode = requestPrams.listWorkTypeCd.stream().filter(Objects::nonNull).collect(Collectors.toList());
        Map<String, String> listWorkTypeCodeName = listWorkTypeCode.size() > 0
                ? workTypeRepository.getCodeNameWorkType(cid, listWorkTypeCode) : new HashMap<>();

        List<String> workTypeName = new ArrayList<>(listWorkTypeCodeName.values());
        String WorkTimeSettingName = workTimeSettingPub.getWorkTimeSettingName(cid, requestPrams.worktimeCode);

        return new WeeklyWorkDto(weeklyWorkDayPattern.getListWorkdayPatternItem(),workTypeName,WorkTimeSettingName);
    }


}
