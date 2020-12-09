package nts.uk.ctx.at.function.infra.entity.alarm.extractionrange.mutilmonth;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.month.mutilmonth.AverageMonth;
import nts.uk.ctx.at.function.infra.entity.alarm.checkcondition.KfnmtAlstPtnDeftm;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * アラームリストのパターン設定 既定期間(基準月)
 * @author phongtq
 *
 */

@Entity
@Table(name = "KFNMT_ALST_PTN_DEFTMBSMON")
@NoArgsConstructor
public class KfnmtAlstPtnDeftmbsmon extends ContractUkJpaEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public KfnmtAlstPtnDeftmbsmonPK pk;

	@Override
	protected Object getKey() {
		return pk;
	}
	
	/** 基準月指定 */
	@Column(name = "STANDARD_MONTH")
	public int strMonth;
	
	
	@OneToOne(mappedBy = "alstPtnDeftmbsmon", orphanRemoval = true)
	public KfnmtAlstPtnDeftm checkCondition;
	
	public void fromEntity(KfnmtAlstPtnDeftmbsmon newEntity) {
		this.strMonth = newEntity.strMonth;
	}
	
	public AverageMonth toDomain() {
		return new AverageMonth(this.pk.extractionId, this.pk.extractionRange, this.strMonth);
	}
	
	public AverageMonth toDomainAdd() {
		return new AverageMonth(this.pk.extractionId, this.pk.extractionRange, this.strMonth);
	}
	
	public KfnmtAlstPtnDeftmbsmon(AverageMonth domain) {
		this.pk = new KfnmtAlstPtnDeftmbsmonPK(domain.getExtractionId(), domain.getExtractionRange().value);
		this.strMonth = domain.getStrMonth().value;
	}
	public static KfnmtAlstPtnDeftmbsmon toEntity(AverageMonth domain) {
		return new KfnmtAlstPtnDeftmbsmon(domain);
	}
}
