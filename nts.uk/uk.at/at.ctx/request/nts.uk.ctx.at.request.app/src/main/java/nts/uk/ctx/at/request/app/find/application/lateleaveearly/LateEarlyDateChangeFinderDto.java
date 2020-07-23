package nts.uk.ctx.at.request.app.find.application.lateleaveearly;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoWithDateOutput;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.LateEarlyDateChangeOutput;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.LateOrEarlyInfo;

@Getter
@Setter
@AllArgsConstructor
public class LateEarlyDateChangeFinderDto {

	private AppDispInfoWithDateOutput appDispInfoWithDateOutput;
	
	private List<LateOrEarlyInfo> lateOrEarlyInfoLst;
	
	private String errorInfo;
	
	public static LateEarlyDateChangeFinderDto fromDomain(LateEarlyDateChangeOutput dto) {
		return new LateEarlyDateChangeFinderDto(
				dto.getAppDispInfoWithDateOutput(),
				dto.getLateOrEarlyInfoLst(),
				dto.getErrorInfo().get());
	}
}
