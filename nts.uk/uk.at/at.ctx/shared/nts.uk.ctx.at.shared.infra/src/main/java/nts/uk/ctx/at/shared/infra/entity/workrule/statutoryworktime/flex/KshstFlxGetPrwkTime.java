package nts.uk.ctx.at.shared.infra.entity.workrule.statutoryworktime.flex;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.flex.GetFlexPredWorkTime;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.flex.ReferencePredTimeOfFlex;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * エンティティ：フレックス勤務所定労働時間取得
 * @author shuichu_ishida
 */
@Entity
@Table(name = "KSHST_FLX_GET_PRWK_TIME")
@NoArgsConstructor
public class KshstFlxGetPrwkTime extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/** プライマリキー */
	@EmbeddedId
	public KshstFlxGetPrwkTimePK PK;
	
	/** 所定時間参照 */
	@Column(name = "REFERENCE_PRED_TIME")
	public int referencePredTime;
	
	/**
	 * キー取得
	 */
	@Override
	protected Object getKey() {
		return this.PK;
	}
	
	/**
	 * ドメインに変換
	 * @return フレックス勤務所定労働時間取得
	 */
	public GetFlexPredWorkTime toDomain(){

		return GetFlexPredWorkTime.of(
				this.PK.companyId,
				EnumAdaptor.valueOf(this.referencePredTime, ReferencePredTimeOfFlex.class));
	}
	
	/**
	 * ドメインから変換　（for Insert）
	 * @param domain フレックス勤務所定労働時間取得
	 */
	public void fromDomainForPersist(GetFlexPredWorkTime domain){
		
		this.PK = new KshstFlxGetPrwkTimePK(domain.getCompanyId());
		this.fromDomainForUpdate(domain);
	}
	
	/**
	 * ドメインから変換　(for Update)
	 * @param domain フレックス勤務所定労働時間取得
	 */
	public void fromDomainForUpdate(GetFlexPredWorkTime domain){
		
		this.referencePredTime = domain.getReference().value;
	}
}
