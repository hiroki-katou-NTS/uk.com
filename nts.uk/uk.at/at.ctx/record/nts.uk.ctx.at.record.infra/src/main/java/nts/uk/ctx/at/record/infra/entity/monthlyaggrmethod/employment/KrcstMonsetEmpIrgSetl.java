package nts.uk.ctx.at.record.infra.entity.monthlyaggrmethod.employment;

import java.io.Serializable;

import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.infra.entity.monthlyaggrmethod.KrcstMonsetEmpRegAggr;
import nts.uk.ctx.at.record.infra.entity.monthlyaggrmethod.regularandirregular.KrcstMonsetIrgSetl;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * エンティティ：雇用の変形労働の精算期間
 * @author shuichu_ishida
 */
@Entity
@Table(name = "KRCST_MONSET_EMP_IRG_SETL")
@NoArgsConstructor
@AllArgsConstructor
public class KrcstMonsetEmpIrgSetl extends UkJpaEntity implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/** プライマリキー */
	@EmbeddedId
	public KrcstMonsetEmpIrgSetlPK PK;

	/** 設定値 */
	@Embedded
	public KrcstMonsetIrgSetl setValue;

	/** マッチング：雇用月別実績集計設定 */
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name = "CID", referencedColumnName = "CID", insertable = false, updatable = false),
		@JoinColumn(name = "EMP_CD", referencedColumnName = "EMP_CD", insertable = false, updatable = false)
	})
	public KrcstMonsetEmpRegAggr krcstMonsetEmpRegAggr;
	
	/**
	 * キー取得
	 */
	@Override
	protected Object getKey() {
		return this.PK;
	}
}
