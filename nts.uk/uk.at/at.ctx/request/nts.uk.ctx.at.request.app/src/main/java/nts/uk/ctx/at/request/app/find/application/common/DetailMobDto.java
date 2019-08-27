package nts.uk.ctx.at.request.app.find.application.common;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.find.application.common.dto.ApprovalPhaseStateForAppDto;
import nts.uk.ctx.at.request.app.find.application.overtime.dto.AppOverTimeMobDto;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class DetailMobDto {
	
	public List<ApprovalPhaseStateForAppDto> listApprovalPhaseStateDto;
	
	public Integer appStatus;
	
	public Integer reflectStatus;
	
	public Integer version;
	
	public String reversionReason;
	
	public boolean authorizableFlags;
	
	public Integer approvalATR;
	
	public boolean alternateExpiration;
	
	public String authorComment;
	
	public AppOverTimeMobDto appOvertime;
	
	public Integer loginApprovalAtr;
}
