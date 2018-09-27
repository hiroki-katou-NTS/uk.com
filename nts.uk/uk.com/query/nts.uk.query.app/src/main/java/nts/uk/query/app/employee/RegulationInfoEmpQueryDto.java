/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.query.app.employee;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDateTime;
import nts.uk.query.model.employee.EmployeeSearchQuery;

/**
 * The Class EmployeeSearchQueryDto.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegulationInfoEmpQueryDto implements Serializable {

	/** The Constant TIME_DAY_START. */
	public static final String TIME_DAY_START = " 00:00:00";

	/** The Constant DATE_TIME_FORMAT. */
	public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The base date. */
	private String baseDate; // 基準日

	/** The reference range. */
	private Integer referenceRange; // 検索参照範囲

	/** The filter by employment. */
	private Boolean filterByEmployment; // 雇用で絞り込む

	/** The employment codes. */
	private List<String> employmentCodes; // 雇用コード一覧

	/** The filter by department. */
	private Boolean filterByDepartment; // 部門で絞り込む

	/** The department codes. */
	private List<String> departmentCodes; // 部門ID一覧

	/** The filter by workplace. */
	private Boolean filterByWorkplace; // 職場で絞り込む

	/** The workplace codes. */
	private List<String> workplaceCodes; // 職場ID一覧

	/** The filter by classification. */
	private Boolean filterByClassification; // 分類で絞り込む

	/** The classification codes. */
	private List<String> classificationCodes; // 分類コード一覧

	/** The filter by job title. */
	private Boolean filterByJobTitle; // 職位で絞り込む

	/** The job title codes. */
	private List<String> jobTitleCodes; // 職位ID一覧

	/** The filter by worktype. */
	private Boolean filterByWorktype; // 勤務種別で絞り込む
	
	/** The worktype codes. */
	private List<String> worktypeCodes; // 勤務種別コード一覧

	/** The filter by closure. */
	private Boolean filterByClosure;

	/** The closure ids. */
	private List<Integer> closureIds;

	/** The period start. */
	private String periodStart; // 在職・休職・休業のチェック期間

	/** The period end. */
	private String periodEnd; // 在職・休職・休業のチェック期間

	/** The include incumbents. */
	private Boolean includeIncumbents; // 在職者を含める

	/** The include workers on leave. */
	private Boolean includeWorkersOnLeave; // 休職者を含める

	/** The include occupancy. */
	private Boolean includeOccupancy; // 休業者を含める

	/** The include retirees. */
	private Boolean includeRetirees; // 退職者を含める

	/** The include are on loan. */
	private Boolean includeAreOnLoan; // 出向に来ている社員を含める

	/** The include going on loan. */
	private Boolean includeGoingOnLoan; // 出向に行っている社員を含める

	/** The retire start. */
	private String retireStart; // 退職日のチェック期間

	/** The retire end. */
	private String retireEnd; // 退職日のチェック期間

	/** The sort order no. */
	private Integer sortOrderNo; // 並び順NO

	/** The name type. */
	private String nameType; // 氏名の種類

	/** The system type.
	 *  Note: 
	 *  With Algorithm: <<Public>> 個人情報条件で社員を検索して並び替える. please set to 1.
	 *  With Algorithm: <<Public>> 就業条件で社員を検索して並び替える. please set to 2.
	 *  */
	private Integer systemType;

	/**
	 * To query model.
	 *
	 * @return the employee search query
	 */
	public EmployeeSearchQuery toQueryModel() {
		return EmployeeSearchQuery.builder()
				.baseDate(GeneralDateTime.fromString(this.baseDate + TIME_DAY_START, DATE_TIME_FORMAT))
				.classificationCodes(this.classificationCodes)
				.departmentCodes(this.departmentCodes)
				.employmentCodes(this.employmentCodes)
				.filterByClassification(this.filterByClassification)
				.filterByDepartment(this.filterByDepartment)
				.filterByEmployment(this.filterByEmployment)
				.filterByJobTitle(this.filterByJobTitle)
				.filterByWorkplace(this.filterByWorkplace)
				.includeIncumbents(this.includeIncumbents)
				.includeOccupancy(this.includeOccupancy)
				.includeRetirees(this.includeRetirees)
				.includeWorkersOnLeave(this.includeWorkersOnLeave)
				.jobTitleCodes(this.jobTitleCodes)
				.filterByWorktype(this.filterByWorktype)
				.worktypeCodes(this.worktypeCodes)
				.filterByClosure(this.filterByClosure)
				.closureIds(this.closureIds)
				.nameType(this.nameType)
				.periodEnd(this.periodEnd == null ? null : GeneralDateTime.fromString(this.periodEnd + TIME_DAY_START, DATE_TIME_FORMAT))
				.periodStart(this.periodStart == null ? null : GeneralDateTime.fromString(this.periodStart + TIME_DAY_START, DATE_TIME_FORMAT))
				.referenceRange(this.referenceRange)
				.retireEnd(this.retireEnd != null ? GeneralDateTime.fromString(this.retireEnd + TIME_DAY_START, DATE_TIME_FORMAT) : null)
				.retireStart(this.retireStart != null ? GeneralDateTime.fromString(this.retireStart + TIME_DAY_START, DATE_TIME_FORMAT) : null)
				.sortOrderNo(this.sortOrderNo)
				.workplaceCodes(this.workplaceCodes)
				.systemType(this.systemType)
				.build();
	}
}
