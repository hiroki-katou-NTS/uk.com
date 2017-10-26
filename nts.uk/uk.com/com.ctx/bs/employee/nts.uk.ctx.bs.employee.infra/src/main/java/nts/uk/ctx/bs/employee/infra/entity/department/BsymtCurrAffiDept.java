package nts.uk.ctx.bs.employee.infra.entity.department;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.infra.data.entity.type.GeneralDateToDBConverter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.infra.entity.jobtitle.BsymtSubJobPosition;
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

	/** The str D. */
	@Column(name = "STR_D")
	@Convert(converter = GeneralDateToDBConverter.class)
	private GeneralDate strD;

	/** The end D. */
	@Column(name = "END_D")
	@Convert(converter = GeneralDateToDBConverter.class)
	private GeneralDate endD;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="BsymtCurrAffiDept", orphanRemoval = true)
	private List<BsymtSubJobPosition> lstBsymtSubJobPosition;

	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return this.bsymtCurrAffiDeptPK;
	}
	
	
}
