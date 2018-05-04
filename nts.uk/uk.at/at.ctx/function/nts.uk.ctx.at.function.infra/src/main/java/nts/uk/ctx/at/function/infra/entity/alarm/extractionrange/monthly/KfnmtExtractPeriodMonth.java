package nts.uk.ctx.at.function.infra.entity.alarm.extractionrange.monthly;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.ctx.at.function.infra.entity.alarm.checkcondition.KfnmtCheckCondition;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@Table(name = "KFNMT_EXTRACT_PER_MONTH")
@NoArgsConstructor
public class KfnmtExtractPeriodMonth extends UkJpaEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public KfnmtExtractPeriodMonthPK pk;

	@Override
	protected Object getKey() {
		return pk;
	}
	
	@Column(name = "STR_SPECIFY")
	public int strSpecify;
	
	@Column(name = "YEAR_TYPE")
	public int yearType;
	
	@Column(name = "SPECIFY_MONTH")
	public int specifyMonth;
	
	@Column(name = "STR_MONTH")
	public int strMonth;
	
	@Column(name = "STR_CURRENT_MONTH")
	public int strCurrentMonth;
	
	@Column(name = "STR_PREVIOUS_ATR")
	public int strPreviousAtr;
	
	@Column(name = "END_SPECIFY")
	public int endSpecify;
	
	@Column(name = "EXTRACT_PERIOD")
	public int extractPeriod;
	
	@Column(name = "END_MONTH")
	public int endMonth;
	
	@Column(name = "END_CURRENT_MONTH")
	public int endCurrentMonth;
	
	@Column(name = "END_PREVIOUS_ATR")
	public int endPreviousAtr;
	

	@ManyToOne
	public KfnmtCheckCondition checkCondition;
	
	
	public void fromEntity(KfnmtExtractPeriodMonth newEntity) {
		this.strSpecify = newEntity.strSpecify;
		this.yearType = newEntity.yearType;
		this.specifyMonth = newEntity.specifyMonth;
		this.strMonth = newEntity.strMonth;
		this.strCurrentMonth = newEntity.strCurrentMonth;		
		this.strPreviousAtr = newEntity.strPreviousAtr;
		this.endSpecify = newEntity.endSpecify;
		this.extractPeriod = newEntity.extractPeriod;
		this.endMonth = newEntity.endMonth;
		this.endCurrentMonth = newEntity.endCurrentMonth;
		this.endPreviousAtr = newEntity.endPreviousAtr;
	}
}
