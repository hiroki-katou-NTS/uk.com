package nts.uk.file.pr.app.export.comparingsalarybonus.data;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
/**
 * The class Salary Bonus Detail
 * @author lanlt
 *
 */
@Setter
@Getter
public class SalaryBonusDetail {
	private String itemAbName;
	private int processingYM;
	private String employeeName;
	private String specificationCode;
	private String makeMethodFlag;
	private String personId;
    private String categoryATR;
	private String itemCode;
	private BigDecimal value;
	private String nameB;
	private String scd;
	private String departmentName;
	private String departmentCode;
	private String hierarchyId;
}
