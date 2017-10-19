package nts.uk.ctx.bs.employee.infra.entity.empfilemanagement;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "BSYMT_EMP_FILE_MANAGEMENT")
public class BsymtEmpFileManagement extends UkJpaEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public BsymtEmpFileManagementPK bsymtEmpFileManagementPK;
	
	/** The name. */
	@Basic(optional = false)
	@Column(name = "SID")
	public String sid;
	
	@Basic(optional = true)
	@Column(name = "FILE_TYPE")
	public int filetype;
	

	@Basic(optional = true)
	@Column(name = "DISPORDER")
	public Integer disPOrder;
	
	@Basic(optional = true)
	@Column(name = "PERSON_INFO_CTG_ID")
	public String personInfoctgId;
	
	
	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return null;
	}
}
