package nts.uk.ctx.exio.app.command.exo.condset;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.exio.dom.exo.condset.BaseDateClassificationCode;
import nts.uk.ctx.exio.dom.exo.condset.DateAdjustment;
import nts.uk.ctx.exio.dom.exo.condset.EndDateClassificationCode;
import nts.uk.ctx.exio.dom.exo.condset.ExternalOutputConditionCode;
import nts.uk.ctx.exio.dom.exo.condset.OutputPeriodSetting;
import nts.uk.ctx.exio.dom.exo.condset.OutputPeriodSettingRepository;
import nts.uk.ctx.exio.dom.exo.condset.StartDateClassificationCode;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * 「出力期間設定」に更新登録する
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class SaveOutputPeriodSetCommandHandler extends CommandHandler<SaveOutputPeriodSetCommand>{

	@Inject
	private OutputPeriodSettingRepository repo;
	
	@Override
	protected void handle(CommandHandlerContext<SaveOutputPeriodSetCommand> context) {
		SaveOutputPeriodSetCommand command = context.getCommand();
		String cId = AppContexts.user().companyId();
		OutputPeriodSetting domain = OutputPeriodSetting.builder()
				.cid(cId)
				.periodSetting(EnumAdaptor.valueOf(command.getPeriodSetting(), NotUseAtr.class))
				.conditionSetCode(new ExternalOutputConditionCode(command.getConditionSetCode()))
				.deadlineClassification(Optional.ofNullable(command.getDeadlineClassification()))
				.baseDateClassification(command.getBaseDateClassification() != null 
						? Optional.of(EnumAdaptor.valueOf(command.getBaseDateClassification(), BaseDateClassificationCode.class))
						: Optional.empty())
				.baseDateSpecify(Optional.ofNullable(command.getBaseDateSpecify()))
				.startDateClassification(command.getStartDateClassification() != null 
						? Optional.of(EnumAdaptor.valueOf(command.getStartDateClassification(), StartDateClassificationCode.class))
						: Optional.empty())
				.startDateSpecify(Optional.ofNullable(command.getStartDateSpecify()))
				.startDateAdjustment(command.getStartDateAdjustment() != null 
						? Optional.of(new DateAdjustment(command.getStartDateAdjustment()))
						: Optional.empty())
				.endDateClassification(command.getEndDateClassification() != null 
						? Optional.of(EnumAdaptor.valueOf(command.getEndDateClassification(), EndDateClassificationCode.class))
						: Optional.empty())
				.endDateSpecify(Optional.ofNullable(command.getEndDateSpecify()))
				.endDateAdjustment(command.getEndDateAdjustment() != null 
						? Optional.of(new DateAdjustment(command.getEndDateAdjustment()))
						: Optional.empty())
				.build();
		if (command.getIsNew()) {
			this.repo.add(domain);
		} else {
			this.repo.update(domain);
		}
	}

}
