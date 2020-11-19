package nts.uk.ctx.at.record.app.command.monthly.standardtime.workplace;

import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.AgreementTimeOfWorkPlace;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.Workplace36AgreedHoursRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.enums.LaborSystemtAtr;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class CopyTimeWorkplaceCommandHandler extends CommandHandler<CopyTimeWorkplaceCommand> {

    @Inject
    private Workplace36AgreedHoursRepository repo;

    @Override
    protected void handle(CommandHandlerContext<CopyTimeWorkplaceCommand> context) {
        CopyTimeWorkplaceCommand command = context.getCommand();
		val laborSystemAtr = EnumAdaptor.valueOf(command.getLaborSystemAtr(), LaborSystemtAtr.class);

        //1: get(会社ID,雇用コード) : ３６協定基本設定
        val source =  repo.getByWorkplaceId(command.getWorkplaceIdSource(), laborSystemAtr);

		if(!source.isPresent()) return;
		if (CollectionUtil.isEmpty(command.getWorkplaceIdTarget())) return;

		for (val targetCd : command.getWorkplaceIdTarget()) {
			if (targetCd.equals(command.getWorkplaceIdSource())) continue;

			//2: delete(会社ID,雇用コード)
			val existTarget = repo.getByWorkplaceId(targetCd, laborSystemAtr);
			existTarget.ifPresent(x -> repo.delete(x));

			//3: insert(会社ID,雇用コード,３６協定労働制,３６協定基本設定)
			val newTarget = new AgreementTimeOfWorkPlace(targetCd, laborSystemAtr, source.get().getSetting());
			repo.insert(newTarget);
		}
    }
}
