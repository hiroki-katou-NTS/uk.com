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
import nts.uk.ctx.at.record.infra.entity.monthlyaggrmethod.company.KrcstMonsetCmpFlxAggr;
import nts.uk.ctx.at.record.infra.entity.monthlyaggrmethod.company.KrcstMonsetCmpIrgAggr;
import nts.uk.ctx.at.record.infra.entity.monthlyaggrmethod.company.KrcstMonsetCmpIrgExot;
import nts.uk.ctx.at.record.infra.entity.monthlyaggrmethod.company.KrcstMonsetCmpIrgSetl;
import nts.uk.ctx.at.record.infra.entity.monthlyaggrmethod.company.KrcstMonsetCmpRegExot;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * エンティティ：会社月別実績集計設定
 * @author shuichi_ishida
 */
@Entity
@Table(name = "KRCST_MONSET_CMP_REG_AGGR")
@NoArgsConstructor
public class KrcstMonsetCmpRegAggr extends UkJpaEntity implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/** プライマリキー */
	@EmbeddedId
	public KrcstMonsetCmpRegAggrPK PK;

	/** 設定値 */
	@Embedded
	public KrcstMonsetRegAggr setValue;

	/** 通常勤務：時間外超過設定 */
	@OneToOne(cascade = CascadeType.ALL, mappedBy="krcstMonsetCmpRegAggr", orphanRemoval = true)
	public KrcstMonsetCmpRegExot krcstMonsetCmpRegExot;

	/** 変形労働時間勤務 */
	@OneToOne(cascade = CascadeType.ALL, mappedBy="krcstMonsetCmpRegAggr", orphanRemoval = true)
	public KrcstMonsetCmpIrgAggr krcstMonsetCmpIrgAggr;

	/** 変形労働時間勤務：時間外超過設定 */
	@OneToOne(cascade = CascadeType.ALL, mappedBy="krcstMonsetCmpRegAggr", orphanRemoval = true)
	public KrcstMonsetCmpIrgExot krcstMonsetCmpIrgExot;

    /** 変形労働時間勤務：精算期間 */
    @OneToMany(cascade = CascadeType.ALL, mappedBy="krcstMonsetCmpRegAggr", orphanRemoval = true)
    public List<KrcstMonsetCmpIrgSetl> krcstMonsetCmpIrgSetls;
	
	/** フレックス時間勤務 */
	@OneToOne(cascade = CascadeType.ALL, mappedBy="krcstMonsetCmpRegAggr", orphanRemoval = true)
	public KrcstMonsetCmpFlxAggr krcstMonsetCmpFlxAggr;
	
	/**
	 * キー取得
	 */
	@Override
	protected Object getKey() {
		return this.PK;
	}
}
