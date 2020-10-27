package nts.uk.ctx.at.record.infra.entity.workrule.specific;

import java.io.Serializable;
import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;


/**
 * The persistent class for the KRCST_WK_HOUR_LIMIT_CTRL database table.
 * 
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="KRCST_WK_HOUR_LIMIT_CTRL")
public class KrcstWkHourLimitCtrl extends ContractUkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private KrcstWkHourLimitCtrlPK id;
	
	@Column(name="WORK_LIMIT_CTRL")
	private int workLimitCtrl;

	@Override
	protected Object getKey() {
		return id;
	}
}