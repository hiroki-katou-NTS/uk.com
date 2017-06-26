package nts.uk.ctx.at.shared.app.find.bonuspay;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Value;

@AllArgsConstructor
@Value
public class BPSettingDto {
	public String companyId;
	public String code;
	public String name;
	public List<BPTimesheetDto> lstBonusPayTimesheet;
	public List<SpecBPTimesheetDto> lstSpecBonusPayTimesheet;
}
