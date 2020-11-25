package nts.uk.ctx.at.shared.app.command.worktime.common.dto;

import java.util.List;
import java.util.stream.Collectors;

import nts.uk.ctx.at.shared.dom.worktime.common.RoundingSet;
import nts.uk.ctx.at.shared.dom.worktime.common.RoundingTimeGetMemento;
import nts.uk.ctx.at.shared.dom.worktime.common.RoundingTimeSetMemento;
import nts.uk.shr.com.enumcommon.NotUseAtr;

public class RoundingTimeDto implements RoundingTimeGetMemento, RoundingTimeSetMemento {

	private Integer attendanceMinuteLaterCalculate;
	
	private Integer leaveWorkMinuteAgoCalculate;
	
	private List<RoundingSetDto> roundingSets;
	
	
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

	@Override
	public void setAttendanceMinuteLaterCalculate(NotUseAtr attendanceMinutelater) {
		this.attendanceMinuteLaterCalculate = attendanceMinutelater.value;
	}

	@Override
	public void setLeaveWorkMinuteAgoCalculate(NotUseAtr leaveMinutelater) {
		this.leaveWorkMinuteAgoCalculate = leaveMinutelater.value;
	}

	@Override
	public void getRoundingSets(List<RoundingSet> roundingSets) {
		this.roundingSets = roundingSets.stream().map(i ->
			new RoundingSetDto(
				new InstantRoundingDto(
						i.getRoundingSet().getFontRearSection().value, 
						i.getRoundingSet().getRoundingTimeUnit().value), 
				i.getSection().value)).collect(Collectors.toList());
		
	}
}
