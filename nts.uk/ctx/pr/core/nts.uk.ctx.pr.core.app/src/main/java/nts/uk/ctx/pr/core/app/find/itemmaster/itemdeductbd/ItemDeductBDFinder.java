package nts.uk.ctx.pr.core.app.find.itemmaster.itemdeductbd;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.core.app.find.itemmaster.dto.itemdeductbd.ItemDeductBDDto;
import nts.uk.ctx.pr.core.dom.itemmaster.itemdeductbd.ItemDeductBDRepository;

@Stateless
public class ItemDeductBDFinder {

	@Inject
	private ItemDeductBDRepository itemDeductBDRepo;

	public List<ItemDeductBDDto> findAll(String itemCode) {
		return this.itemDeductBDRepo.findAll(itemCode).stream()
				.map(item -> ItemDeductBDDto.fromDomain(item)).collect(Collectors.toList());

	}

}
