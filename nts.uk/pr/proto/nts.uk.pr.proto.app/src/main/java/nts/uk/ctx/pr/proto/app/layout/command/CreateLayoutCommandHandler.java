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
import nts.uk.ctx.pr.proto.dom.layout.line.LayoutMasterLine;
import nts.uk.shr.com.context.AppContexts;

/**
 * CreateLayoutCommandHandler
 * @author lamvt
 *
 */
@RequestScoped
@Transactional
public class CreateLayoutCommandHandler extends CommandHandler<CreateLayoutCommand>{

	@Inject
	private LayoutMasterRepository layoutRepo;
	@Inject
	private LayoutMasterCategoryRepository categoryRepo;
	@Inject
	private LayoutMasterLine lineRepo;
	@Inject
	private LayoutMasterDetailRepository detailRepo;
	
	
	@Override
	protected void handle(CommandHandlerContext<CreateLayoutCommand> context) {
		
		CreateLayoutCommand command = context.getCommand();
		String companyCode = AppContexts.user().companyCode();
		
		if (layoutRepo.isExist(companyCode, command.getStmtCode())) {
			throw new BusinessException(new RawErrorMessage("入力した明細書コードは既に存在しています。\r\n明細書コードを確認してください。"));
		}
		
		LayoutMaster layout = command.toDomain();
		layout.validate();
		this.layoutRepo.add(layout);
		
		if (command.isCopy()) {
			LayoutMaster layoutCopied = layoutRepo.getLayout(companyCode, command.getStmtCodeCopied(), command.getStartYmCopied())
					.orElseThrow(() -> new BusinessException(new RawErrorMessage("Not found layout")));
			
			
		} 
	}
}
