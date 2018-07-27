package nts.uk.ctx.at.shared.app.find.scherec.monthlyattditem;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattendanceitem.MonthlyItemControlByAuthority;

@Getter
@Setter
@NoArgsConstructor
public class MonthlyItemControlByAuthDto {

	/**会社ID*/
	private String companyId;
	/**ロール*/
	private String authorityMonthlyId;
	
	List<DisplayAndInputMonthlyDto> listDisplayAndInputMonthly = new ArrayList<>();

	public MonthlyItemControlByAuthDto(String companyId, String authorityMonthlyId, List<DisplayAndInputMonthlyDto> listDisplayAndInputMonthly) {
		super();
		this.companyId = companyId;
		this.authorityMonthlyId = authorityMonthlyId;
		this.listDisplayAndInputMonthly = listDisplayAndInputMonthly;
	}
	
	public static MonthlyItemControlByAuthDto fromDomain( MonthlyItemControlByAuthority domain) {
		return new MonthlyItemControlByAuthDto(
				domain.getCompanyId(),
				domain.getAuthorityMonthlyId(),
				domain.getListDisplayAndInputMonthly().stream().map(c->DisplayAndInputMonthlyDto.fromDomain(c)).collect(Collectors.toList())
				);
	}
	
}
