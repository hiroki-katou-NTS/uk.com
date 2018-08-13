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
import nts.uk.ctx.exio.dom.exo.outputitemorder.StandardOutputItemOrder;
import nts.uk.ctx.exio.dom.exo.outputitemorder.StandardOutputItemOrderRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class RegisterStdOutItemCommandHandler extends CommandHandler<StdOutItemCommand> {

	@Inject
	private StandardOutputItemRepository repository;
	
	@Inject
	private StandardOutputItemOrderRepository standardOutputItemOrderRepository;

	@Override
	protected void handle(CommandHandlerContext<StdOutItemCommand> context) {
		StdOutItemCommand addCommand = context.getCommand();
		String cid = AppContexts.user().companyId();
		StandardOutputItem domain = new StandardOutputItem(cid, addCommand.getOutItemCd(), addCommand.getCondSetCd(),
				addCommand.getOutItemName(), addCommand.getItemType(),
				addCommand.getCategoryItems().stream().map(item -> {
					return new CategoryItem(item.getCategoryItemNo(), item.getCategoryId(), item.getOperationSymbol(),
							item.getDisplayOrder());
				}).collect(Collectors.toList()));
		int order = addCommand.getDispOrder();
		StandardOutputItemOrder orderDomain= new StandardOutputItemOrder(cid, addCommand.getOutItemCd(), addCommand.getCondSetCd(), order);
		if (addCommand.isNewMode()) {
			repository.add(domain);
			standardOutputItemOrderRepository.add(orderDomain);
		} else {
			repository.update(domain);
		}
		
		ItemType itemType = EnumAdaptor.valueOf(addCommand.getItemType(), ItemType.class);
		switch (itemType) {
		case NUMERIC:
			NumberDfsCommand numberDfsCommand = addCommand.getNumberDataFormatSetting();
			if (numberDfsCommand != null) {
				repository.register(new NumberDataFmSetting(cid, numberDfsCommand.getNullValueReplace(),
						numberDfsCommand.getValueOfNullValueReplace(), numberDfsCommand.getOutputMinusAsZero(),
						numberDfsCommand.getFixedValue(), numberDfsCommand.getValueOfFixedValue(),
						numberDfsCommand.getFixedValueOperation(), numberDfsCommand.getFixedCalculationValue(),
						numberDfsCommand.getFixedValueOperationSymbol(), numberDfsCommand.getFixedLengthOutput(),
						numberDfsCommand.getFixedLengthIntegerDigit(), numberDfsCommand.getFixedLengthEditingMethod(),
						numberDfsCommand.getDecimalDigit(), numberDfsCommand.getDecimalPointClassification(),
						numberDfsCommand.getDecimalFraction(), numberDfsCommand.getFormatSelection(),
						addCommand.getCondSetCd(), addCommand.getOutItemCd()));
			}
			break;
		case CHARACTER:
			CharacterDfsCommand characterDfsCommand = addCommand.getCharacterDataFormatSetting();
			if (characterDfsCommand != null) {
				repository.register(new CharacterDataFmSetting(cid, characterDfsCommand.getNullValueReplace(),
						characterDfsCommand.getValueOfNullValueReplace(), characterDfsCommand.getCdEditting(),
						characterDfsCommand.getFixedValue(), characterDfsCommand.getCdEdittingMethod(),
						characterDfsCommand.getCdEditDigit(), characterDfsCommand.getCdConvertCd(),
						characterDfsCommand.getSpaceEditting(), characterDfsCommand.getEffectDigitLength(),
						characterDfsCommand.getStartDigit(), characterDfsCommand.getEndDigit(),
						characterDfsCommand.getValueOfFixedValue(), addCommand.getCondSetCd(),
						addCommand.getOutItemCd()));
			}
			break;
		case DATE:
			DateDfsCommand dateDfsCommand = addCommand.getDateDataFormatSetting();
			if (dateDfsCommand != null) {
				repository.register(new DateFormatSetting(cid, dateDfsCommand.getNullValueSubstitution(),
						dateDfsCommand.getFixedValue(), dateDfsCommand.getValueOfFixedValue(),
						dateDfsCommand.getValueOfNullValueSubs(), dateDfsCommand.getFormatSelection(),
						addCommand.getCondSetCd(), addCommand.getOutItemCd()));
			}

			break;
		case TIME:
			TimeDfsCommand timeDfsCommand = addCommand.getTimeDataFormatSetting();
			if (timeDfsCommand != null) {
				repository.register(new TimeDataFmSetting(cid, timeDfsCommand.getNullValueSubs(),
						timeDfsCommand.getOutputMinusAsZero(), timeDfsCommand.getFixedValue(),
						timeDfsCommand.getValueOfFixedValue(), timeDfsCommand.getFixedLengthOutput(),
						timeDfsCommand.getFixedLongIntegerDigit(), timeDfsCommand.getFixedLengthEditingMethod(),
						timeDfsCommand.getDelimiterSetting(), timeDfsCommand.getSelectHourMinute(),
						timeDfsCommand.getMinuteFractionDigit(), timeDfsCommand.getDecimalSelection(),
						timeDfsCommand.getFixedValueOperationSymbol(), timeDfsCommand.getFixedValueOperation(),
						timeDfsCommand.getFixedCalculationValue(), timeDfsCommand.getValueOfNullValueSubs(),
						timeDfsCommand.getMinuteFractionDigitProcessCls(), addCommand.getCondSetCd(),
						addCommand.getOutItemCd()));
			}
			break;
		case INS_TIME:
			InstantTimeDfsCommand instantTimeDfsCommand = addCommand.getInTimeDataFormatSetting();
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
						instantTimeDfsCommand.getMinuteFractionDigitProcessCls(), addCommand.getCondSetCd(),
						addCommand.getOutItemCd()));
			}
			break;
		case AT_WORK_CLS:
			AtWorkClsDfsCommand atWorkClsDfsCommand = addCommand.getAtWorkDataOutputItem();
			if (atWorkClsDfsCommand != null) {
				repository.register(new AwDataFormatSetting(cid, atWorkClsDfsCommand.getClosedOutput(),
						atWorkClsDfsCommand.getAbsenceOutput(), atWorkClsDfsCommand.getFixedValue(),
						atWorkClsDfsCommand.getValueOfFixedValue(), atWorkClsDfsCommand.getAtWorkOutput(),
						atWorkClsDfsCommand.getRetirementOutput(), addCommand.getCondSetCd(),
						addCommand.getOutItemCd()));
			}
			break;
		}

	}
}
