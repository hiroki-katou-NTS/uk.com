package nts.uk.ctx.pereg.app.command.process.checkdata;

import java.util.Observable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ErrorInfoCPS013 extends Observable{
	
	public String employeeId;
	
	public String categoryId;
	// B2_003
	public String employeeCode; 
	// B2_004	
	public String bussinessName;
	// B2_005
	public String clsCategoryCheck;
	// B2_006
	public String categoryName;
	// B2_007
	public String error;
	
	public ErrorInfoCPS013(String employeeId, String categoryId, String employeeCode, String bussinessName,
			String clsCategoryCheck, String categoryName, String error) {
		super();
		this.employeeId = employeeId;
		this.categoryId = categoryId;
		this.employeeCode = employeeCode;
		this.bussinessName = bussinessName;
		this.clsCategoryCheck = clsCategoryCheck;
		this.categoryName = categoryName;
		this.error = error;
	}
}
