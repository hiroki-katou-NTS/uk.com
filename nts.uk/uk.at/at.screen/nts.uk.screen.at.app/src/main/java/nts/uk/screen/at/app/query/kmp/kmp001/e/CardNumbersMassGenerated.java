package nts.uk.screen.at.app.query.kmp.kmp001.e;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.stamp.card.stamcardedit.GenerateStampCardForEmployees;
import nts.uk.ctx.at.record.dom.stamp.card.stamcardedit.ImprintedCardGenerationResult;
import nts.uk.ctx.at.record.dom.stamp.card.stamcardedit.MakeEmbossedCard;
import nts.uk.ctx.at.record.dom.stamp.card.stamcardedit.StampCardEditing;
import nts.uk.ctx.at.record.dom.stamp.card.stamcardedit.StampCardEditingRepo;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCard;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCardRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * UKDesign.UniversalK.就業.KDP_打刻.KMP001_IDカードの登録.KMP001_カードNOの登録_追加機能版.E：カードNO一括作成.メニュー別OCD.選択社員の打刻カード番号一括作成を実行する
 * @author chungnt
 *
 */

@Stateless
public class CardNumbersMassGenerated {
	
	@Inject
	private StampCardEditingRepo stampCardEditingRepo;
	
	@Inject
	private StampCardRepository stampCardRepo;
	
	public List<CardNumbersMassGeneratedDto> get(CardNumbersMassGeneratedInput input){
		
		String contractCd = AppContexts.user().contractCode();
		String companyCd = AppContexts.user().companyCode();
		String companyId = AppContexts.user().companyId();
		String sid = AppContexts.user().employeeId();
		
		MakeEmbossedCard makeEmbossedCard = EnumAdaptor.valueOf(input.getMakeEmbossedCard(), MakeEmbossedCard.class);
		
		List<CardNumbersMassGeneratedDto> result = new ArrayList<>();
		CardNumbersMassGeneratedRequireImpl require = new CardNumbersMassGeneratedRequireImpl(stampCardEditingRepo, stampCardRepo);
		
		List<ImprintedCardGenerationResult> cardGenerationResults = GenerateStampCardForEmployees.generate(require,
				contractCd,
				companyCd,
				makeEmbossedCard,
				input.getTargetPerson(),
				companyId,
				sid);
		
		if (cardGenerationResults.isEmpty()) {
			return result;
		}
		
		result = cardGenerationResults.stream().map(m -> {
			CardNumbersMassGeneratedDto dto = new CardNumbersMassGeneratedDto();
			
			dto.setEmployeeCd(m.getEmployeeCd());
			dto.setCardNumber(m.getCardNumber().getStampNumber().v());
			dto.setDuplicateCard(m.getDuplicateCards().map(c -> c.getStampNumber().v()).orElse(""));
			
			return dto;
		}).collect(Collectors.toList());
		
		return result;
	}
	
	
	@AllArgsConstructor
	private class CardNumbersMassGeneratedRequireImpl implements GenerateStampCardForEmployees.Require {
		
		private StampCardEditingRepo stampCardEditingRepo;
		
		private StampCardRepository stampCardRepo;
		
		@Override
		public StampCardEditing get(String companyId) {
			return stampCardEditingRepo.get(companyId);
		}

		@Override
		public Optional<StampCard> getByCardNoAndContractCode(String cardNo, String contractCd) {
			return stampCardRepo.getByCardNoAndContractCode(cardNo, contractCd);
		}
	}
}
