package nts.uk.ctx.at.record.app.command.monthly.standardtime.classification;

import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.affiliationinfor.ClassificationCode;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.AgreementTimeOfClassification;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.Classification36AgreementTimeRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.enums.LaborSystemtAtr;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class CopyTimeClassificationCommandHandler extends CommandHandler<CopyTimeClassificationCommand> {

    @Inject
    private Classification36AgreementTimeRepository repo;

    @Override
    protected void handle(CommandHandlerContext<CopyTimeClassificationCommand> context) {
        CopyTimeClassificationCommand command = context.getCommand();

		String cid = AppContexts.user().companyId();
		val laborSystemAtr = EnumAdaptor.valueOf(command.getLaborSystemAtr(), LaborSystemtAtr.class);

		// 1: get(会社ID,雇用コード) : ３６協定基本設定
        val source = repo.getByCidAndClassificationCode(cid, command.getClassificationCdSource(), laborSystemAtr);

		if(!source.isPresent()) return;
		if (CollectionUtil.isEmpty(command.getClassificationCdTarget())) return;

		val basicAgreementSetting = source.get().getSetting();
		for (val targetCd : command.getClassificationCdTarget()) {
			if (targetCd.equals(command.getClassificationCdSource())) continue;

			// 2: delete(会社ID,雇用コード)
			val existTarget = repo.getByCidAndClassificationCode(cid, targetCd, laborSystemAtr);
			existTarget.ifPresent(x -> repo.delete(x));

			// 3: insert(会社ID,雇用コード,３６協定労働制,３６協定基本設定)
			val newTarget = new AgreementTimeOfClassification(
					cid,
					laborSystemAtr,
					new ClassificationCode(targetCd),
					basicAgreementSetting
			);
			repo.insert(newTarget);
		}
    }
}
