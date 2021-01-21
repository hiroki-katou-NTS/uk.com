package nts.uk.ctx.at.request.dom.application.common.service.setting;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSet;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSetRepository;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSetting;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSettingRepository;

@Stateless
public class PreBeforeApplicationServiceImpl implements PreBeforeApplicationService{

	@Inject
	AppEmploymentSettingRepository employmentSetting;

	@Inject
	private AppEmploymentSetRepository appEmploymentSetRepo;

	public void copyEmploymentSettingNew(String companyId,Optional<AppEmploymentSetting> sourceData, List<String> targetEmploymentCodes, boolean isOveride) {		
		for (String target : targetEmploymentCodes) {
			// 上書き確認処理
			if (isOveride) {
				// 複写先の前準備設定を削除する
				//employmentSetting.remove(companyId, target);
			} else {
				//複写先に前準備設定が存在するかどうかチェック
				Optional<AppEmploymentSetting> testData = employmentSetting.getEmploymentSetting(companyId, target);
				if(testData.isPresent()){
						 //エラーメッセージ（Msg_888）を表示する
						 throw new BusinessException("Msg_888");
				}
		
				 				 
			}
			// 複写先の前準備設定を追加する (Add)
			addEmploymentSetNew(sourceData, target, isOveride);
		}
	}

	@Override
	public void copyAppEmploymentSet_New(String companyId, Optional<AppEmploymentSet> sourceData1, List<String> targetEmploymentCodes) {
		if (sourceData1.isPresent()) {
			for (String target : targetEmploymentCodes) {
				if (appEmploymentSetRepo.findByCompanyIDAndEmploymentCD(companyId, target).isPresent()) {
					appEmploymentSetRepo.delete(companyId, target);
				}
				AppEmploymentSet domain = sourceData1.get();
				domain.setEmploymentCD(target);
				appEmploymentSetRepo.insert(domain);
			}
		}
	}

	/**
	 * 複写先の前準備設定を追加する (Add)
	 * @param sourceData: source data copy
	 * @param employmentCode: target employment code
	 */

	private void addEmploymentSetNew(Optional<AppEmploymentSetting> sourceData, String employmentCode, boolean isOverride){
		if(sourceData.isPresent()) {
			sourceData.get().setEmploymentCode(employmentCode);
			AppEmploymentSetting copyData = sourceData.get();
			if(isOverride) {
				employmentSetting.update(copyData);

			}else {
				employmentSetting.insert(copyData);
				
			}
		}
		
	}
}
