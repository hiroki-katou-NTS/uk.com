package nts.uk.screen.com.app.smm.smm001.screenquery;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.uk.ctx.exio.dom.input.setting.ExternalImportSettingRepository;
import nts.uk.ctx.link.smile.dom.smilelinked.cooperationacceptance.SmileCooperationAcceptanceSetting;
import nts.uk.ctx.link.smile.dom.smilelinked.cooperationacceptance.SmileCooperationAcceptanceSettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * Smile初期起動の情報取得する
 *
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class GetInitialStartupInformationScreenQuery {

	// Smile連携受入設定
	@Inject
	private SmileCooperationAcceptanceSettingRepository smileCooperationAcceptanceSettingRepository;
	
	@Inject
	private ExternalImportSettingRepository externalImportSettingRepository;

	public OutputOfStartupDto get() {
		// 会社IDを指定してSM連携受入設定を取得する
		String contractCode = AppContexts.user().contractCode();
		String companyId = AppContexts.user().companyId();
		List<SmileCooperationAcceptanceSetting> acceptanceSettings = smileCooperationAcceptanceSettingRepository
				.get(contractCode, companyId);
		List<SmileCooperationAcceptanceSettingDto> smileCooperationAcceptanceSettingDtos = acceptanceSettings.stream()
				.map(item -> new SmileCooperationAcceptanceSettingDto(item.getCooperationAcceptance().value,
						item.getCooperationAcceptanceClassification().value,
						item.getCooperationAcceptanceConditions().isPresent()
								? item.getCooperationAcceptanceConditions().get().v()
								: null))
				.collect(Collectors.toList());
		
		// システム区分から受入条件設定（定型）を取得
		List<ExternalImportSettingDto> externalImportSettings = this.externalImportSettingRepository
				.getDomainBase(companyId).stream().map(e -> new ExternalImportSettingDto(companyId, e.getCode().v(), e.getName().v()))
				.collect(Collectors.toList());
		
		// return SM linkage acceptance setting list, acceptance condition setting list
		return new OutputOfStartupDto(smileCooperationAcceptanceSettingDtos, externalImportSettings);
	}
}
