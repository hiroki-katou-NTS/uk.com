package nts.uk.ctx.at.schedule.infra.entity.schedule.setting.modify;

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
 * @author phongtq
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSCST_SCHE_PERS_AUTHORITY")
public class KscstSchePersAuthority  extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public KscstSchePersAuthorityPK kscstSchePersAuthorityPK;
	
			/** 利用できる*/
			@Column(name = "AVAILABLE_PERS")
			public int availablePers;

	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}