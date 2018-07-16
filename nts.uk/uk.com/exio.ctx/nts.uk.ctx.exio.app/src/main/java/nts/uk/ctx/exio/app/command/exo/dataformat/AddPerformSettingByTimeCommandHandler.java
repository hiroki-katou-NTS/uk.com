package nts.uk.ctx.exio.app.command.exo.dataformat;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.exio.dom.exo.dataformat.TimeDataFmSet;
import nts.uk.ctx.exio.dom.exo.dataformat.TimeDataFmSetRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class AddPerformSettingByTimeCommandHandler extends CommandHandler<AddPerformSettingByTimeCommand> {
	@Inject
	private TimeDataFmSetRepository repoTimeDataFmSet;

	@Override
	protected void handle(CommandHandlerContext<AddPerformSettingByTimeCommand> context) {
		AddPerformSettingByTimeCommand addCommand = context.getCommand();
		// 外部出力時間型登録チェック
		String cid = AppContexts.user().companyId();
		int itemType = 3;
		TimeDataFmSet timeDataFmSet = new TimeDataFmSet(itemType, cid, addCommand.getNullValueSubs(),
				addCommand.getOutputMinusAsZero(), addCommand.getFixedValue(), addCommand.getValueOfFixedValue(),
				addCommand.getFixedLengthOutput(), addCommand.getFixedLongIntegerDigit(),
				addCommand.getFixedLengthEditingMothod(), addCommand.getDelimiterSetting(),
				addCommand.getSelectHourMinute(), addCommand.getMinuteFractionDigit(), addCommand.getDecimalSelection(),
				addCommand.getFixedValueOperationSymbol(), addCommand.getFixedValueOperation(),
				addCommand.getFixedCalculationValue(), addCommand.getValueOfNullValueSubs(),
				addCommand.getMinuteFractionDigitProcessCla());
		// Check exist in database
		Optional<TimeDataFmSet> dataTimeDataFmSet = repoTimeDataFmSet.getTimeDataFmSetByCid(cid);
		if (dataTimeDataFmSet.isPresent()) {
			repoTimeDataFmSet.update(timeDataFmSet);
		} else {
			repoTimeDataFmSet.add(timeDataFmSet);
		}
	}
}
