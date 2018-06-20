package nts.uk.ctx.at.function.dom.adapter.annualworkschedule;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;
import nts.arc.time.GeneralDate;

@AllArgsConstructor
@Value
@Getter
@Setter
public class EmployeeInformationQueryDtoImport {
	/** The employee ids. */
	List<String> employeeIds; // 社員一覧

	/** The reference date. */
	GeneralDate referenceDate; // 年月日

	/** The to get workplace. */
	boolean toGetWorkplace; // 職場を取得する

	/** The to get department. */
	boolean toGetDepartment; // 部門を取得する

	/** The to get position. */
	boolean toGetPosition; // 職位を取得する

	/** The to get employment. */
	boolean toGetEmployment; // 雇用を取得する

	/** The to get classification. */
	boolean toGetClassification; // 分類を取得する

	/** The to get employment cls. */
	boolean toGetEmploymentCls; // 就業区分を取得する
}
