package nts.uk.ctx.at.schedule.infra.entity.schedule.setting.function.control;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 完了実行条件
 * 
 * @author TanLV
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSFST_SCHE_FUNC_COND")
public class KsfstScheFuncCondition extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public KsfstScheFuncConditionPK ksfstScheFuncConditionPK;
	
	@ManyToOne
	@JoinColumns( {
        @JoinColumn(name = "CID", referencedColumnName = "CID", insertable = false, updatable = false)
    })
	public KsfstScheFuncControl scheFuncControl;

	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return ksfstScheFuncConditionPK;
	}

	/**
	 * 
	 * @param ksfstScheFuncConditionPK
	 */
	public KsfstScheFuncCondition(KsfstScheFuncConditionPK ksfstScheFuncConditionPK) {
		
		this.ksfstScheFuncConditionPK = ksfstScheFuncConditionPK;
	}
}
