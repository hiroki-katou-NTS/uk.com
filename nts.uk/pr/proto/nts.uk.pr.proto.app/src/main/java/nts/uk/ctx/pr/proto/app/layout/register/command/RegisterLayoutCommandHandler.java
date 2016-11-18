package nts.uk.ctx.pr.proto.app.layout.register.command;

import java.util.List;

import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.proto.dom.layout.LayoutMaster;
import nts.uk.ctx.pr.proto.dom.layout.LayoutMasterRepository;
import nts.uk.ctx.pr.proto.dom.layout.category.LayoutMasterCategoryRepository;
import nts.uk.ctx.pr.proto.dom.layout.detail.LayoutMasterDetail;
import nts.uk.ctx.pr.proto.dom.layout.detail.LayoutMasterDetailRepository;
import nts.uk.ctx.pr.proto.dom.layout.line.LayoutMasterLine;
import nts.uk.ctx.pr.proto.dom.layout.line.LayoutMasterLineRepository;
import nts.uk.shr.com.context.AppContexts;

public class RegisterLayoutCommandHandler extends CommandHandler<RegisterLayoutCommand>{

	@Inject
	private LayoutMasterRepository layoutRepo;
	@Inject
	private LayoutMasterCategoryRepository categoryRepo;
	@Inject
	private LayoutMasterLineRepository lineRepo;
	@Inject
	private LayoutMasterDetailRepository detailRepo;
	
	@Override
	protected void handle(CommandHandlerContext<RegisterLayoutCommand> context) {
		RegisterLayoutCommand command = context.getCommand();
		LayoutCommand layoutCommand = context.getCommand().getLayoutCommand();
		String companyCode = AppContexts.user().companyCode();
		
		//Validate by EAP: 02.明細レイアウトの作成-登録時チェック処理
		LayoutMaster layoutOrg = layoutRepo.getLayout(companyCode, layoutCommand.getStartYm(), layoutCommand.getStmtCode())
				.orElseThrow(() -> new BusinessException(new RawErrorMessage("更新対象のデータが存在しません。")));
		
		LayoutMaster layoutRegister = layoutCommand.toDomain(layoutOrg.getLayoutAtr().value);
		layoutRepo.update(layoutRegister);
		
		//Follow EAP: 03.明細レイアウトの作成-登録処理
		//Follow EAP: 03.1.明細書マスタカテゴリ登録処理
		if (command.getListCategoryAtrDeleted().size() > 0) {
			for(Integer categoryAtr : command.getListCategoryAtrDeleted()){
				categoryRepo.remove(companyCode, layoutCommand.getStmtCode(), layoutCommand.getStartYm(), categoryAtr);
				List<LayoutMasterLine> linesDelete = lineRepo.getLines(companyCode, layoutCommand.getStmtCode(), layoutCommand.getStartYm(), categoryAtr);
				lineRepo.remove(linesDelete);
				
				List<LayoutMasterDetail> detailsDelete = detailRepo.getDetailsByCategory(
						companyCode, layoutCommand.getStmtCode(), layoutCommand.getStartYm(), categoryAtr);
				
			}
		}
	}

}
