/**
 * 
 */
package nts.uk.ctx.bs.employee.infra.entity.classification.affiliate_ver1;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * @author danpv
 *
 */
@Entity
@Table(name = "BSYMT_AFF_CLASS_HIS_ITEM")
@NoArgsConstructor
@AllArgsConstructor
public class BsymtAffClassHistItem_Ver1 extends UkJpaEntity {

	@Id
	@Column(name = "HISTORY_ID")
	public String historyId;

	@Column(name = "SID")
	public String sid;

	@Column(name = "CLASSIFICATION_CODE")
	public String classificationCode;

	@Override
	protected Object getKey() {
		return historyId;
	}

}
