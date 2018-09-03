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
import nts.uk.shr.infra.data.entity.UkJpaEntity;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="WWFDT_APP_ROOT_INSTANCE")
public class WwfdtAppRootInstance extends UkJpaEntity {
	
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
	
	@OneToMany(targetEntity=WwfdtAppPhaseInstance.class, cascade = CascadeType.ALL, mappedBy = "wwfdtAppRootInstance", orphanRemoval = true)
	@JoinTable(name = "WWFDT_APP_PHASE_INSTANCE")
	public List<WwfdtAppPhaseInstance> listWwfdtAppPhaseInstance;

	@Override
	protected Object getKey() {
		return rootID;
	}
}
