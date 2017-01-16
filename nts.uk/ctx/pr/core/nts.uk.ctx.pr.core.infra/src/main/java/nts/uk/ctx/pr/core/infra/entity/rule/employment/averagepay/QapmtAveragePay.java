package nts.uk.ctx.pr.core.infra.entity.rule.employment.averagepay;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="QAPMT_AVE_PAY")
public class QapmtAveragePay {
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
	
}
