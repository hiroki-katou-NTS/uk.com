package nts.uk.ctx.at.record.infra.entity.monthlyaggrmethod.employment;

import java.io.Serializable;

import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.infra.entity.monthlyaggrmethod.KrcstMonsetEmpRegAggr;
import nts.uk.ctx.at.record.infra.entity.monthlyaggrmethod.KrcstMonsetEmpRegAggrPK;
import nts.uk.ctx.at.record.infra.entity.monthlyaggrmethod.regularandirregular.KrcstMonsetIrgAggr;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * エンティティ：雇用の変形労働時間勤務の法定内集計設定
 * @author shuichi_ishida
 */
@Entity
@Table(name = "KRCST_MONSET_EMP_IRG_AGGR")
@NoArgsConstructor
public class KrcstMonsetEmpIrgAggr extends UkJpaEntity implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/** プライマリキー */
	@EmbeddedId
	public KrcstMonsetEmpRegAggrPK PK; 

	/** 設定値 */
	@Embedded
	public KrcstMonsetIrgAggr setValue; 
	
	/** マッチング：雇用月別実績集計設定 */
	@OneToOne
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
