package nts.uk.ctx.exio.app.find.exo.dataformat;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.exio.dom.exo.dataformat.init.DataFormatSettingRepository;
import nts.uk.ctx.exio.dom.exo.dataformat.init.NumberDataFmSet;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class NumberDataFormatSettingFinder {
	@Inject
	private DataFormatSettingRepository dataFormatSettingRepository;

	public NumberDataFormatSettingDTO getNumberDataFormatSettingByCid() {
		String cid = AppContexts.user().companyId();
		Optional<NumberDataFmSet> numberDataFormatSetting = dataFormatSettingRepository.getNumberDataFmSetByCid(cid);
		return numberDataFormatSetting.isPresent()
				? NumberDataFormatSettingDTO.fromDomain(numberDataFormatSetting.get()) : null;
	}
}
