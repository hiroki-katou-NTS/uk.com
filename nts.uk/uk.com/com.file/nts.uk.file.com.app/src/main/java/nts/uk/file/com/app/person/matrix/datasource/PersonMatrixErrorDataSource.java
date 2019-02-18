package nts.uk.file.com.app.person.matrix.datasource;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.pereg.dom.person.error.ErrorWarningEmployeeInfo;
@AllArgsConstructor
@Getter
public class PersonMatrixErrorDataSource {
	private boolean isDisplayE1_006;
	private List<ErrorWarningEmployeeInfo> errorEmployeeInfoLst;
}
