package nts.uk.ctx.at.function.infra.entity.alarm.extractionrange.daily;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.daily.ExtractionPeriodDaily;
import nts.uk.ctx.at.function.infra.entity.alarm.checkcondition.KfnmtCheckCondition;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@Table(name="KFNMT_EXTRACT_PER_DAILY")
@AllArgsConstructor
@NoArgsConstructor

/**抽出する範囲*/
public class KfnmtExtractionPeriodDaily extends UkJpaEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public KfnmtExtractionPeriodDailyPK kfnmtExtractionPeriodDailyPK;

	@Override
	protected Object getKey() {
		return kfnmtExtractionPeriodDailyPK;
	}
	
	@Column(name = "PREVIOUS_CLASSIFICATION")
	public int previousClassification;
	
	@Column(name = "DAY")
	public int day;
	
	@Column(name = "MAKE_TO_DAY")
	public int makeToDay;
	
	@Column(name = "SPECIFY_STARTDATE")
	public int specifyStartDate;
	
	@Column(name = "SPECIFY_ENDDATE")
	public int specifyEndDate;
	
	@OneToOne
	@JoinColumns({
		@JoinColumn(name="EXTRACTION_ID", referencedColumnName="EXTRACTION_ID", insertable = false, updatable = false),
		@JoinColumn(name="EXTRACTION_RANGE", referencedColumnName="EXTRACTION_RANGE", insertable = false, updatable = false)
	})
	public KfnmtCheckCondition checkCondition;
	
	public ExtractionPeriodDaily toDomain() {
		return null;
	}
	
	public static KfnmtExtractionPeriodDaily toEntity(ExtractionPeriodDaily domain) {
		return null;
	}
}
