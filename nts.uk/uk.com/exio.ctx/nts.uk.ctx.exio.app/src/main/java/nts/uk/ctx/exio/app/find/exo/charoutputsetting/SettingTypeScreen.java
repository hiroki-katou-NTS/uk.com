package nts.uk.ctx.exio.app.find.exo.charoutputsetting;

import java.util.Optional;

import javax.inject.Inject;

import nts.uk.ctx.exio.dom.exo.datafomat.ChacDataFmSet;
import nts.uk.ctx.exio.dom.exo.datafomat.ChacDataFmSetRepository;

public class SettingTypeScreen {

	@Inject
	ChacDataFmSetRepository chacDataFmSetRepository;

	public SettingItemScreenCommand getActiveType(OutputTypeSettingCommand outputTypeSettingCommand) {
		SettingItemScreenCommand settingItemScreenCommand = null;
		Optional<ChacDataFmSet> chacDataFmSet = chacDataFmSetRepository
				.getChacDataFmSetById(outputTypeSettingCommand.getCid());
		if ("individual".equals(outputTypeSettingCommand.getClassification()) || "initial".equals(outputTypeSettingCommand.getClassification())) {
				if (chacDataFmSet.isPresent()) {
					ChacDataFmSet charFormatSet = chacDataFmSet.get();
					// check valueOfNullValueReplace not null
					charFormatSet.getValueOfNullValueReplace().ifPresent(x -> {
						settingItemScreenCommand.setValueOfNullValueReplace(x.v());
					});
					// check cdEditDigit not null
					charFormatSet.getCdEditDigit().ifPresent(x -> {
						settingItemScreenCommand.setCdEditDigit(x.v());
					});
					// check startDigit not null
					charFormatSet.getStartDigit().ifPresent(x -> {
						settingItemScreenCommand.setStartDigit(x.v());
					});
					// check endDigit not null
					charFormatSet.getEndDigit().ifPresent(x -> {
						settingItemScreenCommand.setEndDigit(x.v());
					});
					// check cdConvertCd not null
					charFormatSet.getCdConvertCd().ifPresent(x -> {
						settingItemScreenCommand.setCdConvertCd(x.v());
					});
					// check valueOfFixedValue not null
					charFormatSet.getValueOfFixedValue().ifPresent(x -> {
						settingItemScreenCommand.setValueOfFixedValue(x.v());
					});
					settingItemScreenCommand.setNullValueReplace(charFormatSet.getNullValueReplace().value);
					settingItemScreenCommand.setCdEditting(charFormatSet.getCdEditting().value);
					settingItemScreenCommand.setFixedValue(charFormatSet.getFixedValue().value);
					settingItemScreenCommand.setCdEdittingMethod(charFormatSet.getCdEdittingMethod().value);
					settingItemScreenCommand.setSpaceEditting(charFormatSet.getSpaceEditting().value);
					settingItemScreenCommand.setEffectDigitLength(charFormatSet.getEffectDigitLength().value);
				}
			}
		return settingItemScreenCommand;
	}
}
