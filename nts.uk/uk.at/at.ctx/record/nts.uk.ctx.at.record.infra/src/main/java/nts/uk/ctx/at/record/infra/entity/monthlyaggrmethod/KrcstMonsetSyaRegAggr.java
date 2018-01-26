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
import nts.uk.ctx.at.record.infra.entity.monthlyaggrmethod.employee.KrcstMonsetSyaFlxAggr;
import nts.uk.ctx.at.record.infra.entity.monthlyaggrmethod.employee.KrcstMonsetSyaIrgAggr;
import nts.uk.ctx.at.record.infra.entity.monthlyaggrmethod.employee.KrcstMonsetSyaIrgExot;
import nts.uk.ctx.at.record.infra.entity.monthlyaggrmethod.employee.KrcstMonsetSyaIrgSetl;
import nts.uk.ctx.at.record.infra.entity.monthlyaggrmethod.employee.KrcstMonsetSyaRegExot;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * エンティティ：社員月別実績集計設定
 * @author shuichi_ishida
 */
@Entity
@Table(name = "KRCST_MONSET_SYA_REG_AGGR")
@NoArgsConstructor
public class KrcstMonsetSyaRegAggr extends UkJpaEntity implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/** プライマリキー */
	@EmbeddedId
	public KrcstMonsetSyaRegAggrPK PK;

	/** 設定値 */
	@Embedded
	public KrcstMonsetRegAggr setValue;

	/** 通常勤務：時間外超過設定 */
	@OneToOne(cascade = CascadeType.ALL, mappedBy="krcstMonsetSyaRegAggr", orphanRemoval = true)
	public KrcstMonsetSyaRegExot krcstMonsetSyaRegExot;

	/** 変形労働時間勤務 */
	@OneToOne(cascade = CascadeType.ALL, mappedBy="krcstMonsetSyaRegAggr", orphanRemoval = true)
	public KrcstMonsetSyaIrgAggr krcstMonsetSyaIrgAggr;

	/** 変形労働時間勤務：時間外超過設定 */
	@OneToOne(cascade = CascadeType.ALL, mappedBy="krcstMonsetSyaRegAggr", orphanRemoval = true)
	public KrcstMonsetSyaIrgExot krcstMonsetSyaIrgExot;

    /** 変形労働時間勤務：精算期間 */
    @OneToMany(cascade = CascadeType.ALL, mappedBy="krcstMonsetSyaRegAggr", orphanRemoval = true)
    public List<KrcstMonsetSyaIrgSetl> krcstMonsetSyaIrgSetls;
	
	/** フレックス時間勤務 */
	@OneToOne(cascade = CascadeType.ALL, mappedBy="krcstMonsetSyaRegAggr", orphanRemoval = true)
	public KrcstMonsetSyaFlxAggr krcstMonsetSyaFlxAggr;
	
	/**
	 * キー取得
	 */
	@Override
	protected Object getKey() {
		return this.PK;
	}
}
