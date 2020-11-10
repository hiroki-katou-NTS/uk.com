package nts.uk.ctx.at.request.app.find.application.overtime;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrame;

@AllArgsConstructor
@NoArgsConstructor
public class OvertimeWorkFrameDto {
	
	public String companyId;
	
	//残業枠NO
	public Integer overtimeWorkFrNo;
	
	//使用区分
	public Integer useClassification;
	
	//振替枠名称
	public String transferFrName;
	
	//残業枠名称
	public String overtimeWorkFrName;
	
	public static OvertimeWorkFrameDto fromDomain(OvertimeWorkFrame overtimeWorkFrame) {
		return new OvertimeWorkFrameDto(
				overtimeWorkFrame.getCompanyId(),
				overtimeWorkFrame.getOvertimeWorkFrNo().v().intValue(),
				overtimeWorkFrame.getUseClassification().value,
				overtimeWorkFrame.getTransferFrName().v(),
				overtimeWorkFrame.getOvertimeWorkFrName().v());
	}
}
