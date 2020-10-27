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
@Table(name = "KSCMT_FUNCTION_CONP_COND")
public class KscmtFunctionConpCondition extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public KscmtFunctionConpConditionPK kscmtFunctionConpConditionPK;
	
	@ManyToOne
	@JoinColumns( {
        @JoinColumn(name = "CID", referencedColumnName = "CID", insertable = false, updatable = false)
    })
	public KscstScheFuncControl scheFuncControl;

	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return kscmtFunctionConpConditionPK;
	}

	/**
	 * 
	 * @param kscmtFunctionConpConditionPK
	 */
	public KscmtFunctionConpCondition(KscmtFunctionConpConditionPK kscmtFunctionConpConditionPK) {
		
		this.kscmtFunctionConpConditionPK = kscmtFunctionConpConditionPK;
	}
}
