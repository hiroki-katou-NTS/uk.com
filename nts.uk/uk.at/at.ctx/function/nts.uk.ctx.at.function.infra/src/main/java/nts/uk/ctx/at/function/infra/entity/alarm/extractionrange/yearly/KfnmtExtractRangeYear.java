package nts.uk.ctx.at.function.infra.entity.alarm.extractionrange.yearly;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.ctx.at.function.infra.entity.alarm.checkcondition.KfnmtCheckCondition;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@Table(name = "KFNMT_EXTRACT_RANGE_YEAR")
@NoArgsConstructor
public class KfnmtExtractRangeYear extends UkJpaEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public KfnmtExtractRangeYearPK pk;

	@Override
	protected Object getKey() {
		return pk;
	}
	
	@Column(name = "YEAR")
	public int year;
	
	@Column(name = "THIS_YEAR")
	public int thisYear;
	
	@OneToOne(mappedBy = "extractRangeYear", orphanRemoval = true)
	public KfnmtCheckCondition checkCondition;
	
	public void fromEntity(KfnmtExtractRangeYear newEntity) {
		this.year = newEntity.year;
		this.thisYear = newEntity.thisYear;
	}
}
