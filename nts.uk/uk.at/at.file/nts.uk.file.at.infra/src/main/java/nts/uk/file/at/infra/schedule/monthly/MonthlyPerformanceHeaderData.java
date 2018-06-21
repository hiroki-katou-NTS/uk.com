package nts.uk.file.at.infra.schedule.monthly;

import java.util.List;

import lombok.Data;
import nts.uk.file.at.app.export.dailyschedule.data.OutputItemSetting;

/**
 * The header data of daily report.
 *
 * @author HoangNDH
 */
@Data
public class MonthlyPerformanceHeaderData {
	
	/** The company name. */
	public String companyName;
	
	/** The fixed header data. */
	public List<String> fixedHeaderData;
	
	/** The lst output item setting code. */
	public List<OutputItemSetting> lstOutputItemSettingCode;
}
