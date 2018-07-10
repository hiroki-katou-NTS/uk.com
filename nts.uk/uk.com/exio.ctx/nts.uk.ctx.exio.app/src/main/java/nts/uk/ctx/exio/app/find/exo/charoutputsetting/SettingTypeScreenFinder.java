package nts.uk.ctx.exio.app.find.exo.charoutputsetting;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.exio.dom.exo.dataformat.ChacDataFmSet;
import nts.uk.ctx.exio.dom.exo.dataformat.ChacDataFmSetRepository;
import nts.uk.shr.com.context.AppContexts;


@Stateless
public class SettingTypeScreenFinder {

	@Inject
	ChacDataFmSetRepository chacDataFmSetRepository;

	public SettingItemScreenDTO getActiveType(){
		SettingItemScreenDTO settingItemScreenCommand = null;
		String cid = AppContexts.user().companyId();
		Optional<ChacDataFmSet> chacDataFmSet = chacDataFmSetRepository
				.getChacDataFmSetById(cid);
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
					charFormatSet.getConvertCode().ifPresent(x -> {
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
		return settingItemScreenCommand;
	}
}
