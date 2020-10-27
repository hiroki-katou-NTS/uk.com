package nts.uk.ctx.workflow.infra.entity.resultrecord;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="WWFDT_INST_ROUTE")
public class WwfdtInstRoute extends ContractUkJpaEntity {
	
	@Id
	@Column(name="ROOT_ID")
	private String rootID;
	
	@Column(name="CID")
	private String companyID;
	
	@Column(name="EMPLOYEE_ID")
	private String employeeID;
	
	@Column(name="START_DATE")
	private GeneralDate startDate;
	
	@Column(name="END_DATE")
	private GeneralDate endDate;
	
	@Column(name="ROOT_TYPE")
	private Integer rootType;
	
	@OneToMany(targetEntity=WwfdtInstPhase.class, cascade = CascadeType.ALL, mappedBy = "wwfdtInstRoute", orphanRemoval = true)
	@JoinTable(name = "WWFDT_INST_PHASE")
	public List<WwfdtInstPhase> listWwfdtInstPhase;

	@Override
	protected Object getKey() {
		return rootID;
	}
}
