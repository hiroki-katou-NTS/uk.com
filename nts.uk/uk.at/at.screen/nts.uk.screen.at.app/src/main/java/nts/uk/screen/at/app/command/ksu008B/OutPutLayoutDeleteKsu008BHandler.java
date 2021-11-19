/**
 *
 */
package nts.uk.screen.at.app.command.ksu008B;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.aggregation.dom.form9.Form9Code;
import nts.uk.ctx.at.aggregation.dom.form9.Form9LayoutRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

/**
 * @author rafiqul.islam
 * Command: ユーザー定義出力レイアウト削除
 * Path: UKDesign.UniversalK.就業.KSU_スケジュール.KSU008_様式９.Ｂ：様式９_出力項目設定.B：メニュー別OCD.ユーザー定義出力レイアウト削除
 */

@Stateless
@Transactional
public class OutPutLayoutDeleteKsu008BHandler extends CommandHandler<OutPutLayoutDeleteKsu008BCommand> {

    @Inject
    private Form9LayoutRepository form9LayoutRepository;

    @Override
    protected void handle(CommandHandlerContext<OutPutLayoutDeleteKsu008BCommand> commandHandlerContext) {
        String loginCompany = AppContexts.user().companyId();
        OutPutLayoutDeleteKsu008BCommand command = commandHandlerContext.getCommand();
        val form9LayoutOpt = form9LayoutRepository.get(loginCompany, new Form9Code(command.getCode()));
        if (form9LayoutOpt.isPresent()) {
            form9LayoutRepository.deleteLayoutOfUser(loginCompany, new Form9Code(command.getCode()));
        }
    }
}
