package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.publicholiday;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveGrantDayNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveRemainingDayNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveUsedDayNumber;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.ClosureStatus;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.time.calendar.date.ClosureDate;


/**
 * 公休月別残数データ
 * @author hayata_maekawa
 *
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PublicHolidayRemNumEachMonth extends AggregateRoot{

	/** 社員ID */
	private  String employeeId;
	/** 年月 */
	private  YearMonth yearMonth;
	/** 締めID */
	private  ClosureId closureId;
	/** 締め日 */
	private  ClosureDate closureDate;
	/** 締め処理状態 */
	private ClosureStatus closureStatus;
	/** 公休日数 */
	private LeaveGrantDayNumber publicHolidayday;
	/** 繰越数 */
	private LeaveRemainingDayNumber carryForwardNumber;
	/** 取得数 */
	private LeaveUsedDayNumber numberOfAcquisitions;
	/** 翌月繰越数 */
	private LeaveRemainingDayNumber numberCarriedOverToTheNextMonth;
	/** 未消化数 */
	private LeaveRemainingDayNumber unDegestionNumber;
	
	
	
}
