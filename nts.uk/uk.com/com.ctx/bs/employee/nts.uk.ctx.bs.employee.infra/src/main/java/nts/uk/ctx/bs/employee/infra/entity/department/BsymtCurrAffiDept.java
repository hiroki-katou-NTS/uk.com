package nts.uk.ctx.bs.employee.infra.entity.department;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.bs.employee.infra.entity.jobtitle.BsymtSubJobPosition;
import nts.uk.ctx.bs.employee.infra.entity.workplace.assigned.BsymtAssiWorkplaceHist;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class bsymt current affiliation department
 * */

@Getter
@Setter
@Entity
@Table(name="BSYMT_CURR_AFFI_DEPT")
public class BsymtCurrAffiDept extends UkJpaEntity implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**the current affi dept id*/
	@EmbeddedId
	private BsymtCurrAffiDeptPK bsymtCurrAffiDeptPK;
	
	/** The sid. */
	@Column(name = "SID")
	private String sid;
	
	/** The dep id. */
	@Column(name = "DEP_ID")
	private String depId;
	
	/** The dep id. */
	@Column(name = "HIST_ID")
	private String histId;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="depId", orphanRemoval = true)
	private List<BsymtSubJobPosition> lstBsymtSubJobPosition;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="histId", orphanRemoval = true)
	private List<BsymtAssiWorkplaceHist> lstBsymtAssiWorkplaceHist;

	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return this.bsymtCurrAffiDeptPK;
	}
	
	
}
