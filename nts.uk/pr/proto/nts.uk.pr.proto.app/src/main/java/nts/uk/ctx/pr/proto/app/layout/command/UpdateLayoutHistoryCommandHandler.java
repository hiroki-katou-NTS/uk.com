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
import nts.uk.shr.com.context.AppContexts;
/**
 * UpdateLayoutCommandHandler
 * @author lamvt
 *
 */
@RequestScoped
@Transactional
public class UpdateLayoutHistoryCommandHandler extends CommandHandler<UpdateLayoutHistoryCommand> {

	@Inject
	private LayoutMasterRepository layoutRepo;

	
	@Override
	protected void handle(CommandHandlerContext<UpdateLayoutHistoryCommand> handlerContext) {
		  //this.repository.update(handlerContext.getCommand().toDomain());
		UpdateLayoutHistoryCommand command = handlerContext.getCommand();
		String companyCode = AppContexts.user().companyCode();
		
		LayoutMaster layoutOrigin = layoutRepo.getLayout(companyCode, command.getStartYM(), command.getStmtCode())
				.orElseThrow(() -> new BusinessException(new RawErrorMessage("Not found layout")));
		
		LayoutMaster layoutNew = command.toDomain(layoutOrigin.getEndYM().v(), layoutOrigin.getLayoutAtr().value, layoutOrigin.getStmtName().v());
		
		//Validate follow EAP
		if (layoutNew.getStartYM().compareTo(layoutOrigin.getStartYM()) < 0){
			//ER023
			throw new BusinessException(new RawErrorMessage("履歴の期間が重複しています。"));
		//validate follow EAP 12.履歴の編集-登録時チェック処理
		//Tìm xem với layout này thì có tồn tại lịch sử trước đó không?
		
	}
	}
}

