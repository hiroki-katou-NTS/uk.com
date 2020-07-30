package nts.uk.ctx.at.request.dom.application.stamp;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.StampRecordOutput;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;
import nts.uk.ctx.at.request.dom.application.stamp.output.AppStampOutput;
import nts.uk.ctx.at.request.dom.application.stamp.output.ErrorStampInfo;
import nts.uk.ctx.at.request.dom.setting.company.request.stamp.AppStampReflect;

public interface AppCommonDomainService {
	
	/**
	 * 打刻申請（新規）起動前処理
	 *UKDesign.UniversalK.就業.KAF_申請.KAF002_打刻申請.B：打刻申請（新規）→A・B画面.打刻申請（新規）共通アルゴリズム.打刻申請（新規）起動前処理
	 * @param companyId
	 * @param dates
	 * @param appDispInfoStartupOutput
	 * @param recoderFlag
	 * @return 打刻申請起動時の表示情報
	 */
	public AppStampOutput getDataCommon(String companyId, Optional<GeneralDate> dates, AppDispInfoStartupOutput appDispInfoStartupOutput, Boolean recoderFlag);
	
	/**Refactor4
	 * 打刻申請の設定チェック
	 * UKDesign.UniversalK.就業.KAF_申請.KAF002_打刻申請.B：打刻申請（新規）→A・B画面.打刻申請（新規）共通アルゴリズム.打刻申請の設定チェック
	 * @param appStampReflect
	 * @param temporaryWorkUseManage
	 */
	public void checkAppStampSetting(AppStampReflect appStampReflect, Boolean temporaryWorkUseManage);
	
	/**Refactor4
	 * 実績の打刻のチェック
	 * @param achievementDetail
	 * @return 打刻エラー情報
	 */
	public List<ErrorStampInfo> getErrorStampList(StampRecordOutput stampRecordOutput);
	
}
