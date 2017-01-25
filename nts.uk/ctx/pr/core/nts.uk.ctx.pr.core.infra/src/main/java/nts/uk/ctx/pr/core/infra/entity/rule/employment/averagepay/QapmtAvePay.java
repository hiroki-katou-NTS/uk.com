package nts.uk.ctx.pr.core.infra.entity.rule.employment.averagepay;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.TableEntity;


@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="QAPMT_AVE_PAY")
public class QapmtAvePay extends TableEntity{
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((companyCode == null) ? 0 : companyCode.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		QapmtAvePay other = (QapmtAvePay) obj;
		if (companyCode == null) {
			if (other.companyCode != null)
				return false;
		} else if (!companyCode.equals(other.companyCode))
			return false;
		return true;
	}

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
