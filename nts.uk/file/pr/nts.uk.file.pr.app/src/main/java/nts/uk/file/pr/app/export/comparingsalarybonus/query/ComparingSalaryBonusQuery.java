package nts.uk.file.pr.app.export.comparingsalarybonus.query;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
public class ComparingSalaryBonusQuery {
	private String month1;
	private String month2;
	private int payBonusAttr=0;
	private List<String> employeeCodeList;
}
