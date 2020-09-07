package nts.uk.screen.at.app.ksm003.find;

import lombok.val;
import nts.uk.ctx.at.schedule.dom.shift.workcycle.WorkCycle;
import nts.uk.ctx.at.schedule.dom.shift.workcycle.WorkCycleInfo;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class GetWorkCycle {

    @Inject
    WorkCycleQueryRepository workCycleQueryRepository;

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
     * @param code
     * @return
     */
    public WorkCycleDto getWorkCycleInfo(String patternCode) {
        String cid = AppContexts.user().companyId();
        Optional<WorkCycle> workingCycle = workCycleQueryRepository.getByCidAndCode(cid, patternCode);
        if (workingCycle.isPresent()) {
            return WorkCycleDto.createFromDomain(workingCycle.get());
        }
        return null;
    }

}
