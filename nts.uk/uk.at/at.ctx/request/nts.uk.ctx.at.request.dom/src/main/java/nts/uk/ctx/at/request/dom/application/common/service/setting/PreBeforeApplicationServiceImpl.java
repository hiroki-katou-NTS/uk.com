package nts.uk.ctx.at.request.dom.application.common.service.setting;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSetting;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSettingRepository;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSetting;
@Stateless
public class PreBeforeApplicationServiceImpl implements PreBeforeApplicationService{

	@Inject
	AppEmploymentSettingRepository employmentSetting;
//	@Override
//	public void copyEmploymentSetting(String companyId, List<AppEmploymentSetting> sourceData, List<String> targetEmploymentCodes, boolean isOveride) {		
//		for (String target : targetEmploymentCodes) {
//			// 上書き確認処理
//			if (isOveride) {
//				// 複写先の前準備設定を削除する
//				employmentSetting.remove(companyId, target);
//			} else {
//				//複写先に前準備設定が存在するかどうかチェック
//				 List<AppEmploymentSetting> testData = employmentSetting.getEmploymentSetting(companyId, target);
//				 if(!CollectionUtil.isEmpty(testData)){
//					 //エラーメッセージ（Msg_888）を表示する
//					 throw new BusinessException("Msg_888");
//				 }					 
//			}
//			// 複写先の前準備設定を追加する (Add)
//			addEmploymentSet(sourceData, target);
//		}
//	}
	public void copyEmploymentSettingNew(String companyId,List<AppEmploymentSetting> sourceData, List<String> targetEmploymentCodes, boolean isOveride) {		
		for (String target : targetEmploymentCodes) {
			// 上書き確認処理
			if (isOveride) {
				// 複写先の前準備設定を削除する
				//employmentSetting.remove(companyId, target);
			} else {
				//複写先に前準備設定が存在するかどうかチェック
				List<AppEmploymentSetting> testData = employmentSetting.getEmploymentSetting(companyId, target);
				if(!CollectionUtil.isEmpty(testData)){
						 //エラーメッセージ（Msg_888）を表示する
						 throw new BusinessException("Msg_888");
				}
		
				 				 
			}
			// 複写先の前準備設定を追加する (Add)
			addEmploymentSetNew(sourceData, target, isOveride);
		}
	}
	/**
	 * 複写先の前準備設定を追加する (Add)
	 * @param sourceData: source data copy
	 * @param employmentCode: target employment code
	 */
//	private void addEmploymentSet(List<AppEmploymentSetting> sourceData, String employmentCode){
//		List<AppEmploymentSetting> copyData = sourceData.stream()
//				.map(item ->{ item.setEmploymentCode(employmentCode);
//					item.getLstWorkType().stream().map(workType -> {
//						workType.setEmploymentCode(employmentCode);
//						return workType;
//					}).collect(Collectors.toList());
//					return item;})
//				.collect(Collectors.toList());
//		employmentSetting.insert(copyData);
//	}
	private void addEmploymentSetNew(List<AppEmploymentSetting> sourceData, String employmentCode, boolean isOverride){
		sourceData.get(0).setEmploymentCode(employmentCode);
		AppEmploymentSetting copyData = sourceData.get(0);
		if(isOverride) {
			employmentSetting.update(copyData);

		}else {
			employmentSetting.insert(copyData);
			
		}
	}
}
