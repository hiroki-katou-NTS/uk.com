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
	private int payBonusAttr=0;
	private List<String> employeeCodeList;
//	public  List<String> fakeDataList(){
//		employeeCodeList = new ArrayList();
//		employeeCodeList.add("99900000-0000-0000-0000-000000000001");
//		employeeCodeList.add("99900000-0000-0000-0000-000000000002");
//		employeeCodeList.add("99900000-0000-0000-0000-000000000003");
//		employeeCodeList.add("99900000-0000-0000-0000-000000000004");
//		employeeCodeList.add("99900000-0000-0000-0000-000000000005");
//		employeeCodeList.add("99900000-0000-0000-0000-000000000006");
//		employeeCodeList.add("99900000-0000-0000-0000-000000000007");
//		employeeCodeList.add("99900000-0000-0000-0000-000000000008");
//		employeeCodeList.add("99900000-0000-0000-0000-000000000009");
//		employeeCodeList.add("99900000-0000-0000-0000-000000000010");
//		return this.employeeCodeList;
//	}
}
