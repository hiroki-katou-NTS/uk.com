package nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.childcare;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.usenumber.DayNumberOfUse;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.usenumber.TimeOfUse;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.ClosureStatus;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

/**
 *　子の看護休暇月別残数データ
  * @author yuri_tamakoshi
 */
public class ChildcareNurseRemNumEachMonth extends AggregateRoot {

	/** 社員ID */
	private  String employeeId;
	/** 年月 */
	private  YearMonth yearMonth;
	/** 締めID */
	private  ClosureId closureId;
	/** 締め日付 */
	private  ClosureDate closureDate;



//	/** 締め処理状態 */
//	private ClosureStatus closureStatus;
//	/** 本年使用数 */
//	private ChildCareNurseUsedInfo thisYearUsedNumber;
//	/** 翌年使用数 */
//	private Optional<ChildCareNurseUsedInfo> nextYearUsedNumber;
//	/** 合計使用数 */
//	private ChildCareNurseUsedInfo usedNumber;

	/**
	 * コンストラクタ
	 * @param employeeId 社員ID
	 * @param yearMonth 年月
	 * @param closureId 締めID
	 * @param closureDate 締め日
	 */
	public ChildcareNurseRemNumEachMonth(
			String employeeId,
			YearMonth yearMonth,
			ClosureId closureId,
			ClosureDate closureDate){

		super();
		this.employeeId = employeeId;
		this.yearMonth = yearMonth;
		this.closureId = closureId;
		this.closureDate = closureDate;
//		this.closureStatus = ClosureStatus.UNTREATED;
//		this.thisYearUsedNumber = new ChildCareNurseUsedInfo();
//		this.nextYearUsedNumber = Optional.empty();
//		this.usedNumber = new ChildCareNurseUsedInfo();
	}

	/**
	 * ファクトリー
	 * @param employeeId 社員ID
	 * @param yearMonth 年月
	 * @param closureId 締めID
	 * @param closureDate 締め日付
	 * @param closureStatus 締め処理状態
	 * @param ThisYearUsedNumber 本年使用数
	 * @param NextYearUsedNumber  翌年使用数
	 * @param usedNumber 合計使用数
	 * @return 育児介護休暇月別残数データ
	 */
	public static ChildcareNurseRemNumEachMonth of(
			String employeeId,
			YearMonth yearMonth,
			ClosureId closureId,
			ClosureDate closureDate){
//			ClosureStatus closureStatus,
//			ChildCareNurseUsedInfo ThisYearUsedNumber,
//			Optional<ChildCareNurseUsedInfo> NextYearUsedNumber,
//			ChildCareNurseUsedInfo usedNumber)


		ChildcareNurseRemNumEachMonth domain = new ChildcareNurseRemNumEachMonth(
				employeeId, yearMonth, closureId, closureDate);
//		domain.closureStatus = closureStatus;
//		domain.thisYearUsedNumber = ThisYearUsedNumber;
//		domain.nextYearUsedNumber = NextYearUsedNumber;
//		domain.usedNumber = usedNumber;
		return domain;
	}
}
