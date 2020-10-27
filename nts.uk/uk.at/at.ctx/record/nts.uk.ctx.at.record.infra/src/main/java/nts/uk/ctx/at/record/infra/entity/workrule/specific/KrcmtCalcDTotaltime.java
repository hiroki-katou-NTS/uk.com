package nts.uk.ctx.at.record.infra.entity.workrule.specific;

import java.io.Serializable;
import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;


/**
 * The persistent class for the KRCMT_CALC_D_TOTALTIME database table.
 * 
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="KRCMT_CALC_D_TOTALTIME")
public class KrcmtCalcDTotaltime extends ContractUkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private KrcmtCalcDTotaltimePK id;
	
	@Column(name="WORK_LIMIT_CTRL")
	private int workLimitCtrl;

	@Override
	protected Object getKey() {
		return id;
	}
}