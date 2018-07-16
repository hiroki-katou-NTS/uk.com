package nts.uk.ctx.sys.log.infra.entity.log.startpage;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDateTime;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**　 @author Tindh - 起動記録*/

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "SRCDT_START_PAGE_LOG_INFO")
public class SrcdtStartPageLogInfo extends UkJpaEntity {

	@Id
	@Column(name = "OPERATION_ID")
	public String operationId;

	@Column(name = "START_BEFORE_PGID")
	public String startBeforePid;

	@Column(name = "START_BEFORE_SCREEN_ID")
	public String startBeforScId;

	@Column(name = "START_BEFORE_QUERY_STRING")
	public String startBeforeQuery;

	@Column(name = "CID")
	@Basic(optional = false)
	public String companyId;

	@Column(name = "USER_ID")
	@Basic(optional = false)
	public String userId;

	@Column(name = "USER_NAME")
	@Basic(optional = false)
	public String userName;

	@Column(name = "SID")
	@Basic(optional = false)
	public String employeeId;

	@Column(name = "IP_ADDRESS")
	public String ipAddress;

	@Column(name = "PC_NAME")
	public String pcName;

	@Column(name = "ACCOUNT")
	public String account;

	@Column(name = "MODIFIED_DT")
	@Basic(optional = false)
	public GeneralDateTime modifiedDateTime;

	@Column(name = "PGID")
	@Basic(optional = false)
	public String programId;

	@Column(name = "SCREEN_ID")
	@Basic(optional = false)
	public String screenId;

	@Column(name = "QUERY_STRING")
	@Basic(optional = false)
	public String queryString;

	@Column(name = "OFFICE_HELPER_ROLE")
	public String officeHelperRoleId;

	@Column(name = "GROUP_COM_ADMIN_ROLE")
	public String groupCompaniesAdminRoleId;

	@Column(name = "SYS_ADMIN_ROLE")
	public String systemAdminRoleId;

	@Column(name = "MY_NUMBER_ROLE")
	public String myNumberRoleId;

	@Column(name = "PERSONNEL_ROLE")
	public String personnelRoleId;

	@Column(name = "COM_ADMIN_ROLE")
	public String companyAdminRoleId;

	@Column(name = "ACCOUNTING_ROLE")
	public String accountingRoleId;

	@Column(name = "PERSON_INFO_ROLE")
	public String personalInfoRoleId;

	@Column(name = "ATTENDANCE_ROLE")
	public String attendanceRoleId;

	@Column(name = "PAYROLL_ROLE")
	public String payrollRoleId;

	@Column(name = "NOTE")
	public String note;

	@Override
	protected Object getKey() {
		return this.operationId;
	}
}
