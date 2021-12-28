package nts.uk.ctx.at.record.infra.entity.workrule.specific;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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

	@Id
	@Column(name="CID")
	private String cid;

	@Column(name="CAL_METHOD")
	private int calMethod;

	@Override
	protected Object getKey() {
		return cid;
	}

}