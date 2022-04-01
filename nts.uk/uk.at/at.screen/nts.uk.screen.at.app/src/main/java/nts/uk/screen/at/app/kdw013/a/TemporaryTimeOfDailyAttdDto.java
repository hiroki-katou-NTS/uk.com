package nts.uk.screen.at.app.kdw013.a;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TemporaryTimeOfDailyAttd;
import nts.uk.screen.at.app.ksus01.a.TimeLeavingWorkDto;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TemporaryTimeOfDailyAttdDto {
	// 1 ~ 10
	// 出退勤
	private List<TimeLeavingWorkDto> timeLeavingWorks = new ArrayList<>();

	public static TemporaryTimeOfDailyAttdDto fromDomain(Optional<TemporaryTimeOfDailyAttd> domain) {

		return domain.map(x -> new TemporaryTimeOfDailyAttdDto(x.getTimeLeavingWorks().stream()
				.map(tw -> TimeLeavingWorkDto.fromDomain(tw)).collect(Collectors.toList()))).orElse(null);
	}
}
