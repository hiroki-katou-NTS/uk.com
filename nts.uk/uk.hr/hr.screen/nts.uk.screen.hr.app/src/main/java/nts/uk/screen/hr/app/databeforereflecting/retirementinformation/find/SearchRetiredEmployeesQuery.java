package nts.uk.screen.hr.app.databeforereflecting.retirementinformation.find;

import java.util.List;

import lombok.Getter;
import nts.arc.time.GeneralDate;

@Getter
public class SearchRetiredEmployeesQuery {
	
	private static final String DATE_FORMAT = "yyyy/MM/dd";
	
	private boolean confirmCheckRetirementPeriod;

	// A222_2_2 反映済みを含む
	private boolean includingReflected;

	// A222_3 定年退職期間(開始日)
	private GeneralDate startDate;

	// A222_3 定年退職期間(終了日)
	private GeneralDate endDate;

	// （A222_4）退職年齢指定
	private boolean retirementAgeSetting;

	// (A222_5)定年退職年齢
	private Integer retirementAge;

	// （A222_7_1）すべて選択 = チェックなし
	private boolean allSelectDepartment;

	// （A222_7_3）部門選択 = 空白
	private List<String> selectDepartment;

	private String selectDepartmentName;

	// （A222_9_1）すべて選択 = チェックなし
	private boolean allSelectEmployment;

	// （A222_9_3）雇用選択 = 空白
	private List<String> selectEmployment;

	private String selectEmploymentName;
	
	private List<String> hidedColumns;
	
	public void setStartDate(String date) {
		this.startDate = GeneralDate.fromString(date, DATE_FORMAT);
	}
	
	public void setEndDate(String date) {
		this.endDate = GeneralDate.fromString(date, DATE_FORMAT);
	}
	
	
}
