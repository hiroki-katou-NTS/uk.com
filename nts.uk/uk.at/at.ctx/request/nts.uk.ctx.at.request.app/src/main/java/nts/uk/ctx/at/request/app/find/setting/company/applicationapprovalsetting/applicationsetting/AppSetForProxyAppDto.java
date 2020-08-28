package nts.uk.ctx.at.request.app.find.setting.company.applicationapprovalsetting.applicationsetting;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.overtime.OvertimeAppAtr;
import nts.uk.ctx.at.request.dom.application.stamp.StampRequestMode;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.AppSetForProxyApp;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
public class AppSetForProxyAppDto {
	/**
	 * 申請種類
	 */
	private Integer appType;
	
	/**
	 * 残業区分
	 */
	private Integer overtimeAppAtr;
	
	/**
	 * 打刻申請モード
	 */
	private Integer stampRequestMode;
	
	public static AppSetForProxyAppDto fromDomain(AppSetForProxyApp appSetForProxyApp) {
		if (appSetForProxyApp == null) return null;
		return new AppSetForProxyAppDto(
				appSetForProxyApp.getAppType().value,
				appSetForProxyApp.getOpOvertimeAppAtr().map(x -> x.value).orElse(null), 
				appSetForProxyApp.getOpStampRequestMode().map(x -> x.value).orElse(null));
	}

	public AppSetForProxyApp toDomain() {
		return new AppSetForProxyApp(
				EnumAdaptor.valueOf(appType, ApplicationType.class),
				overtimeAppAtr == null ? Optional.empty() : Optional.of(EnumAdaptor.valueOf(overtimeAppAtr, OvertimeAppAtr.class)),
				stampRequestMode == null ? Optional.empty() : Optional.of(EnumAdaptor.valueOf(stampRequestMode, StampRequestMode.class)));
	}
}
