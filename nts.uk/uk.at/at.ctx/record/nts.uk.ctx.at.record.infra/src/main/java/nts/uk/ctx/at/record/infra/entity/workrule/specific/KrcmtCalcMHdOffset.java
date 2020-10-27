package nts.uk.ctx.at.record.infra.entity.workrule.specific;

import java.io.Serializable;
import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

//import java.math.BigDecimal;


/**
 * The persistent class for the KRCMT_CALC_M_HD_OFFSET database table.
 * 
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="KRCMT_CALC_M_HD_OFFSET")
public class KrcmtCalcMHdOffset extends ContractUkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private KrcmtCalcMHdOffsetPK id;
	
	@Column(name="SIXTY_HOUR")
	private int sixtyHour;

	@Column(name="SUBSTITUTE")
	private int substitute;

	@Column(name="ANNUAL")
	private int annual;

	@Column(name="SPECIAL")
	private int special;

	@Override
	protected Object getKey() {
		return id;
	}
}