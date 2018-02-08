package nts.uk.query.model;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;

@AllArgsConstructor
@Getter

//社員のソート用の規定情報
public class EmployeeQueryModel {
	
	/** The employee ID. */
	private String employeeID; //社員ID
	
	/** The employee code. */
	private String employeeCode; //社員コード
	
	/** The hire date. */
	private Optional<GeneralDate> hireDate; //入社日
	
	/** The classification code. */
	private Optional<String> classificationCode; //分類コード
	
	/** The name. */
	private Optional<String> name; //氏名
	
	/** The job title code. */
	private Optional<String> jobTitleCode; //職位コード
	
//	private Integer oderOfJobTitle; //職位の序列の並び順
	
	/** The workplace code. */
	private Optional<String> workplaceCode; //職場の階層コード
	
	/** The department code. */
	private Optional<String> departmentCode; //部門の階層コード
	
	/** The employment code. */
	private Optional<String> employmentCode; //雇用コード
	
}
