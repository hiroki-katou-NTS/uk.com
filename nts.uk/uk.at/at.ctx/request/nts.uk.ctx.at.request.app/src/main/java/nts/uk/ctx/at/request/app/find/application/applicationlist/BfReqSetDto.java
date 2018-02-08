package nts.uk.ctx.at.request.app.find.application.applicationlist;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.setting.company.request.RequestSetting;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.apptypesetting.ReceptionRestrictionSetting;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class BfReqSetDto {
	public String companyId;
	public Integer appType;
	public Integer retrictPreMethodFlg;
	public Integer retrictPreUseFlg;
	public Integer retrictPreDay;
	public Integer retrictPreTimeDay;
	public Integer retrictPostAllowFutureFlg;
	
	public static List<BfReqSetDto> convertToDto(RequestSetting domain){
		List<ReceptionRestrictionSetting> appType = domain.getApplicationSetting().getReceptionRestrictionSetting();
		List<BfReqSetDto> listDto = new ArrayList<>();
		for(ReceptionRestrictionSetting item: appType){
			listDto.add(new BfReqSetDto(domain.getCompanyID(), item.getAppType().value, item.getBeforehandRestriction().getMethodCheck().value, item.getBeforehandRestriction().getToUse() == true ? 1 : 0, item.getBeforehandRestriction().getDateBeforehandRestriction().value, item.getBeforehandRestriction().getTimeBeforehandRestriction().v(), item.getAfterhandRestriction().getAllowFutureDay() == true ? 1: 0));
		}
		return listDto;
	}
}
