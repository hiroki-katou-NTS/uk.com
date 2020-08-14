package nts.uk.ctx.at.request.dom.application.applist.service.content;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.util.Strings;

import nts.arc.i18n.I18NText;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.application.AppReason;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.appabsence.HolidayAppType;
import nts.uk.ctx.at.request.dom.application.applist.service.ApplicationTypeDisplay;
import nts.uk.ctx.at.request.dom.application.applist.service.ListOfAppTypes;
import nts.uk.ctx.at.request.dom.application.applist.service.datacreate.StampAppOutputTmp;
import nts.uk.ctx.at.request.dom.application.applist.service.detail.ScreenAtr;
import nts.uk.ctx.at.request.dom.setting.company.appreasonstandard.AppReasonStandard;
import nts.uk.ctx.at.request.dom.setting.company.appreasonstandard.AppReasonStandardRepository;
import nts.uk.ctx.at.request.dom.setting.company.appreasonstandard.AppStandardReasonCode;
import nts.uk.ctx.at.request.dom.setting.company.appreasonstandard.ReasonForFixedForm;
import nts.uk.ctx.at.request.dom.setting.company.appreasonstandard.ReasonTypeItem;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.displaysetting.DisplayAtr;
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

	@Override
	public String getArrivedLateLeaveEarlyContent(AppReason appReason, DisplayAtr appReasonDisAtr, ScreenAtr screenAtr, List<ArrivedLateLeaveEarlyItemContent> itemContentLst,
			ApplicationType appType, AppStandardReasonCode appStandardReasonCD) {
		String result = Strings.EMPTY;
		String paramString = Strings.EMPTY;
		if(screenAtr == ScreenAtr.KAF018 || screenAtr == ScreenAtr.CMM045) {
			// @＝改行
			paramString = "\n";
		} else {
			// @＝”　”
			paramString = "	";
		}
		// ・<List>（項目名、勤務NO、区分（遅刻早退）、時刻、取消）
		if(!CollectionUtil.isEmpty(itemContentLst)) {
			for(int i = 0; i < itemContentLst.size(); i++) {
				ArrivedLateLeaveEarlyItemContent item = itemContentLst.get(0);
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
			result += appReasonContent;
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
			return opReasonTypeItem.get().getOpReasonForFixedForm().orElse(null);
		}
		return null;
	}

	@Override
	public String getAppReasonContent(DisplayAtr appReasonDisAtr, AppReason appReason, ScreenAtr screenAtr,
			AppStandardReasonCode appStandardReasonCD, ApplicationType appType,
			Optional<HolidayAppType> opHolidayAppType) {
		// 申請理由内容　＝　String.Empty
		String result = Strings.EMPTY;
		if(!(screenAtr != ScreenAtr.KAF018 && appReason!= null && appReasonDisAtr == DisplayAtr.DISPLAY)) {
			return result;
		}
		// アルゴリズム「申請内容定型理由取得」を実行する
		ReasonForFixedForm reasonForFixedForm = this.getAppStandardReasonContent(appType, appStandardReasonCD, opHolidayAppType);
		if(reasonForFixedForm!=null) {
			if(screenAtr == ScreenAtr.KDL030) {
				// 申請理由内容　+＝　”申請理由：”を改行
				result += "申請理由：  " + "\n";
				// 申請理由内容　+＝　定型理由＋改行＋Input．申請理由
				result += reasonForFixedForm.v() + "\n" + appReason.v();
			} else {
				// 申請理由内容　+＝　定型理由＋’　’＋Input．申請理由
				result += reasonForFixedForm.v() + " " + appReason.v();
			}
		} else {
			if(screenAtr == ScreenAtr.KDL030) {
				// 申請理由内容　+＝　”申請理由：”を改行
				result += "申請理由：  " + "\n";
				// 申請理由内容　+＝　Input．申請理由
				result += appReason.v();
			} else {
				// 申請理由内容　+＝　Input．申請理由
				result += appReason.v();
			}
		}
		return result;
	}

	@Override
	public List<ListOfAppTypes> getAppNameInAppList() {
		List<ListOfAppTypes> result = new ArrayList<>();
		// アルゴリズム「申請一覧申請種類のプログラムID」を実行する
		List<AppTypeMapProgramID> appTypeMapProgramIDLst = this.getListProgramIDOfAppType();
		for(AppTypeMapProgramID item : appTypeMapProgramIDLst) {
			result.add(new ListOfAppTypes(
					item.getAppType(), 
					Strings.EMPTY, 
					false, 
					Optional.of(item.getProgramID()), 
					item.getApplicationTypeDisplay() == null ? Optional.empty() : Optional.of(item.getApplicationTypeDisplay()), 
					Optional.of("A")));
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
		if(screenAtr == ScreenAtr.KAF018 || screenAtr == ScreenAtr.CMM045) {
			// @＝改行
			paramString = "\n";
		} else {
			// @＝”　”
			paramString = "	";
		}
		if(!CollectionUtil.isEmpty(stampAppOutputTmpLst)) {
			for(int i = 0; i < stampAppOutputTmpLst.size(); i++) {
				StampAppOutputTmp item = stampAppOutputTmpLst.get(0);
				if(i > 0) {
					// 申請内容＋＝@
					result += paramString;
				}
				// 打刻申請出力用Tmp.取消
				if(!item.isCancel()) {
					// 申請内容＋＝$.項目名＋'　'＋$.開始時刻＋#CMM045_100(～)＋$.終了時刻
					result += item.getOpItemName().get() + item.getOpStartTime().get().getFullText() + I18NText.getText("CMM045_100") + item.getOpEndTime().get().getFullText();
				} else {
					// 申請内容＋＝$.項目名＋'　'＋#CMM045_292(取消)
					result += item.getOpItemName().get() + I18NText.getText("CMM045_292");
				}
			}
		}
		// アルゴリズム「申請内容の申請理由」を実行する
		String appReasonContent = this.getAppReasonContent(appReasonDisAtr, appReason, screenAtr, appStandardReasonCD, appType, Optional.empty());
		if(Strings.isNotBlank(appReasonContent)) {
			result += appReasonContent;
		}
		return result;
	}

	@Override
	public String getWorkChangeGoBackContent(ApplicationType appType, String workTypeName, String workTimeName,
			NotUseAtr goWorkAtr1, TimeWithDayAttr workTimeStart1, NotUseAtr goBackAtr1, TimeWithDayAttr workTimeEnd1,
			TimeWithDayAttr breakTimeStart1, TimeWithDayAttr breakTimeEnd1, DisplayAtr appReasonDisAtr,
			AppReason appReason, Application application) {
		// 申請内容　＝　String.Empty ( Nội dung application = 　String.Empty)
		String result = Strings.EMPTY;
		if(appType == ApplicationType.WORK_CHANGE_APPLICATION) {
			// 申請内容　＝　Input．勤務種類名称 ( Nội dung = Input. Work type name)
			result = workTypeName;
			// 申請内容　+＝’　’　＋　Input．就業時間帯名称  ( Nội dung +＝　 Input.Working hour name)
			result += " " + workTimeName;
		}
		// Input．．勤務直行1をチェック ( Check Input．．đi làm thẳng 1
		if(goWorkAtr1 == NotUseAtr.NOT_USE) {
			// 申請内容　+＝　Input．勤務時間開始1
			result += workTimeStart1.getInDayTimeWithFormat();
		} else {
			if(appType == ApplicationType.WORK_CHANGE_APPLICATION) {
				// 申請内容　+＝’　’＋#CMM045_252
				result += " " + I18NText.getText("CMM045_252");
				// 申請内容　+＝　Input．勤務時間開始1
				result += workTimeStart1.getInDayTimeWithFormat();
			} else if(appType == ApplicationType.GO_RETURN_DIRECTLY_APPLICATION) {
				// 申請内容　+＝#CMM045_259＋’　’ ( Nội dung đơn xin　+＝#CMM045_259)
				result += I18NText.getText("CMM045_259");
			}
		}
		// Input．勤務直帰1をチェック
		if(goBackAtr1 == NotUseAtr.NOT_USE) {
			// 申請内容　+＝　#CMM045_100 
			result += I18NText.getText("CMM045_100");
			// 申請内容　+＝　Input．勤務時間終了1
			result += workTimeEnd1.getInDayTimeWithFormat();
		} else {
			if(appType == ApplicationType.WORK_CHANGE_APPLICATION) {
				// 申請内容　+＝　#CMM045_100　+　#CMM045_252
				result += I18NText.getText("CMM045_100") + I18NText.getText("CMM045_252");
				// 申請内容　+＝　Input．勤務時間終了1
				result += workTimeEnd1.getInDayTimeWithFormat();
			} else if(appType == ApplicationType.GO_RETURN_DIRECTLY_APPLICATION) {
				// 申請内容　+＝　#CMM045_260
				result += I18NText.getText("CMM045_260");
			}
		}
		if(appType == ApplicationType.WORK_CHANGE_APPLICATION) {
			// 
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
			result += appReasonContent;
		}
		return result;
	}

}
