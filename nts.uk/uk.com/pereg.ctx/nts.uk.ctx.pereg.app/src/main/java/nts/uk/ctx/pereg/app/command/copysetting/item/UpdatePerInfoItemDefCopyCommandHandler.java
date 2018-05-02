package nts.uk.ctx.pereg.app.command.copysetting.item;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pereg.dom.copysetting.setting.EmpCopySettingRepository;
import nts.uk.ctx.pereg.dom.copysetting.setting.EmployeeCopyCategory;
import nts.uk.shr.com.context.AppContexts;

@RequestScoped
public class UpdatePerInfoItemDefCopyCommandHandler extends CommandHandler<UpdatePerInfoItemDefCopy> {

	@Inject
	private EmpCopySettingRepository empCopyRepo;

	@Override
	protected void handle(CommandHandlerContext<UpdatePerInfoItemDefCopy> context) {
		String companyId = AppContexts.user().companyId();
		UpdatePerInfoItemDefCopy command = context.getCommand();
		String categoryId = command.getPerInfoCtgId();
		List<String> itemIdList = command.getPerInfoItemDefLst().stream().filter(x -> x.getChecked())
				.map(PerInfoDefDto::getId).collect(Collectors.toList());

		Optional<EmployeeCopyCategory> copyCategoryOpt = empCopyRepo.findCopyCategory(companyId, categoryId);
		if (copyCategoryOpt.isPresent()) {
			remove(categoryId);
		}
		if (!itemIdList.isEmpty()) {
			EmployeeCopyCategory newCopyCategory = new EmployeeCopyCategory(categoryId, itemIdList);
			add(newCopyCategory);
		}
		
	}
	
	@Transactional
	private void remove(String categoryId) {
		empCopyRepo.removeCopyCategory(categoryId);
	}
	
	@Transactional
	private void add(EmployeeCopyCategory newCopyCategory) {
		empCopyRepo.addCopyCategory(newCopyCategory);
	}

}
