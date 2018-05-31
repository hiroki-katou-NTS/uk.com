package nts.uk.ctx.at.shared.app.find.scherec.dailyattendanceitem;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.DailyAttendanceItemAuthority;

@Getter
@Setter
@NoArgsConstructor
public class DailyAttendanceItemAuthorityDto {
	private String companyID;
	private String authorityDailyId;
	private List<DisplayAndInputControlDto> displayAndInput;
	public DailyAttendanceItemAuthorityDto(String companyID, String authorityDailyId, List<DisplayAndInputControlDto> displayAndInput) {
		super();
		this.companyID = companyID;
		this.authorityDailyId = authorityDailyId;
		this.displayAndInput = displayAndInput;
	}
	
	public static DailyAttendanceItemAuthorityDto fromDomain(DailyAttendanceItemAuthority domain) {
		return new DailyAttendanceItemAuthorityDto(
			domain.getCompanyID(),
			domain.getAuthorityDailyId(),
			domain.getListDisplayAndInputControl().stream().map(c->DisplayAndInputControlDto.fromDomain(c)).collect(Collectors.toList())
				);
	}
	
}
