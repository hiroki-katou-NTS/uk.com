package nts.uk.ctx.hr.develop.app.databeforereflecting.retirementinformation.find;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchRetiredEmployeesQuery {
	
	private boolean confirmCheckRetirementPeriod;

	// A222_3 定年退職期間(開始日)
	private GeneralDate startDate;

	// A222_3 定年退職期間(終了日)
	private GeneralDate endDate;

	// （A222_4）退職年齢指定
	private boolean retirementAgeSetting;

	// (A222_5)定年退職年齢
	private String retirementAge;

	// （A222_7_1）すべて選択 = チェックなし
	private boolean allSelectDepartment;

	// （A222_7_3）部門選択 = 空白
	private List<String> selectDepartment;

	// （A222_9_1）すべて選択 = チェックなし
	private boolean allSelectEmployment;

	// （A222_9_3）雇用選択 = 空白
	private List<String> selectEmployment;
}
