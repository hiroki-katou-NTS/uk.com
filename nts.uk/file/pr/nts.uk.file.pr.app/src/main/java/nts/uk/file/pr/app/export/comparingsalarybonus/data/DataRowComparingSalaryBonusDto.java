package nts.uk.file.pr.app.export.comparingsalarybonus.data;

import lombok.Getter;
import lombok.Setter;
/**
 * The Class DataRowComparingSalaryBonusDto
 * @author lanlt
 *
 */
@Setter
@Getter
public class DataRowComparingSalaryBonusDto {
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
