package nts.uk.file.pr.app.export.comparingsalarybonus.query;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
public class ComparingSalaryBonusQuery {
	private int month1;
	private int month2;
	private String monthJapan1;
	private String monthJapan2;
	private String formCode;
	private int payBonusAttr = 0;
	private List<String> employeeCodeList;
}
