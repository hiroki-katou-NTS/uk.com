package nts.uk.ctx.bs.employee.infra.entity.workplace.assigned;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 所属職場履歴
 * 
 * @author xuan vinh
 *
 */
@Entity
@Table(name = "BSYMT_ASSI_WORKPLACE")
public class BsymtAssiWorkplace extends UkJpaEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** the assign workplace id */
	@Id
	@Column(name = "ASSI_WORKPLACE_ID")
	public String assiWorkplaceId;

	/** The emp id. */
	@Column(name = "SID")
	public String empId;

	/** the workplace id */
	@Column(name = "WORKPLACE_ID")
	public String workplaceId;

	@Column(name = "HIST_ID")
	public String histId;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "bsymtAssiWorkplace", orphanRemoval = true)
	public List<BsymtAssiWorkplaceHist> lstBsymtAssiWorkplaceHist;

	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return null;
	}

}
