package nts.uk.ctx.at.record.infra.entity.monthlyaggrmethod.regularandirregular;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * 変形労働の精算期間　（共通）
 * @author shuichu_ishida
 */
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class KrcstMonsetIrgSetl implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	/** 終了月 */
	@Column(name = "END_MONTH")
	public int endMonth;
}
