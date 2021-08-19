package nts.uk.screen.com.app.cmf.cmf001.d.get;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import lombok.RequiredArgsConstructor;
import nts.arc.diagnose.stopwatch.embed.EmbedStopwatch;
import nts.uk.ctx.exio.dom.input.domain.ImportingDomainId;
import nts.uk.ctx.exio.dom.input.importableitem.ImportableItem;
import nts.uk.ctx.exio.dom.input.importableitem.ImportableItemsRepository;
import nts.uk.screen.com.app.cmf.cmf001.d.get.GetImportableItem.Require;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class GetImportableItemRequire {
	
	@Inject
	private ImportableItemsRepository importableItemsRepo;

	public Require create() {
		
		return EmbedStopwatch.embed(new RequireImpl());
	}
	
	@RequiredArgsConstructor
	public class RequireImpl implements Require {
		
		@Override
		public List<ImportableItem> getImportableItems(ImportingDomainId groupId) {
			return importableItemsRepo.get(groupId);
		}
	}
}
