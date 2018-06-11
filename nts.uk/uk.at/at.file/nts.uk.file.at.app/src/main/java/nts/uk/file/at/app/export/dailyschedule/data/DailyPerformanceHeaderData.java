package nts.uk.file.at.app.export.dailyschedule.data;

import java.util.List;

import lombok.Data;

/**
 * The header data of daily report.
 *
 * @author HoangNDH
 */
@Data
public class DailyPerformanceHeaderData {
	
	/** The company name. */
	public String companyName;
	
	/** The fixed header data. */
	public List<String> fixedHeaderData;
	
	/** The lst output item setting code. */
	public List<OutputItemSetting> lstOutputItemSettingCode;
}
