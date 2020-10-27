package nts.uk.ctx.at.function.infra.entity.dailymodification;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * @author thanhnx
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KFNMT_DAY_APP_CALL")
public class KfnmtDayAppCall extends ContractUkJpaEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public KfnmtDayAppCallPK kfnmtDayAppCallPK;

	@Override
	protected Object getKey() {
		return kfnmtDayAppCallPK;
	}
	
}
