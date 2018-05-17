package nts.uk.ctx.at.shared.app.command.scherec.monthlyattendanceitem;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattendanceitem.DisplayAndInputMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattendanceitem.MonthlyItemControlByAuthority;

@Setter
@Getter
@NoArgsConstructor
public class MonthlyItemControlByAuthCmd {

	/**会社ID*/
	private String companyId;
	/**ロール*/
	private String authorityMonthlyId;
	
	List<DisplayAndInputMonthCmd> listDisplayAndInputMonthly = new ArrayList<>();
	
	public MonthlyItemControlByAuthCmd(String companyId, String authorityMonthlyId, List<DisplayAndInputMonthCmd> listDisplayAndInputMonthly) {
		super();
		this.companyId = companyId;
		this.authorityMonthlyId = authorityMonthlyId;
		this.listDisplayAndInputMonthly = listDisplayAndInputMonthly;
	}
	
	public static MonthlyItemControlByAuthority fromCommand(MonthlyItemControlByAuthCmd command) {
		return new MonthlyItemControlByAuthority(
				command.getCompanyId(),
				command.getAuthorityMonthlyId(),
				command.getListDisplayAndInputMonthly().stream().map(c->DisplayAndInputMonthCmd.fromCommand(c)).collect(Collectors.toList())
				);
	}

}
