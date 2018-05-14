package nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
/**
 * 権限別日次項目制御
 * @author tutk
 *
 */
@Getter
public class DailyAttendanceItemAuthority extends AggregateRoot {
	
	/**会社ID*/
	private String companyID;
	/**ロール*/
	private String authorityDailyId;

	private List<DisplayAndInputControl> listDisplayAndInputControl = new ArrayList<>();

	public DailyAttendanceItemAuthority(String companyID, String authorityDailyId, List<DisplayAndInputControl> listDisplayAndInputControl) {
		super();
		this.companyID = companyID;
		this.authorityDailyId = authorityDailyId;
		this.listDisplayAndInputControl = listDisplayAndInputControl;
	}
}
