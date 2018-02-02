package nts.uk.ctx.at.function.infra.entity.alarm.extractionrange.periodunit;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.periodunit.ExtractionPeriodUnit;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KFNMT_EXTRACTION_PER_UNIT")
public class KfnmtExtractionPerUnit extends UkJpaEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KfnmtExtractionPerUnitPK pk;

	@Column(name = "SEGMENTATION_OF_CYCLE")
	public int segmentationOfCycle;

	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return this.pk;
	}

	public ExtractionPeriodUnit toDomain() {
		return new ExtractionPeriodUnit(pk.extractionID, pk.extractionRange, segmentationOfCycle);
	}

	public static KfnmtExtractionPerUnit toEntity(ExtractionPeriodUnit domain) {
		return new KfnmtExtractionPerUnit(
				new KfnmtExtractionPerUnitPK(domain.getExtractionId(), domain.getExtractionRange().value),
				domain.getSegmentationOfCycle().value);
	}

	public KfnmtExtractionPerUnit(String extractionID, int extractionRange, int segmentationOfCycle) {
		this.pk = new KfnmtExtractionPerUnitPK(extractionID , extractionRange);
		this.segmentationOfCycle = segmentationOfCycle;
	}
	
	public void fromEntity(KfnmtExtractionPerUnit newEntity){
		this.segmentationOfCycle = newEntity.segmentationOfCycle;
	}
}
