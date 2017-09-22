package nts.uk.file.com.app;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class HeaderEmployeeUnregisterOutput {
	private String nameCompany;
	private String title;
	private String employee;
	private String workplaceCode;
	private String workplaceName;
	private String appName;

}
