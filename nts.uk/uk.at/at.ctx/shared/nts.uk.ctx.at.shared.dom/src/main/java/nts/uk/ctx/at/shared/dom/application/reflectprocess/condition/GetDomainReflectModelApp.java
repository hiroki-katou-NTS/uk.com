package nts.uk.ctx.at.shared.dom.application.reflectprocess.condition;

import java.util.Optional;

import nts.uk.ctx.at.shared.dom.adapter.application.reflect.SHAppReflectionSetting;
import nts.uk.ctx.at.shared.dom.application.common.ApplicationTypeShare;

/**
 * @author thanh_nx
 *
 *         各申請反映のドメインモデルを取得する
 */
public class GetDomainReflectModelApp {

	public static void process(ApplicationTypeShare appType, Optional<Object> typeDaikyu) {

	}

	public static interface Require {
		/**
		 * 
		 * require{ 申請反映設定を取得する(会社ID、申請種類） }
		 * 
		 * SHRequestSettingAdapter
		 */
		public Optional<SHAppReflectionSetting> getAppReflectionSetting(String companyId, ApplicationTypeShare appType);
	}
}
