package nts.uk.ctx.at.request.dom.application.applist.service.content;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.util.Strings;

import nts.arc.i18n.I18NText;
import nts.uk.ctx.at.request.dom.application.AppReason;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.appabsence.HolidayAppType;
import nts.uk.ctx.at.request.dom.setting.company.appreasonstandard.AppReasonStandard;
import nts.uk.ctx.at.request.dom.setting.company.appreasonstandard.AppReasonStandardRepository;
import nts.uk.ctx.at.request.dom.setting.company.appreasonstandard.AppStandardReasonCode;
import nts.uk.ctx.at.request.dom.setting.company.appreasonstandard.ReasonForFixedForm;
import nts.uk.ctx.at.request.dom.setting.company.appreasonstandard.ReasonTypeItem;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.displaysetting.DisplayAtr;
import nts.uk.shr.com.context.AppContexts;

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
	public String getArrivedLateLeaveEarlyContent(AppReason appReason, DisplayAtr appReasonDisAtr, String screenID, List<ArrivedLateLeaveEarlyItemContent> itemContentLst,
			ApplicationType appType, AppStandardReasonCode appStandardReasonCD) {
		String result = Strings.EMPTY;
		String paramString = Strings.EMPTY;
		if(screenID.equals("KAF018") || screenID.equals("CMM045")) {
			// @＝改行
			paramString = "\n";
		} else {
			// @＝”　”
			paramString = "	";
		}
		// ・<List>（項目名、勤務NO、区分（遅刻早退）、時刻、取消）
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
		String appReasonContent = this.getAppReasonContent(appReasonDisAtr, appReason, screenID, appStandardReasonCD, appType, Optional.empty());
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
			return opReasonTypeItem.get().getReasonForFixedForm();
		}
		return null;
	}

	@Override
	public String getAppReasonContent(DisplayAtr appReasonDisAtr, AppReason appReason, String screenID,
			AppStandardReasonCode appStandardReasonCD, ApplicationType appType,
			Optional<HolidayAppType> opHolidayAppType) {
		// 申請理由内容　＝　String.Empty
		String result = Strings.EMPTY;
		if(!(!screenID.equals("KAF018") && appReason!= null && appReasonDisAtr == DisplayAtr.DISPLAY)) {
			return result;
		}
		// アルゴリズム「申請内容定型理由取得」を実行する
		ReasonForFixedForm reasonForFixedForm = this.getAppStandardReasonContent(appType, appStandardReasonCD, opHolidayAppType);
		if(reasonForFixedForm!=null) {
			if(screenID.equals("メール送信画面")) {
				// 申請理由内容　+＝　”申請理由：”を改行
				result += "申請理由：  " + "\n";
				// 申請理由内容　+＝　定型理由＋改行＋Input．申請理由
				result += reasonForFixedForm.v() + "\n" + appReason.v();
			} else {
				// 申請理由内容　+＝　定型理由＋’　’＋Input．申請理由
				result += reasonForFixedForm.v() + " " + appReason.v();
			}
		} else {
			if(screenID.equals("メール送信画面")) {
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

}
