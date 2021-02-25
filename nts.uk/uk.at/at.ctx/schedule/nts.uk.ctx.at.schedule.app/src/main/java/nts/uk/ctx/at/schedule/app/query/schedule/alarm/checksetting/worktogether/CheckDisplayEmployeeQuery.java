package nts.uk.ctx.at.schedule.app.query.schedule.alarm.checksetting.worktogether;

import nts.uk.ctx.at.schedule.app.query.schedule.alarm.checksetting.worktogether.Dto.CheckDisplayEmployeeDto;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.worktogether.together.WorkTogether;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.worktogether.together.WorkTogetherRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class CheckDisplayEmployeeQuery {

    @Inject
    private WorkTogetherRepository workTogetherRepository;

    public List<CheckDisplayEmployeeDto> getEmployeeIsDisplay(List<String> sids) {

        List<String> existWorkTogetherSidList = workTogetherRepository.getWithEmpIdList(sids).stream().map(WorkTogether::getSid).collect(Collectors.toList());

        return sids.stream().map(sid -> new CheckDisplayEmployeeDto(sid, existWorkTogetherSidList.contains(sid))).collect(Collectors.toList());
    }

}
