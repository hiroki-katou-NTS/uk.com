package nts.uk.ctx.at.request.dom.application.common.service.smartphone.output;

import lombok.Getter;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.service.checkpostappaccept.PostAppAcceptLimit;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.service.checkpreappaccept.PreAppAcceptLimit;
import nts.uk.ctx.at.request.dom.setting.workplace.appuseset.ApplicationUseSetting;

/**
 * refactor 4
 * UKDesign.UniversalK.就業.KAF_申請.KAFS00_申請部品（スマホ）.KAFS00_A_申請メッセージ.アルゴリズム.起動する.KAFS00_A_表示情報
 * @author Doan Duy Hung
 *
 */
@Getter
public class RequestMsgInfoOutput {
	
	/**
	 * 申請利用設定
	 */
	private ApplicationUseSetting applicationUseSetting;
	
	/**
	 * 当月の締切制限
	 */
	private DeadlineLimitCurrentMonth deadlineLimitCurrentMonth;
	
	/**
	 * 事前申請の受付制限
	 */
	private PreAppAcceptLimit preAppAcceptLimit;
	
	/**
	 * 事後申請の受付制限
	 */
	private PostAppAcceptLimit postAppAcceptLimit;
	
	public RequestMsgInfoOutput(ApplicationUseSetting applicationUseSetting, DeadlineLimitCurrentMonth deadlineLimitCurrentMonth,
			PreAppAcceptLimit preAppAcceptLimit, PostAppAcceptLimit postAppAcceptLimit) {
		this.applicationUseSetting = applicationUseSetting;
		this.deadlineLimitCurrentMonth = deadlineLimitCurrentMonth;
		this.preAppAcceptLimit = preAppAcceptLimit;
		this.postAppAcceptLimit = postAppAcceptLimit;
	}
	
}
