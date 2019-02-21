package nts.uk.ctx.at.function.infra.entity.alarm.extractionrange.mutilmonth;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.StandardMonth;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.month.mutilmonth.AverageMonth;
import nts.uk.ctx.at.function.infra.entity.alarm.checkcondition.KfnmtCheckCondition;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@Table(name = "KFNMT_EXTRACT_AVER_MONTH")
@NoArgsConstructor
public class KfnmtExtractAverageMonth extends UkJpaEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public KfnmtExtractAverageMonthPK pk;

	@Override
	protected Object getKey() {
		return pk;
	}
	
	@Column(name = "MONTH")
	public int month;
	
	
	@OneToOne(mappedBy = "extractRangeYear", orphanRemoval = true)
	public KfnmtCheckCondition checkCondition;
	
	public void fromEntity(KfnmtExtractAverageMonth newEntity) {
		this.month = newEntity.month;
	}
	
	public AverageMonth toDomain() {
		return new AverageMonth(this.pk.extractionId, this.pk.extractionRange, EnumAdaptor.valueOf(month, StandardMonth.class));
	}
	
	public KfnmtExtractAverageMonth(AverageMonth domain) {
		this.pk = new KfnmtExtractAverageMonthPK(domain.getExtractionId(), domain.getExtractionRange().value);
		this.month = domain.getMonth().value;
	}
	public static KfnmtExtractAverageMonth toEntity(AverageMonth domain) {
		return new KfnmtExtractAverageMonth(domain);
	}
}
