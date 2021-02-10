package nts.uk.ctx.at.schedule.infra.entity.schedule.setting.modify;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;
/**
 * 
 * @author phongtq
 *
 */

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSCMT_SCHE_AUTH_SHIFT")
public class KscstScheShiftPermisson  extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public KscstScheShiftPermissonPK kscstScheShiftPermissonPK;
	
			/** 利用できる*/
			@Column(name = "AVAILABLE_SHIFT")
			public int availableShift;

	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}