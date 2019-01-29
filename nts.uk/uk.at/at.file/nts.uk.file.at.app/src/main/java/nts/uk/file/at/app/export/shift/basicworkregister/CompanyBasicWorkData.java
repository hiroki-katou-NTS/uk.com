package nts.uk.file.at.app.export.shift.basicworkregister;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CompanyBasicWorkData {
	private int workDayAtr;
	private String workTypeCD;
	private String workTypeName;
	private String workTimeCD;
	private String workTimeName;
	
	public static CompanyBasicWorkData createFromJavaType(int workDayAtr, 
			String workTypeCD, String workTypeName, String workTimeCD, String workTimeName){
		return new CompanyBasicWorkData(workDayAtr, workTypeCD, workTypeName, workTimeCD, workTimeName);
	}
}
