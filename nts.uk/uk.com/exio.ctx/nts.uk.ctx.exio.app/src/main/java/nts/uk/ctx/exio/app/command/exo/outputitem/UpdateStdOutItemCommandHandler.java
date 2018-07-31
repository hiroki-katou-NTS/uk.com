package nts.uk.ctx.exio.app.command.exo.outputitem;

import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.exio.app.command.exo.dataformat.dataformatsetting.AtWorkClsDfsCommand;
import nts.uk.ctx.exio.app.command.exo.dataformat.dataformatsetting.CharacterDfsCommand;
import nts.uk.ctx.exio.app.command.exo.dataformat.dataformatsetting.DateDfsCommand;
import nts.uk.ctx.exio.app.command.exo.dataformat.dataformatsetting.InstantTimeDfsCommand;
import nts.uk.ctx.exio.app.command.exo.dataformat.dataformatsetting.NumberDfsCommand;
import nts.uk.ctx.exio.app.command.exo.dataformat.dataformatsetting.TimeDfsCommand;
import nts.uk.ctx.exio.dom.exo.base.ItemType;
import nts.uk.ctx.exio.dom.exo.dataformat.dataformatsetting.AwDataFormatSetting;
import nts.uk.ctx.exio.dom.exo.dataformat.dataformatsetting.CharacterDataFmSetting;
import nts.uk.ctx.exio.dom.exo.dataformat.dataformatsetting.DateFormatSetting;
import nts.uk.ctx.exio.dom.exo.dataformat.dataformatsetting.InstantTimeDataFmSetting;
import nts.uk.ctx.exio.dom.exo.dataformat.dataformatsetting.NumberDataFmSetting;
import nts.uk.ctx.exio.dom.exo.dataformat.dataformatsetting.TimeDataFmSetting;
import nts.uk.ctx.exio.dom.exo.outputitem.CategoryItem;
import nts.uk.ctx.exio.dom.exo.outputitem.StandardOutputItem;
import nts.uk.ctx.exio.dom.exo.outputitem.StandardOutputItemRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class UpdateStdOutItemCommandHandler extends CommandHandler<StdOutItemCommand> {

	@Inject
	private StandardOutputItemRepository repository;

	@Override
	protected void handle(CommandHandlerContext<StdOutItemCommand> context) {
		StdOutItemCommand updateCommand = context.getCommand();
		String cid = AppContexts.user().companyId();
		StandardOutputItem domain = new StandardOutputItem(cid, updateCommand.getOutItemCd(),
				updateCommand.getCondSetCd(), updateCommand.getOutItemName(), updateCommand.getItemType(),
				updateCommand.getCategoryItems().stream().map(item -> {
					return new CategoryItem(item.getCategoryItemNo(), item.getCategoryId(), item.getOperationSymbol(),
							item.getDisplayOrder());
				}).collect(Collectors.toList()));
		repository.update(domain);

		ItemType itemType = EnumAdaptor.valueOf(updateCommand.getItemType(), ItemType.class);
		switch (itemType) {
		case NUMERIC:
			NumberDfsCommand numberDfsCommand = updateCommand.getNumberDataFormatSetting();
			if (numberDfsCommand != null) {
				repository.register(new NumberDataFmSetting(cid, numberDfsCommand.getNullValueReplace(),
						numberDfsCommand.getValueOfNullValueReplace(), numberDfsCommand.getOutputMinusAsZero(),
						numberDfsCommand.getFixedValue(), numberDfsCommand.getValueOfFixedValue(),
						numberDfsCommand.getFixedValueOperation(), numberDfsCommand.getFixedCalculationValue(),
						numberDfsCommand.getFixedValueOperationSymbol(), numberDfsCommand.getFixedLengthOutput(),
						numberDfsCommand.getFixedLengthIntegerDigit(), numberDfsCommand.getFixedLengthEditingMethod(),
						numberDfsCommand.getDecimalDigit(), numberDfsCommand.getDecimalPointClassification(),
						numberDfsCommand.getDecimalFraction(), numberDfsCommand.getFormatSelection(),
						updateCommand.getCondSetCd(), updateCommand.getOutItemCd()));
			}
			break;
		case CHARACTER:
			CharacterDfsCommand characterDfsCommand = updateCommand.getCharacterDataFormatSetting();
			if (characterDfsCommand != null) {
				repository.register(new CharacterDataFmSetting(cid, characterDfsCommand.getNullValueReplace(),
						characterDfsCommand.getValueOfNullValueReplace(), characterDfsCommand.getCdEditting(),
						characterDfsCommand.getFixedValue(), characterDfsCommand.getCdEdittingMethod(),
						characterDfsCommand.getCdEditDigit(), characterDfsCommand.getCdConvertCd(),
						characterDfsCommand.getSpaceEditting(), characterDfsCommand.getEffectDigitLength(),
						characterDfsCommand.getStartDigit(), characterDfsCommand.getEndDigit(),
						characterDfsCommand.getValueOfFixedValue(), updateCommand.getCondSetCd(),
						updateCommand.getOutItemCd()));
			}
			break;
		case DATE:
			DateDfsCommand dateDfsCommand = updateCommand.getDateDataFormatSetting();
			if (dateDfsCommand != null) {
				repository.register(new DateFormatSetting(cid, dateDfsCommand.getNullValueSubstitution(),
						dateDfsCommand.getFixedValue(), dateDfsCommand.getValueOfFixedValue(),
						dateDfsCommand.getValueOfNullValueSubs(), dateDfsCommand.getFormatSelection(),
						updateCommand.getCondSetCd(), updateCommand.getOutItemCd()));
			}

			break;
		case TIME:
			TimeDfsCommand timeDfsCommand = updateCommand.getTimeDataFormatSetting();
			if (timeDfsCommand != null) {
				repository.register(new TimeDataFmSetting(cid, timeDfsCommand.getNullValueSubs(),
						timeDfsCommand.getOutputMinusAsZero(), timeDfsCommand.getFixedValue(),
						timeDfsCommand.getValueOfFixedValue(), timeDfsCommand.getFixedLengthOutput(),
						timeDfsCommand.getFixedLongIntegerDigit(), timeDfsCommand.getFixedLengthEditingMethod(),
						timeDfsCommand.getDelimiterSetting(), timeDfsCommand.getSelectHourMinute(),
						timeDfsCommand.getMinuteFractionDigit(), timeDfsCommand.getDecimalSelection(),
						timeDfsCommand.getFixedValueOperationSymbol(), timeDfsCommand.getFixedValueOperation(),
						timeDfsCommand.getFixedCalculationValue(), timeDfsCommand.getValueOfNullValueSubs(),
						timeDfsCommand.getMinuteFractionDigitProcessCls(), updateCommand.getCondSetCd(),
						updateCommand.getOutItemCd()));
			}
			break;
		case INS_TIME:
			InstantTimeDfsCommand instantTimeDfsCommand = updateCommand.getInTimeDataFormatSetting();
			if (instantTimeDfsCommand != null) {
				repository.register(new InstantTimeDataFmSetting(cid, instantTimeDfsCommand.getNullValueSubs(),
						instantTimeDfsCommand.getValueOfNullValueSubs(), instantTimeDfsCommand.getOutputMinusAsZero(),
						instantTimeDfsCommand.getFixedValue(), instantTimeDfsCommand.getValueOfFixedValue(),
						instantTimeDfsCommand.getTimeSeletion(), instantTimeDfsCommand.getFixedLengthOutput(),
						instantTimeDfsCommand.getFixedLongIntegerDigit(),
						instantTimeDfsCommand.getFixedLengthEditingMethod(),
						instantTimeDfsCommand.getDelimiterSetting(), instantTimeDfsCommand.getPreviousDayOutputMethod(),
						instantTimeDfsCommand.getNextDayOutputMethod(), instantTimeDfsCommand.getMinuteFractionDigit(),
						instantTimeDfsCommand.getDecimalSelection(),
						instantTimeDfsCommand.getMinuteFractionDigitProcessCls(), updateCommand.getCondSetCd(),
						updateCommand.getOutItemCd()));
			}
			break;
		case AT_WORK_CLS:
			AtWorkClsDfsCommand atWorkClsDfsCommand = updateCommand.getAtWorkDataOutputItem();
			if (atWorkClsDfsCommand != null) {
				repository.register(new AwDataFormatSetting(cid, atWorkClsDfsCommand.getClosedOutput(),
						atWorkClsDfsCommand.getAbsenceOutput(), atWorkClsDfsCommand.getFixedValue(),
						atWorkClsDfsCommand.getValueOfFixedValue(), atWorkClsDfsCommand.getAtWorkOutput(),
						atWorkClsDfsCommand.getRetirementOutput(), updateCommand.getCondSetCd(),
						updateCommand.getOutItemCd()));
			}
			break;
		}
	}
}
