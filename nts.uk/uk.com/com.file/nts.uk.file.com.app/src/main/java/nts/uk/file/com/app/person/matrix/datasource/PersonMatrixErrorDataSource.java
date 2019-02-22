package nts.uk.file.com.app.person.matrix.datasource;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
@AllArgsConstructor
@Getter
public class PersonMatrixErrorDataSource {
	private boolean isDisplayE1_006;
	private List<ErrorWarningEmployeeInfoDataSource> errorEmployeeInfoLst;
}
