package nts.uk.ctx.bs.employee.infra.entity.empfilemanagement;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Getter
@Setter
@Entity
@Table(name = "BSYMT_EMP_FILE_MANAGEMENT")
public class BsymtEmpFileManagement extends UkJpaEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	protected BsymtEmpFileManagementPK bsymtEmpFileManagementPK;
	
	/** The name. */
	@Basic(optional = false)
	@Column(name = "SID")
	private String sid;
	
	@Basic(optional = true)
	@Column(name = "FILE_TYPE")
	private int filetype;
	

	@Basic(optional = true)
	@Column(name = "DISPORDER")
	private int disPOrder;
	
	@Basic(optional = true)
	@Column(name = "PERSON_INFO_CTG_ID")
	private int personInfoctgId;
	
	
	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return null;
	}
}
