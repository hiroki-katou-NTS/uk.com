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
	private List<Integer> appTypeLst;
	
	/**
	 * 残業区分
	 */
	private Integer opOvertimeAppAtr;
	
	/**
	 * 打刻申請モード
	 */
	private Integer opStampRequestMode;
	
	public static AppSetForProxyAppDto fromDomain(AppSetForProxyApp appSetForProxyApp) {
		return new AppSetForProxyAppDto(
				appSetForProxyApp.getAppTypeLst().stream().map(x -> x.value).collect(Collectors.toList()), 
				appSetForProxyApp.getOpOvertimeAppAtr().map(x -> x.value).orElse(null), 
				appSetForProxyApp.getOpStampRequestMode().map(x -> x.value).orElse(null));
	}
	
	public AppSetForProxyApp toDomain() {
		return new AppSetForProxyApp(
				appTypeLst.stream().map(x -> EnumAdaptor.valueOf(x, ApplicationType.class)).collect(Collectors.toList()), 
				opOvertimeAppAtr == null ? Optional.empty() : Optional.of(EnumAdaptor.valueOf(opOvertimeAppAtr, OvertimeAppAtr.class)), 
				opStampRequestMode == null ? Optional.empty() : Optional.of(EnumAdaptor.valueOf(opStampRequestMode, StampRequestMode.class)));
	}
}
