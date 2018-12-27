package nts.uk.ctx.exio.app.command.exo.dataformat;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.exio.dom.exi.dataformat.ItemType;
import nts.uk.ctx.exio.dom.exo.dataformat.init.DataFormatSettingRepository;
import nts.uk.ctx.exio.dom.exo.dataformat.init.InTimeDataFmSet;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class AddPerformSettingByInTimeCommandHandler extends CommandHandler<AddPerformSettingByInTimeCommand> {
	@Inject
	private DataFormatSettingRepository repoTimeDataFmSet;

	@Override
	protected void handle(CommandHandlerContext<AddPerformSettingByInTimeCommand> context) {
		AddPerformSettingByInTimeCommand addCommand = context.getCommand();
		// 外部出力時間型登録チェック
		String cid = AppContexts.user().companyId();
		int itemType = ItemType.INS_TIME.value;
		InTimeDataFmSet inTimeDataFmSet = new InTimeDataFmSet(itemType, cid, 
				addCommand.getNullValueSubs(),
				addCommand.getValueOfNullValueSubs(), 
				addCommand.getOutputMinusAsZero(), 
				addCommand.getFixedValue(),
				addCommand.getValueOfFixedValue(), 
				addCommand.getTimeSeletion(), 
				addCommand.getFixedLengthOutput(),
				addCommand.getFixedLongIntegerDigit(), 
				addCommand.getFixedLengthEditingMethod(),
				addCommand.getDelimiterSetting(), 
				addCommand.getPreviousDayOutputMethod(),
				addCommand.getNextDayOutputMethod(), 
				addCommand.getMinuteFractionDigit(),
				addCommand.getDecimalSelection(), 
				addCommand.getMinuteFractionDigitProcessCls());
		// Check exist in database
		Optional<InTimeDataFmSet> dataInTimeDataFmSet = repoTimeDataFmSet.getInTimeDataFmSetByCid(cid);
		if (dataInTimeDataFmSet.isPresent()) {
			repoTimeDataFmSet.update(inTimeDataFmSet);
		} else {
			repoTimeDataFmSet.add(inTimeDataFmSet);
		}
	}
}
