package nts.uk.ctx.at.shared.app.command.worktype.worktypedisporder;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.shared.app.find.worktype.WorkTypeDto;
import nts.uk.ctx.at.shared.app.find.worktype.WorkTypeFinder;
import nts.uk.ctx.at.shared.dom.worktype.worktypedisporder.WorkTypeDispOrderRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class WorkTypeDispInitializeOrderCommandHandler
		extends CommandHandlerWithResult<WorkTypeDispInitializeOrderCommand, List<WorkTypeDto>> {
	@Inject
	private WorkTypeDispOrderRepository workTypeDisporderRepository;

	@Inject
	WorkTypeFinder workTypeFinder;

	@Override
	protected List<WorkTypeDto> handle(CommandHandlerContext<WorkTypeDispInitializeOrderCommand> context) {
		String companyId = AppContexts.user().companyId();
		// ドメインモデル「勤務種類の並び順」を削除する(xóa domain 「勤務種類の並び順」)
		workTypeDisporderRepository.remove(companyId);

		// アルゴリズム「起動時処理」を実行する(thực hiện xử lý 「起動時処理」)
		return workTypeFinder.findByCompanyId();
	}

}
