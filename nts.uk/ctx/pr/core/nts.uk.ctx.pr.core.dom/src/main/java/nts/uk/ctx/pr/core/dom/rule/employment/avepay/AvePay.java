package nts.uk.ctx.pr.core.dom.rule.employment.avepay;


import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.AggregateRoot;

/**
 * 平均賃金計算設定マスタ
 * Average wage calculation setting master
 * @author Doan Duy Hung
 *
 */
public class AvePay extends AggregateRoot {
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
		if (this.roundDigitSet == null) {
			throw new BusinessException("ERR20");
		}
	} 
	
	public AvePay(AttendDayGettingSet attendDayGettingSet, ExceptionPayRate exceptionPayRate,
			RoundDigitSet roundDigitSet, RoundTimingSet roundTimingSet) {
		super();
		this.attendDayGettingSet = attendDayGettingSet;
		this.exceptionPayRate = exceptionPayRate;
		this.roundDigitSet = roundDigitSet;
		this.roundTimingSet = roundTimingSet;
	}

	public static AvePay createFromJavaType(int attendDayGettingSet, int exceptionPayRate, int roundTimingSet, int roundDigitSet) {
		return new AvePay(
				EnumAdaptor.valueOf(attendDayGettingSet, AttendDayGettingSet.class) ,
				new ExceptionPayRate(exceptionPayRate), 
				EnumAdaptor.valueOf(roundDigitSet, RoundDigitSet.class),
				EnumAdaptor.valueOf(roundTimingSet, RoundTimingSet.class));
	}
	
	
	
}
