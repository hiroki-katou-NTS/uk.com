package nts.uk.ctx.pr.core.dom.rule.employment.averagepay;


import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.AggregateRoot;
import nts.gul.text.StringUtil;

/**
 * 平均賃金計算設定マスタ
 * Average wage calculation setting master
 * @author Doan Duy Hung
 *
 */
public class AveragePay extends AggregateRoot {
	@Getter
	private String companyCode;
	
	@Getter
	private AttendDayGettingSet attendDayGettingSet;
	
	@Getter
	private ExceptionPayRate exceptionPayRate;
	
	@Getter
	private RoundDigitSet roundDigitSet;
	
	@Getter
	private RoundTimingSet roundTimingSet;
	
	@Override
	public void validate() {
		super.validate();
		if (StringUtil.isNullOrEmpty(this.roundDigitSet.toString(), true) ) {
			throw new BusinessException("ER001");
		}
	} 
	
	public AveragePay(String companyCode, AttendDayGettingSet attendDayGettingSet, ExceptionPayRate exceptionPayRate,
			RoundDigitSet roundDigitSet, RoundTimingSet roundTimingSet) {
		super();
		this.companyCode = companyCode;
		this.attendDayGettingSet = attendDayGettingSet;
		this.exceptionPayRate = exceptionPayRate;
		this.roundDigitSet = roundDigitSet;
		this.roundTimingSet = roundTimingSet;
	}
	
	/**
	 * check attend day select
	 * @return attend day statement
	 */
	public boolean isAttenDayStatementItem() {
		 return AttendDayGettingSet.SELECT_FROM_STATEMENT_ITEM.equals(this.attendDayGettingSet);
	}
}
