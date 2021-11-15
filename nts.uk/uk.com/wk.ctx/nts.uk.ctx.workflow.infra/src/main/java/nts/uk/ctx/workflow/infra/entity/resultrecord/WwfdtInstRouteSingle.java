package nts.uk.ctx.workflow.infra.entity.resultrecord;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Entity
@Table(name="WWFDT_INST_ROUTE")
public class WwfdtInstRouteSingle extends ContractUkJpaEntity {
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
	
	@Override
	protected Object getKey() {
		return rootID;
	}
}
