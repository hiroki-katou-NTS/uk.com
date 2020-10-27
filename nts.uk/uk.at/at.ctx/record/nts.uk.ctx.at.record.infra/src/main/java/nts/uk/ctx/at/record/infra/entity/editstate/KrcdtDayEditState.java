package nts.uk.ctx.at.record.infra.entity.editstate;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 
 * @author nampt
 * 日別実績の編集状態
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCDT_DAY_EDIT_STATE")
public class KrcdtDayEditState extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrcdtDayEditStatePK krcdtDayEditStatePK;
	
	@Column(name = "EDIT_STATE")
	public int editState;
	
	@Override
	protected Object getKey() {
		return this.krcdtDayEditStatePK;
	}
}
