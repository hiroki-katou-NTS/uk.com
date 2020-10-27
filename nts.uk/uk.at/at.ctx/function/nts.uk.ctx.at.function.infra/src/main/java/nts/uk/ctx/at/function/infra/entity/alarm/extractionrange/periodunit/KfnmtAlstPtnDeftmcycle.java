package nts.uk.ctx.at.function.infra.entity.alarm.extractionrange.periodunit;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.periodunit.ExtractionPeriodUnit;
import nts.uk.ctx.at.function.infra.entity.alarm.checkcondition.KfnmtAlstPtnDeftm;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KFNMT_ALST_PTN_DEFTMCYCLE")
public class KfnmtAlstPtnDeftmcycle extends ContractUkJpaEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KfnmtAlstPtnDeftmcyclePK pk;

	@Column(name = "SEGMENTATION_OF_CYCLE")
	public int segmentationOfCycle;

	@OneToOne(mappedBy = "extractionPerUnit", orphanRemoval = true)
	public KfnmtAlstPtnDeftm checkCondition;
	
	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return this.pk;
	}

	public ExtractionPeriodUnit toDomain() {
		return new ExtractionPeriodUnit(pk.extractionID, pk.extractionRange, segmentationOfCycle);
	}

	public static KfnmtAlstPtnDeftmcycle toEntity(ExtractionPeriodUnit domain) {
		return new KfnmtAlstPtnDeftmcycle(
				new KfnmtAlstPtnDeftmcyclePK(domain.getExtractionId(), domain.getExtractionRange().value),
				domain.getSegmentationOfCycle().value);
	}

	public KfnmtAlstPtnDeftmcycle(String extractionID, int extractionRange, int segmentationOfCycle) {
		this.pk = new KfnmtAlstPtnDeftmcyclePK(extractionID , extractionRange);
		this.segmentationOfCycle = segmentationOfCycle;
	}
	
	public void fromEntity(KfnmtAlstPtnDeftmcycle newEntity){
		this.segmentationOfCycle = newEntity.segmentationOfCycle;
	}

	public KfnmtAlstPtnDeftmcycle(KfnmtAlstPtnDeftmcyclePK pk, int segmentationOfCycle) {
		super();
		this.pk = pk;
		this.segmentationOfCycle = segmentationOfCycle;
	}
}
