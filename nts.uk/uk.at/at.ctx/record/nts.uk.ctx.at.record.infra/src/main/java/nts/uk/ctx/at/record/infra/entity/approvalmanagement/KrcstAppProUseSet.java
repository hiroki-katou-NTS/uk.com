/**
 * 9:06:59 AM Mar 13, 2018
 */
package nts.uk.ctx.at.record.infra.entity.approvalmanagement;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * @author hungnm
 * 承認処理の利用設定
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCST_APP_PRO_USE_SET")
public class KrcstAppProUseSet extends UkJpaEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "CID")
	public String cId;

	@Column(name = "DAY_APPROVER_COMFIRM_ATR")
	public BigDecimal dayApproverComfirmAtr;
	
	@Column(name = "MONTH_APPROVER_COMFIRM_ATR")
	public BigDecimal monthApproverComfirmAtr;
	
	@Column(name = "COMFIRM_ERROR_ATR")
	public BigDecimal comfirmErrorAtr;

	@Override
	protected Object getKey() {
		return this.cId;
	}
}
