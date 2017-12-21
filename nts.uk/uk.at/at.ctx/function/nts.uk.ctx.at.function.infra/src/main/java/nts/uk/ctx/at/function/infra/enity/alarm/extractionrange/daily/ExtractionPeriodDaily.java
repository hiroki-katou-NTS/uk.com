package nts.uk.ctx.at.function.infra.enity.alarm.extractionrange.daily;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name="KFNMT_EXTRACT_PER_DAILY")
@AllArgsConstructor
@NoArgsConstructor

/**抽出する範囲*/
public class ExtractionPeriodDaily{

	/**Id*/
	@Column(name = "EXTRACTION_ID")
	private String extractionId;
	
	/**抽出する範囲*/
	@Column(name = "EXTRACTION_RANGE")
	private int extractionRange;
	
	/**日*/
	@Column(name = "DAY")
	private int day;
	
	/**当日とする*/
	@Column(name = "MAKE_TO_DAY")
	private boolean makeToDay;
}
