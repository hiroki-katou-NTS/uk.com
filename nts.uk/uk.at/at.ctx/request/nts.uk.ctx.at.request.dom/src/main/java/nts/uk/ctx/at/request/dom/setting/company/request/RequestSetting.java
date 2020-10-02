package nts.uk.ctx.at.request.dom.setting.company.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.approvallistsetting.WeekNumberDays;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.ApplicationSetting;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.displaysetting.DisplayAtr;
import nts.uk.ctx.at.request.dom.setting.company.request.appreflect.AppReflectionSetting;
import nts.uk.ctx.at.request.dom.setting.company.request.appreflect.ApplyTimeSchedulePriority;
import nts.uk.ctx.at.request.dom.setting.company.request.appreflect.ClassifyScheAchieveAtr;
import nts.uk.ctx.at.request.dom.setting.company.request.appreflect.PriorityTimeReflectAtr;
import nts.uk.ctx.at.request.dom.setting.company.request.approvallistsetting.AppReflectAfterConfirm;
import nts.uk.ctx.at.request.dom.setting.company.request.approvallistsetting.ApprovalListDisplaySetting;
import nts.uk.ctx.at.request.dom.setting.company.request.approvallistsetting.ReflectAtr;

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
	
	public static RequestSetting createSimpleFromJavaType(String companyID, int scheReflectFlg, int priorityTimeReflectFlag, int attendentTimeReflectFlg, 
			int classScheAchi, int reflecTimeofSche,
			int advanceExcessMessDisAtr,int hwAdvanceDisAtr, int hwActualDisAtr, int actualExcessMessDisAtr,int otAdvanceDisAtr, 
			int otActualDisAtr, int warningDateDisAtr, int appReasonDisAtr,
			int appContentChangeFlg, int scheduleConfirmedAtr, int achievementConfirmedAtr, ApplicationSetting applicationSetting){
		return new RequestSetting(companyID, 
				applicationSetting,
				new AppReflectionSetting(scheReflectFlg == 1 ? true : false, 
											EnumAdaptor.valueOf(priorityTimeReflectFlag, PriorityTimeReflectAtr.class),
											attendentTimeReflectFlg == 1 ? true : false,
											EnumAdaptor.valueOf(classScheAchi, ClassifyScheAchieveAtr.class),
											EnumAdaptor.valueOf(reflecTimeofSche, ApplyTimeSchedulePriority.class)),
				new ApprovalListDisplaySetting(EnumAdaptor.valueOf(advanceExcessMessDisAtr, DisplayAtr.class),
						EnumAdaptor.valueOf(hwAdvanceDisAtr, DisplayAtr.class),
						EnumAdaptor.valueOf(hwActualDisAtr, DisplayAtr.class),
						EnumAdaptor.valueOf(actualExcessMessDisAtr, DisplayAtr.class),
						EnumAdaptor.valueOf(otAdvanceDisAtr, DisplayAtr.class),
						EnumAdaptor.valueOf(otActualDisAtr, DisplayAtr.class),
						new WeekNumberDays(Integer.valueOf(warningDateDisAtr)), 
						EnumAdaptor.valueOf(advanceExcessMessDisAtr, DisplayAtr.class)),
				new AuthorizationSetting(appContentChangeFlg == 1? true : false),
				new AppReflectAfterConfirm(EnumAdaptor.valueOf(scheduleConfirmedAtr, ReflectAtr.class),
						EnumAdaptor.valueOf(achievementConfirmedAtr, ReflectAtr.class))
				);
	}
	
	public static RequestSetting toDomain(String companyID, ApplicationSetting applicationSetting, AppReflectionSetting appReflectionSetting,
		ApprovalListDisplaySetting approvalListDisplaySetting, AuthorizationSetting authorizationSetting, AppReflectAfterConfirm appReflectAfterConfirm){
		return new RequestSetting(companyID, applicationSetting, appReflectionSetting, approvalListDisplaySetting, authorizationSetting, appReflectAfterConfirm);
	}
}
