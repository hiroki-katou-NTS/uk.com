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
import nts.uk.ctx.at.record.infra.entity.monthlyaggrmethod.workplace.KrcstMonsetWkpFlxAggr;
import nts.uk.ctx.at.record.infra.entity.monthlyaggrmethod.workplace.KrcstMonsetWkpIrgAggr;
import nts.uk.ctx.at.record.infra.entity.monthlyaggrmethod.workplace.KrcstMonsetWkpIrgExot;
import nts.uk.ctx.at.record.infra.entity.monthlyaggrmethod.workplace.KrcstMonsetWkpIrgSetl;
import nts.uk.ctx.at.record.infra.entity.monthlyaggrmethod.workplace.KrcstMonsetWkpRegExot;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * エンティティ：職場月別実績集計設定
 * @author shuichi_ishida
 */
@Entity
@Table(name = "KRCST_MONSET_WKP_REG_AGGR")
@NoArgsConstructor
public class KrcstMonsetWkpRegAggr extends UkJpaEntity implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/** プライマリキー */
	@EmbeddedId
	public KrcstMonsetWkpRegAggrPK PK;

	/** 設定値 */
	@Embedded
	public KrcstMonsetRegAggr setValue;

	/** 通常勤務：時間外超過設定 */
	@OneToOne(cascade = CascadeType.ALL, mappedBy="krcstMonsetWkpRegAggr", orphanRemoval = true)
	public KrcstMonsetWkpRegExot krcstMonsetWkpRegExot;

	/** 変形労働時間勤務 */
	@OneToOne(cascade = CascadeType.ALL, mappedBy="krcstMonsetWkpRegAggr", orphanRemoval = true)
	public KrcstMonsetWkpIrgAggr krcstMonsetWkpIrgAggr;

	/** 変形労働時間勤務：時間外超過設定 */
	@OneToOne(cascade = CascadeType.ALL, mappedBy="krcstMonsetWkpRegAggr", orphanRemoval = true)
	public KrcstMonsetWkpIrgExot krcstMonsetWkpIrgExot;

    /** 変形労働時間勤務：精算期間 */
    @OneToMany(cascade = CascadeType.ALL, mappedBy="krcstMonsetWkpRegAggr", orphanRemoval = true)
    public List<KrcstMonsetWkpIrgSetl> krcstMonsetWkpIrgSetls;
	
	/** フレックス時間勤務 */
	@OneToOne(cascade = CascadeType.ALL, mappedBy="krcstMonsetWkpRegAggr", orphanRemoval = true)
	public KrcstMonsetWkpFlxAggr krcstMonsetWkpFlxAggr;
	
	/**
	 * キー取得
	 */
	@Override
	protected Object getKey() {
		return this.PK;
	}
}
