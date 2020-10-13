package nts.uk.screen.at.app.command.kbt.outputexecutionhistory;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.gul.text.IdentifierUtil;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * The class GetDataToOutPutCommandHandler
 */

@Stateless
public class GetDataToOutputCommandHandler extends CommandHandlerWithResult<GetDataToOutputCommand, String> {

    @Inject
    private GetDataToOutputService getDataToOutputService;

    /**
     * UKDesign.UniversalK.就業.KBT_更新処理自動実行.KBT002_更新処理自動実行.K：実行履歴の出力.アルゴリズム.出力するデータを取得する.出力するデータを取得する
     */
    @Override
    protected String handle(CommandHandlerContext<GetDataToOutputCommand> context) {
        String delId = IdentifierUtil.randomUniqueId();
        GetDataToOutputCommand command = context.getCommand();
        this.getDataToOutputService.start(command);

        return delId;
    }

}
