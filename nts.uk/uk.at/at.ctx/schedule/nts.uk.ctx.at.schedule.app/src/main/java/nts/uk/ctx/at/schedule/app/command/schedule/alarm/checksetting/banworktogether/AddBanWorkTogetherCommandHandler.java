package nts.uk.ctx.at.schedule.app.command.schedule.alarm.checksetting.banworktogether;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.worktogether.ban.*;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrganizationUnit;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

/**
 * UKDesign.UniversalK.就業.KSM_スケジュールマスタ.KSM008 スケジュールのアラームチェック設定.C: 同時出勤禁止.メニュー別OCD.同時出勤禁止を新規する
 */
@Stateless
public class AddBanWorkTogetherCommandHandler extends CommandHandler<AddBanWorkTogetherCommand> {

    @Inject
    private BanWorkTogetherRepository banWorkTogetherRepo;

    @Override
    protected void handle(CommandHandlerContext<AddBanWorkTogetherCommand> context) {
        AddBanWorkTogetherCommand command = context.getCommand();

        TargetOrgIdenInfor targetOrgIdenInfor = command.toDomainTargetOrgIdenInfor();
        BanWorkTogether banWorkTogether = this.toDomainBanWork(
                command.getTargetOrgIdenInfor().getUnit(),
                command.getApplicableTimeZoneCls(),
                command.getCode(),
                command.getName(),
                command.getTargetList(),
                command.getUpperLimit(),
                targetOrgIdenInfor
        );

        Boolean exist = banWorkTogetherRepo.exists(AppContexts.user().companyId(), targetOrgIdenInfor, banWorkTogether.getCode());

        if (exist) {
            throw new BusinessException("Msg_3");
        }

        banWorkTogetherRepo.insert(AppContexts.user().companyId(), banWorkTogether);
    }

    private BanWorkTogether toDomainBanWork(int unit, int applicableTimeZoneCls, String code, String name, List<String> targetList, int upperLimit, TargetOrgIdenInfor targetOrgIdenInfor) {
        BanWorkTogether result = null;
        if (unit == TargetOrganizationUnit.WORKPLACE_GROUP.value
                && applicableTimeZoneCls == ApplicableTimeZoneCls.NIGHTSHIFT.value) {
            result = BanWorkTogether.createByNightShift(
                    targetOrgIdenInfor,
                    new BanWorkTogetherCode(code),
                    new BanWorkTogetherName(name),
                    targetList,
                    upperLimit
            );
        } else {
            result = BanWorkTogether.createBySpecifyingAllDay(
                    targetOrgIdenInfor,
                    new BanWorkTogetherCode(code),
                    new BanWorkTogetherName(name),
                    targetList,
                    upperLimit
            );
        }
        return result;
    }
}
