package nts.uk.ctx.exio.app.input.find.setting;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.diagnose.stopwatch.embed.EmbedStopwatch;
import nts.uk.ctx.exio.app.input.find.setting.ExternalImportSettingDto.Require;
import nts.uk.ctx.exio.dom.input.group.ImportingGroupId;
import nts.uk.ctx.exio.dom.input.importableitem.ImportableItem;
import nts.uk.ctx.exio.dom.input.importableitem.ImportableItemsRepository;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ExternalImportSettingRequire {
	
	@Inject
	private ImportableItemsRepository importableItemsRepo;
	
	public Require create() {
		
		return EmbedStopwatch.embed(new RequireImpl());
	}
	
	public class RequireImpl implements Require {
		
		public RequireImpl() {
			
		}
		
		@Override
		public List<ImportableItem> getImportableItems(ImportingGroupId groupId) {
			return importableItemsRepo.get(groupId);
		}
	}
}
