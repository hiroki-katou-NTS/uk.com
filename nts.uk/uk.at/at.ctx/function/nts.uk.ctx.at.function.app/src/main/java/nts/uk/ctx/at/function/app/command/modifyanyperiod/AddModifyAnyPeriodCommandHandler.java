package nts.uk.ctx.at.function.app.command.modifyanyperiod;


import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.repository.BusinessTypeSFormatDailyRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class AddModifyAnyPeriodCommandHandler extends CommandHandler<AddModifyAnyPeriodCommand> {

    @Inject
    private BusinessTypeSFormatDailyRepository businessTypeFormatMDailyRepository;
    @Override
    protected void handle(CommandHandlerContext<AddModifyAnyPeriodCommand> commandHandlerContext) {
        AddModifyAnyPeriodCommand command = commandHandlerContext.getCommand();
        String cid = AppContexts.user().companyId();
        String code = command.getCode();
        //アルゴリズム「重複チェック」を実行する   (thực hiện thuật toán check duplicate)
        boolean exit = businessTypeFormatMDailyRepository.checkExistData(cid,code);
        if(exit){
            throw  new BusinessException("Msg_3");
        }
        //ドメインモデル「任意期間修正のフォーマット設定」を登録する Delete domain: 任意期間修正のフォーマット設定

        //画面項目「A9_1：このフォーマットを初期設定にする」の状態をチェックする


    }
}
