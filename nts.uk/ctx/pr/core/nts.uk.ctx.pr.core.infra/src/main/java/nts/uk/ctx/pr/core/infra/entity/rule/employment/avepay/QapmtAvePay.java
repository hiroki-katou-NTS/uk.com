package nts.uk.ctx.pr.core.infra.entity.rule.employment.avepay;


import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Table(name="QAPMT_AVE_PAY")
public class QapmtAvePay {
	@Id
	@Column(name="CCD")
	public String companyCode;
	
	@Column(name="ROUND_DIGIT_SET")
	public int roundDigitSet;
	
	@Column(name="ATTEND_DAY_GETTING_SET")
	public int attendDayGettingSet;
	
	@Column(name="ROUND_TIMING_SET")
	public int roundTimingSet;
	
	@Column(name="EXCEPTION_PAY_RATE")
	public int exceptionPayRate;

/*
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="QAPMT_AVE_PAY")
public class QapmtAveragePay {
		@Id
		@Column(name="CCD")
		private String companyCode;
		
		@Column(name="ROUND_DIGIT_SET")
		private int roundDigitSet;
		
		@Column(name="ATTEND_DAY_GETTING_SET")
		private int attendDayGettingSet;
		
		@Column(name="ROUND_TIMING_SET")
		private int roundTimingSet;
		
		@Column(name="EXCEPTION_PAY_RATE")
		private int exceptionPayRate;
*/
}
