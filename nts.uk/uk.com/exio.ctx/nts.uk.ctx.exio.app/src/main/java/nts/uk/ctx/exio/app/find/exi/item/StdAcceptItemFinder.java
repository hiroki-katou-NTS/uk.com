package nts.uk.ctx.exio.app.find.exi.item;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.uk.ctx.exio.dom.exi.item.StdAcceptItemRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 受入項目（定型）
 */
@Stateless
public class StdAcceptItemFinder {

	@Inject
	private StdAcceptItemRepository stdAcceptItemRepo;

	public List<StdAcceptItemDto> getStdAcceptItems(String conditionSetCd) {
		String companyId = AppContexts.user().companyId();
		return stdAcceptItemRepo.getListStdAcceptItems(companyId, conditionSetCd).stream()
				.map(item -> StdAcceptItemDto.fromDomain(item)).collect(Collectors.toList());
	}
}
