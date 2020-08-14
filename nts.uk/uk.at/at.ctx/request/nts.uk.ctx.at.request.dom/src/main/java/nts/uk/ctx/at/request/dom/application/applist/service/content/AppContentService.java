package nts.uk.ctx.at.request.dom.application.applist.service.content;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.request.dom.application.AppReason;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.appabsence.HolidayAppType;
import nts.uk.ctx.at.request.dom.application.applist.service.ListOfAppTypes;
import nts.uk.ctx.at.request.dom.application.applist.service.datacreate.StampAppOutputTmp;
import nts.uk.ctx.at.request.dom.application.applist.service.detail.ScreenAtr;
import nts.uk.ctx.at.request.dom.setting.company.appreasonstandard.AppStandardReasonCode;
import nts.uk.ctx.at.request.dom.setting.company.appreasonstandard.ReasonForFixedForm;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.displaysetting.DisplayAtr;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * refactor 4
 * UKDesign.UniversalK.就業.KAF_申請.CMM045_申請一覧・承認一覧.A:申請一覧画面ver4.アルゴリズム.申請内容ver4
 * @author Doan Duy Hung
 *
 */
public interface AppContentService {
	
	/**
	 * refactor 4
	 * UKDesign.UniversalK.就業.KAF_申請.CMM045_申請一覧・承認一覧.A:申請一覧画面ver4.アルゴリズム.申請内容ver4.申請内容（遅刻早退取消）.申請内容（遅刻早退取消）
	 * @param appReason 申請理由
	 * @param appReasonDisAtr 申請理由表示区分
	 * @param screenID ScreenID
	 * @param itemContentLst <List>（項目名、勤務NO、区分（遅刻早退）、時刻、取消）
	 * @param appType 申請種類
	 * @param appStandardReasonCD 定型理由コード
	 * @return
	 */
	public String getArrivedLateLeaveEarlyContent(AppReason appReason, DisplayAtr appReasonDisAtr, ScreenAtr screenAtr, List<ArrivedLateLeaveEarlyItemContent> itemContentLst,
			ApplicationType appType, AppStandardReasonCode appStandardReasonCD);
	
	/**
	 * refactor 4
	 * UKDesign.UniversalK.就業.KAF_申請.CMM045_申請一覧・承認一覧.A:申請一覧画面.アルゴリズム.各申請データを作成.申請内容.申請内容定型理由取得.申請内容定型理由取得
	 * @param appType 申請種類
	 * @param appStandardReasonCD 定型理由コード
	 * @param opHolidayAppType 休暇申請の種類(Optional)
	 * @return
	 */
	public ReasonForFixedForm getAppStandardReasonContent(ApplicationType appType, AppStandardReasonCode appStandardReasonCD, Optional<HolidayAppType> opHolidayAppType);
	
	/**
	 * refactor 4
	 * UKDesign.UniversalK.就業.KAF_申請.CMM045_申請一覧・承認一覧.A:申請一覧画面.アルゴリズム.各申請データを作成.申請内容.申請内容（残業申請、休日出勤申請）.申請内容の申請理由.申請内容の申請理由
	 * @param appReasonDisAtr 申請理由表示区分
	 * @param appReason 申請理由
	 * @param screenID ScreenID
	 * @param appStandardReasonCD 定型理由コード
	 * @param appType 申請種類
	 * @param opHolidayAppType 休暇申請の種類(Optional)
	 * @return
	 */
	public String getAppReasonContent(DisplayAtr appReasonDisAtr, AppReason appReason, ScreenAtr screenAtr, AppStandardReasonCode appStandardReasonCD,
			ApplicationType appType, Optional<HolidayAppType> opHolidayAppType);
	
	/**
	 * refactor 4
	 * UKDesign.UniversalK.就業.KAF_申請.CMM045_申請一覧・承認一覧.A:申請一覧画面ver4.アルゴリズム.申請一覧の申請名称を取得する.申請一覧の申請名称を取得する
	 * @return
	 */
	public List<ListOfAppTypes> getAppNameInAppList();
	
	/**
	 * refactor 4
	 * UKDesign.UniversalK.就業.KAF_申請.CMM045_申請一覧・承認一覧.A:申請一覧画面ver4.アルゴリズム.申請一覧の申請名称を取得する.申請一覧申請種類のプログラムID.申請一覧申請種類のプログラムID
	 * @return
	 */
	public List<AppTypeMapProgramID> getListProgramIDOfAppType(); 
	
	/**
	 * refactor 4
	 * UKDesign.UniversalK.就業.KAF_申請.CMM045_申請一覧・承認一覧.A:申請一覧画面ver4.アルゴリズム.申請内容ver4.申請内容（打刻申請）.申請内容（打刻申請）
	 * @param appReasonDisAtr 申請理由表示区分
	 * @param appReason 申請理由
	 * @param screenAtr ScreenID
	 * @param stampAppOutputTmpLst 打刻申請出力用Tmp
	 * @param appType 申請種類
	 * @param appStandardReasonCD 定型理由コード
	 * @return
	 */
	public String getAppStampContent(DisplayAtr appReasonDisAtr, AppReason appReason, ScreenAtr screenAtr, List<StampAppOutputTmp> stampAppOutputTmpLst,
			ApplicationType appType, AppStandardReasonCode appStandardReasonCD);
	
	/**
	 * refactor 4
	 * UKDesign.UniversalK.就業.KAF_申請.CMM045_申請一覧・承認一覧.A:申請一覧画面.アルゴリズム.各申請データを作成.申請内容.申請内容（勤務変更申請、直行直帰申請）.申請内容（勤務変更申請、直行直帰申請）
	 * @param appType 申請種類
	 * @param workTypeName 勤務種類名称
	 * @param workTimeName 就業時間帯名称
	 * @param goWorkAtr1 勤務直行1
	 * @param workTimeStart1 勤務時間開始1
	 * @param goBackAtr1 勤務直帰1
	 * @param workTimeEnd1 勤務時間終了1
	 * @param breakTimeStart1 休憩時間開始1
	 * @param breakTimeEnd1 休憩時間終了1
	 * @param appReasonDisAtr 申請理由表示区分
	 * @param appReason 申請理由
	 * @param application 申請
	 * @return
	 */
	public String getWorkChangeGoBackContent(ApplicationType appType, String workTypeName, String workTimeName, NotUseAtr goWorkAtr1, TimeWithDayAttr workTimeStart1, 
			NotUseAtr goBackAtr1, TimeWithDayAttr workTimeEnd1, TimeWithDayAttr breakTimeStart1, TimeWithDayAttr breakTimeEnd1, DisplayAtr appReasonDisAtr,
			AppReason appReason, Application application);
}
