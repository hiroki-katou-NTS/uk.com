package nts.uk.ctx.pereg.app.command.copysetting.item;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.pereg.app.find.copysetting.item.CopySettingItemDto;
import nts.uk.ctx.pereg.app.find.copysetting.item.CopySettingItemFinder;
import nts.uk.ctx.pereg.dom.copysetting.item.EmpCopySettingItemRepository;
import nts.uk.ctx.pereg.dom.copysetting.setting.EmpCopySetting;
import nts.uk.ctx.pereg.dom.copysetting.setting.EmpCopySettingRepository;
import nts.uk.shr.com.context.AppContexts;

@RequestScoped
public class UpdatePerInfoItemDefCopyCommandHandler extends CommandHandler<UpdatePerInfoItemDefCopy> {

	@Inject
	private EmpCopySettingItemRepository empCopyItemRepo;

	@Inject
	private EmpCopySettingRepository empCopyRepo;

	@Inject
	private CopySettingItemFinder itemFinder;

	String ctgId;

	@Override
	protected void handle(CommandHandlerContext<UpdatePerInfoItemDefCopy> context) {
		UpdatePerInfoItemDefCopy command = context.getCommand();

		ctgId = command.getPerInfoCtgId();

		List<CopySettingItemDto> items = this.itemFinder.getPerInfoDefById(ctgId).stream()
				.filter(x -> x.isAlreadyItemDefCopy()).collect(Collectors.toList());
		removeObject(items);

		addObject(command.getPerInfoItemDefLst());

	}

	@Transactional
	private void removeObject(List<CopySettingItemDto> items) {

		this.empCopyRepo.removeCtgCopySetting(ctgId);
		items.forEach(x -> {

			this.empCopyItemRepo.removePerInfoItemInCopySetting(x.getId());

		});
		// delete object

	}

	@Transactional
	private void addObject(List<PerInfoDefDto> items) {
		// add objects

		// add item child for list
		List<String> itemCds = getItemDefCdList(items);
		new ArrayList<String>();

		// get itemIds from itemDefCdlst
		List<String> itemIds = new ArrayList<String>();

		items.forEach(x -> {
			if (itemCds.indexOf(x.getItemCd()) != -1) {
				itemIds.add(x.getId());
			}
		});
		if (!CollectionUtil.isEmpty(itemIds)) {
			EmpCopySetting newCtg = EmpCopySetting.createFromJavaType(ctgId, AppContexts.user().companyId());
			this.empCopyRepo.addCtgCopySetting(newCtg);
			this.empCopyItemRepo.updatePerInfoItemInCopySetting(ctgId, itemIds);
		}

	}

	private List<String> getItemDefCdList(List<PerInfoDefDto> items) {

		// add parent
		List<String> itemCds = new ArrayList<String>();
		items.forEach(x -> {

			if (x.getChecked()) {
				if (StringUtils.isEmpty(x.getItemParentCd())) {
					itemCds.add(x.getItemCd());
				}
			}
		});
		// add child
		addChildItems(items, itemCds);

		// add grandchild
		addChildItems(items, itemCds);

		return itemCds;
	}

	private void addChildItems(List<PerInfoDefDto> items, List<String> itemCds) {
		items.stream().filter(x -> {
			return !StringUtils.isEmpty(x.getItemParentCd());
		}).collect(Collectors.toList()).forEach(item -> {
			if (itemCds.contains(item.getItemParentCd())) {
				itemCds.add(item.getItemCd());
			}
		});

	}

}
