package nts.uk.ctx.at.shared.infra.entity.workcheduleworkrecord.appreflectprocess.appreflectcondition.directgoback;
import javax.persistence.Column;
/**
 * @author hoangnd
 */
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "KRQMT_APP_GOBACK_DIRECTLY")
public class KrqmtAppGoBackReflect extends ContractUkJpaEntity {
	@EmbeddedId
	private KrqmtAppGoBackDirectlyPK appGoBackDirectlyPK;
	@Column(name = "WORK_REFLECT_ATR")
	private int reflectApp;
	@Override
	protected Object getKey() {
		return appGoBackDirectlyPK;
	}

}
