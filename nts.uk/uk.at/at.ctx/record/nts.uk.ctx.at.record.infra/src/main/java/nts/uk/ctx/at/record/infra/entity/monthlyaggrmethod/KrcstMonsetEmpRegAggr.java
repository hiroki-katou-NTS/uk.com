package nts.uk.ctx.at.record.infra.entity.monthlyaggrmethod;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.infra.entity.monthlyaggrmethod.employment.KrcstMonsetEmpFlxAggr;
import nts.uk.ctx.at.record.infra.entity.monthlyaggrmethod.employment.KrcstMonsetEmpIrgAggr;
import nts.uk.ctx.at.record.infra.entity.monthlyaggrmethod.employment.KrcstMonsetEmpIrgExot;
import nts.uk.ctx.at.record.infra.entity.monthlyaggrmethod.employment.KrcstMonsetEmpIrgSetl;
import nts.uk.ctx.at.record.infra.entity.monthlyaggrmethod.employment.KrcstMonsetEmpRegExot;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * エンティティ：雇用月別実績集計設定
 * @author shuichi_ishida
 */
@Entity
@Table(name = "KRCST_MONSET_EMP_REG_AGGR")
@NoArgsConstructor
public class KrcstMonsetEmpRegAggr extends UkJpaEntity implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/** プライマリキー */
	@EmbeddedId
	public KrcstMonsetEmpRegAggrPK PK;

	/** 設定値 */
	@Embedded
	public KrcstMonsetRegAggr setValue;

	/** 通常勤務：時間外超過設定 */
	@OneToOne(cascade = CascadeType.ALL, mappedBy="krcstMonsetEmpRegAggr", orphanRemoval = true)
	public KrcstMonsetEmpRegExot krcstMonsetEmpRegExot;

	/** 変形労働時間勤務 */
	@OneToOne(cascade = CascadeType.ALL, mappedBy="krcstMonsetEmpRegAggr", orphanRemoval = true)
	public KrcstMonsetEmpIrgAggr krcstMonsetEmpIrgAggr;

	/** 変形労働時間勤務：時間外超過設定 */
	@OneToOne(cascade = CascadeType.ALL, mappedBy="krcstMonsetEmpRegAggr", orphanRemoval = true)
	public KrcstMonsetEmpIrgExot krcstMonsetEmpIrgExot;

    /** 変形労働時間勤務：精算期間 */
    @OneToMany(cascade = CascadeType.ALL, mappedBy="krcstMonsetEmpRegAggr", orphanRemoval = true)
    public List<KrcstMonsetEmpIrgSetl> krcstMonsetEmpIrgSetls;
	
	/** フレックス時間勤務 */
	@OneToOne(cascade = CascadeType.ALL, mappedBy="krcstMonsetEmpRegAggr", orphanRemoval = true)
	public KrcstMonsetEmpFlxAggr krcstMonsetEmpFlxAggr;
	
	/**
	 * キー取得
	 */
	@Override
	protected Object getKey() {
		return this.PK;
	}
}
