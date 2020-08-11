package nts.uk.ctx.at.request.dom.application.applist.service.datacreate;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.util.Strings;

import nts.arc.i18n.I18NText;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.applist.service.ListOfAppTypes;
import nts.uk.ctx.at.request.dom.application.applist.service.content.AppContentService;
import nts.uk.ctx.at.request.dom.application.applist.service.content.ArrivedLateLeaveEarlyItemContent;
import nts.uk.ctx.at.request.dom.application.lateleaveearly.ArrivedLateLeaveEarly;
import nts.uk.ctx.at.request.dom.application.lateleaveearly.ArrivedLateLeaveEarlyRepository;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.LateCancelation;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.LateOrEarlyAtr;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.TimeReport;
import nts.uk.ctx.at.request.dom.application.stamp.StampRequestMode;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.displaysetting.DisplayAtr;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@Stateless
public class AppDataCreationImpl implements AppDataCreation {
	
	@Inject
	private ArrivedLateLeaveEarlyRepository arrivedLateLeaveEarlyRepository;
	
	@Inject
	private AppContentService appContentService;

	@Override
	public void createAppStampData(Application application, DisplayAtr appReasonDisAtr, String screenID,
			String companyID, ListOfAppTypes listOfAppTypes) {
		// TODO Auto-generated method stub
		if(application.getOpStampRequestMode().get()==StampRequestMode.STAMP_ONLINE_RECORD) {
			
		}
	}

	@Override
	public String createArrivedLateLeaveEarlyData(Application application, DisplayAtr appReasonDisAtr, String screenID,
			String companyID) {
		// ドメインモデル「遅刻早退取消申請」
		ArrivedLateLeaveEarly arrivedLateLeaveEarly = arrivedLateLeaveEarlyRepository.getLateEarlyApp(companyID, application.getAppID(), application);
		List<ArrivedLateLeaveEarlyItemContent> itemContentLst = new ArrayList<>();
		// 「遅刻早退取消申請.時刻報告」
		for(TimeReport timeReport : arrivedLateLeaveEarly.getLateOrLeaveEarlies()) {
			String itemName = Strings.EMPTY;
			if(timeReport.getLateOrEarlyClassification() == LateOrEarlyAtr.LATE) {
				itemName = I18NText.getText("CMM045_236");
			} else if(timeReport.getLateOrEarlyClassification() == LateOrEarlyAtr.EARLY) {
				itemName = I18NText.getText("CMM045_238");
			}
			itemContentLst.add(new ArrivedLateLeaveEarlyItemContent(
					itemName, 
					timeReport.getWorkNo(), 
					timeReport.getLateOrEarlyClassification(), 
					Optional.of(timeReport.getTimeWithDayAttr()), 
					false));
		}
		// 「遅刻早退取消申請.取消」
		for(LateCancelation lateCancelation : arrivedLateLeaveEarly.getLateCancelation()) {
			String itemName = Strings.EMPTY;
			if(lateCancelation.getLateOrEarlyClassification() == LateOrEarlyAtr.LATE) {
				itemName = I18NText.getText("CMM045_236");
			} else if(lateCancelation.getLateOrEarlyClassification() == LateOrEarlyAtr.EARLY) {
				itemName = I18NText.getText("CMM045_238");
			}
			itemContentLst.add(new ArrivedLateLeaveEarlyItemContent(
					itemName, 
					lateCancelation.getWorkNo(), 
					lateCancelation.getLateOrEarlyClassification(), 
					Optional.empty(), 
					true));
		}
		// <List>を勤務NO+区分（遅刻、早退の順）で並べる
		itemContentLst.sort(Comparator.comparing((ArrivedLateLeaveEarlyItemContent x) -> {
			return String.valueOf(x.getWorkNo()) + String.valueOf(x.getLateOrEarlyAtr().value);
		}));
		// アルゴリズム「申請内容（遅刻早退取消）」を実行する
		return appContentService.getArrivedLateLeaveEarlyContent(
				application.getOpAppReason().orElse(null), 
				appReasonDisAtr, 
				screenID, 
				itemContentLst, 
				application.getAppType(), 
				application.getOpAppStandardReasonCD().orElse(null));
	}

}
