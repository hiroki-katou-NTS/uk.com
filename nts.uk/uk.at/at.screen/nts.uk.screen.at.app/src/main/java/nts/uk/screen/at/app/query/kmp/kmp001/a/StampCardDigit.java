package nts.uk.screen.at.app.query.kmp.kmp001.a;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

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
	
	public StampCardDigitDto get() {
		String companyId = AppContexts.user().companyId();
		
		Optional<StampCardEditing> cardEditing = stampCardEditingRepo.get(companyId);
		
		if (!cardEditing.isPresent()) {
			throw new RuntimeException("Not found");
		}
		
		StampCardDigitDto cardDigitNumberDto = new StampCardDigitDto(cardEditing.get().getDigitsNumber().v());
		
		return cardDigitNumberDto;
	}
	
}
