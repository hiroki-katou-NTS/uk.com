package nts.uk.ctx.at.request.dom.application.common.service.smartphone;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.appabsence.HolidayAppType;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoNoDateOutput;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoWithDateOutput;
import nts.uk.ctx.at.request.dom.application.common.service.smartphone.output.AppReasonOutput;
import nts.uk.ctx.at.request.dom.application.common.service.smartphone.output.RequestMsgInfoOutput;
import nts.uk.ctx.at.request.dom.application.overtime.OvertimeAppAtr;
import nts.uk.ctx.at.request.dom.setting.DisplayAtr;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.ApplicationSetting;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.OTAppBeforeAccepRestric;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.PrePostInitAtr;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.ReceptionRestrictionSetting;
import nts.uk.ctx.at.request.dom.setting.workplace.appuseset.ApplicationUseSetting;

/**
 * refactor 4
 * UKDesign.UniversalK.就業.KAF_申請.KAFS00_申請部品（スマホ）.スマホ申請共通アルゴリズム.スマホ申請共通アルゴリズム
 * @author Doan Duy Hung
 *
 */
public interface CommonAlgorithmMobile {
	
	/**
	 * UKDesign.UniversalK.就業.KAF_申請.KAFS00_申請部品（スマホ）.スマホ申請共通アルゴリズム.申請共通起動処理.申請共通起動処理
	 * @param mode 起動モード（編集モード・表示モード） 編集モード: true, 表示モード: false
	 * @param companyID 会社ID
	 * @param employeeID 社員ID
	 * @param appType 申請種類
	 * @param opHolidayAppType 休暇申請の種類＜Optional＞
	 * @param dateLst 申請対象日リスト
	 * @param opOvertimeAppAtr 残業区分＜Optional＞
	 * @return 申請表示情報
	 */
	public AppDispInfoStartupOutput appCommonStartProcess(boolean mode, String companyID, String employeeID,
			ApplicationType appType, Optional<HolidayAppType> opHolidayAppType, 
			List<GeneralDate> dateLst, Optional<OvertimeAppAtr> opOvertimeAppAtr);
	
	/**
	 * UKDesign.UniversalK.就業.KAF_申請.KAFS00_申請部品（スマホ）.スマホ申請共通アルゴリズム.申請共通設定情報を取得する.申請共通設定情報を取得する
	 * @param companyID 会社ID
	 * @param employeeID 社員ID
	 * @param appType 申請種類
	 * @param opHolidayAppType 休暇申請の種類<Optional>
	 * @return 申請表示情報(基準日関係なし)
	 */
	public AppDispInfoNoDateOutput getAppCommonSetInfo(String companyID, String employeeID,
			ApplicationType appType, Optional<HolidayAppType> opHolidayAppType );
	
	/**
	 * UKDesign.UniversalK.就業.KAF_申請.KAFS00_申請部品（スマホ）.スマホ申請共通アルゴリズム.申請理由表示区分を取得する.申請理由表示区分を取得する
	 * @param companyID 会社ID
	 * @param appType 申請種類
	 * @param opHolidayAppType 休暇申請の種類<Optional>
	 * @return
	 */
	public AppReasonOutput getAppReasonDisplay(String companyID, ApplicationType appType, Optional<HolidayAppType> opHolidayAppType);
	
	/**
	 * UKDesign.UniversalK.就業.KAF_申請.KAFS00_申請部品（スマホ）.スマホ申請共通アルゴリズム.基準日に関係する申請共通設定情報を取得する.基準日に関係する申請設定情報を取得する
	 * @param mode 起動モード（編集モード・表示モード） 編集モード: true, 表示モード: false
	 * @param companyID 会社ID
	 * @param employeeID 社員ID
	 * @param appDateLst 申請対象日リスト
	 * @param appType 申請種類
	 * @param applicationSetting 申請設定
	 * @param opOvertimeAppAtr 残業区分<Optional>
	 * @return
	 */
	public AppDispInfoWithDateOutput getAppSetInfoRelatedBaseDate(boolean mode, String companyID, String employeeID,
			List<GeneralDate> appDateLst, ApplicationType appType, ApplicationSetting applicationSetting, 
			Optional<OvertimeAppAtr> opOvertimeAppAtr);
	
	/**
	 * UKDesign.UniversalK.就業.KAF_申請.KAFS00_申請部品（スマホ）.スマホ申請共通アルゴリズム.基準日として扱う日の取得.基準日として扱う日の取得
	 * @param applicationSetting 申請設定
	 * @param appType 申請種類
	 * @param appDateLst 申請対象日リスト
	 * @return
	 */
	public GeneralDate getBaseDate(ApplicationSetting applicationSetting, ApplicationType appType, List<GeneralDate> appDateLst);
	
	/**
	 * UKDesign.UniversalK.就業.KAF_申請.KAFS00_申請部品（スマホ）.スマホ申請共通アルゴリズム.事前事後の初期選択状態を取得する.事前事後の初期選択状態を取得する
	 * @param appDate 申請対象日
	 * @param appType 申請種類
	 * @param prePostDisplayAtr 事前事後区分表示
	 * @param displayInitialSegment 事前事後区分の初期表示
	 * @param overtimeAppAtr 残業区分<Optional>
	 * @param otAppBeforeAccepRestric 残業申請事前の受付制限
	 * @return
	 */
	public PrePostInitAtr getPrePostInitAtr(Optional<GeneralDate> opAppDate, ApplicationType appType, DisplayAtr prePostDisplayAtr,
			PrePostInitAtr displayInitialSegment, Optional<OvertimeAppAtr> opOvertimeAppAtr, OTAppBeforeAccepRestric otAppBeforeAccepRestric);
	
	/**
	 * UKDesign.UniversalK.就業.KAF_申請.KAFS00_申請部品（スマホ）.KAFS00_A_申請メッセージ.アルゴリズム.起動する.起動する
	 * @param companyID 会社ID
	 * @param employeeID 社員ID
	 * @param employmentCD 雇用コード
	 * @param applicationUseSetting 申請利用設定
	 * @param receptionRestrictionSetting 受付制限設定
	 * @param opOvertimeAppAtr Optional＜残業申請区分＞
	 * @return
	 */
	public RequestMsgInfoOutput getRequestMsgInfoOutputMobile(String companyID, String employeeID, String employmentCD, 
			ApplicationUseSetting applicationUseSetting, ReceptionRestrictionSetting receptionRestrictionSetting,
			Optional<OvertimeAppAtr> opOvertimeAppAtr);
	
	/**
	 * UKDesign.UniversalK.就業.KAF_申請.KAFS00_申請部品（スマホ）.スマホ申請共通アルゴリズム.申請共通起動処理（詳細）.申請共通起動処理（詳細）
	 * @param companyID 会社ID
	 * @param appID 申請ID
	 * @return
	 */
	public AppDispInfoStartupOutput getDetailMob(String companyID, String appID);
}
