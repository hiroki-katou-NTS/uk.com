package nts.uk.file.at.app.export.dailyschedule.data;

import java.util.List;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

/**
 * The header data of daily report
 * @author HoangNDH
 *
 */
@Data
public class DailyPerformanceHeaderData {
	public String companyName;
	
	public List<String> fixedHeaderData;
	
	public List<OutputItemSetting> lstOutputItemSettingCode;
}
