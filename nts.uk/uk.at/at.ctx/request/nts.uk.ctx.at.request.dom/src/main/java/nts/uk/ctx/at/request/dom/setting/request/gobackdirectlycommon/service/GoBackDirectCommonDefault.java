package nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.service;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.application.common.ApplicationType;
import nts.uk.ctx.at.request.dom.setting.applicationformreason.ApplicationFormReason;
import nts.uk.ctx.at.request.dom.setting.applicationformreason.ApplicationFormReasonRepository;
import nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.GoBackDirectlyCommonSetting;
import nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.GoBackDirectlyCommonSettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 直行直帰基本データ
 * 
 * @author ducpm
 *
 */
@Stateless
public class GoBackDirectCommonDefault implements GoBackDirectCommonService {

	@Inject
	GoBackDirectlyCommonSettingRepository goBackRepo;

	@Inject
	ApplicationFormReasonRepository appFormRepo;

	/**
	 * 直行直帰基本データ
	 */
	@Override
	public GoBackDirectBasicData getSettingData(String SID) {
		String companyID = AppContexts.user().companyId();
		GoBackDirectBasicData dataSetting = new GoBackDirectBasicData();
		// ドメインモデル「直行直帰申請共通設定」より取得する
		Optional<GoBackDirectlyCommonSetting> goBackCommonSet = goBackRepo.findByCompanyID(companyID);
		// dataSetting.goBackDirectSet = goBackCommonSet;
		dataSetting.setGoBackDirectSet(goBackCommonSet);
		// アルゴリズム「社員IDから社員を取得する」を実行する
		// lay dc cai ten thang Nhan vien
		String employeeName = AppContexts.user().personId();
		//TEST
		dataSetting.setEmployeeName("90000000-0000-0000-0000-000000000001");
		// ドメインモデル「申請定型理由」を取得
		List<ApplicationFormReason> listReason = appFormRepo.getReasonByAppType(companyID,
				ApplicationType.GO_RETURN_DIRECTLY_APPLICATION.value);
		dataSetting.setListAppReason(listReason);
		return dataSetting;
	}

}
