package nts.uk.ctx.at.record.infra.entity.monthlyaggrmethod.employee;

import java.io.Serializable;

import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.infra.entity.monthlyaggrmethod.KrcstMonsetSyaRegAggr;
import nts.uk.ctx.at.record.infra.entity.monthlyaggrmethod.KrcstMonsetSyaRegAggrPK;
import nts.uk.ctx.at.record.infra.entity.monthlyaggrmethod.regularandirregular.KrcstMonsetIrgAggr;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * エンティティ：社員の変形労働時間勤務の法定内集計設定
 * @author shuichi_ishida
 */
@Entity
@Table(name = "KRCST_MONSET_SYA_IRG_AGGR")
@NoArgsConstructor
public class KrcstMonsetSyaIrgAggr extends UkJpaEntity implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/** プライマリキー */
	@EmbeddedId
	public KrcstMonsetSyaRegAggrPK PK; 

	/** 設定値 */
	@Embedded
	public KrcstMonsetIrgAggr setValue; 
	
	/** マッチング：社員月別実績集計設定 */
	@OneToOne
	@JoinColumns({
		@JoinColumn(name = "CID", referencedColumnName = "CID", insertable = false, updatable = false),
		@JoinColumn(name = "SID", referencedColumnName = "SID", insertable = false, updatable = false)
	})
	public KrcstMonsetSyaRegAggr krcstMonsetSyaRegAggr;
	
	/**
	 * キー取得
	 */
	@Override
	protected Object getKey() {
		return this.PK;
	}
}
