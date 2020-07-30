package nts.uk.ctx.at.schedule.app.find.shift.workcycle;

import lombok.val;
import nts.uk.ctx.at.schedule.dom.shift.workcycle.WorkCycle;
import nts.uk.ctx.at.schedule.dom.shift.workcycle.WorkCycleDtlRepository;
import nts.uk.ctx.at.schedule.dom.shift.workcycle.WorkCycleInfo;
import nts.uk.ctx.at.schedule.dom.shift.workcycle.WorkCycleRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class WorkingCycleFinder {

    @Inject
    WorkCycleRepository workCycleRepository;

    @Inject
    WorkCycleDtlRepository workCycleDtlRepository;

    /**
     * 勤務サイクル一覧を取得する
     * @return
     */
    public List<WorkingCycleDto> getStartScreen() {
        String cid = AppContexts.user().companyId();
        val result = workCycleRepository.getByCid(cid);
        return result.stream().map(i -> WorkingCycleDto.fromDomain(i)).collect(Collectors.toList());
    }

    /**
     * 勤務サイクルの明細を表示する
     * @param code
     * @return
     */
    public WorkingCycleDtlDto getWorkCycleInfo(String code) {
        String cid = AppContexts.user().companyId();
        Optional<WorkCycle> workingCycle = workCycleRepository.getByCidAndCode(cid, code);
        if (workingCycle.isPresent()) {
            List<WorkCycleInfo> infos = workCycleDtlRepository.getByCode(cid, code);
            workingCycle.get().setInfos(infos);
            return WorkingCycleDtlDto.fromDomain(workingCycle.get());
        }
        return null;
    }

}
