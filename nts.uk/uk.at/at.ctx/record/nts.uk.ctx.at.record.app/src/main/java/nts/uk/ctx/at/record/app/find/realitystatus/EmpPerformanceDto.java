package nts.uk.ctx.at.record.app.find.realitystatus;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Value;

/**
 * @author dat.lh
 *
 */
@Value
@AllArgsConstructor
public class EmpPerformanceDto {
	/**
	 * 社員ID
	 */
	private String sId;
	/**
	 * 社員名
	 */
	private String sName;
	private boolean monthConfirm;
	private boolean personConfirm;
	private boolean bossConfirm;
	/**
	 * 日別実績
	 */
	List<DailyPerformanceDto> dailyPerformance;
}
