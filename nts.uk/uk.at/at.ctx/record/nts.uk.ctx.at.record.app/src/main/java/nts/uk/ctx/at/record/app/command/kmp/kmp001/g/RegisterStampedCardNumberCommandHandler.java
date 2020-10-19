package nts.uk.ctx.at.record.app.command.kmp.kmp001.g;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCard;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCardRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author chungnt
 *         UKDesign.UniversalK.就業.KDP_打刻.KMP001_IDカードの登録.KMP001_カードNOの登録_追加機能版.G：処理確認ダイアログ.メニュー別OCD.一括作成した打刻カード番号を登録する
 *
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class RegisterStampedCardNumberCommandHandler {

	@Inject
	private StampCardRepository stampCardRepo;

	public void add(RegisterStampedCardNumberInput param) {

		if (!param.sid.isEmpty()) {

			for (int i = 0; i < param.cardGeneration.size(); i++) {
				Optional<StampCard> stampCard = this.stampCardRepo.getStampCardByEmployeeCardNumber(param.sid.get(i),
						param.cardGeneration.get(i).getCardNumber());

				if (stampCard.isPresent()) {
					continue;
				}
				// 1 create
				StampCard card = new StampCard(AppContexts.user().contractCode(),
						param.cardGeneration.get(i).getCardNumber(), param.sid.get(i));

				this.stampCardRepo.add(card);
			}
		}
	}
}
