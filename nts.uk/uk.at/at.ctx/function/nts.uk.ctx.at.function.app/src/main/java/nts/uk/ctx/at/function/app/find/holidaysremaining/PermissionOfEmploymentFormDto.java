package nts.uk.ctx.at.function.app.find.holidaysremaining;

import lombok.Data;

@Data
public class PermissionOfEmploymentFormDto {
	private String companyId;
	private String roleId;
	private int functionNo;
	private boolean availability;
	public PermissionOfEmploymentFormDto(String companyId, String roleId, int functionNo, boolean availability) {
		super();
		this.companyId = companyId;
		this.roleId = roleId;
		this.functionNo = functionNo;
		this.availability = availability;
	}
}