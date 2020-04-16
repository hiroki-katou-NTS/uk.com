package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice;

import java.util.List;

import lombok.Getter;
import nts.arc.time.GeneralDate;

/**
 * 社員の打刻情報
 * @author tutk
 *
 */
public class EmployeeStampInfo {
	/**
	 * 社員ID
	 */
	@Getter
	private final String employeeId;

	/**
	 * 年月日
	 */
	@Getter
	private final GeneralDate date;
	
	/**
	 * 打刻情報リスト
	 */
	@Getter
	private final List<StampInfoDisp> listStampInfoDisp;

	public EmployeeStampInfo(String employeeId, GeneralDate date, List<StampInfoDisp> listStampInfoDisp) {
		super();
		this.employeeId = employeeId;
		this.date = date;
		this.listStampInfoDisp = listStampInfoDisp;
	}
	
	
}
