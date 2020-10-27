package nts.uk.ctx.at.function.infra.entity.alarm.extractionrange.periodunit;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode

public class KfnmtAlstPtnDeftmcyclePK implements Serializable  {

	private static final long serialVersionUID = 1L;
    /** 抽出する範囲ID */
	@Column (name = "EXTRACTION_ID")
	public String extractionID;
	
	/** 抽出する範囲 */
	@Column (name = "EXTRACTION_RANGE")
	public int extractionRange;
	
}
