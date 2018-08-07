package nts.uk.file.at.infra.schedule.monthly;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;
import nts.uk.file.at.app.export.dailyschedule.totalsum.WorkplaceTotal;

/**
 * Instantiates a new daily workplace data.
 * @author HoangNDH
 */
@Data
public class MonthlyWorkplaceData {
	
	/** The workplace code. */
	public String workplaceCode;
	
	/** The workplace name. */
	public String workplaceName;
	
	/** The level. */
	public int level;
	
	/** Parent workplace */
	public MonthlyWorkplaceData parent;
	
	/** The lst daily personal data. */
	public List<MonthlyPersonalPerformanceData> lstDailyPersonalData = new ArrayList<>();
	
	/** The lst child workplace data. */
	public Map<String, MonthlyWorkplaceData> lstChildWorkplaceData = new HashMap<String, MonthlyWorkplaceData>();
	
	/** The workplace total. */
	public WorkplaceTotal workplaceTotal;
	
	/** The workplace hierarchy total. */
	public WorkplaceTotal workplaceHierarchyTotal;
}
