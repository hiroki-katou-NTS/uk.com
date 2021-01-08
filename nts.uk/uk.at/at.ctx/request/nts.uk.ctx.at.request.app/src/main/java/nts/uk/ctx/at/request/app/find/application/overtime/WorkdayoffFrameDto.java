package nts.uk.ctx.at.request.app.find.application.overtime;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrame;

@AllArgsConstructor
@NoArgsConstructor
public class WorkdayoffFrameDto {
	// 会社ID
	public String companyId;
	
	//休出枠NO
	public Integer workdayoffFrNo;
	
	//使用区分
	public Integer useClassification;
	
	//振替枠名称
	public String transferFrName;
	
	//休出枠名称
	public String workdayoffFrName;
	
	//役割
	public Integer role;
	
	public static WorkdayoffFrameDto fromDomain(WorkdayoffFrame workdayoffFrame) {
		return new WorkdayoffFrameDto(
				workdayoffFrame.getCompanyId(),
				workdayoffFrame.getWorkdayoffFrNo().v().intValue(),
				workdayoffFrame.getUseClassification().value,
				workdayoffFrame.getTransferFrName().v(),
				workdayoffFrame.getWorkdayoffFrName().v(),
				workdayoffFrame.getRole().value);
	}
}
