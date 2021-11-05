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
 * Command: シフトで勤務予定を登録するシステム固定出力レイアウト_利用区分変更
 * Path: UKDesign.UniversalK.就業.KSU_スケジュール.KSU008_様式９.Ｂ：様式９_出力項目設定.B：メニュー別OCD.システム固定出力レイアウト_利用区分変更
 */

@Stateless
@Transactional
public class WorkScheduleSameKsu008BHandler extends CommandHandler<WorkScheduleSaveKsu008BCommand> {

    @Inject
    private Form9LayoutRepository form9LayoutRepository;

    @Override
    protected void handle(CommandHandlerContext<WorkScheduleSaveKsu008BCommand> commandHandlerContext) {
        String loginCompany = AppContexts.user().companyId();
        WorkScheduleSaveKsu008BCommand command = commandHandlerContext.getCommand();
        val form9Layout = form9LayoutRepository.get(loginCompany, new Form9Code(command.getForm9Code()));
        if (form9Layout.isPresent()) {
            form9LayoutRepository.updateUseAttrOfSystemLayout(
                    loginCompany,
                    form9Layout.get().getCode(),
                    command.isUse()
            );
        }
    }
}
