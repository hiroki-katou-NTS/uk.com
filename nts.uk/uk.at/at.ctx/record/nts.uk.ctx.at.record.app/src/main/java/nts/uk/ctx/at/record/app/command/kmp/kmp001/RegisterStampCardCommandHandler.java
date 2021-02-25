package nts.uk.ctx.at.record.app.command.kmp.kmp001;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.uk.ctx.at.record.app.command.kmp.kmp001.a.CardInformationCommands;
import nts.uk.ctx.at.record.app.command.kmp.kmp001.a.CardNumberNewCommand;
import nts.uk.ctx.at.record.app.command.kmp.kmp001.a.EmployeeCardInformationViewACommand;
//import nts.uk.ctx.at.record.app.command.kmp.kmp001.b.RegisterEmployeeCardCommand;
import nts.uk.ctx.at.record.app.command.kmp.kmp001.c.RegisterStampCardViewCCommand;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCard;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCardRepository;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampNumber;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author chungnt
 *
 */
@Stateless
public class RegisterStampCardCommandHandler {

	@Inject
	private StampCardRepository stampCardRepo;
	
	/**
	 * UKDesign.UniversalK.就業.KDP_打刻.KMP001_IDカードの登録.A：個人指定によるIDカード登録.メニュー別OCD.新規モード時にIDカードNOの登録を行う
	 * @param command
	 */
	public void saveStampCardViewA(CardNumberNewCommand command) {
		String contractCode = AppContexts.user().contractCode();
		
		Optional<StampCard> stampCards = stampCardRepo.getByCardNoAndContractCode(command.getCardNumber(), contractCode);
		
		if (stampCards.isPresent()) {
			throw new BusinessException("Msg_1659");
		} else {
			StampCard card = new StampCard(contractCode, command.getCardNumber(), command.getEmployeeId());
			stampCardRepo.add(card);
		}
	}
	
	/**
	 * UKDesign.UniversalK.就業.KDP_打刻.KMP001_IDカードの登録.A：個人指定によるIDカード登録.メニュー別OCD.通常モード時にIDカードNOの登録を行う
	 * @param command
	 */
	public void updateStampCardViewA(EmployeeCardInformationViewACommand command) {
		Optional<StampCard> stampCard = stampCardRepo.getStampCardByEmployeeCardNumber(command.getEmployeeId(), command.getOlderCardNumber());
		
		if (stampCard.isPresent()) {
			StampCard card = stampCard.get();
			StampCard cardUpdate = new StampCard(
					card.getContractCd(),
					new StampNumber(command.getNewCardNumber()), 
					card.getEmployeeId(), card.getRegisterDate(),
					card.getStampCardId());
			
			stampCardRepo.update(cardUpdate);
		}
	}
	
	
	/**
	 * UKDesign.UniversalK.就業.KDP_打刻.KMP001_IDカードの登録.A：個人指定によるIDカード登録.メニュー別OCD.IDカードNOの削除を行う
	 * @param command
	 */
	public void deleteStampCardViewA(CardInformationCommands command) {
		String contractCode = AppContexts.user().contractCode();

		for (int i = 0; i < command.getCardNumbers().size(); i++) {
			StampCard card = new StampCard(contractCode, command.getCardNumbers().get(i), command.getEmployeeId());
			stampCardRepo.delete(card);
		}
	}

//	public void saveStarpCardViewB(RegisterEmployeeCardCommand command) {
//		String contractCd = AppContexts.user().contractCode();
//		StampCard card = new StampCard(contractCd, command.getCardNumber(), command.getEmployeeId());
//
//		Optional<StampCard> stampCard = stampCardRepo.getStampCardByEmployeeCardNumber(command.getEmployeeId(),
//				command.getCardNumber());
//
//		if (stampCard.isPresent()) {
//			stampCardRepo.update(card);
//		}
//
//		stampCardRepo.add(card);
//	}

	public void saveStampCardViewC(RegisterStampCardViewCCommand command) {
		String contractCd = AppContexts.user().contractCode();
		StampCard card = new StampCard(contractCd, command.getCardNumber(), command.getEmployeeId());

		Optional<StampCard> stampCard = stampCardRepo.getStampCardByContractCdEmployeeCardNumber(contractCd,
				command.getEmployeeId(), command.getCardNumber());

		if (stampCard.isPresent()) {
			stampCardRepo.update(card);
		}

		stampCardRepo.add(card);
	}
	
	
	public void deleteCardInfomaiton (CardInformationCommands command) {
		String contractCd = AppContexts.user().contractCode();
		 
		if (!command.getCardNumbers().isEmpty()) {
			for (int i = 0 ; i < command.getCardNumbers().size(); i++) {
				Optional<StampCard> card = stampCardRepo.getByStampCardId(command.getCardId().get(i));
				if(card.isPresent()) {
					stampCardRepo.delete(card.get());
				}
			}
		}
	}
}
