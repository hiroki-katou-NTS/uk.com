package nts.uk.screen.at.app.kdw013.a;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TemporaryTimeOfDailyAttd;

@AllArgsConstructor
@Getter
public class TemporaryTimeOfDailyAttdCommand {
	// 1 ~ 10
	// 出退勤
	private List<TimeLeavingWorkCommand> timeLeavingWorks;

	public TemporaryTimeOfDailyAttd toDomain() {

		return new TemporaryTimeOfDailyAttd(
				this.getTimeLeavingWorks().stream().map(x -> x.toDomain()).collect(Collectors.toList()));

	}
}
