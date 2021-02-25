package nts.uk.ctx.at.shared.app.find.bonuspay;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BusinessException;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.repository.BPTimeItemRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.timeitem.BonusPayTimeItem;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class BPTimeItemFinder {
	@Inject
	private BPTimeItemRepository bpTimeItemRepository;

	public int checkInit() {
		String companyId = AppContexts.user().companyId();
		return this.bpTimeItemRepository.checkInit(companyId);
	}

	public List<BPTimeItemDto> getListBonusPayTimeItem() {
		String companyId = AppContexts.user().companyId();
		List<BonusPayTimeItem> listBonusPayTimeItem = this.bpTimeItemRepository.getListBonusPayTimeItem(companyId);
		return listBonusPayTimeItem.stream().map(c -> toBPTimeItemDto(c)).collect(Collectors.toList());
	}

	public List<BPTimeItemDto> getListBonusPayTimeItemInUse() {
		String companyId = AppContexts.user().companyId();
		List<BonusPayTimeItem> listBonusPayTimeItem = this.bpTimeItemRepository.getListBonusPayTimeItemInUse(companyId);
		return listBonusPayTimeItem.stream().map(c -> toBPTimeItemDto(c)).collect(Collectors.toList());
	}

	public List<BPTimeItemDto> getListSpecialBonusPayTimeItem() {
		String companyId = AppContexts.user().companyId();
		List<BonusPayTimeItem> listBonusPayTimeItem = this.bpTimeItemRepository.getListSpecialBonusPayTimeItem(companyId);
		return listBonusPayTimeItem.stream().map(c -> toBPTimeItemDto(c)).collect(Collectors.toList());
	}

	public List<BPTimeItemDto> getListSpecialBonusPayTimeItemInUse() {
		String companyId = AppContexts.user().companyId();
		List<BonusPayTimeItem> listBonusPayTimeItem = this.bpTimeItemRepository.getListSpecialBonusPayTimeItemInUse(companyId);
		return listBonusPayTimeItem.stream().map(c -> toBPTimeItemDto(c)).collect(Collectors.toList());
	}

	public BPTimeItemDto getBonusPayTimeItem(BigDecimal timeItemNo) {
		String companyId = AppContexts.user().companyId();
		
		Optional<BonusPayTimeItem> bonusPayTimeItem = this.bpTimeItemRepository.getBonusPayTimeItem(companyId, timeItemNo);
		if(bonusPayTimeItem.isPresent()){
			return this.toBPTimeItemDto(
					bonusPayTimeItem.get());
		}
		return null;
	}
	
	public void checkUseArt(List<Boolean> lstuseArt){
		boolean checkUseExist = false;
		
		for (Boolean useArt : lstuseArt) {
			if(useArt){
				checkUseExist=useArt;
			}
		}
		if(!checkUseExist){
			throw new BusinessException("Msg_131");
		}
		
	}
	
	
	

	public BPTimeItemDto getSpecialBonusPayTimeItem(BigDecimal timeItemNo) {
		String companyId = AppContexts.user().companyId();
		Optional<BonusPayTimeItem> bonusPayTimeItem = this.bpTimeItemRepository.getSpecialBonusPayTimeItem(companyId, timeItemNo);
		if(bonusPayTimeItem.isPresent()){
			return  this.toBPTimeItemDto(
					bonusPayTimeItem.get());
		}
		return null;
		
	}

	private BPTimeItemDto toBPTimeItemDto(BonusPayTimeItem bonusPayTimeItem) {
		return new BPTimeItemDto(bonusPayTimeItem.getCompanyId().toString(),
				bonusPayTimeItem.getUseAtr().value,
				bonusPayTimeItem.getTimeItemName().toString(), bonusPayTimeItem.getId(),
				bonusPayTimeItem.getTimeItemTypeAtr().value);
	}
}
