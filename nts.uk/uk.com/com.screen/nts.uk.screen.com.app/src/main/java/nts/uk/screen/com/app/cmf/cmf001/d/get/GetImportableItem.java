package nts.uk.screen.com.app.cmf.cmf001.d.get;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.uk.ctx.exio.dom.input.group.ImportingGroupId;
import nts.uk.ctx.exio.dom.input.importableitem.ImportableItem;

@Stateless
public class GetImportableItem {
	
	@Inject
	private GetImportableItemRequire require;
	
	public List<ImportableItemDto> get(int importingGroupId) {
		val require = this.require.create();
		val importableItems = require.getImportableItems(ImportingGroupId.valueOf(importingGroupId));
		return importableItems.stream().map(d -> ImportableItemDto.fromDomain(d)).collect(Collectors.toList());
	}
	
	public static interface Require {
		List<ImportableItem> getImportableItems(ImportingGroupId groupId);
	}
}
