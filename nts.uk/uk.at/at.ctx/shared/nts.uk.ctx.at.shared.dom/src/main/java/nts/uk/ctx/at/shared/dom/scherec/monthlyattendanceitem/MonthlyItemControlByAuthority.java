package nts.uk.ctx.at.shared.dom.scherec.monthlyattendanceitem;
/**
 * 権限別月次項目制御
 * @author tutk
 *
 */

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;


@Getter
public class MonthlyItemControlByAuthority extends AggregateRoot {
	/**会社ID*/
	private String companyId;
	/**ロール*/
	private String authorityMonthlyId;
	
	List<DisplayAndInputMonthly> listDisplayAndInputMonthly = new ArrayList<>();

	public MonthlyItemControlByAuthority(String companyId, String authorityMonthlyId, List<DisplayAndInputMonthly> listDisplayAndInputMonthly) {
		super();
		this.companyId = companyId;
		this.authorityMonthlyId = authorityMonthlyId;
		this.listDisplayAndInputMonthly = listDisplayAndInputMonthly;
	}
	
}
