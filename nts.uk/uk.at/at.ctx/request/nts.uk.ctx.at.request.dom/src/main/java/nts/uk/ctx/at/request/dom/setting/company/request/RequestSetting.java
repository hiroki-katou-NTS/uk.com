package nts.uk.ctx.at.request.dom.setting.company.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.ApplicationSetting;
import nts.uk.ctx.at.request.dom.setting.company.request.appreflect.AppReflectionSetting;
import nts.uk.ctx.at.request.dom.setting.company.request.approvallistsetting.AppReflectAfterConfirm;
import nts.uk.ctx.at.request.dom.setting.company.request.approvallistsetting.ApprovalListDisplaySetting;

/**
 * 申請承認設定
 * @author Doan Duy Hung
 *
 */
@Getter
@AllArgsConstructor
public class RequestSetting extends AggregateRoot {

	private String companyID;
	
	/**
	 * 申請設定
	 */
	private ApplicationSetting applicationSetting;
	
	/**
	 * 申請反映設定
	 */
	private AppReflectionSetting appReflectionSetting;
	
	/**
	 * 承認一覧表示設定
	 */
	private ApprovalListDisplaySetting approvalListDisplaySetting;
	
	/**
	 * 承認設定
	 */
	private AuthorizationSetting authorizationSetting;
	
	/**
	 * データが確立が確定されている場合の承認済申請の反映
	 */
	private AppReflectAfterConfirm appReflectAfterConfirm;
	
}
