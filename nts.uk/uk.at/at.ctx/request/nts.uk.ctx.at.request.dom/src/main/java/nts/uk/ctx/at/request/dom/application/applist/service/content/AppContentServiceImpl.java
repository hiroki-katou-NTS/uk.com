package nts.uk.ctx.at.request.dom.application.applist.service.content;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.util.Strings;

import nts.arc.enums.EnumAdaptor;
import nts.arc.i18n.I18NText;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.application.AppReason;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.ApprovalDevice;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.ReflectedState;
import nts.uk.ctx.at.request.dom.application.appabsence.HolidayAppType;
import nts.uk.ctx.at.request.dom.application.applist.extractcondition.AppListExtractCondition;
import nts.uk.ctx.at.request.dom.application.applist.extractcondition.ApplicationListAtr;
import nts.uk.ctx.at.request.dom.application.applist.service.ApplicationTypeDisplay;
import nts.uk.ctx.at.request.dom.application.applist.service.datacreate.StampAppOutputTmp;
import nts.uk.ctx.at.request.dom.application.applist.service.detail.AppContentDetailCMM045;
import nts.uk.ctx.at.request.dom.application.applist.service.detail.AppHolidayWorkDataOutput;
import nts.uk.ctx.at.request.dom.application.applist.service.detail.AppOvertimeDataOutput;
import nts.uk.ctx.at.request.dom.application.applist.service.detail.AppStampDataOutput;
import nts.uk.ctx.at.request.dom.application.applist.service.detail.ScreenAtr;
import nts.uk.ctx.at.request.dom.application.applist.service.param.ListOfApplication;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalBehaviorAtrImport_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalFrameImport_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalPhaseStateImport_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApproverStateImport_New;
import nts.uk.ctx.at.request.dom.application.common.ovetimeholiday.PreActualColorCheck;
import nts.uk.ctx.at.request.dom.application.common.service.other.CollectAchievement;
import nts.uk.ctx.at.request.dom.application.common.service.other.PreAppContentDisplay;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ActualContentDisplay;
import nts.uk.ctx.at.request.dom.application.holidayworktime.AppHolidayWork;
import nts.uk.ctx.at.request.dom.application.holidayworktime.AppHolidayWorkRepository;
import nts.uk.ctx.at.request.dom.application.overtime.AppOverTime;
import nts.uk.ctx.at.request.dom.application.overtime.AppOverTimeRepository;
import nts.uk.ctx.at.request.dom.application.overtime.ApplicationTime;
import nts.uk.ctx.at.request.dom.application.overtime.OverStateOutput;
import nts.uk.ctx.at.request.dom.setting.DisplayAtr;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.PrePostInitAtr;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.appovertime.OvertimeAppSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.appovertime.OvertimeAppSetRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.approvallistsetting.ApprovalListDisplaySetting;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.hdworkapplicationsetting.CalcStampMiss;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.hdworkapplicationsetting.HolidayWorkAppSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.hdworkapplicationsetting.HolidayWorkAppSetRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.optionalitemappsetting.OptionalItemApplicationTypeName;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon.ApplicationDetailSetting;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon.OvertimeLeaveAppCommonSet;
import nts.uk.ctx.at.request.dom.setting.company.appreasonstandard.AppReasonStandard;
import nts.uk.ctx.at.request.dom.setting.company.appreasonstandard.AppReasonStandardRepository;
import nts.uk.ctx.at.request.dom.setting.company.appreasonstandard.AppStandardReasonCode;
import nts.uk.ctx.at.request.dom.setting.company.appreasonstandard.ReasonForFixedForm;
import nts.uk.ctx.at.request.dom.setting.company.appreasonstandard.ReasonTypeItem;
import nts.uk.ctx.at.shared.dom.attendance.AttendanceItem;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@Stateless
public class AppContentServiceImpl implements AppContentService {
	
	@Inject
	private AppReasonStandardRepository appReasonStandardRepository;
	
	@Inject
	private AppContentDetailCMM045 appContentDetailCMM045;
	
	@Inject
	private WorkTypeRepository workTypeRepository;
	
	@Inject
	private WorkTimeSettingRepository workTimeSettingRepository;
	
	@Inject
	private PreActualColorCheck preActualColorCheck;
	
	@Inject
	private OvertimeAppSetRepository overtimeAppSetRepository;
	
	@Inject
	private HolidayWorkAppSetRepository holidayWorkAppSetRepository;
	
	@Inject
	private ApplicationRepository applicationRepository;
	
	@Inject
	private AppOverTimeRepository appOverTimeRepository;
	
	@Inject
	private AppHolidayWorkRepository appHolidayWorkRepository;
	
	@Inject
	private CollectAchievement collectAchievement;

	@Override
	public String getArrivedLateLeaveEarlyContent(AppReason appReason, DisplayAtr appReasonDisAtr, ScreenAtr screenAtr, List<ArrivedLateLeaveEarlyItemContent> itemContentLst,
			ApplicationType appType, AppStandardReasonCode appStandardReasonCD) {
		String result = Strings.EMPTY;
		String paramString = Strings.EMPTY;
		if(screenAtr != ScreenAtr.KAF018 && screenAtr != ScreenAtr.CMM045) {
			// @＝改行
			paramString = "\n";
		} else {
			// @＝”　”
			paramString = "	";
		}
		// ・<List>（項目名、勤務NO、区分（遅刻早退）、時刻、取消）
		if(!CollectionUtil.isEmpty(itemContentLst)) {
			for(int i = 0; i < itemContentLst.size(); i++) {
				ArrivedLateLeaveEarlyItemContent item = itemContentLst.get(i);
				if(i > 0) {
					// 申請内容＋＝@
					result += paramString;
				}
				// <List>.取消
				if(!item.isCancellation()) {
					// 申請内容＋＝項目名＋勤務NO＋'　'＋時刻
					result += item.getItemName() + item.getWorkNo() + " " + item.getOpTimeWithDayAttr().get().getFullText();
				} else {
					// 申請内容＋＝項目名＋勤務NO＋'　'＋#CMM045_292(取消)
					result += item.getItemName() + item.getWorkNo() + "	" + I18NText.getText("CMM045_292");
				}
			}
		}
		// アルゴリズム「申請内容の申請理由」を実行する
		String appReasonContent = this.getAppReasonContent(appReasonDisAtr, appReason, screenAtr, appStandardReasonCD, appType, Optional.empty());
		if(Strings.isNotBlank(appReasonContent)) {
			result += "\n" + appReasonContent;
		}
		return result;
	}

	@Override
	public ReasonForFixedForm getAppStandardReasonContent(ApplicationType appType, AppStandardReasonCode appStandardReasonCD,
			Optional<HolidayAppType> opHolidayAppType) {
		String companyID = AppContexts.user().companyId();
		// 定型理由コード(FormReasonCode)
		if(appStandardReasonCD == null) {
			return null;
		} 
		// ドメインモデル「申請定型理由」を取得する(Get domain 「ApplicationFormReason」)
		Optional<AppReasonStandard> opAppReasonStandard = Optional.empty();
		if(!opHolidayAppType.isPresent()) {
			opAppReasonStandard = appReasonStandardRepository.findByAppType(companyID, appType);
		} else {
			opAppReasonStandard = appReasonStandardRepository.findByHolidayAppType(companyID, opHolidayAppType.get());
		}
		if(!opAppReasonStandard.isPresent()) {
			return null;
		}
		// 定型理由＝定型理由項目.定型理由(FormReason = FormReasonItem. FormReason)
		List<ReasonTypeItem> reasonTypeItemLst = opAppReasonStandard.get().getReasonTypeItemLst();
		Optional<ReasonTypeItem> opReasonTypeItem = reasonTypeItemLst.stream().filter(x -> x.getAppStandardReasonCD().equals(appStandardReasonCD)).findAny();
		if(opReasonTypeItem.isPresent()) {
			return opReasonTypeItem.get().getReasonForFixedForm();
		}
		return null;
	}

	@Override
	public String getAppReasonContent(DisplayAtr appReasonDisAtr, AppReason appReason, ScreenAtr screenAtr,
			AppStandardReasonCode appStandardReasonCD, ApplicationType appType,
			Optional<HolidayAppType> opHolidayAppType) {
		// 申請理由内容　＝　String.Empty
		String result = Strings.EMPTY;
		if(!(screenAtr != ScreenAtr.KAF018 && 
				((appReason!= null && Strings.isNotBlank(appReason.v())) || (appStandardReasonCD != null))  && 
				appReasonDisAtr == DisplayAtr.DISPLAY)) {
			return result;
		}
		// アルゴリズム「申請内容定型理由取得」を実行する
		ReasonForFixedForm reasonForFixedForm = this.getAppStandardReasonContent(appType, appStandardReasonCD, opHolidayAppType);
		if(reasonForFixedForm!=null) {
			if(screenAtr == ScreenAtr.KDL030) {
				// 申請理由内容　+＝　”申請理由：”を改行
				result += "申請理由：  " + "\n";
				// 申請理由内容　+＝　定型理由＋改行＋Input．申請理由
				result += reasonForFixedForm.v();
				if(appReason!=null) {
					result += "\n" + appReason.v();
				}
			} else {
				// 申請理由内容　+＝　定型理由＋’　’＋Input．申請理由
				result += reasonForFixedForm.v();
				if(appReason!=null) {
					result += " " + appReason.v().replaceAll("\n", " ");	
				}
			}
		} else {
			if(screenAtr == ScreenAtr.KDL030) {
				// 申請理由内容　+＝　”申請理由：”を改行
				result += "申請理由：  " + "\n";
				// 申請理由内容　+＝　Input．申請理由
				if(appReason!=null) {
					result += appReason.v();	
				}
			} else {
				// 申請理由内容　+＝　Input．申請理由
				if(appReason!=null) {
					result += appReason.v().replaceAll("\n", " ");	
				}
			}
		}
		return result;
	}

	@Override
	public List<AppTypeMapProgramID> getListProgramIDOfAppType() {
		List<AppTypeMapProgramID> result = new ArrayList<>();
		// 残業申請＝KAF005、0
		result.add(new AppTypeMapProgramID(ApplicationType.OVER_TIME_APPLICATION, "KAF005", ApplicationTypeDisplay.EARLY_OVERTIME));
		// 残業申請＝KAF005、1
		result.add(new AppTypeMapProgramID(ApplicationType.OVER_TIME_APPLICATION, "KAF005", ApplicationTypeDisplay.NORMAL_OVERTIME));
		// 残業申請＝KAF005、2
		result.add(new AppTypeMapProgramID(ApplicationType.OVER_TIME_APPLICATION, "KAF005", ApplicationTypeDisplay.EARLY_NORMAL_OVERTIME));
		// 休暇申請＝KAF006
		result.add(new AppTypeMapProgramID(ApplicationType.ABSENCE_APPLICATION, "KAF006", null));
		// 勤務変更申請＝KAF007
		result.add(new AppTypeMapProgramID(ApplicationType.WORK_CHANGE_APPLICATION, "KAF007", null));
		// 出張申請＝KAF008
		result.add(new AppTypeMapProgramID(ApplicationType.BUSINESS_TRIP_APPLICATION, "KAF008", null));
		// 直行直帰申請＝KAF009
		result.add(new AppTypeMapProgramID(ApplicationType.GO_RETURN_DIRECTLY_APPLICATION, "KAF009", null));
		// 休出時間申請＝KAF010
		result.add(new AppTypeMapProgramID(ApplicationType.HOLIDAY_WORK_APPLICATION, "KAF010", null));
		// 打刻申請＝KAF002、0
		result.add(new AppTypeMapProgramID(ApplicationType.STAMP_APPLICATION, "KAF002", ApplicationTypeDisplay.STAMP_ADDITIONAL));
		// 打刻申請＝KAF002、1
		result.add(new AppTypeMapProgramID(ApplicationType.STAMP_APPLICATION, "KAF002", ApplicationTypeDisplay.STAMP_ONLINE_RECORD));
		// 時間年休申請＝KAF012
		result.add(new AppTypeMapProgramID(ApplicationType.ANNUAL_HOLIDAY_APPLICATION, "KAF012", null));
		// 遅刻早退取消申請＝KAF004
		result.add(new AppTypeMapProgramID(ApplicationType.EARLY_LEAVE_CANCEL_APPLICATION, "KAF004", null));
		// 振休振出申請＝KAF011
		result.add(new AppTypeMapProgramID(ApplicationType.COMPLEMENT_LEAVE_APPLICATION, "KAF011", null));
		// 任意申請＝KAF020
		result.add(new AppTypeMapProgramID(ApplicationType.OPTIONAL_ITEM_APPLICATION, "KAF020", null));
		// 対象の申請種類に対しプログラムIDを返す
		return result;
	}

	@Override
	public String getAppStampContent(DisplayAtr appReasonDisAtr, AppReason appReason, ScreenAtr screenAtr,
			List<StampAppOutputTmp> stampAppOutputTmpLst, ApplicationType appType, AppStandardReasonCode appStandardReasonCD) {
		String result = Strings.EMPTY;
		String paramString = Strings.EMPTY;
		if(screenAtr != ScreenAtr.KAF018 && screenAtr != ScreenAtr.CMM045) {
			// @＝改行
			paramString = "\n";
		} else {
			// @＝”　”
			paramString = "	";
		}
		if(!CollectionUtil.isEmpty(stampAppOutputTmpLst)) {
			for(int i = 0; i < stampAppOutputTmpLst.size(); i++) {
				StampAppOutputTmp item = stampAppOutputTmpLst.get(i);
				if(i > 0) {
					// 申請内容＋＝@
					result += paramString;
				}
				// 打刻申請出力用Tmp.取消
				if(!item.isCancel()) {
					// 申請内容＋＝$.項目名＋'　'＋$.開始時刻＋#CMM045_100(～)＋$.終了時刻
					result += item.getOpItemName().orElse("") + " " +
							item.getOpStartTime().map(x -> x.getFullText()).orElse("") + I18NText.getText("CMM045_100") + item.getOpEndTime().map(x -> x.getFullText()).orElse("");
				} else {
					// 申請内容＋＝$.項目名＋'　'＋#CMM045_292(取消)
					result += item.getOpItemName().orElse("") + " " + I18NText.getText("CMM045_292");
				}
			}
		}
		// アルゴリズム「申請内容の申請理由」を実行する
		String appReasonContent = this.getAppReasonContent(appReasonDisAtr, appReason, screenAtr, appStandardReasonCD, appType, Optional.empty());
		if(Strings.isNotBlank(appReasonContent)) {
			result += "\n" + appReasonContent;
		}
		return result;
	}

	@Override
	public String getWorkChangeGoBackContent(ApplicationType appType, String workTypeName, String workTimeName, NotUseAtr goWorkAtr1, TimeWithDayAttr workTimeStart1, 
			NotUseAtr goBackAtr1, TimeWithDayAttr workTimeEnd1, TimeWithDayAttr workTimeStart2, TimeWithDayAttr workTimeEnd2,
			TimeWithDayAttr breakTimeStart1, TimeWithDayAttr breakTimeEnd1, DisplayAtr appReasonDisAtr, AppReason appReason, Application application) {
		// 申請内容　＝　String.Empty ( Nội dung application = 　String.Empty)
		String result = Strings.EMPTY;
		if(appType == ApplicationType.WORK_CHANGE_APPLICATION) {
			// 申請内容　＝　Input．勤務種類名称 ( Nội dung = Input. Work type name)
			result = workTypeName;
			// 申請内容　+＝’　’　＋　Input．就業時間帯名称  ( Nội dung +＝　 Input.Working hour name)
			result += " " + workTimeName;
			// Input．．勤務直行1をチェック ( Check Input．．đi làm thẳng 1
			if(goWorkAtr1 == NotUseAtr.USE) {
				// 申請内容　+＝’　’＋#CMM045_252
				result += " " + I18NText.getText("CMM045_252");
			}
			// 申請内容　+＝　Input．勤務時間開始1
			result += workTimeStart1 == null ? "" : workTimeStart1.getInDayTimeWithFormat();
			// Input．勤務直帰1をチェック
			result += I18NText.getText("CMM045_100");
			if(goBackAtr1 == NotUseAtr.USE) {
				// 申請内容　+＝　#CMM045_100　+　#CMM045_252
				result += I18NText.getText("CMM045_252");
			}
			// 申請内容　+＝　Input．勤務時間終了1
			result += workTimeEnd1 == null ? "" : workTimeEnd1.getInDayTimeWithFormat();
		} else {
			// Input．．勤務直行1をチェック ( Check Input．．đi làm thẳng 1
			if(goWorkAtr1 == NotUseAtr.USE) {
				// 申請内容　+＝#CMM045_259＋’　’ ( Nội dung đơn xin　+＝#CMM045_259)
				result += I18NText.getText("CMM045_259") + " ";
			}
			// Input．勤務直帰1をチェック
			if(goBackAtr1 == NotUseAtr.USE) {
				// 申請内容　+＝　#CMM045_260
				result += I18NText.getText("CMM045_260");
			}
		}
		if(appType == ApplicationType.WORK_CHANGE_APPLICATION) {
			if(workTimeStart2!=null && workTimeEnd2!=null) {
				// 申請内容　+＝　’　’＋Input．勤務時間開始2＋#CMM045_100＋Input．勤務時間終了2
				result += " " + workTimeStart2.getInDayTimeWithFormat() + I18NText.getText("CMM045_100") + workTimeEnd2.getInDayTimeWithFormat();
			}
			if(!(breakTimeStart1==null || breakTimeStart1.v()==0 || breakTimeEnd1 == null || breakTimeEnd1.v()==0)) {
				result += " " + I18NText.getText("CMM045_251") + breakTimeStart1.getInDayTimeWithFormat() + breakTimeEnd1.getInDayTimeWithFormat();
			}
		}
		// 申請理由内容　＝　申請内容の申請理由
		String appReasonContent = this.getAppReasonContent(
				appReasonDisAtr, 
				appReason, 
				null, 
				application.getOpAppStandardReasonCD().orElse(null), 
				appType, 
				Optional.empty());
		if(Strings.isNotBlank(appReasonContent)) {
			result += "\n" + appReasonContent;
		}
		return result;
	}

	@Override
	public ListOfApplication createEachAppData(Application application, String companyID, List<WorkTimeSetting> lstWkTime,
			List<WorkType> lstWkType, List<AttendanceItem> attendanceItemLst, ApplicationListAtr mode, ApprovalListDisplaySetting approvalListDisplaySetting,
			ListOfApplication listOfApp, Map<String, List<ApprovalPhaseStateImport_New>> mapApproval, int device,
			AppListExtractCondition appListExtractCondition, List<String> agentLst) {
		if(device == ApprovalDevice.PC.value) {
			// ドメインモデル「申請」．申請種類をチェック (Check Domain「Application.ApplicationType
			switch (application.getAppType()) {
			case COMPLEMENT_LEAVE_APPLICATION:
				// 振休振出申請データを作成( Tạo data application nghỉ bù làm bù)
				break;
			case ABSENCE_APPLICATION:
				// 申請一覧リスト取得休暇 (Ngày nghỉ lấy  Application list)
				break;
			case GO_RETURN_DIRECTLY_APPLICATION:
				// 直行直帰申請データを作成 ( Tạo dữ liệu đơn xin đi làm, về nhà thẳng)
				String contentGoBack = appContentDetailCMM045.getContentGoBack(
						application, 
						approvalListDisplaySetting.getAppReasonDisAtr(), 
						lstWkTime, 
						lstWkType, 
						ScreenAtr.CMM045);
				listOfApp.setAppContent(contentGoBack);
				break;
			case WORK_CHANGE_APPLICATION:
				// 勤務変更申請データを作成
				String contentWorkChange = appContentDetailCMM045.getContentWorkChange(
						application, 
						approvalListDisplaySetting.getAppReasonDisAtr(), 
						lstWkTime, 
						lstWkType, 
						companyID);
				listOfApp.setAppContent(contentWorkChange);
				break;
			case OVER_TIME_APPLICATION:
				// 残業申請データを作成
				AppOvertimeDataOutput appOvertimeDataOutput = appContentDetailCMM045.createOvertimeContent(
						application, 
						lstWkType, 
						lstWkTime, 
						attendanceItemLst, 
						appListExtractCondition.getAppListAtr(), 
						approvalListDisplaySetting, 
						companyID);
				listOfApp.setAppContent(appOvertimeDataOutput.getAppContent());
				listOfApp.setOpAppTypeDisplay(appOvertimeDataOutput.getOpAppTypeDisplay());
				break;
			case HOLIDAY_WORK_APPLICATION:
				// 休出時間申請データを作成
				AppHolidayWorkDataOutput appHolidayWorkDataOutput = appContentDetailCMM045.createHolidayWorkContent(
						application, 
						lstWkType, 
						lstWkTime, 
						attendanceItemLst, 
						appListExtractCondition.getAppListAtr(), 
						approvalListDisplaySetting, 
						companyID);
				listOfApp.setAppContent(appHolidayWorkDataOutput.getAppContent());
				break;
			case BUSINESS_TRIP_APPLICATION:
				// 出張申請データを作成(Tạo data của 出張申請 )
				String contentBusinessTrip = appContentDetailCMM045.createBusinessTripData(
						application, 
						approvalListDisplaySetting.getAppReasonDisAtr(), 
						ScreenAtr.CMM045, 
						companyID);
				listOfApp.setAppContent(contentBusinessTrip);
				break;
			case OPTIONAL_ITEM_APPLICATION:
				// 任意申請データを作成(tạo data của任意申請 )
				String contentOptionalItemApp = appContentDetailCMM045.createOptionalItemApp(
						application, 
						approvalListDisplaySetting.getAppReasonDisAtr(), 
						ScreenAtr.CMM045, 
						companyID);
				listOfApp.setAppContent(contentOptionalItemApp);
				break;
			case STAMP_APPLICATION:
				// 打刻申請データを作成(tạo data của打刻申請 )
				AppStampDataOutput appStampDataOutput = appContentDetailCMM045.createAppStampData(
						application, 
						approvalListDisplaySetting.getAppReasonDisAtr(), 
						ScreenAtr.CMM045, 
						companyID, 
						null);
				listOfApp.setAppContent(appStampDataOutput.getAppContent());
				// 申請一覧.申請種類表示＝取得した申請種類表示(ApplicationList.AppTypeDisplay= AppTypeDisplay đã get)
				listOfApp.setOpAppTypeDisplay(appStampDataOutput.getOpAppTypeDisplay());
				break;
			case ANNUAL_HOLIDAY_APPLICATION:
				// 時間休暇申請データを作成(tạo data của時間休暇申請 )
				break;
			case EARLY_LEAVE_CANCEL_APPLICATION:
				// 遅刻早退取消申請データを作成(tạo data của 遅刻早退取消申請)
				String contentArrivedLateLeaveEarly = appContentDetailCMM045.createArrivedLateLeaveEarlyData(
						application, 
						approvalListDisplaySetting.getAppReasonDisAtr(), 
						ScreenAtr.CMM045, 
						companyID);
				listOfApp.setAppContent(contentArrivedLateLeaveEarly);
				break;
			default:
				listOfApp.setAppContent("-1");
				break;
			}
		}
		// 承認フェーズList　＝　Input．Map＜ルートインスタンスID、承認フェーズList＞を取得(ApprovalPhaseList= Input．Map＜get RootInstanceID, ApprovalPhaseList>)
		listOfApp.setOpApprovalPhaseLst(Optional.of(mapApproval.get(application.getAppID())));
		// 申請一覧．承認状況照会　＝　承認状況照会内容(AppList.ApproveStatusRefer =ApproveStatusReferContents )
		listOfApp.setOpApprovalStatusInquiry(Optional.of(this.getApprovalStatusInquiryContent(listOfApp.getOpApprovalPhaseLst().get())));
		// アルゴリズム「反映状態を取得する」を実行する(Thực hiện thuật toán [lấy trạng thái phản ánh])
		ReflectedState reflectedState = application.getAppReflectedState();
		String reflectedStateString = reflectedState.name;
		if(mode == ApplicationListAtr.APPLICATION) {
			switch (reflectedState) {
			case NOTREFLECTED:
				if(device==ApprovalDevice.PC.value) {
					reflectedStateString = "CMM045_62";
				} else {
					reflectedStateString = "CMMS45_7";
				}
				break;
			case WAITREFLECTION:
				if(device==ApprovalDevice.PC.value) {
					reflectedStateString = "CMM045_63";
				} else {
					reflectedStateString = "CMMS45_8";
				}
				break;
			case REFLECTED:
				if(device==ApprovalDevice.PC.value) {
					reflectedStateString = "CMM045_64";
				} else {
					reflectedStateString = "CMMS45_9";
				}
				break;
			case DENIAL:
				if(device==ApprovalDevice.PC.value) {
					reflectedStateString = "CMM045_65";
				} else {
					reflectedStateString = "CMMS45_11";
				}
				break;
			case REMAND:
				if(device==ApprovalDevice.PC.value) {
					reflectedStateString = "CMM045_66";
				} else {
					reflectedStateString = "CMMS45_36";
				}
				break;
			case CANCELED:
				if(device==ApprovalDevice.PC.value) {
					reflectedStateString = "CMM045_67";
				} else {
					reflectedStateString = "CMMS45_10";
				}
				break;
			default:
				break;
			}
		}
		if(mode == ApplicationListAtr.APPROVER) {
			ApprovalBehaviorAtrImport_New phaseAtr = null;
			ApprovalBehaviorAtrImport_New frameAtr = null;
			String loginID = AppContexts.user().employeeId();
			List<ApprovalPhaseStateImport_New> listPhase = listOfApp.getOpApprovalPhaseLst().orElse(Collections.emptyList());
			boolean isBreak = false;
			for(ApprovalPhaseStateImport_New phase : listPhase) {
				if(isBreak) {
					break;
				}
				for(ApprovalFrameImport_New frame : phase.getListApprovalFrame()) {
					if(isBreak) {
						break;
					}
					for(ApproverStateImport_New approver : frame.getListApprover()) {
						boolean isMapWithApprover = false;
						boolean isMapWithAgent = false;
						if(phase.getApprovalAtr()==ApprovalBehaviorAtrImport_New.REMAND ||
							(phase.getApprovalAtr()==ApprovalBehaviorAtrImport_New.UNAPPROVED && approver.getApprovalAtr()==ApprovalBehaviorAtrImport_New.UNAPPROVED)) {
							isMapWithApprover = approver.getApproverID().equals(loginID);
							isMapWithAgent = agentLst.contains(approver.getApproverID());
						} else {
							isMapWithApprover = approver.getApproverID().equals(loginID);
							isMapWithAgent = (Strings.isNotBlank(approver.getAgentID()) && approver.getAgentID().equals(loginID));
						}
						if (!isMapWithApprover && !isMapWithAgent) {
							continue;
						}
						Optional<ApprovalPhaseStateImport_New> opPhaseBeforeNotApproved = Optional.empty();
						if(listPhase.size()!=1) {
							opPhaseBeforeNotApproved = listPhase.stream()
									.filter(x -> x.getPhaseOrder() > phase.getPhaseOrder())
									.filter(x -> x.getApprovalAtr()!=ApprovalBehaviorAtrImport_New.APPROVED).findAny();
						}
						if(opPhaseBeforeNotApproved.isPresent()) {
							continue;
						}
						// 承認枠　＝　ロープの承認枠(ApprovalFrame= ApproveFrame dang loop)
						// 承認フェーズ　＝　ロープの承認フェーズ(ApprovalPhase = ApprovalPhase dang loop)
						// 承認枠の承認状態　＝　承認枠．承認区分(ApprovalStatus của ApprovalFrame = ApprovalFrame. ApprovalAtr)
						phaseAtr = phase.getApprovalAtr();
						frameAtr = approver.getApprovalAtr();
						isBreak = true;
						listOfApp.setOpApprovalFrameStatus(Optional.of(frameAtr.value));
						// 反映状態　＝　反映状態（承認一覧モード）//Trạng thái phản ánh= trạng thái phản ánh(mode danh sách approve)
						reflectedStateString = this.getReflectStatusApprovalListMode(reflectedState, phaseAtr, frameAtr, device);
					}
				}
			}
		}
		// 申請一覧．反映状態　＝　申請の反映状態(ApplicationList. trạng thái phản ánh = trạng thái phản ánh của đơn xin)
		listOfApp.setReflectionStatus(reflectedStateString);
		return listOfApp;
	}

	@Override
	public String getApprovalStatusInquiryContent(List<ApprovalPhaseStateImport_New> approvalPhaseLst) {
		// 承認状況照会　＝　String.Empty (tham khảo tình trạng approval)
		String result = Strings.EMPTY;
		for(ApprovalPhaseStateImport_New phase : approvalPhaseLst) {
			if(phase.getApprovalAtr() == ApprovalBehaviorAtrImport_New.UNAPPROVED || 
					phase.getApprovalAtr() == ApprovalBehaviorAtrImport_New.REMAND ||
					phase.getApprovalAtr() == ApprovalBehaviorAtrImport_New.ORIGINAL_REMAND) {
				// 承認状況照会　+＝　”－”  // (tham khảo tình trạng approval)
				result += "－";
			}
			if(phase.getApprovalAtr() == ApprovalBehaviorAtrImport_New.APPROVED) {
				// 承認状況照会　+＝　”〇” // (tham khảo tình trạng approval)
				result += "〇";
			}
			if(phase.getApprovalAtr() == ApprovalBehaviorAtrImport_New.DENIAL) {
				// 承認状況照会　+＝　”×” // (tham khảo tình trạng approval)
				result += "×";
			}
		}
		return result;
	}

	@Override
	public String getReflectStatusApprovalListMode(ReflectedState reflectedState,
			ApprovalBehaviorAtrImport_New phaseAtr, ApprovalBehaviorAtrImport_New frameAtr, int device) {
		String result = Strings.EMPTY;
		// 反映状態(trạng thái phản ánh)　＝　PC：#CMM045_62スマホ：#CMMS45_7
		if(device==ApprovalDevice.PC.value) {
			result = "CMM045_62";
		} else {
			result = "CMMS45_7";
		}
		// 反映状態(trạng thái phản ánh)　＝　PC：#CMM045_64スマホ：#CMMS45_9
		if(reflectedState==ReflectedState.REFLECTED) {
			if(device==ApprovalDevice.PC.value) {
				result = "CMM045_64";
			} else {
				result = "CMMS45_9";
			}
			return result;
		}
		// 反映状態(trạng thái phản ánh)　＝　PC：#CMM045_63スマホ：#CMMS45_8
		boolean condition1 = 
				(reflectedState==ReflectedState.WAITREFLECTION) ||
				(phaseAtr==ApprovalBehaviorAtrImport_New.APPROVED) ||
				(frameAtr==ApprovalBehaviorAtrImport_New.APPROVED);
		if(condition1) {
			if(device==ApprovalDevice.PC.value) {
				result = "CMM045_63";
			} else {
				result = "CMMS45_8";
			}
			return result;
		}
		// 反映状態　＝　PC：#CMM045_65スマホ：#CMMS45_11
		if(reflectedState==ReflectedState.DENIAL) {
			if(device==ApprovalDevice.PC.value) {
				result = "CMM045_65";
			} else {
				result = "CMMS45_11";
			}
			return result;
		}
		// 反映状態　＝　PC：#CMM045_66スマホ：#CMMS45_36
		if(reflectedState==ReflectedState.NOTREFLECTED && phaseAtr==ApprovalBehaviorAtrImport_New.REMAND) {
			if(device==ApprovalDevice.PC.value) {
				result = "CMM045_66";
			} else {
				result = "CMMS45_36";
			}
			return result;
		}
		// 反映状態　＝　PC：#CMM045_67スマホ：#CMMS45_10
		if(reflectedState==ReflectedState.CANCELED) {
			if(device==ApprovalDevice.PC.value) {
				result = "CMM045_67";
			} else {
				result = "CMMS45_10";
			}
			return result;
		}
		return result;
	}

	@Override
	public String getOptionalItemAppContent(AppReason appReason, DisplayAtr appReasonDisAtr, ScreenAtr screenAtr,
			OptionalItemApplicationTypeName optionalItemApplicationTypeName, List<OptionalItemOutput> optionalItemOutputLst, 
			ApplicationType appType, AppStandardReasonCode appStandardReasonCD) {
		String result = Strings.EMPTY;
		String paramString = Strings.EMPTY;
		// ScreenID
		if(screenAtr != ScreenAtr.KAF018 && screenAtr != ScreenAtr.CMM045) {
			// @＝改行
			paramString = "\n";
		} else {
			// @＝”　”
			paramString = "";
		}
		// 申請内容＝任意申請種類名
		result = optionalItemApplicationTypeName.v();
		for(OptionalItemOutput optionalItemOutput : optionalItemOutputLst) {
			// 申請内容＋＝@＋”　”＋<List>任意項目名称＋”　”＋<List>値＋<List>単位
			result += paramString + " " + optionalItemOutput.getOptionalItemName().v() + " ";
			if(optionalItemOutput.getAnyItemValue().getTime().isPresent()) {
				result += new TimeWithDayAttr(optionalItemOutput.getAnyItemValue().getTime().get().v()).getRawTimeWithFormat() + optionalItemOutput.getUnit().v();
				continue;
			}
			if(optionalItemOutput.getAnyItemValue().getTimes().isPresent()) {
				result += optionalItemOutput.getAnyItemValue().getTimes().get().v() + optionalItemOutput.getUnit().v();
				continue;
			}
			if(optionalItemOutput.getAnyItemValue().getAmount().isPresent()) {
				result += optionalItemOutput.getAnyItemValue().getAmount().get().v() + optionalItemOutput.getUnit().v();
				continue;
			}
		}
		// アルゴリズム「申請内容の申請理由」を実行する
		String appReasonContent = this.getAppReasonContent(appReasonDisAtr, appReason, screenAtr, appStandardReasonCD, appType, Optional.empty());
		if(Strings.isNotBlank(appReasonContent)) {
			result += "\n" + appReasonContent;
		}
		return result;
	}

	@Override
	public String getOvertimeHolidayWorkContent(AppOverTimeData appOverTimeData, AppHolidayWorkData appHolidayWorkData,
			ApplicationType appType, PrePostAtr prePostAtr, ApplicationListAtr applicationListAtr, AppReason appReason, 
			DisplayAtr appReasonDisAtr, ScreenAtr screenAtr, boolean actualStatus, Application application) {
		// 申請内容　＝　String．Empty
		String result = "";
		if(prePostAtr==PrePostAtr.POSTERIOR && applicationListAtr==ApplicationListAtr.APPROVER) {
			// 申請内容　+＝　#CMM045_272
			result += I18NText.getText("CMM045_272");
		}
		// 申請内容　+＝「申請の内容（事前、事後内容）」を取得
		result += this.getDetailApplicationPrePost(appType, appOverTimeData, appHolidayWorkData);
		if(!(applicationListAtr==ApplicationListAtr.APPROVER && prePostAtr==PrePostAtr.POSTERIOR)) {
			// 申請内容を改行(thêm kí tự xuống dòng)
			result += "\n";
			// 申請内容　+＝　#CMM045_282 +　”　”　+　時間外時間データ．「実績時間 + 申請時間」　+　#CMM045_283　+　{0}回
			result += I18NText.getText("CMM045_282") + I18NText.getText("CMM045_283", I18NText.getText("CMM045_284")); 
		} else {
			if((appType==ApplicationType.OVER_TIME_APPLICATION && appOverTimeData.getOpPreAppData().isPresent()) ||
				(appType==ApplicationType.HOLIDAY_WORK_APPLICATION && appHolidayWorkData.getOpPreAppData().isPresent())){
				// 申請内容　を改行(thêm ký tự xuống dòng)
				result += "\n";
				// 申請内容　+＝　#CMM045_273 (nội dung đơn xin)
				result += I18NText.getText("CMM045_273");
				// 申請内容　+＝　「申請の内容（事前、事後内容）」を取得
				result += this.getDetailApplicationPrePost(
						appType, 
						appOverTimeData.getOpPreAppData().orElse(null), 
						appHolidayWorkData.getOpPreAppData().orElse(null));
			} else {
				// 申請内容　＋＝CMM045_273＋CMM045_306
				result += I18NText.getText("CMM045_273") + I18NText.getText("CMM045_306");
			}
			// Input実績状態
			if(!actualStatus) {
				// 申請内容　＋＝　#CMM045_274
				result += I18NText.getText("CMM045_274");
//				if(actualStatus==ActualStatus.NO_ACTUAL) {
//					// 申請内容　＋＝#CMM045_306
//					result += I18NText.getText("CMM045_306");
//				} else {
//					// 申請内容　＋＝#CMM045_305
//					result += I18NText.getText("CMM045_305");
//				}
				// 申請内容　＋＝#CMM045_306
				result += I18NText.getText("CMM045_306");
			} else {
				// 申請内容　+＝　「事後申請の実績データの内容」を取得
				if(appType==ApplicationType.HOLIDAY_WORK_APPLICATION) {
					result += this.getContentActualStatusCheckResult(appHolidayWorkData.getOpPostAppData().orElse(null));
				} else {
					result += this.getContentActualStatusCheckResult(appHolidayWorkData.getOpPostAppData().orElse(null));
				}
				// 申請内容　+＝　「勤怠項目の内容」を取得
				List<AppTimeFrameData> appTimeFrameDataLst = Collections.emptyList();
				if(appType==ApplicationType.HOLIDAY_WORK_APPLICATION) {
					appTimeFrameDataLst = appHolidayWorkData.getOpPostAppData().map(x -> x.getAppTimeFrameDataLst()).orElse(Collections.emptyList());
				} else {
					appTimeFrameDataLst = appOverTimeData.getOpPostAppData().map(x -> x.getAppTimeFrameDataLst()).orElse(Collections.emptyList());
				}
				result += this.getDisplayFrame(appType, appTimeFrameDataLst);
			}
//			if(appType==ApplicationType.HOLIDAY_WORK_APPLICATION) {
//				if(appHolidayWork.getAppOvertimeDetail().isPresent()) {
//					// 申請内容　+＝　#CMM045_282 +　”　”　+　時間外時間データ．「実績時間 + 申請時間」　+　#CMM045_283　+　{0}回
//					result += I18NText.getText("CMM045_282") + I18NText.getText("CMM045_283", I18NText.getText("CMM045_284"));
//				}
//			} else {
//				if(appOverTime.getDetailOverTimeOp().isPresent()) {
//					// 申請内容　+＝　#CMM045_282 +　”　”　+　時間外時間データ．「実績時間 + 申請時間」　+　#CMM045_283　+　{0}回
//					result += I18NText.getText("CMM045_282") + I18NText.getText("CMM045_283", I18NText.getText("CMM045_284"));
//				}
//			}
		}
		// 申請理由内容　＝　申請内容の申請理由
		String appReasonContent = this.getAppReasonContent(
				appReasonDisAtr, 
				appReason, 
				null, 
				application.getOpAppStandardReasonCD().orElse(null), 
				appType, 
				Optional.empty());
		// 申請内容を改行(xuống dòng nội dung đơn xin)
		if(Strings.isNotBlank(appReasonContent)) {
			result += "\n" + appReasonContent;
		}
		return result;
	}
	
	/**
	 * 申請の内容（事前、事後内容）
	 * @return
	 */
	private String getDetailApplicationPrePost(ApplicationType appType, AppOverTimeData appOverTimeData, AppHolidayWorkData appHolidayWorkData) {
		String companyID = AppContexts.user().companyId();
		// 申請内容　＝　String．Empty
		String result = "";
		if(appType==ApplicationType.HOLIDAY_WORK_APPLICATION) {
			// 申請内容　+＝　申請データ．勤務種類名称
			result += appHolidayWorkData.getOpWorkTimeCD().orElse("");
			// 申請内容　+＝　申請データ．就業時間帯名称
			result += appHolidayWorkData.getOpWorkTypeName().orElse("");
			if(Strings.isNotBlank(result)) {
				result += " ";
			}
		}
		if(appType==ApplicationType.HOLIDAY_WORK_APPLICATION) {
			// 申請内容　+＝　申請データ．勤務開始時間
			result += new TimeWithDayAttr(appHolidayWorkData.getStartTime()).getFullText();
			if(Strings.isNotBlank(result)) {
				result += " ";
			}
			// 申請内容　+＝　#CMM045_100+　申請データ．勤務終了時間
			result += I18NText.getText("CMM045_100") + new TimeWithDayAttr(appHolidayWorkData.getEndTime()).getFullText();
			if(Strings.isNotBlank(result)) {
				result += " ";
			}
			// 勤怠項目の内容
			result += this.getDisplayFrame(appType, appHolidayWorkData.getAppTimeFrameDataLst());
			if(Strings.isNotBlank(result)) {
				result += " ";
			}
		} else {
			// 申請内容　+＝　申請データ．勤務開始時間
			result += new TimeWithDayAttr(appOverTimeData.getStartTime()).getFullText();
			if(Strings.isNotBlank(result)) {
				result += " ";
			}
			// 申請内容　+＝　#CMM045_100+　申請データ．勤務終了時間
			result += I18NText.getText("CMM045_100") + new TimeWithDayAttr(appOverTimeData.getEndTime()).getFullText();
			if(Strings.isNotBlank(result)) {
				result += " ";
			}
			// 勤怠項目の内容
			result += this.getDisplayFrame(appType, appOverTimeData.getAppTimeFrameDataLst());
			if(Strings.isNotBlank(result)) {
				result += " ";
			}
		}
		return result;
	}
	
	/**
	 * 勤怠項目の内容
	 * @param appType
	 * @param appHolidayWork
	 * @param appOverTime
	 * @return
	 */
	private String getDisplayFrame(ApplicationType appType, List<AppTimeFrameData> appTimeFrameDataLst) {
		String result = "";
		return result;
	}
	
	/**
	 * 事後申請の実績データの内容
	 * @param actualStatusCheckResult
	 * @return
	 */
	private String getContentActualStatusCheckResult(PostAppData postAppData) {
		// 実績内容　＝　#CMM045_274 (nội dung thực tế ＝　#CMM045_274)
		String result = I18NText.getText("CMM045_274");
		// 実績内容　+＝　事後申請の実績データ．勤務種類名称 (nội dung thực tế +＝　data thực tế của đơn xin sau . WorktypeName)
		result += postAppData.getWorkTypeName();
		// 実績内容　+＝　事後申請の実績データ．就業時間帯名称 (nội dung thực tế　+＝ data thực tế của đơn xin sau . WorkTimeName )
		result += postAppData.getOpWorkTimeName().orElse("");
		// 実績内容　+＝　事後申請の実績データ．開始時間 (Nội dung thực tế +＝ Data thực tế của đơn xin sau . StartTime)
		result += new TimeWithDayAttr(postAppData.getStartTime()).getFullText();
		// 実績内容　+＝　#CMM045_100　+　事後申請の実績データ．終了時間 (Nội dung thực tế +＝ 　#CMM045_100　+　Data thực tế của đơn xin sau . EndTime)
		result += I18NText.getText("CMM045_100") + new TimeWithDayAttr(postAppData.getEndTime()).getFullText();
		return result;
	}

	@Override
	public OvertimeHolidayWorkActual getOvertimeHolidayWorkActual(String companyID, Application application, WorkTypeCode workType, WorkTimeCode workTime) {
		// 申請.申請種類(Application.AppType)
		if(application.getAppType()!=ApplicationType.OVER_TIME_APPLICATION && application.getAppType()!=ApplicationType.HOLIDAY_WORK_APPLICATION) {
			return null;
		}
		OvertimeLeaveAppCommonSet overtimeLeaveAppCommonSet = null;
		ApplicationDetailSetting applicationDetailSetting = null;
		Optional<CalcStampMiss> calStampMiss = Optional.empty();
		if(application.getAppType()==ApplicationType.OVER_TIME_APPLICATION) {
			// ドメインモデル「残業申請設定」を取得する
			OvertimeAppSet overtimeAppSet = overtimeAppSetRepository.findSettingByCompanyId(companyID).get();
			overtimeLeaveAppCommonSet = overtimeAppSet.getOvertimeLeaveAppCommonSet();
			applicationDetailSetting = overtimeAppSet.getApplicationDetailSetting();
		} else {
			// ドメインモデル「休日出勤申請設定」を取得する
			HolidayWorkAppSet holidayWorkAppSet = holidayWorkAppSetRepository.findSettingByCompany(companyID).get();
			overtimeLeaveAppCommonSet = holidayWorkAppSet.getOvertimeLeaveAppCommonSet();
			applicationDetailSetting = holidayWorkAppSet.getApplicationDetailSetting();
			calStampMiss = Optional.of(holidayWorkAppSet.getCalcStampMiss());
		}
		// アルゴリズム「事前内容の取得」を実行する
		PreAppContentDisplay preAppContentDisplay = new PreAppContentDisplay(application.getAppDate().getApplicationDate(), Optional.empty(), Optional.empty());
		List<PreAppContentDisplay> preAppContentDisplayLst = collectAchievement.getPreAppContents(
				companyID, 
				application.getEmployeeID(), 
				Collections.emptyList(), 
				application.getAppType());
		if(!CollectionUtil.isEmpty(preAppContentDisplayLst)) {
			preAppContentDisplay = preAppContentDisplayLst.get(0);
		}
		// アルゴリズム「実績内容の取得」を実行する
		Optional<ActualContentDisplay> actualContentDisplay = Optional.empty();
		List<ActualContentDisplay> actualContentDisplayLst = collectAchievement.getAchievementContents(
				companyID, 
				application.getEmployeeID(), 
				Arrays.asList(application.getAppDate().getApplicationDate()), 
				application.getAppType());
		if(!CollectionUtil.isEmpty(actualContentDisplayLst)) {
			actualContentDisplay = Optional.of(actualContentDisplayLst.get(0));
		}
		// アルゴリズム「07-02_実績取得・状態チェック」を実行する(thực hiện thuật toán「07-02_get thực tế・check trạng thái」 )
		ApplicationTime achiveOp = preActualColorCheck.checkStatus(
				companyID, 
				application.getEmployeeID(), 
				application.getAppDate().getApplicationDate(), 
				application.getAppType(),
				workType, 
				workTime, 
				overtimeLeaveAppCommonSet.getOverrideSet(),
				Optional.empty(), 
				Collections.emptyList(),
				actualContentDisplay);
		// アルゴリズム「事前申請・実績の時間超過をチェックする」を実行する
		ApplicationTime subsequentOp = application.getAppType()==ApplicationType.OVER_TIME_APPLICATION
				? ((AppOverTime) application).getApplicationTime() : ((AppHolidayWork) application).getApplicationTime();
		OverStateOutput overStateOutput = overtimeLeaveAppCommonSet.checkPreApplication(
				EnumAdaptor.valueOf(application.getPrePostAtr().value, PrePostInitAtr.class), 
				preAppContentDisplay.getAppHolidayWork().map(x -> x.getApplicationTime()), 
				Optional.of(subsequentOp), 
				Optional.ofNullable(achiveOp));
		return null;
	}
}
