package nts.uk.ctx.at.request.app.find.dialog.employmentsystem.holidaysubstitute;

import lombok.Data;
/**
 * 
 * @author phongtq
 *
 */
@Data
public class RemainNumberDetailedInfo {
	// 期限日
	private String deadline;
	
	// 期限日状況
	private String dueDateStatus;
	
	// 消化数
	private String digestionCount;
	
	// 消化日
	private String digestionDate;
	
	// 消化日状況
	private String digestionDateStatus;
	
	// 消化状況
	private String digestionStatus;
	
	// 発生数
	private String numberOccurrences;
	
	// 発生日
	private String accrualDate;
	
	// 発生日状況
	private String occurrenceDateStatus;

	public RemainNumberDetailedInfo(String deadline, String dueDateStatus, String digestionCount, String digestionDate,
			String digestionDateStatus, String digestionStatus, String numberOccurrences, String accrualDate,
			String occurrenceDateStatus) {
		super();
		this.deadline = deadline;
		this.dueDateStatus = dueDateStatus;
		this.digestionCount = digestionCount;
		this.digestionDate = digestionDate;
		this.digestionDateStatus = digestionDateStatus;
		this.digestionStatus = digestionStatus;
		this.numberOccurrences = numberOccurrences;
		this.accrualDate = accrualDate;
		this.occurrenceDateStatus = occurrenceDateStatus;
	}
}
