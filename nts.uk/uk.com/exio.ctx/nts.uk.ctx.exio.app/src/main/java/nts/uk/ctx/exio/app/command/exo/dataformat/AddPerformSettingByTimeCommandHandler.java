package nts.uk.ctx.exio.app.command.exo.dataformat;

import java.math.BigDecimal;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.exio.dom.exi.dataformat.ItemType;
import nts.uk.ctx.exio.dom.exo.dataformat.init.DataFormatSettingRepository;
import nts.uk.ctx.exio.dom.exo.dataformat.init.TimeDataFmSet;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class AddPerformSettingByTimeCommandHandler extends CommandHandler<AddPerformSettingByTimeCommand> {
	@Inject
	private DataFormatSettingRepository repoTimeDataFmSet;

	@Override
	protected void handle(CommandHandlerContext<AddPerformSettingByTimeCommand> context) {
		AddPerformSettingByTimeCommand addCommand = context.getCommand();
		// 外部出力時間型登録チェック
		String cid = AppContexts.user().companyId();
		int itemType = ItemType.TIME.value;
		TimeDataFmSet timeDataFmSet = new TimeDataFmSet(itemType, cid, 
				addCommand.getNullValueSubs(),
				addCommand.getOutputMinusAsZero(), 
				addCommand.getFixedValue(), 
				addCommand.getValueOfFixedValue(),
				addCommand.getFixedLengthOutput(), 
				addCommand.getFixedLongIntegerDigit(),
				addCommand.getFixedLengthEditingMethod(), 
				addCommand.getDelimiterSetting(),
				addCommand.getSelectHourMinute(), 
				addCommand.getMinuteFractionDigit(), 
				addCommand.getDecimalSelection(),
				addCommand.getFixedValueOperationSymbol(),
				addCommand.getFixedValueOperation(),
				!(addCommand.getFixedCalculationValue() == null) ? BigDecimal.valueOf(addCommand.getFixedCalculationValue()): null, 
				addCommand.getValueOfNullValueSubs(),
				addCommand.getMinuteFractionDigitProcessCls());
		// Check exist in database
		Optional<TimeDataFmSet> dataTimeDataFmSet = repoTimeDataFmSet.getTimeDataFmSetByCid(cid);
		if (dataTimeDataFmSet.isPresent()) {
			repoTimeDataFmSet.update(timeDataFmSet);
		} else {
			repoTimeDataFmSet.add(timeDataFmSet);
		}
	}
}
