package nts.uk.screen.at.app.query.kmp.kmp001.a;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.stamp.application.SettingsUsingEmbossing;
import nts.uk.ctx.at.record.dom.stamp.application.SettingsUsingEmbossingRepository;
import nts.uk.ctx.at.record.dom.stamp.card.stamcardedit.StampCardEditing;
import nts.uk.ctx.at.record.dom.stamp.card.stamcardedit.StampCardEditingRepo;
import nts.uk.shr.com.context.AppContexts;

/**
 * UKDesign.UniversalK.就業.KDP_打刻.KMP001_IDカードの登録.A：個人指定によるIDカード登録.メニュー別OCD.抽出した社員のカード番号設定状況を取得する
 * @author chungnt
 *
 */
@Stateless
public class StampCardDigit {

	@Inject
	private StampCardEditingRepo stampCardEditingRepo;
	
	@Inject
	private SettingsUsingEmbossingRepository settingsUsingEmbossingRepo;
	
	public StampCardDigitDto get() {
		String companyId = AppContexts.user().companyId();
		
		StampCardEditing cardEditing = stampCardEditingRepo.get(companyId);
		
		Optional<SettingsUsingEmbossing> usingEmbossing = settingsUsingEmbossingRepo.get(companyId);
		
		StampCardDigitDto cardDigitNumberDto = new StampCardDigitDto();
		
		cardDigitNumberDto.setStampCardDigitNumber(cardEditing.getDigitsNumber().v());
		cardDigitNumberDto.setStampCardEditMethod(cardEditing.getStampMethod().value);
		cardDigitNumberDto.setIc_card(usingEmbossing.map(m -> m.isIc_card()).orElse(false));
		
//		StampCardDigitDto cardDigitNumberDto = new StampCardDigitDto(cardEditing.getDigitsNumber().v(), cardEditing.getStampMethod().value);
		
		return cardDigitNumberDto;
	}
}