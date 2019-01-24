package nts.uk.file.at.app.export.attendanceitemprepare;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author hoidd 
 *
 */
public interface PerAuthFormatExport {
	/**
	 * 
	 * @param companyId
	 * @return
	 */
	public Map<String, Map<Integer, List<PerAuthFormatItem>>>  getAllDailyByComp(String companyId);
	/**
	 * 
	 * @param companyId
	 * @return
	 */
	public Map<String, List<PerAuthFormatItem>>  getAllDaiMonthlyByComp(String companyId);
	/**
	 * 
	 * @param companyId
	 * @return
	 */
	public Map<String, Map<Integer, List<PerAuthFormatItem>>>  getAllMonByComp(String companyId);
}
