/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.calculation.holiday.time;
/**
 */
import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class OverTimeFrame.
 */
@AllArgsConstructor
@Getter
public class OverTimeFrame extends DomainObject {
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

	/**
	 * Create from Java Type of OverTimeFrame
	 * @param companyId
	 * @param overtimeFrameNo
	 * @param useAtr
	 * @param transferFrameName
	 * @param overtimeFrameName
	 * @return
	 */
	public static OverTimeFrame createFromJavaType(String companyId, int overtimeFrameNo, int useAtr,
			String transferFrameName, String overtimeFrameName) {
		return new OverTimeFrame(companyId, overtimeFrameNo, useAtr, new TransferFrameName(transferFrameName),
				new OvertimeFrameName(overtimeFrameName));
	}
}
