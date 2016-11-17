package nts.uk.ctx.pr.proto.app.layout.command;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.proto.dom.layout.LayoutMaster;
import nts.uk.ctx.pr.proto.dom.layout.LayoutMasterRepository;
import nts.uk.ctx.pr.proto.dom.layout.category.LayoutMasterCategoryRepository;
import nts.uk.ctx.pr.proto.dom.layout.detail.LayoutMasterDetailRepository;
import nts.uk.ctx.pr.proto.dom.layout.line.LayoutMasterLineRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author lamvt
 *
 */
@RequestScoped
@Transactional
public class CreateLayoutHistoryCommandHandler extends CommandHandler<CreateLayoutHistoryCommand>{

	@Inject
	private LayoutMasterRepository layoutRepo;
	@Inject
	private LayoutMasterCategoryRepository categoryRepo;
	@Inject
	private LayoutMasterLineRepository lineRepo;
	@Inject
	private LayoutMasterDetailRepository detailRepo;
	
	@Override
	protected void handle(CommandHandlerContext<CreateLayoutHistoryCommand> context) {
		CreateLayoutHistoryCommand command = context.getCommand();
		String companyCode = AppContexts.user().companyCode();
		
		LayoutMaster layoutOrigin = layoutRepo.getLayout(companyCode, command.getStmtCode(), command.getStartYm())
				.orElseThrow(() -> new BusinessException(new RawErrorMessage("Not found layout")));
		
		LayoutMaster layoutNew = command.toDomain(layoutOrigin.getLayoutAtr().value, layoutOrigin.getStmtName().v());
		
		//Validate follow EAP
		if (layoutNew.getStartYM().compareTo(layoutOrigin.getStartYM()) < 0){
			//ER011
			throw new BusinessException(new RawErrorMessage("履歴の期間が正しくありません。"));
		}
		
		
		
	}

}
