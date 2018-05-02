package nts.uk.ctx.at.function.dom.adapter;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
@Getter
@Setter
public class RegulationInfoEmployeeAdapterImport {
	/** The base date. */
	private GeneralDateTime baseDate; //基準日
	
	/** The reference range. */
	private Integer referenceRange; //検索参照範囲
	
	/** The filter by employment. */
	private Boolean filterByEmployment; //雇用で絞り込む

	/** The employment codes. */
	private List<String> employmentCodes; //雇用コード一覧
	
	/** The filter by department. */
	private Boolean filterByDepartment; //部門で絞り込む
	
	/** The department codes. */
	private List<String> departmentCodes; //部門ID一覧
	
	/** The filter by workplace. */
	private Boolean filterByWorkplace; //職場で絞り込む

	/** The workplace codes. */
	private List<String> workplaceCodes; //職場ID一覧
	
	/** The filter by classification. */
	private Boolean filterByClassification; //分類で絞り込む
	
	/** The classification codes. */
	private List<String> classificationCodes; //分類コード一覧
	
	/** The filter by job title. */
	private Boolean filterByJobTitle; //職位で絞り込む

	/** The job title codes. */
	private List<String> jobTitleCodes; //職位ID一覧

	/** The filter by worktype. */
	private Boolean filterByWorktype; // 勤務種別で絞り込む

	/** The worktype codes. */
	private List<String> worktypeCodes; // 勤務種別コード一覧
	
	/** The period start. */
	private GeneralDate periodStart; //在職・休職・休業のチェック期間
	
	/** The period end. */
	private GeneralDate periodEnd; //在職・休職・休業のチェック期間
	
	/** The include incumbents. */
	private Boolean includeIncumbents; //在職者を含める
	
	/** The include workers on leave. */
	private Boolean includeWorkersOnLeave; //休職者を含める
	
	/** The include occupancy. */
	private Boolean includeOccupancy; //休業者を含める
	
	/** The include retirees. */
	private Boolean includeRetirees; //退職者を含める

	/** The include are on loan. */
	private Boolean includeAreOnLoan; // 出向に来ている社員を含める

	/** The include going on loan. */
	private Boolean includeGoingOnLoan; // 出向に行っている社員を含める
	
	/** The retire start. */
	private GeneralDate retireStart; //退職日のチェック期間
	
	/** The retire end. */
	private GeneralDate retireEnd; //退職日のチェック期間
	
	/** The sort order no. */
	private Integer sortOrderNo; //並び順NO
	
	/** The name type. */
	private String nameType; //氏名の種類
	
	private boolean filterByClosure; //就業締めで絞り込む

	/** The system type.
	 *  Note: 
	 *  With Algorithm: <<Public>> 個人情報条件で社員を検索して並び替える. please set to 1.
	 *  With Algorithm: <<Public>> 就業条件で社員を検索して並び替える. please set to 2.
	 *  */
	private Integer systemType;
}
