package nts.uk.ctx.at.record.app.command.monthly.standardtime.employment;

import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.AgreementTimeOfEmployment;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.Employment36HoursRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.enums.LaborSystemtAtr;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class CopyTimeEmploymentCommandHandler extends CommandHandler<CopyTimeEmploymentCommand> {

    @Inject
    private Employment36HoursRepository repo;

    @Override
    protected void handle(CommandHandlerContext<CopyTimeEmploymentCommand> context) {
        CopyTimeEmploymentCommand command = context.getCommand();

        val cid = AppContexts.user().companyId();
		val laborSystemAtr = EnumAdaptor.valueOf(command.getLaborSystemAtr(), LaborSystemtAtr.class);

		// 1: get(会社ID,雇用コード) : ３６協定基本設定
        val source = repo.getByCidAndCd(cid, command.getEmpCdSource(), laborSystemAtr);

        if(!source.isPresent()) return;
		if (CollectionUtil.isEmpty(command.getEmpCdTarget())) return;

		for (val targetCd : command.getEmpCdTarget()) {
			if (targetCd.equals(command.getEmpCdSource())) continue;

			// 2: delete
			val existTarget = repo.getByCidAndCd(cid, targetCd, laborSystemAtr);
			existTarget.ifPresent(x -> repo.delete(x));

			//3: insert(会社ID,雇用コード,３６協定労働制,３６協定基本設定)
			val newTarget = new AgreementTimeOfEmployment(
					cid,
					laborSystemAtr,
					new EmploymentCode(targetCd),
					source.get().getSetting()
			);
			repo.insert(newTarget);
		}
    }
}
