package nts.uk.screen.at.app.ksm003.find;

import lombok.val;
import nts.uk.ctx.at.schedule.dom.shift.workcycle.WorkCycle;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

@Stateless
public class GetWorkCycle {

    @Inject
    private WorkCycleQueryRepository workCycleQueryRepository;

    @Inject
    private WorkTypeRepository workTypeRepo;

    @Inject
    private WorkTimeSettingRepository workTimeSettingRepository;

    /**
     * 勤務サイクル一覧を取得する
     * @return
     */
    public List<WorkCycleDto> getDataStartScreen() {
        String cid = AppContexts.user().companyId();
        val result = workCycleQueryRepository.getByCid(cid);
        return result.stream().map(i -> WorkCycleDto.startScreen(i)).collect(Collectors.toList());
    }

    /**
     * 勤務サイクルの明細を表示する
     * @param patternCode
     * @return
     */
    public WorkCycleDto getWorkCycleInfo(String patternCode) {
        String cid = AppContexts.user().companyId();
        Optional<WorkCycle> workingCycle = workCycleQueryRepository.getByCidAndCode(cid, patternCode);
        if (!workingCycle.isPresent()) {
            return null;
        }

        Map<String , String> workTypes = new LinkedHashMap<>();
        val listWrkTypes = workingCycle.get().getInfos().stream().map(i->i.getWorkInformation().getWorkTypeCode().v()).distinct().collect(Collectors.toList());
        if (!listWrkTypes.isEmpty()) {
            workTypes = this.workTypeRepo.getCodeNameWorkType(cid, listWrkTypes);
        }

        Map<String , String> workTimeItems = new LinkedHashMap<>();
        val listWrkTimes = workingCycle.get().getInfos().stream().map(i->i.getWorkInformation().getWorkTimeCodeNotNull()).collect(Collectors.toList());
        val listWrkTimeStr = listWrkTimes.stream().filter(i->i.isPresent()).map(i->i.get().v()).distinct().collect(Collectors.toList());
        if (!listWrkTimeStr.isEmpty()) {
            workTimeItems = this.workTimeSettingRepository.getCodeNameByListWorkTimeCd(cid, listWrkTimeStr);
        }

        return WorkCycleDto.createFromDomain(workingCycle.get(),workTypes,workTimeItems);
    }

}
