package nts.uk.ctx.exio.app.command.exo.condset;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.exio.dom.exo.condset.OutputPeriodSetting;
import nts.uk.ctx.exio.dom.exo.condset.OutputPeriodSettingRepository;
import nts.uk.shr.com.context.AppContexts;

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
		OutputPeriodSetting domain = this.toDomain(cId, command);
		if (command.getIsNew()) {
			this.repo.add(domain);
		} else {
			this.repo.update(domain);
		}
	}

	private OutputPeriodSetting toDomain(String cId, SaveOutputPeriodSetCommand command) {
		return OutputPeriodSetting.createFromMemento(new OutputPeriodSetting.MementoGetter() {
			@Override
			public String getCid() {
				return cId;
			}
			
			@Override
			public int getPeriodSetting() {
				return command.getPeriodSetting();
			}
			
			@Override
			public String getConditionSetCode() {
				return command.getConditionSetCode();
			}
			
			@Override
			public Integer getClosureDayAtr() {
				return command.getClosureDayAtr();
			}
			
			@Override
			public GeneralDate getBaseDateSpecify() {
				return command.getBaseDateSpecify();
			}
			
			@Override
			public Integer getBaseDateClassification() {
				return command.getBaseDateClassification();
			}
			
			@Override
			public GeneralDate getStartDateSpecify() {
				return command.getStartDateSpecify();
			}
			
			@Override
			public Integer getStartDateClassification() {
				return command.getStartDateClassification();
			}
			
			@Override
			public Integer getStartDateAdjustment() {
				return command.getStartDateAdjustment();
			}
			
			@Override
			public GeneralDate getEndDateSpecify() {
				return command.getEndDateSpecify();
			}
			
			@Override
			public Integer getEndDateClassification() {
				return command.getEndDateClassification();
			}
			
			@Override
			public Integer getEndDateAdjustment() {
				return command.getEndDateAdjustment();
			}
		});
	}

}
