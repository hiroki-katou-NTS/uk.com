package nts.uk.ctx.sys.assist.infra.entity.deletedata;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.sys.assist.dom.deletedata.EmployeeDeletion;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@Entity
@Table(name = "SSPDT_DELETION_EMPLOYEES")
@NoArgsConstructor
@AllArgsConstructor
public class SspdtDeletionEmployees extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
    public SspdtDeletionEmployeesPK sspdtDeletionEmployeesPK;
	
	/**
	 * The employee code
	 * 社員コード
	 */
	@Basic(optional = false)
	@Column(name = "EMPLOYEE_CODE")
	public String employeeCode;
	
	/** The business name. */
	/** ビジネスネーム */
	@Basic(optional = false)
	@Column(name = "BUSINESS_NAME")
	public String businessName;
	
	@Override
	protected Object getKey() {
		return sspdtDeletionEmployeesPK;
	}

	public EmployeeDeletion toDomain() {
		return EmployeeDeletion.createFromJavatype(this.sspdtDeletionEmployeesPK.delId, 
				this.sspdtDeletionEmployeesPK.employeeId, this.employeeCode, this.businessName);
	}

	public static SspdtDeletionEmployees toEntity(EmployeeDeletion employeeDeletion) {
		return new SspdtDeletionEmployees(new SspdtDeletionEmployeesPK(
				employeeDeletion.getDelId(), employeeDeletion.getEmployeeId()), 
				employeeDeletion.getEmployeeCode().v(), employeeDeletion.getBusinessName().v());
	}
}
