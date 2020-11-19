package nts.uk.ctx.at.request.app.command.application.overtime;

import java.math.BigDecimal;

import nts.uk.ctx.at.shared.dom.workdayoff.frame.NotUseAtr;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrame;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrameGetMemento;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrameName;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrameNo;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrameRole;

public class WorkdayoffFrameCommand implements WorkdayoffFrameGetMemento{
	
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
	
	public WorkdayoffFrame toDomain() {
		return new WorkdayoffFrame(this);
	}

	@Override
	public String getCompanyId() {
		return companyId;
	}

	@Override
	public WorkdayoffFrameNo getWorkdayoffFrameNo() {
		return new WorkdayoffFrameNo(BigDecimal.valueOf(this.workdayoffFrNo));

	}

	@Override
	public NotUseAtr getUseClassification() {
		return NotUseAtr.valueOf(this.useClassification);
	}

	@Override
	public WorkdayoffFrameName getTransferFrameName() {
		return new WorkdayoffFrameName(this.transferFrName);
	}

	@Override
	public WorkdayoffFrameName getWorkdayoffFrameName() {
		return new WorkdayoffFrameName(this.workdayoffFrName);
	}

	@Override
	public WorkdayoffFrameRole getRole() {
		return WorkdayoffFrameRole.valueOf(this.role);
	}
}
