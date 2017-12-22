package nts.uk.ctx.at.function.infra.enity.alarm.extractionrange.daily;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
public class ExtractionPeriodDaily extends UkJpaEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Override
	protected Object getKey() {
		return null;
	}
	
	/**Id*/
	@Id
	@Column(name = "EXTRACTION_ID")
	private String extractionId;
	
	/**抽出する範囲*/
	@Column(name = "EXTRACTION_RANGE")
	private int extractionRange;
	
	/**前・先区分*/
	@Column(name = "PREVIOUS_CLASSIFICATION")
	private int previousClassification;
	
	/**日*/
	@Column(name = "DAY")
	private int day;
	
	/**本年度とする*/
	@Column(name = "MAKE_TO_DAY")
	private boolean makeToDay;
	
	/**開始日の指定方法*/
	@Column(name = "SPECIFY_STARTDATE")
	private int specifyStartDate;
	
	/**終了日の指定方法*/
	@Column(name = "SPECIFY_ENDDATE")
	private int specifyEndDate;
}
