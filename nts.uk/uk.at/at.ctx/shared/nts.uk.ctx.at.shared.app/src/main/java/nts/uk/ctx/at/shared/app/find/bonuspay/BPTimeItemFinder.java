package nts.uk.ctx.at.shared.app.find.bonuspay;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.uk.ctx.at.shared.dom.bonuspay.repository.BPTimeItemRepository;
import nts.uk.ctx.at.shared.dom.bonuspay.timeitem.BonusPayTimeItem;

@Stateless
@Transactional
public class BPTimeItemFinder {
	@Inject
	private BPTimeItemRepository bpTimeItemRepository;

	public List<BPTimeItemDto> getListBonusPayTimeItem() {
		List<BonusPayTimeItem> listBonusPayTimeItem = bpTimeItemRepository.getListBonusPayTimeItem();
		return listBonusPayTimeItem.stream().map(c -> toBPTimeItemDto(c)).collect(Collectors.toList());
	}

	public List<BPTimeItemDto> getListSpecialBonusPayTimeItem() {
		List<BonusPayTimeItem> listBonusPayTimeItem = bpTimeItemRepository.getListSpecialBonusPayTimeItem();
		return listBonusPayTimeItem.stream().map(c -> toBPTimeItemDto(c)).collect(Collectors.toList());
	}

	private BPTimeItemDto toBPTimeItemDto(BonusPayTimeItem bonusPayTimeItem) {
		return new BPTimeItemDto(bonusPayTimeItem.getCompanyId().toString(),
				bonusPayTimeItem.getTimeItemId().toString(), bonusPayTimeItem.getUseAtr().value,
				bonusPayTimeItem.getTimeItemName().toString(), bonusPayTimeItem.getId(),
				bonusPayTimeItem.getTimeItemTypeAtr().value);
	}
}
