package nts.uk.ctx.at.shared.dom.yearholidaygrant;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * 付与条件
 * 
 * @author TanLV
 *
 */

@AllArgsConstructor
@Getter
public class GrantCondition implements Serializable{
	/* 会社ID */
	private String companyId;
	
	/* 年休付与テーブル設定コード */
	private YearHolidayCode yearHolidayCode;
	
	/* 条件NO */
	private int conditionNo;
	
	/* 条件値 */
	private Optional<ConditionValue> conditionValue;

	/* 条件利用区分 */
	private UseConditionAtr useConditionAtr;
	
	
	protected void validate(CalculationMethod method){
		
		// 利用区分がTRUEの付与条件は、選択されている計算方法の条件値が入力されていること
		if (this.isUse() && !this.getConditionValue().isPresent()) {
			throw new BusinessException("Msg_271");
		}
		
		// 付与日数の計算対象」が「出勤率」の場合、条件値<=100
		if (CalculationMethod.ATTENDENCE_RATE.equals(method)) {
			if (this.isOver(100)) {
				throw new BusinessException("Msg_262");
			}
		}
		
		// 付与日数の計算対象」が「労働日数」の場合、条件値<=366
		if (CalculationMethod.WORKING_DAY.equals(method)) {
			if (this.isOver(366)) {
				throw new BusinessException("Msg_263");
			}
		}
		
	}

	/**
	 * 年休付与テーブルが登録されているか
	 * @param require
	 * @return
	 */
	public boolean isHadSet(Require require){
		return !(require.findByCode(getCompanyId(), getConditionNo(), getYearHolidayCode().toString()).isEmpty());
	}

	/**
	 * 使用する設定か判断する
	 * @return
	 */
	public boolean isUse(){
		return this.getUseConditionAtr().equals(UseConditionAtr.USE);
	}
	
	/**条件値を取得する
	 * 外部の層のクラスに変換する時に使用する。
	 *項目移送以外では使用しないこと。
	 * @return
	 */
	public Double getConditionValueToDouble(){
		return this.conditionValue.map(x->x.v()).orElse(null);
	}
	
	/**
	 * 条件値を超えているか
	 * @param value
	 * @return
	 */
	protected boolean isOver(double value){
		if(!this.isUse())return false;
		return this.getConditionValue().get().v() > value;
	}
	
	/**
	 * 条件値以内か
	 * @param value
	 * @return
	 */
	protected boolean isLessThanEqual(double value){
		if(!this.isUse())return false;
		return this.getConditionValue().get().v() <= value;
	}
	
	/**
	 * 期間で按分した付与条件を取得する
	 * @param period
	 * @return
	 */
	public GrantCondition toDivideProportionately(Optional<DatePeriod> period){
		return new GrantCondition(this.companyId, this.yearHolidayCode, this.conditionNo,
				this.calcConditionValue(period), this.useConditionAtr);
	}
	
	/**
	 * 期間で按分した条件値を計算する
	 * @param calculationMethod
	 * @param period
	 * @return
	 */
	private Optional<ConditionValue> calcConditionValue(Optional<DatePeriod> period){
		
		if(!this.isUse()){
			return Optional.empty();
		}
		if(!period.isPresent()){
			return this.conditionValue;
		}
	
		int calendarDay = period.get().start().daysTo(period.get().end().addDays(1));
		if(calendarDay >= 365){
			return this.conditionValue;
		}
		
		return Optional.of(new ConditionValue(Math.floor(this.getConditionValue().get().v() * (calendarDay / 365.0))));
	}
	
	public static interface Require{
		List<GrantHdTbl> findByCode(String companyId, int conditionNo, String yearHolidayCode);
	}
}
