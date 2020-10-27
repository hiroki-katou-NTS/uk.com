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
@Table(name = "KSCST_SCHE_MODIFYDEADLINE")
public class KscstSchemodifyDeadline  extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public KscstSchemodifyDeadlinePK kscstSchemodifyDeadlinePK;
	
			/** 利用区分*/
			@Column(name = "USE_CLS")
			public int useCls;
			
			/** 修正期限*/
			@Column(name = "CORRECT_DEADLINE")
			public Integer correctDeadline;

	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
