package nts.uk.shr.com.context.loginuser.role;

import java.io.Serializable;

import lombok.RequiredArgsConstructor;

public class DefaultLoginUserRoles implements LoginUserRoles, Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	private RolesForOneBusinessWork forAttendance = new RolesForOneBusinessWork(null, null);
	private RolesForOneBusinessWork forPayroll = new RolesForOneBusinessWork(null, null);
	private RolesForOneBusinessWork forPersonnel = new RolesForOneBusinessWork(null, null);
	private RolesForOneBusinessWork forPersonalInfo = new RolesForOneBusinessWork(null, null);
	private RolesForOneBusinessWork forSystemAdmin = new RolesForOneBusinessWork(null, null);
	private RolesForOneBusinessWork forCompanyAdmin = new RolesForOneBusinessWork(null, null);
	
	@Override
	public String forAttendance() {
		return this.forAttendance.getRoleId();
	}

	@Override
	public String forPayroll() {
		return this.forPayroll.getRoleId();
	}

	@Override
	public String forPersonnel() {
		return this.forPersonnel.getRoleId();
	}

	@Override
	public String forPersonalInfo() {
		return this.forPersonalInfo.getRoleId();
	}

	@Override
	public String forSystemAdmin() {
		return this.forSystemAdmin.getRoleId();
	}

	@Override
	public String forCompanyAdmin() {
		return this.forCompanyAdmin.getRoleId();
	}

	public void setRoleIdsForAttendance(String forPersonInCharge, String forGeneral) {
		this.forAttendance = new RolesForOneBusinessWork(forPersonInCharge, forGeneral);
	}

	public void setRoleIdsForPayroll(String forPersonInCharge, String forGeneral) {
		this.forPayroll = new RolesForOneBusinessWork(forPersonInCharge, forGeneral);
	}

	public void setRoleIdsForPersonnel(String forPersonInCharge, String forGeneral) {
		this.forPersonnel = new RolesForOneBusinessWork(forPersonInCharge, forGeneral);
	}

	public void setRoleIdsforPersonalInfo(String forPersonInCharge, String forGeneral) {
		this.forPersonalInfo = new RolesForOneBusinessWork(forPersonInCharge, forGeneral);
	}

	public void setRoleIdsforSystemAdmin(String forPersonInCharge, String forGeneral) {
		this.forSystemAdmin = new RolesForOneBusinessWork(forPersonInCharge, forGeneral);
	}

	public void setRoleIdsforCompanyAdmin(String forPersonInCharge, String forGeneral) {
		this.forCompanyAdmin = new RolesForOneBusinessWork(forPersonInCharge, forGeneral);
	}

	@RequiredArgsConstructor
	private static class RolesForOneBusinessWork implements Serializable {
		
		/** serialVersionUID */
		private static final long serialVersionUID = 1L;
		
		private final String forPersonInCharge;
		private final String forGeneral;
		
		public String getRoleId() {
			if (this.forPersonInCharge != null) {
				return this.forPersonInCharge;
			} else if (this.forGeneral != null) {
				return this.forGeneral;
			} else {
				return null;
			}
		}
	}
}
