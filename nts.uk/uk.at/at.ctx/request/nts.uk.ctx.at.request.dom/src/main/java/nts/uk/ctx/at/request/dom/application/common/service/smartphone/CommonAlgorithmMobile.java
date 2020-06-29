package nts.uk.ctx.at.request.dom.application.common.service.smartphone;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.appabsence.HolidayAppType;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoNoDateOutput;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;
import nts.uk.ctx.at.request.dom.application.common.service.smartphone.output.AppReasonOutput;
import nts.uk.ctx.at.request.dom.application.overtime.OverTimeAtr;

/**
 * refactor 4
 * スマホ申請共通アルゴリズム
 * @author Doan Duy Hung
 *
 */
public interface CommonAlgorithmMobile {
	
	/**
	 * 申請共通起動処理
	 * @param mode 起動モード（編集モード・表示モード） 編集モード: true, 表示モード: false
	 * @param companyID 会社ID
	 * @param employeeID 社員ID
	 * @param appType 申請種類
	 * @param opHolidayAppType 休暇申請の種類＜Optional＞
	 * @param dateLst 申請対象日リスト
	 * @param opOverTimeAtr 残業区分＜Optional＞
	 * @return 申請表示情報
	 */
	public AppDispInfoStartupOutput appCommonStartProcess(boolean mode, String companyID, String employeeID,
			ApplicationType appType, Optional<HolidayAppType> opHolidayAppType, 
			List<GeneralDate> dateLst, Optional<OverTimeAtr> opOverTimeAtr);
	
	/**
	 * UKDesign.UniversalK.就業.KAF_申請.KAFS00_申請部品（スマホ）.スマホ申請共通アルゴリズム.申請共通設定情報を取得する.申請共通設定情報を取得する
	 * @param mode 起動モード（編集モード・表示モード） 編集モード: true, 表示モード: false
	 * @param companyID 会社ID
	 * @param employeeID 社員ID
	 * @param appType 申請種類
	 * @param opHolidayAppType 休暇申請の種類<Optional>
	 * @return 申請表示情報(基準日関係なし)
	 */
	public AppDispInfoNoDateOutput getAppCommonSetInfo(boolean mode, String companyID, String employeeID,
			ApplicationType appType, Optional<HolidayAppType> opHolidayAppType );
	
	/**
	 * UKDesign.UniversalK.就業.KAF_申請.KAFS00_申請部品（スマホ）.スマホ申請共通アルゴリズム.申請理由表示区分を取得する.申請理由表示区分を取得する
	 * @param companyID 会社ID
	 * @param appType 申請種類
	 * @param opHolidayAppType 休暇申請の種類<Optional>
	 * @return
	 */
	public AppReasonOutput getAppReasonDisplay(String companyID, ApplicationType appType, Optional<HolidayAppType> opHolidayAppType);
	
	
}
