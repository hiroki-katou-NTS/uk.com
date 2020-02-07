package nts.uk.ctx.hr.develop.app.guidance.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.hr.develop.dom.guidance.Guidance;

@Data
@AllArgsConstructor
public class GuidanceDto {

	public String companyId;
	
	public Boolean usageFlgCommon;

	public Integer guideMsgAreaRow;

	public Integer guideMsgMaxNum;

	public List<GuideMsgDto> guideMsg;
	
	public GuidanceDto(Guidance guidance) {
		super();
		this.companyId = guidance.getCompanyId();
		this.usageFlgCommon = guidance.isUsageFlgCommon();
		this.guideMsgAreaRow = guidance.getGuideMsgAreaRow().v();
		this.guideMsgMaxNum = guidance.getGuideMsgMaxNum().v();
		this.guideMsg = guidance.getGuideMsg().stream().map(c -> new GuideMsgDto(c)).collect(Collectors.toList());
	}
	
	public Guidance toDomain() {
		return Guidance.createFromJavaType(this.companyId, this.usageFlgCommon, this.guideMsgAreaRow, this.guideMsgMaxNum, this.guideMsg.stream().map(c-> c.toDomain()).collect(Collectors.toList()));
	}
}
