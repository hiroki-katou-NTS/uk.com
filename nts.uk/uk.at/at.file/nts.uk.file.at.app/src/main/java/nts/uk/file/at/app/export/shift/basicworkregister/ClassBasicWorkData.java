package nts.uk.file.at.app.export.shift.basicworkregister;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ClassBasicWorkData {
	private String classCode;
	private String className;
	private Optional<Integer> workDayAtr;
	private Optional<String> workTypeCD;
	private Optional<String> workTypeName;
	private Optional<String> workTimeCD;
	private Optional<String> workTimeName;

	public static ClassBasicWorkData createFromJavaType(String classCode,String className,Optional<Integer> workDayAtr, 
			Optional<String> workTypeCD, Optional<String> workTypeName, Optional<String> workTimeCD, Optional<String> workTimeName){
		return new ClassBasicWorkData(classCode,className,
				workDayAtr, workTypeCD, workTypeName, workTimeCD, workTimeName);
	}
	
}
