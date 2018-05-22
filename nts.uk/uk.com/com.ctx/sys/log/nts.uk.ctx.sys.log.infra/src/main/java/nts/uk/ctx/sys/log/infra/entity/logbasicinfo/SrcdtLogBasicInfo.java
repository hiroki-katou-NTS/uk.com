package nts.uk.ctx.sys.log.infra.entity.logbasicinfo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDateTime;
import nts.uk.shr.com.security.audittrail.basic.LogBasicInformation;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 
 * @author HungTT
 *
 */

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "SRCDT_LOG_BASIC_INFO")
public class SrcdtLogBasicInfo extends UkJpaEntity {

	@Id
	@Column(name = "OPERATION_ID")
	String operationId;

	@Column(name = "CID")
	String companyId;

	@Column(name = "USER_ID")
	String userId;

	@Column(name = "USER_NAME")
	String userName;

	@Column(name = "SID")
	String employeeId;

	@Column(name = "IP_ADDRESS")
	String ipAddress;

	@Column(name = "PC_NAME")
	String pcName;

	@Column(name = "ACCOUNT")
	String account;

	@Column(name = "MODIFIED_DT")
	GeneralDateTime modifiedDateTime;

	@Column(name = "PGID")
	String programId;

	@Column(name = "SCREEN_ID")
	String screenId;

	@Column(name = "QUERY_STRING")
	String queryString;

	@Column(name = "OFFICE_HELPER_ROLE")
	String officeHelperRoleId;

	@Column(name = "GROUP_COM_ADMIN_ROLE")
	String groupCompaniesAdminRoleId;

	@Column(name = "SYS_ADMIN_ROLE")
	String systemAdminRoleId;

	@Column(name = "MY_NUMBER_ROLE")
	String myNumberRoleId;

	@Column(name = "PERSONNEL_ROLE")
	String personnelRoleId;

	@Column(name = "COM_ADMIN_ROLE")
	String companyAdminRoleId;

	@Column(name = "ACCOUNTING_ROLE")
	String accountingRoleId;

	@Column(name = "PERSON_INFO_ROLE")
	String personalInfoRoleId;

	@Column(name = "ATTENDANCE_ROLE")
	String attendanceRoleId;

	@Column(name = "PAYROLL_ROLE")
	String payrollRoleId;

	@Column(name = "NOTE")
	String note;

	@Override
	protected Object getKey() {
		return this.operationId;
	}

	public LogBasicInformation toDomain() {
		return null;
	}

	public static SrcdtLogBasicInfo fromDomain(LogBasicInformation domain) {
		return null;
	}

}
