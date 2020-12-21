package nts.uk.ctx.at.request.app.command.application.overtime;

import java.math.BigDecimal;

import nts.uk.ctx.at.shared.dom.ot.frame.NotUseAtr;
import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrame;
import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrameGetMemento;
import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrameName;
import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrameNo;

public class OvertimeWorkFrameCommand implements OvertimeWorkFrameGetMemento{
	
	public String companyId;
	
	//残業枠NO
	public Integer overtimeWorkFrNo;
	
	//使用区分
	public Integer useClassification;
	
	//振替枠名称
	public String transferFrName;
	
	//残業枠名称
	public String overtimeWorkFrName;
	
	
	public OvertimeWorkFrame toDomain() {
		return new OvertimeWorkFrame(this);
	}


	@Override
	public String getCompanyId() {
		return companyId;
	}


	@Override
	public OvertimeWorkFrameNo getOvertimeWorkFrameNo() {
		return new OvertimeWorkFrameNo(BigDecimal.valueOf(this.overtimeWorkFrNo));

	}


	@Override
	public NotUseAtr getUseClassification() {
		return NotUseAtr.valueOf(this.useClassification);
	}


	@Override
	public OvertimeWorkFrameName getTransferFrameName() {
		return new OvertimeWorkFrameName(this.transferFrName);
	}


	@Override
	public OvertimeWorkFrameName getOvertimeWorkFrameName() {
		return new OvertimeWorkFrameName(this.overtimeWorkFrName);
	}
}
