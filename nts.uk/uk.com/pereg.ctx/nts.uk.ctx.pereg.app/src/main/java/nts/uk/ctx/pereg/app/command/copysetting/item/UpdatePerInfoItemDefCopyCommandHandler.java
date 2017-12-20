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
	UpdatePerInfoItemDefCopy command;
	List<CopySettingItemDto> itemList;

	@Override
	protected void handle(CommandHandlerContext<UpdatePerInfoItemDefCopy> context) {
		command = context.getCommand();

		ctgId = command.getPerInfoCtgId();

		itemList = this.itemFinder.getPerInfoDefById(ctgId).stream().filter(x -> x.isAlreadyItemDefCopy())
				.collect(Collectors.toList());
		removeObject();

		addObject();

	}

	@Transactional
	private void removeObject() {

		this.empCopyRepo.removeCtgCopySetting(command.getPerInfoCtgId());
		itemList.forEach(x -> {

			empCopyItemRepo.removePerInfoItemInCopySetting(x.getId());

		});
		// delete object

	}

	@Transactional
	private void addObject() {
		// add objects
		if (!CollectionUtil.isEmpty(command.getPerInfoItemDefLst())) {
			EmpCopySetting newCtg = EmpCopySetting.createFromJavaType(ctgId, AppContexts.user().companyId());
			this.empCopyRepo.addCtgCopySetting(newCtg);
			List<String> itemDefIds = new ArrayList<String>();

			command.getPerInfoItemDefLst().forEach(x -> {

				if (x.getChecked()) {
					if (StringUtils.isEmpty(x.getItemParentCd())) {
						itemDefIds.add(x.getId());
					}
				}
			});
			
			command.getPerInfoItemDefLst().stream().filter(x -> {
				return !StringUtils.isEmpty(x.getItemParentCd());
			}).collect(Collectors.toList()).forEach(item -> {
				if (itemDefIds.contains(item.getItemParentCd())) {
					itemDefIds.add(item.getId());
				}
			});

			empCopyItemRepo.updatePerInfoItemInCopySetting(ctgId, itemDefIds);
		}

	}

}
