package nts.uk.ctx.at.function.infra.enity.alarm.extractionrange.daily;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Setter
@Getter
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
	public boolean makeToDay;
	
	@Column(name = "SPECIFY_STARTDATE")
	public int specifyStartDate;
	
	@Column(name = "SPECIFY_ENDDATE")
	public int specifyEndDate;
}
