package nts.uk.ctx.hr.develop.infra.entity.humanresourcedev.hryear;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.ctx.hr.develop.dom.humanresourcedev.hryear.HrPeriodRegulation;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

//JSHMT_HR_PERIOD_REG
/**
 * 
 * @author hieult
 *
 */
@Entity
@NoArgsConstructor

@Table(name = "JSHMT_HR_PERIOD_REG")
public class JshmtHrPeriodReg extends UkJpaEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Column(name = "CID")
	public String companyId;
	
	@Id
	@Column(name = "HIST_ID")
	public String historyId;
	
	@Column(name = "YEAR_START_DATE")
	public int yearStartDate;
	
	@Column(name = "YEAR_END_DATE")
	public int yearEndDate;
	
	@Column(name = "YEAR_START_MONTH")
	public int yearStartMonth;
	
	@Column(name = "YEAR_END_MONTH")
	public int yearEndMonth;
	
	@Override
	protected Object getKey() {
		
		return historyId;
	}
	public JshmtHrPeriodReg(String companyId, String historyId, int yearStartDate, int yearEndDate,
			int yearStartMonth, int yearEndMonth) {
		super();
		this.companyId = companyId;
		this.historyId = historyId;
		this.yearStartDate = yearStartDate;
		this.yearEndDate = yearEndDate;
		this.yearStartMonth = yearStartMonth;
		this.yearEndMonth = yearEndMonth;
	}

	public HrPeriodRegulation toDomain() 
	{
		return new HrPeriodRegulation(
				this.companyId,
				this.historyId,
				this.yearStartDate,
				this.yearEndDate,
				this.yearStartMonth,
				this.yearEndMonth);
	}


}
