package nts.uk.ctx.pereg.app.find.deleteemployee;

import lombok.Data;

@Data
public class EmployeeToDeleteDto {

	// employeeCode
	private String code;
	// ReasonRemove
	private String reason;
	// BusinessName
	private String name;
	//employeeId
	private String id;

	public static EmployeeToDeleteDto fromDomain(String scd, String reason, String name, String id) {
		return new EmployeeToDeleteDto(scd, reason, name, id);
	}

	public static EmployeeToDeleteDto fromDomain(String scd, String reason) {
		return new EmployeeToDeleteDto(scd, reason);
	}

	public static EmployeeToDeleteDto fromDomain(String name) {
		return new EmployeeToDeleteDto(name);
	}

	public EmployeeToDeleteDto(String name) {
		super();
		this.name = name;
	}

	public EmployeeToDeleteDto(String code, String reason) {
		super();
		this.code = code;
		this.reason = reason;
	}

	public EmployeeToDeleteDto(String code, String reason, String name, String id) {
		super();
		this.code = code;
		this.reason = reason;
		this.name = name;
		this.id = id;
	}

}
