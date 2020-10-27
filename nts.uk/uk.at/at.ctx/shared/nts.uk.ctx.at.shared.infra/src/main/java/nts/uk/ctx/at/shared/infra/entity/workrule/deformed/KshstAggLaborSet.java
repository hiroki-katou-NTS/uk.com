package nts.uk.ctx.at.shared.infra.entity.workrule.deformed;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;


/**
 * The persistent class for the KSHST_AGG_LABOR_SET database table.
 * @author HoangNDH
 * 変形労働の集計設定
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="KSHST_AGG_LABOR_SET")
public class KshstAggLaborSet extends ContractUkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private KshstAggLaborSetPK id;
	
	/** 変形労働を使用する */
	@Column(name="USE_DEFORM_LABOR")
	private long useDeformLabor;

	@Override
	protected Object getKey() {
		return id;
	}


}