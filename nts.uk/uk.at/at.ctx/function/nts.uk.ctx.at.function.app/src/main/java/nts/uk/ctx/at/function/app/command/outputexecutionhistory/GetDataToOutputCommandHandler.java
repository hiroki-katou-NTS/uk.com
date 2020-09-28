package nts.uk.ctx.at.function.app.command.outputexecutionhistory;

import nts.arc.layer.app.command.AsyncCommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * The class GetDataToOutPutCommandHandler
 */

@Stateless
public class GetDataToOutputCommandHandler extends AsyncCommandHandler<GetDataToOutputCommand> {

    @Inject
    private GetDataToOutputService getDataToOutputService;

    /**
     * UKDesign.UniversalK.就業.KBT_更新処理自動実行.KBT002_更新処理自動実行.K：実行履歴の出力.アルゴリズム.出力するデータを取得する.出力するデータを取得する
     */
    @Override
    protected void handle(CommandHandlerContext<GetDataToOutputCommand> context) {
        this.getDataToOutputService.start(context);
    }

}
