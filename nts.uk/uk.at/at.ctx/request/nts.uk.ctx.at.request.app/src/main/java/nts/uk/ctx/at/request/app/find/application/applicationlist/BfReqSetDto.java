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
	// 申請種類
	public Integer appType;
	// チェック方法
	public Integer retrictPreMethodFlg;
	// 利用する
	public Integer retrictPreUseFlg;
	// 日数
	public Integer retrictPreDay;
	// 時刻 (早出残業・通常残業)
	public Integer retrictPreTimeDay;
	// 未来日許可しない
	public Integer retrictPostAllowFutureFlg;
	// 早出残業
	public Integer preOtTime;
	// 通常残業
	public Integer normalOtTime;

	public static List<BfReqSetDto> convertToDto(RequestSetting domain) {
		List<ReceptionRestrictionSetting> appType = domain.getApplicationSetting().getListReceptionRestrictionSetting();
		List<BfReqSetDto> listDto = new ArrayList<>();
		for (ReceptionRestrictionSetting item : appType) {
			listDto.add(new BfReqSetDto(domain.getCompanyID(), item.getAppType().value,
					item.getBeforehandRestriction().getMethodCheck().value,
					item.getBeforehandRestriction().getOtToUse() == true ? 1 : 0,
					item.getBeforehandRestriction().getOtRestrictPreDay().value,
					item.getBeforehandRestriction().getTimeBeforehandRestriction() == null ? null : item.getBeforehandRestriction().getTimeBeforehandRestriction().v(),
					item.getAfterhandRestriction().getAllowFutureDay() == true ? 1 : 0,
					item.getBeforehandRestriction().getPreOtTime() == null ? null : item.getBeforehandRestriction().getPreOtTime().v(),
					item.getBeforehandRestriction().getNormalOtTime() == null ? null : item.getBeforehandRestriction().getNormalOtTime().v()));
		}
		return listDto;
	}
}
