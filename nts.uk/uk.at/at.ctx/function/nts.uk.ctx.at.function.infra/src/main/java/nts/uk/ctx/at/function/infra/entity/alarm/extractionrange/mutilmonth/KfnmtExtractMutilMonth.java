package nts.uk.ctx.at.function.infra.entity.alarm.extractionrange.mutilmonth;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.StandardMonth;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.daily.StartSpecify;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.month.mutilmonth.MutilMonth;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.year.AYear;
import nts.uk.ctx.at.function.infra.entity.alarm.checkcondition.KfnmtCheckCondition;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@Table(name = "KFNMT_EXTRACT_RANGE_YEAR")
@NoArgsConstructor
public class KfnmtExtractMutilMonth extends UkJpaEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public KfnmtExtractMutilMonthPK pk;

	@Override
	protected Object getKey() {
		return pk;
	}
	
	@Column(name = "MONTH")
	public int month;
	
	
	@OneToOne(mappedBy = "extractRangeYear", orphanRemoval = true)
	public KfnmtCheckCondition checkCondition;
	
	public void fromEntity(KfnmtExtractMutilMonth newEntity) {
		this.month = newEntity.month;
	}
	
	public MutilMonth toDomain() {
		return new MutilMonth(this.pk.extractionId, this.pk.extractionRange, EnumAdaptor.valueOf(month, StandardMonth.class));
	}
	
	public KfnmtExtractMutilMonth(MutilMonth domain) {
		this.pk = new KfnmtExtractMutilMonthPK(domain.getExtractionId(), domain.getExtractionRange().value);
		this.month = domain.getMonth().value;
	}
	public static KfnmtExtractMutilMonth toEntity(MutilMonth domain) {
		return new KfnmtExtractMutilMonth(domain);
	}
}
