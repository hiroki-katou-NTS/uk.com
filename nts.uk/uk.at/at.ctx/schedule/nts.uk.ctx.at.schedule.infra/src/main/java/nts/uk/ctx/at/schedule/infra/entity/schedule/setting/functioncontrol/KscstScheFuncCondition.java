package nts.uk.ctx.at.schedule.infra.entity.schedule.setting.functioncontrol;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 完了実行条件
 * 
 * @author TanLV
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSCST_SCHE_FUNC_COND")
public class KscstScheFuncCondition extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public KscstScheFuncConditionPK kscstScheFuncConditionPK;
	
	@ManyToOne
	@JoinColumns( {
        @JoinColumn(name = "CID", referencedColumnName = "CID", insertable = false, updatable = false)
    })
	public KscstScheFuncControl scheFuncControl;

	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return kscstScheFuncConditionPK;
	}

	/**
	 * 
	 * @param kscstScheFuncConditionPK
	 */
	public KscstScheFuncCondition(KscstScheFuncConditionPK kscstScheFuncConditionPK) {
		
		this.kscstScheFuncConditionPK = kscstScheFuncConditionPK;
	}
}
