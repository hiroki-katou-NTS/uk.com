package nts.uk.ctx.at.shared.app.command.scherec.dailyattendanceitem;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.DailyAttendanceItemAuthority;

@Getter
@Setter
@NoArgsConstructor
public class DailyAttendanceItemAuthorityCmd {

	private String companyID;
	private String authorityDailyId;
	private List<DisplayAndInputControlCmd> displayAndInput;
	public DailyAttendanceItemAuthorityCmd(String companyID, String authorityDailyId, List<DisplayAndInputControlCmd> displayAndInput) {
		super();
		this.companyID = companyID;
		this.authorityDailyId = authorityDailyId;
		this.displayAndInput = displayAndInput;
	}
	
	public static DailyAttendanceItemAuthority fromCommand(DailyAttendanceItemAuthorityCmd command) {
		return new DailyAttendanceItemAuthority(
				command.getCompanyID(),
				command.getAuthorityDailyId(),
				command.getDisplayAndInput().stream().map(c->DisplayAndInputControlCmd.fromCommand(c)).collect(Collectors.toList())
				);
	}
	
	
}
