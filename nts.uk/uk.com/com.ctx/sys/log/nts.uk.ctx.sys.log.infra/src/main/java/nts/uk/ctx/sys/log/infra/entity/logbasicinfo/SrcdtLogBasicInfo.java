package nts.uk.ctx.sys.log.infra.entity.logbasicinfo;

import java.util.Optional;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDateTime;
import nts.uk.shr.com.context.ScreenIdentifier;
import nts.uk.shr.com.context.loginuser.role.DefaultLoginUserRoles;
import nts.uk.shr.com.security.audittrail.correction.content.UserInfo;
import nts.uk.shr.com.security.audittrail.basic.LogBasicInformation;
import nts.uk.shr.com.security.audittrail.basic.LoginInformation;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 
 * @author HungTT - ログ基本情報
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
	@Basic(optional = false)
	String companyId;

	@Column(name = "USER_ID")
	@Basic(optional = false)
	String userId;

	@Column(name = "USER_NAME")
	@Basic(optional = false)
	String userName;

	@Column(name = "SID")
	@Basic(optional = false)
	String employeeId;

	@Column(name = "IP_ADDRESS")
	String ipAddress;

	@Column(name = "PC_NAME")
	String pcName;

	@Column(name = "ACCOUNT")
	String account;

	@Column(name = "MODIFIED_DT")
	@Basic(optional = false)
	GeneralDateTime modifiedDateTime;

	@Column(name = "PGID")
	@Basic(optional = false)
	String programId;

	@Column(name = "SCREEN_ID")
	@Basic(optional = false)
	String screenId;

	@Column(name = "QUERY_STRING")
	@Basic(optional = false)
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
		DefaultLoginUserRoles userRoles = new DefaultLoginUserRoles();
		userRoles.setRoleIdForAttendance(attendanceRoleId);
		userRoles.setRoleIdforCompanyAdmin(companyAdminRoleId);
		userRoles.setRoleIdforGroupCompaniesAdmin(groupCompaniesAdminRoleId);
		userRoles.setRoleIdforOfficeHelper(officeHelperRoleId);
		userRoles.setRoleIdForPayroll(payrollRoleId);
		userRoles.setRoleIdforPersonalInfo(personalInfoRoleId);
		userRoles.setRoleIdForPersonnel(personnelRoleId);
		userRoles.setRoleIdforSystemAdmin(systemAdminRoleId);
		LogBasicInformation infor = new LogBasicInformation(operationId, companyId,
				new UserInfo(userId, employeeId, userName), new LoginInformation(ipAddress, pcName, account),
				modifiedDateTime, userRoles, new ScreenIdentifier(programId, screenId, queryString),
				Optional.ofNullable(note));
		return infor;
	}

	public static SrcdtLogBasicInfo fromDomain(LogBasicInformation domain) {
		String programId = domain.getTargetProgram().getProgramId();
		return new SrcdtLogBasicInfo(domain.getOperationId(), domain.getCompanyId(), 
				domain.getUserInfo().getUserId(), domain.getUserInfo().getUserName(), domain.getUserInfo().getEmployeeId(),
				domain.getLoginInformation().getIpAddress().isPresent() ? domain.getLoginInformation().getIpAddress().get() : null, 
				domain.getLoginInformation().getPcName().isPresent() ? domain.getLoginInformation().getPcName().get() : null,
				domain.getLoginInformation().getAccount().isPresent() ? domain.getLoginInformation().getAccount().get() : null,  
				domain.getModifiedDateTime(),
				programId.length() > 6 ? programId.substring(0, 6) : programId, 
				domain.getTargetProgram().getScreenId(),
				domain.getTargetProgram().getQueryString(), 
				domain.getAuthorityInformation().forOfficeHelper(),
				domain.getAuthorityInformation().forGroupCompaniesAdmin(),
				domain.getAuthorityInformation().forSystemAdmin(), 
				null, // myNumberRoleId
				domain.getAuthorityInformation().forPersonnel(), 
				domain.getAuthorityInformation().forCompanyAdmin(),
				null, // accountingRoleId
				domain.getAuthorityInformation().forPersonalInfo(), 
				domain.getAuthorityInformation().forAttendance(),
				domain.getAuthorityInformation().forPayroll(),
				domain.getNote().isPresent() ? domain.getNote().get() : null);
	}

}
