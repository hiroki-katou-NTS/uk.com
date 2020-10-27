package nts.uk.ctx.at.record.infra.entity.monthly.vtotalmethod;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * エンティティ：給与欠勤日数
 * @author shuichi_ishida
 */
@Entity
@Table(name = "KRCMT_CALC_M_PAY_ABSN")
@NoArgsConstructor
public class KrcmtCalcMPayAbsn extends ContractUkJpaEntity implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/** プライマリキー */
	@EmbeddedId
	public KrcmtCalcMPayAttnPK PK;
	
	/**
	 * キー取得
	 */
	@Override
	protected Object getKey() {
		return this.PK;
	}
}
