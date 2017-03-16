package nts.uk.ctx.pr.core.app.find.itemmaster.itemsalarybd;

import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import nts.uk.ctx.pr.core.app.find.itemmaster.dto.itemsalaryDBDto.ItemSalaryBDDto;
import nts.uk.ctx.pr.core.dom.itemmaster.itemsalarybd.ItemSalaryBDRepository;
import nts.uk.shr.com.context.AppContexts;

@RequestScoped
public class ItemSalaryBDFinder {

	@Inject
	private ItemSalaryBDRepository itemSalaryBDRepo;

	public List<ItemSalaryBDDto> findAll(String itemCode) {
		return this.itemSalaryBDRepo.findAll(AppContexts.user().companyCode(), itemCode).stream()
				.map(item -> ItemSalaryBDDto.fromDomain(item)).collect(Collectors.toList());

	}

}
