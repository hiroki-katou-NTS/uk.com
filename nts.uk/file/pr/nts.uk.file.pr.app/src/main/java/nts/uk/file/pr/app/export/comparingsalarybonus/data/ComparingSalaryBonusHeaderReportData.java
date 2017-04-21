package nts.uk.file.pr.app.export.comparingsalarybonus.data;

import lombok.Getter;
import lombok.Setter;
/**
 * ComparingSalaryBonusHeaderReportData
 * @author lanlt
 *
 */
@Getter
@Setter
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

}
