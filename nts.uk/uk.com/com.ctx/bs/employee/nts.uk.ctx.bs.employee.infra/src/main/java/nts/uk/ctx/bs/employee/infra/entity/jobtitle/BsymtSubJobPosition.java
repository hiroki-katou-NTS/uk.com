package nts.uk.ctx.bs.employee.infra.entity.jobtitle;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.infra.data.entity.type.GeneralDateToDBConverter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.infra.entity.department.BsymtCurrAffiDept;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Getter
@Setter
@Entity
@Table(name="BSYMT_SUB_JOB_POSITION")
public class BsymtSubJobPosition extends UkJpaEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**the sub job position id*/
	@EmbeddedId
	private BsymtSubJobPositionPK bsymtSubJobPositionPK;
	
	/** The dep id. */
	@Column(name = "AFFI_DEPT_ID")
	private String affiDeptId;
	
	/** The dep id. */
	@Column(name = "JOB_TITLE_ID")
	private String jobTitleId;
	
	/** The str D. */
	@Column(name = "STR_D")
	@Convert(converter = GeneralDateToDBConverter.class)
	private GeneralDate strD;

	/** The end D. */
	@Column(name = "END_D")
	@Convert(converter = GeneralDateToDBConverter.class)
	private GeneralDate endD;
	
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name = "AFFI_DEPT_ID", referencedColumnName = "AFFI_DEPT_ID", insertable = false, updatable = false)
	})
	public BsymtCurrAffiDept bsymtCurrAffiDept;

	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return null;
	}

}
