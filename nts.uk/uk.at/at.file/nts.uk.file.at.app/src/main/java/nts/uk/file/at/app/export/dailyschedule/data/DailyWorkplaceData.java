package nts.uk.file.at.app.export.dailyschedule.data;

import java.util.List;
import java.util.Map;

import lombok.Data;
import nts.uk.file.at.app.export.dailyschedule.totalsum.WorkplaceTotal;

/**
 * Instantiates a new daily workplace data.
 * @author HoangNDH
 */
@Data
public class DailyWorkplaceData {
	
	/** The workplace code. */
	public String workplaceCode;
	
	/** The workplace name. */
	public String workplaceName;
	
	/** The level. */
	public int level;
	
	/** Parent workplace */
	public DailyWorkplaceData parent;
	
	/** The lst daily personal data. */
	public List<DailyPersonalPerformanceData> lstDailyPersonalData;
	
	/** The lst child workplace data. */
	public Map<String, DailyWorkplaceData> lstChildWorkplaceData;
	
	/** The workplace total. */
	public WorkplaceTotal workplaceTotal;
}
