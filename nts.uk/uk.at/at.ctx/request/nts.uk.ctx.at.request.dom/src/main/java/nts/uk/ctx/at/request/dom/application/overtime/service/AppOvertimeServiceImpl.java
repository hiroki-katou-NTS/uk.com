package nts.uk.ctx.at.request.dom.application.overtime.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.EmploymentRootAtr;
import nts.uk.ctx.at.request.dom.application.UseAtr;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.AtEmployeeAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.EmployeeInfoImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalRootContentImport_New;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.before.BeforePrelaunchAppCommonSet;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.init.CollectApprovalRootPatternService;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.output.AppCommonSettingOutput;
import nts.uk.ctx.at.request.dom.application.overtime.service.output.OvertimeHolidaySetting;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.appovertime.AppOvertimeSetting;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.appovertime.AppOvertimeSettingRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon.OvertimeRestAppCommonSetRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon.OvertimeRestAppCommonSetting;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class AppOvertimeServiceImpl implements AppOvertimeService {

	@Inject
	private AtEmployeeAdapter atEmployeeAdapter;
	
	@Inject
	private BeforePrelaunchAppCommonSet beforePrelaunchAppCommonSet;
	
	@Inject
	private OvertimeRestAppCommonSetRepository overtimeRestAppCommonSetRepository;
	
	@Inject
	private AppOvertimeSettingRepository appOvertimeSettingRepository;
	
	@Inject
	private CollectApprovalRootPatternService collectApprovalRootPatternService;
	
	@Override
	public List<EmployeeInfoImport> createApplicants(List<String> employeeIDs) {
		List<String> employeeLst = new ArrayList<String>();
		// Input．申請者リストをチェック
		if(CollectionUtil.isEmpty(employeeIDs)) {
			// Input．申請者リストにログイン者IDを追加
			employeeLst.add(AppContexts.user().employeeId());
		} else {
			employeeLst.addAll(employeeIDs);
		}
		// 申請者情報を取得
		return atEmployeeAdapter.getByListSID(employeeLst);
	}

	@Override
	public OvertimeHolidaySetting getSettingDatas(String companyID, String employeeID, Integer rootAtr,
			ApplicationType appType, GeneralDate appDate) {
		// 1-1.新規画面起動前申請共通設定を取得する
		AppCommonSettingOutput appCommonSettingOutput = beforePrelaunchAppCommonSet.prelaunchAppCommonSetService(companyID, employeeID, rootAtr, appType, appDate);
		// ドメインモデル「申請承認機能設定」．「申請利用設定」．利用区分をチェックする
		if(appCommonSettingOutput.approvalFunctionSetting.getAppUseSetting().getUserAtr().equals(UseAtr.NOTUSE)) {
			throw new BusinessException("Msg_323");
		}
		// 残業休出申請共通設定を取得
		OvertimeRestAppCommonSetting overtimeRestAppCommonSetting = overtimeRestAppCommonSetRepository.getOvertimeRestAppCommonSetting(companyID, appType.value).get();
		// 残業申請設定を取得する
		AppOvertimeSetting appOvertimeSetting = appOvertimeSettingRepository.getByCid(companyID).get();
		return null;
	}

	@Override
	public ApprovalRootContentImport_New getApprovalRoute(String companyID, String employeeID, Integer rootAtr,
			ApplicationType appType, GeneralDate appDate) {
		// 1-4.新規画面起動時の承認ルート取得パターン
		return collectApprovalRootPatternService.getApprovalRootPatternService(
				companyID, 
				employeeID, 
				EnumAdaptor.valueOf(rootAtr, EmploymentRootAtr.class), 
				appType, 
				appDate, 
				null, 
				true).getApprovalRootContentImport();
	}

}
