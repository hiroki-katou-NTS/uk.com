package nts.uk.ctx.at.shared.dom.specialholiday.export;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.AttendanceRate;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.YearDayNumber;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantDays;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantNum;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.LimitedHalfHdCnt;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.LimitedTimeHdDays;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.LimitedTimeHdTime;

/**
 * 次回特休付与
 * @author masaaki_jinno
 *
 */
@Getter
@Setter
public class NextSpecialLeaveGrant {

	/** 付与年月日 */
	private GeneralDate grantDate;
	/** 付与日数 */
	private GrantDays grantDays;
	/** 回数 */
	private GrantNum times;
	/** 期限日 */
	private GeneralDate deadLine;
	
	/**
	 * コンストラクタ
	 */
	public NextSpecialLeaveGrant(){
		
		this.grantDate = GeneralDate.today();
		this.grantDays = new GrantDays(0.0);
		this.times = new GrantNum(0);
	}
}
