package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting;

import java.util.List;
import java.util.Optional;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.overtime.OvertimeAppAtr;
import nts.uk.ctx.at.request.dom.application.stamp.StampRequestMode;

/**
 * refactor 4
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.設定.会社別.申請承認設定.申請設定.代行申請で利用できる申請設定
 * @author Doan Duy Hung
 *
 */
@Getter
public class AppSetForProxyApp {
	
	/**
	 * 申請種類
	 */
	private ApplicationType appType;
	
	/**
	 * 残業区分
	 */
	private Optional<OvertimeAppAtr> opOvertimeAppAtr;
	
	/**
	 * 打刻申請モード
	 */
	private Optional<StampRequestMode> opStampRequestMode;
	
	public AppSetForProxyApp(ApplicationType appType,
			Optional<OvertimeAppAtr> opOvertimeAppAtr, Optional<StampRequestMode> opStampRequestMode) {
		this.appType = appType;
		this.opOvertimeAppAtr = opOvertimeAppAtr;
		this.opStampRequestMode = opStampRequestMode;
	}

	public static AppSetForProxyApp create(int applicationType, Integer overTimeAtr, Integer stampRequestMode) {
		return new AppSetForProxyApp(
				EnumAdaptor.valueOf(applicationType, ApplicationType.class),
				overTimeAtr == null ? Optional.empty() : Optional.of(EnumAdaptor.valueOf(overTimeAtr, OvertimeAppAtr.class)),
				stampRequestMode == null ? Optional.empty() : Optional.of(EnumAdaptor.valueOf(stampRequestMode, StampRequestMode.class))
		);
	}
	
}
