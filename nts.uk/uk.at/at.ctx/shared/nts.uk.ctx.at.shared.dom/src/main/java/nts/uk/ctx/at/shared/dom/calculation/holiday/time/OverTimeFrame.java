package nts.uk.ctx.at.shared.dom.calculation.holiday.time;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.calculation.holiday.OvertimeFrameName;
import nts.uk.ctx.at.shared.dom.calculation.holiday.TransferFrameName;

@AllArgsConstructor
@Getter
public class OverTimeFrame {
	/** 会社ID */
	private String companyId;

	/** NO */
	private int overtimeFrameNo;

	/** 使用区分 */
	private int useAtr;

	/** 振替枠名称 */
	private TransferFrameName transferFrameName;

	/** 残業枠名称 */
	private OvertimeFrameName overtimeFrameName;

	public static OverTimeFrame createFromJavaType(String companyId, int overtimeFrameNo, int useAtr,
			String transferFrameName, String overtimeFrameName) {
		return new OverTimeFrame(companyId, overtimeFrameNo, useAtr, new TransferFrameName(transferFrameName),
				new OvertimeFrameName(overtimeFrameName));
	}
}
