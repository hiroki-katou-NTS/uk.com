package nts.uk.ctx.at.record.infra.entity.workrule.specific;

import java.io.Serializable;
import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

//import java.math.BigDecimal;


/**
 * The persistent class for the KRCMT_CALC_D_REST_TIME database table.
 * @author HoangNDH
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="KRCMT_CALC_D_REST_TIME")
public class KrcmtCalcDRestTime extends ContractUkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private KrcmtCalcDRestTimePK id;

	@Column(name="CAL_METHOD")
	private int calMethod;

	@Override
	protected Object getKey() {
		return id;
	}

}