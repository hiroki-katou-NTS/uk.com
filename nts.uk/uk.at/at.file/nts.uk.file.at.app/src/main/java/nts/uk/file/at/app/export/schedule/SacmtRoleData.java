package nts.uk.file.at.app.export.schedule;

public class SacmtRoleData {
	private String roleId;
	private String code;
	private String cid;
	private String roleType;
	private String name;
	
	public SacmtRoleData (String roleId, String code, String cid, String roleType, String name){
		this.roleId = roleId;
		this.code = code;
		this.cid = cid;
		this.roleType = roleType;
		this.name = name;
	}
	
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getRoleType() {
		return roleType;
	}
	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	
}
