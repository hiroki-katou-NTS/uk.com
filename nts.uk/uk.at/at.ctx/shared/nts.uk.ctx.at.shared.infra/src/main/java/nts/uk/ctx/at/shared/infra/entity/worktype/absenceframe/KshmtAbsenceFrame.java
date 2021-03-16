package nts.uk.ctx.at.shared.infra.entity.worktype.absenceframe;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@Entity
@Table(name="KSHMT_ABSENCE_FRAME")
@AllArgsConstructor
@NoArgsConstructor
public class KshmtAbsenceFrame extends ContractUkJpaEntity implements Serializable {
    
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	public KshmtAbsenceFramePK kshmtAbsenceFramePK;
	/*枠名称*/
	@Column(name = "NAME")
	public String name;
	/*欠勤枠の廃止区分*/
	@Column(name = "ABOLISH_ATR")
	public int abolishAtr;
	@Override
	protected Object getKey() {		
		return kshmtAbsenceFramePK;
	}
}
