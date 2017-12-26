package nts.uk.ctx.at.function.infra.entity.alarm.extractionrange.daily;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.daily.ExtractionPeriodDaily;
import nts.uk.ctx.at.function.infra.entity.alarm.checkcondition.KfnmtCheckCondition;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@Table(name="KFNMT_EXTRACT_PER_DAILY")
@NoArgsConstructor

public class KfnmtExtractionPeriodDaily extends UkJpaEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public KfnmtExtractionPeriodDailyPK kfnmtExtractionPeriodDailyPK;

	@Override
	protected Object getKey() {
		return kfnmtExtractionPeriodDailyPK;
	}
	
	@Column(name = "STR_SPECIFY")
	public int strSpecify;
	
	@Column(name = "STR_PREVIOUS_CLASSIFICATION")
	public int strPreviousClassification;
	
	@Column(name = "STR_MAKE_TO_DAY")
	public int strMakeToDay;
	
	@Column(name = "STR_CURRENT_MONTH")
	public int strCurrentMonth;
	
	@Column(name = "STR_DAY")
	public int strDay;
	
	@Column(name = "STR_MONTH")
	public int strMonth;
	
	@Column(name = "END_SPECIFY")
	public int endSpecify;
	
	@Column(name = "END_PREVIOUS_CLASSIFICATION")
	public int endPreviousClassification;
	
	@Column(name = "END_MAKE_TO_DAY")
	public int endMakeToDay;
	
	@Column(name = "END_CURRENT_MONTH")
	public int endCurrentMonth;
	
	@Column(name = "END_DAY")
	public int endDay;
	
	@Column(name = "END_MONTH")
	public int endMonth;
	
	
	@OneToOne(mappedBy = "extractionPeriodDaily")
	public KfnmtCheckCondition checkCondition;

	public KfnmtExtractionPeriodDaily(KfnmtExtractionPeriodDailyPK kfnmtExtractionPeriodDailyPK, int strSpecify,
			int strPreviousClassification, int strMakeToDay, int strCurrentMonth, int strDay, int strMonth,
			int endSpecify, int endPreviousClassification, int endMakeToDay, int endCurrentMonth, int endDay,
			int endMonth) {
		super();
		this.kfnmtExtractionPeriodDailyPK = kfnmtExtractionPeriodDailyPK;
		this.strSpecify = strSpecify;
		this.strPreviousClassification = strPreviousClassification;
		this.strMakeToDay = strMakeToDay;
		this.strCurrentMonth = strCurrentMonth;
		this.strDay = strDay;
		this.strMonth = strMonth;
		this.endSpecify = endSpecify;
		this.endPreviousClassification = endPreviousClassification;
		this.endMakeToDay = endMakeToDay;
		this.endCurrentMonth = endCurrentMonth;
		this.endDay = endDay;
		this.endMonth = endMonth;
	}
	
	public static ExtractionPeriodDaily toDomain() {
		
		
		return null;
	}
	
	public static KfnmtExtractionPeriodDaily toEntity(ExtractionPeriodDaily domain) {
		return null;
	}
}
