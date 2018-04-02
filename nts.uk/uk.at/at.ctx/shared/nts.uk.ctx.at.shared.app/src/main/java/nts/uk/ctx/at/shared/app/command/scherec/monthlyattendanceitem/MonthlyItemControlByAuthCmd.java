package nts.uk.ctx.at.shared.app.command.scherec.monthlyattendanceitem;

import java.util.ArrayList;
import java.util.List;

import nts.uk.ctx.at.shared.dom.scherec.monthlyattendanceitem.DisplayAndInputMonthly;

public class MonthlyItemControlByAuthCmd {
	/**会社ID*/
	private String companyId;
	/**ロール*/
	private String authorityMonthlyId;
	
	List<DisplayAndInputMonthly> listDisplayAndInputMonthly = new ArrayList<>();
}
