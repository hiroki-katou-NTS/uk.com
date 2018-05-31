package nts.uk.ctx.at.function.dom.dailyfix;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;

@Getter
@AllArgsConstructor
public class AppliCalDaiCorrec {
	/** 会社ID */
	private String companyId;
	/** 呼び出す申請一覧 */
	private ApplicationType appType;
	
	public static AppliCalDaiCorrec createFromJavaType(String companyId, int appType){
		return new AppliCalDaiCorrec(companyId, EnumAdaptor.valueOf(appType, ApplicationType.class));
	}
}
