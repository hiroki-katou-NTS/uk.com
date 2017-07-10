package nts.uk.ctx.at.shared.app.find.bonuspay;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BusinessException;
import nts.uk.ctx.at.shared.dom.bonuspay.primitives.TimeItemId;
import nts.uk.ctx.at.shared.dom.bonuspay.repository.BPTimeItemRepository;
import nts.uk.ctx.at.shared.dom.bonuspay.timeitem.BonusPayTimeItem;
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

	public List<BPTimeItemDto> getListSpecialBonusPayTimeItem() {
		String companyId = AppContexts.user().companyId();
		List<BonusPayTimeItem> listBonusPayTimeItem = this.bpTimeItemRepository.getListSpecialBonusPayTimeItem(companyId);
		return listBonusPayTimeItem.stream().map(c -> toBPTimeItemDto(c)).collect(Collectors.toList());
	}

	public BPTimeItemDto getBonusPayTimeItem(String timeItemId) {
		String companyId = AppContexts.user().companyId();
		
		Optional<BonusPayTimeItem> bonusPayTimeItem = this.bpTimeItemRepository.getBonusPayTimeItem(companyId, new TimeItemId(timeItemId));
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
	
	
	

	public BPTimeItemDto getSpecialBonusPayTimeItem(String timeItemId) {
		String companyId = AppContexts.user().companyId();
		Optional<BonusPayTimeItem> bonusPayTimeItem = this.bpTimeItemRepository.getSpecialBonusPayTimeItem(companyId, new TimeItemId(timeItemId));
		if(bonusPayTimeItem.isPresent()){
			return  this.toBPTimeItemDto(
					bonusPayTimeItem.get());
		}
		return null;
		
	}

	private BPTimeItemDto toBPTimeItemDto(BonusPayTimeItem bonusPayTimeItem) {
		return new BPTimeItemDto(bonusPayTimeItem.getCompanyId().toString(),
				bonusPayTimeItem.getTimeItemId().toString(), bonusPayTimeItem.getUseAtr().value,
				bonusPayTimeItem.getTimeItemName().toString(), bonusPayTimeItem.getId(),
				bonusPayTimeItem.getTimeItemTypeAtr().value);
	}
}
