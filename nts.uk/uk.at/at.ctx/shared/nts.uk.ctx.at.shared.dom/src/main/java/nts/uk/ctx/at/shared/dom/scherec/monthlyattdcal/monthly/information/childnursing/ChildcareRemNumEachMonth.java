package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.information.childnursing;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.common.Day;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.ClosureStatus;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.childcarenurse.ChildcareNurseRemNumEachMonth;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

/**
 * @author phongtq
 * 子の看護休暇月別残数データ
 */
@Getter
@AllArgsConstructor
public class ChildcareRemNumEachMonth extends AggregateRoot{

	/** 社員ID */
	private  String employeeId;
	/** 年月 */
	private  YearMonth yearMonth;
	/** 締めID */
	private  ClosureId closureId;
	/** 締め日付 */
	private  ClosureDate closureDate;
	/** 締め処理状態 */
	private ClosureStatus closureStatus;
	/** 子の看護休暇月別残数データ */
	private ChildcareNurseRemNumEachMonth remNumEachMonth;

	/**
	 * コンストラクタ
	 */
	public ChildcareRemNumEachMonth(
			String employeeId,
			YearMonth yearMonth,
			ClosureId closureId,
			ClosureDate closureDate,
			ChildcareNurseRemNumEachMonth rem){

		super();
		this.employeeId = employeeId;
		this.yearMonth = yearMonth;
		this.closureId = closureId;
		this.closureDate = closureDate;
		this.closureStatus = ClosureStatus.UNTREATED;
		this.remNumEachMonth = rem;
	}

}
