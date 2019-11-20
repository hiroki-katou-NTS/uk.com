package nts.uk.ctx.pereg.app.command.process.checkdata;

/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDateTime;
import nts.uk.query.model.employee.CCG001SystemType;
import nts.uk.query.model.employee.EmployeeSearchQuery;
import nts.uk.query.model.employee.SearchReferenceRange;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class EmployeeSearchQueryDto.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmpQueryDto implements Serializable {

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
	 * 			.filterByWorktype(this.filterByWorktype)
				.worktypeCodes(this.worktypeCodes)
				.filterByClosure(this.filterByClosure)
				.closureIds(this.closureIds)
				.systemType(this.systemType)
	 */
	public EmployeeSearchQuery toQueryModel(GeneralDateTime baseDate) {
		return EmployeeSearchQuery.builder()
				.baseDate(baseDate)
				.systemType(CCG001SystemType.PERSONAL_INFORMATION.value)
				.referenceRange(SearchReferenceRange.ALL_REFERENCE_RANGE.value)
				.filterByEmployment(false)
				.employmentCodes(new ArrayList<>())
				.filterByDepartment(false)
				.departmentCodes(new ArrayList<>())
				.filterByWorkplace(false)
				.workplaceCodes(new ArrayList<>())
				.filterByClassification(false)
				.classificationCodes(new ArrayList<>())
				.filterByJobTitle(false)
				.jobTitleCodes(new ArrayList<>())
				.periodStart(baseDate)
				.periodEnd(baseDate)
				.includeIncumbents(true)
				.includeWorkersOnLeave(true)
				.includeOccupancy(true)
				.includeRetirees(false)
				.includeAreOnLoan(true)
				.includeGoingOnLoan(true)
				.retireStart(baseDate)
				.retireEnd(baseDate)
				.sortOrderNo(0)
				.nameType("")
				.build();
	}
}

