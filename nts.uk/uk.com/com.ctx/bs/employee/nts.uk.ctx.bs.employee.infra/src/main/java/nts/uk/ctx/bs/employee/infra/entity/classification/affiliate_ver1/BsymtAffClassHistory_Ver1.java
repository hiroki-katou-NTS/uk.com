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
import nts.arc.time.GeneralDate;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * @author danpv
 *
 */
@Entity
@Table(name = "BSYMT_AFF_CLASS_HISTORY")
@NoArgsConstructor
@AllArgsConstructor
public class BsymtAffClassHistory_Ver1 extends UkJpaEntity{

	@Id
	@Column(name = "HISTORY_ID")
	public String historyId;
	
	@Column(name = "CID")
	public String cid;
	
	@Column(name = "SID")
	public String sid;
	
	@Column(name = "START_DATE")
	public GeneralDate startDate;
	
	@Column(name = "END_DATE")
	public GeneralDate endDate;
	
	@Override
	protected Object getKey() {
		return historyId;
	}
	
	

}
