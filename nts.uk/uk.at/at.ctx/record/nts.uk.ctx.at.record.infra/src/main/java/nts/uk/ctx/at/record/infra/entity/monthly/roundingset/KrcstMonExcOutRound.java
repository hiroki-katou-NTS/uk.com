package nts.uk.ctx.at.record.infra.entity.monthly.roundingset;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * エンティティ：時間外超過の時間丸め
 * @author shuichu_ishida
 */
@Entity
@Table(name = "KRCST_MON_EXCOUT_ROUND")
@NoArgsConstructor
public class KrcstMonExcOutRound extends UkJpaEntity implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/** プライマリキー */
	@EmbeddedId
	public KrcstMonRoundSetPK PK;
	
	/** 丸め単位 */
	@Column(name = "ROUND_UNIT")
	public int roundUnit;
	
	/** 端数処理 */
	@Column(name = "ROUND_PROC")
	public int roundProc;
	
	/**
	 * キー取得
	 */
	@Override
	protected Object getKey() {
		return this.PK;
	}
}
