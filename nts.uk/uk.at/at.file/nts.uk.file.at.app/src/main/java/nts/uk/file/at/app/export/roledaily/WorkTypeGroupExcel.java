package nts.uk.file.at.app.export.roledaily;

import java.util.List;
import java.util.Map;

public interface WorkTypeGroupExcel {
	public Map<String, Map<Integer, List<WorkTypeDtoExcel>>> getAllWorkType();
}
