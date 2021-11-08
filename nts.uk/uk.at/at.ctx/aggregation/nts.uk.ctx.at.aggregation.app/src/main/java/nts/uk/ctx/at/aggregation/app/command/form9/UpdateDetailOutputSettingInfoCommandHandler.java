package nts.uk.ctx.at.aggregation.app.command.form9;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.aggregation.dom.form9.Form9DetailOutputSettingRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

/**
 * 詳細出力設定情報を更新する
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.予実集計.様式９.App. 詳細出力設定情報を更新する.詳細出力設定情報を更新する
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class UpdateDetailOutputSettingInfoCommandHandler extends CommandHandler<UpdateDetailOutputSettingInfoCommand> {
    @Inject
    private Form9DetailOutputSettingRepository detailOutputRepo;

    @Override
    protected void handle(CommandHandlerContext<UpdateDetailOutputSettingInfoCommand> commandHandlerContext) {
        val command = commandHandlerContext.getCommand();
        if (command == null) return;
        String cid = AppContexts.user().companyId();

        val detailSetting = this.detailOutputRepo.get(cid);
        if (detailSetting != null) {
            this.detailOutputRepo.update(cid, command.toDomain());
        }
    }
}
