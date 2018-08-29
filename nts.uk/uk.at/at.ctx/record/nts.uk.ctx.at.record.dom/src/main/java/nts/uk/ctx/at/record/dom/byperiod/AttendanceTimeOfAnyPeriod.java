package nts.uk.ctx.at.record.dom.byperiod;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.byperiod.anyaggrperiod.AnyAggrFrameCode;
import nts.uk.ctx.at.record.dom.monthly.totalcount.TotalCountByPeriod;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.VerticalTotalOfMonthly;

/**
 * 任意期間別実績の勤怠時間
 * @author shuichu_ishida
 */
@Getter
public class AttendanceTimeOfAnyPeriod extends AggregateRoot {

	/** 社員ID */
	private final String employeeId;
	/** 任意集計枠コード */
	private final AnyAggrFrameCode anyAggrFrameCode;

	/** 月の集計 */
	private MonthlyCalculationByPeriod monthlyAggregation;
	/** 時間外超過 */
	private ExcessOutsideByPeriod excessOutside;
	/** 36協定時間 */
	private AgreementTimeByPeriod agreementTime;
	/** 縦計 */
	private VerticalTotalOfMonthly verticalTotal;
	/** 回数集計 */
	private TotalCountByPeriod totalCount;
	/** 任意項目 */
	private AnyItemByPeriod anyItem;

	/**
	 * コンストラクタ
	 * @param employeeId 社員ID
	 * @param anyAggrFrameCode 任意集計枠コード
	 */
	public AttendanceTimeOfAnyPeriod(String employeeId, AnyAggrFrameCode anyAggrFrameCode){
		
		super();
		this.employeeId = employeeId;
		this.anyAggrFrameCode = anyAggrFrameCode;
		
		this.monthlyAggregation = new MonthlyCalculationByPeriod();
		this.excessOutside = new ExcessOutsideByPeriod();
		this.agreementTime = new AgreementTimeByPeriod();
		this.verticalTotal = new VerticalTotalOfMonthly();
		this.totalCount = new TotalCountByPeriod();
		this.anyItem = new AnyItemByPeriod();
	}
	
	/**
	 * ファクトリー
	 * @param employeeId 社員ID
	 * @param anyAggrFrameCode 任意集計枠コード
	 * @param monthlyAggregation 月の集計
	 * @param excessOutside 時間外超過
	 * @param agreementTime 36協定時間
	 * @param verticalTotal 縦計
	 * @param totalCount 回数集計
	 * @param anyItem 任意項目
	 * @return 任意期間別実績の勤怠時間
	 */
	public static AttendanceTimeOfAnyPeriod of(
			String employeeId,
			AnyAggrFrameCode anyAggrFrameCode,
			MonthlyCalculationByPeriod monthlyAggregation,
			ExcessOutsideByPeriod excessOutside,
			AgreementTimeByPeriod agreementTime,
			VerticalTotalOfMonthly verticalTotal,
			TotalCountByPeriod totalCount,
			AnyItemByPeriod anyItem){
		
		AttendanceTimeOfAnyPeriod domain = new AttendanceTimeOfAnyPeriod(employeeId, anyAggrFrameCode);
		domain.monthlyAggregation = monthlyAggregation;
		domain.excessOutside = excessOutside;
		domain.agreementTime = agreementTime;
		domain.verticalTotal = verticalTotal;
		domain.totalCount = totalCount;
		domain.anyItem = anyItem;
		return domain;
	}
}
