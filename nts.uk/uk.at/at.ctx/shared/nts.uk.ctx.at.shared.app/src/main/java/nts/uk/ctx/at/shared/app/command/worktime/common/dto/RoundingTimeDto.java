package nts.uk.ctx.at.shared.app.command.worktime.common.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.worktime.common.RoundingSet;
import nts.uk.ctx.at.shared.dom.worktime.common.RoundingTimeGetMemento;
import nts.uk.shr.com.enumcommon.NotUseAtr;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
public class RoundingTimeDto implements RoundingTimeGetMemento{

	public Integer attendanceMinuteLaterCalculate;
	
	public Integer leaveWorkMinuteAgoCalculate;
	
	public List<RoundingSetDto> roundingSets;
	
	
	@Override
	public NotUseAtr getAttendanceMinuteLaterCalculate() {
		return NotUseAtr.valueOf(this.attendanceMinuteLaterCalculate);
	}

	@Override
	public NotUseAtr getLeaveWorkMinuteAgoCalculate() {
		return NotUseAtr.valueOf(this.leaveWorkMinuteAgoCalculate);
	}


	@Override
	public List<RoundingSet> getRoundingSets() {
		return this.roundingSets.stream().map(item -> new RoundingSet(item)).collect(Collectors.toList());
	}
}
