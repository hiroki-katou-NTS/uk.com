package nts.uk.file.at.app.export.attendanceitemprepare;

import java.util.List;
import java.util.Map;
/**
 * 
 * @author hoidd 
 *
 */
public interface BusinessDailyRepo {
	/**
	 * 
	 * @param companyId
	 * @return
	 */
	public Map<String, Map<Integer, List<BusinessDailyExcel>>> getAllByComp(String companyId);
}
