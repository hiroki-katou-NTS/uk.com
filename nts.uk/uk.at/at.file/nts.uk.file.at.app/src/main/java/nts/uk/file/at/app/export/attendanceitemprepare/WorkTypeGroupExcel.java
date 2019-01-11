package nts.uk.file.at.app.export.attendanceitemprepare;

import java.util.List;
import java.util.Map;
/**
 * 
 * @author Hoidd 2
 *
 */
public interface WorkTypeGroupExcel {
	/**
	 */
	public Map<String, Map<Integer, List<WorkTypeDtoExcel>>> getAllWorkType();
}
