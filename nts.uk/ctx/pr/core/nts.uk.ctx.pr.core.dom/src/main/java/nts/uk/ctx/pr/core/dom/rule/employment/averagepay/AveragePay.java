package nts.uk.ctx.pr.core.dom.rule.employment.averagepay;


import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.core.dom.company.CompanyCode;

/**
 * 平均賃金計算設定マスタ
 * Average wage calculation setting master
 * @author Doan Duy Hung
 *
 */
public class AveragePay extends AggregateRoot {
	@Getter
	private CompanyCode companyCode;
	
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
	
	public AveragePay(CompanyCode companyCode, AttendDayGettingSet attendDayGettingSet, ExceptionPayRate exceptionPayRate,
			RoundDigitSet roundDigitSet, RoundTimingSet roundTimingSet) {
		super();
		this.companyCode = companyCode;
		this.attendDayGettingSet = attendDayGettingSet;
		this.exceptionPayRate = exceptionPayRate;
		this.roundDigitSet = roundDigitSet;
		this.roundTimingSet = roundTimingSet;
	}

	public static AveragePay createFromJavaType(String companyCode, int attendDayGettingSet, int exceptionPayRate, int roundTimingSet, int roundDigitSet) {
		return new AveragePay(
				new CompanyCode(companyCode),
				EnumAdaptor.valueOf(attendDayGettingSet, AttendDayGettingSet.class) ,
				new ExceptionPayRate(exceptionPayRate), 
				EnumAdaptor.valueOf(roundDigitSet, RoundDigitSet.class),
				EnumAdaptor.valueOf(roundTimingSet, RoundTimingSet.class));
	}
	
	
	
}
