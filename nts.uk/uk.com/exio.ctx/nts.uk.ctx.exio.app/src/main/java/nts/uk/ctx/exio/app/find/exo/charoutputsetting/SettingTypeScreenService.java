package nts.uk.ctx.exio.app.find.exo.charoutputsetting;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.exio.dom.exo.dataformat.init.ChacDataFmSet;
import nts.uk.ctx.exio.dom.exo.dataformat.init.DataFormatSettingRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class SettingTypeScreenService {

	@Inject
	DataFormatSettingRepository chacDataFmSetRepository;

	public SettingItemScreenDTO getActiveType() {
		String cid = AppContexts.user().companyId();
		Optional<ChacDataFmSet> chacDataFmSet = chacDataFmSetRepository.getChacDataFmSetById(cid);
		if (chacDataFmSet.isPresent()) {
			return SettingItemScreenDTO.fromDomain(chacDataFmSet.get());
		}else{
			return null;
		}
	}
}
