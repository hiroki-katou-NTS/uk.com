package nts.uk.screen.com.app.cmf.cmf001.c.get;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import lombok.val;
import nts.uk.ctx.exio.dom.input.domain.ImportingDomainId;
import nts.uk.ctx.exio.dom.input.importableitem.ImportableItemsRepository;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class GetImportableItemAndConstraint {
	
	@Inject
	private ImportableItemsRepository itemRepo;

	public ImportableItemDto get(String settingCode, ImportingDomainId domainId, int itemNo) {
		val item = itemRepo.get(domainId, itemNo).get();
		
		return ImportableItemDto.of(item);
		
	}
}
