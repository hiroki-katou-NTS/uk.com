package nts.uk.ctx.sys.assist.dom.datarestoration.common;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import javax.ejb.Stateless;

import nts.uk.ctx.sys.assist.dom.datarestoration.PerformDataRecovery;
import nts.uk.ctx.sys.assist.dom.datarestoration.ServerPrepareMng;
import nts.uk.ctx.sys.assist.dom.datarestoration.ServerPrepareOperatingCondition;
import nts.uk.ctx.sys.assist.dom.tablelist.TableList;

@Stateless
public class EmployeeRestoration {

	private static final String TARGET_CSV = "対象社員";
	public List<Object> restoreTargerEmployee(ServerPrepareMng serverPrepareMng, PerformDataRecovery performDataRecovery, List<TableList> tableList){
		InputStream inputStream = FileUtil.createInputStreamFromFile(serverPrepareMng.getFileId().get(), TARGET_CSV);
		if(Objects.isNull(inputStream)){
			serverPrepareMng.setOperatingCondition(ServerPrepareOperatingCondition.EM_LIST_ABNORMALITY);
		} else {
			List<List<String>> targetEmployee = FileUtil.getAllRecord(inputStream, 3);
			if (targetEmployee.size() > 0) {
				for(List<String> employeeInfo : targetEmployee){
					
				}
			}
		}
		return Arrays.asList(serverPrepareMng, performDataRecovery, tableList);
	}
}
