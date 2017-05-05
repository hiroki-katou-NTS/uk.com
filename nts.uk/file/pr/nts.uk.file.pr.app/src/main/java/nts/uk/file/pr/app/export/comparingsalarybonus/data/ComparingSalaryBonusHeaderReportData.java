package nts.uk.file.pr.app.export.comparingsalarybonus.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Value;
/**
 * ComparingSalaryBonusHeaderReportData
 * @author lanlt
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ComparingSalaryBonusHeaderReportData {
	
	/** The name company. */
	private String nameCompany;
	
	/** The title Report. */
	private String titleReport;
	
	/** The name deparment. */
	private String nameDeparment;

	/** The type department. */
	private String typeDeparment;

	/** The employee. */
	private String postion;

	/** The target year month. */
	private String targetYearMonth;
	
	/** 項目名 */
	private String itemName;

	/** 項目額1  **/
	private String month1;
	
	/** 項目額2  **/
	private String month2;
	
	/**  different Salary */
	private String differentSalary;
	
	/** 登録状況1 **/
	private String registrationStatus1;
	
	/** 登録状況2 **/
	private String registrationStatus2;
	
	/** 差異理由 **/
	private String reason;
	
	/** 確認済 **/
	private String confirmed;

}
