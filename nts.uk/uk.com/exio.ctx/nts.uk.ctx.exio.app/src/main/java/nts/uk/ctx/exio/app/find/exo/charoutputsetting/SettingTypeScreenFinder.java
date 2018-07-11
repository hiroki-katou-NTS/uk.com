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

	public Optional<SettingItemScreenDTO> getActiveType() {
		String cid = AppContexts.user().companyId();
		Optional<ChacDataFmSet> chacDataFmSet = chacDataFmSetRepository.getChacDataFmSetById(cid);
		if (!chacDataFmSet.isPresent()) {
			return Optional.empty();
		}
		return Optional.of(SettingItemScreenDTO.fromDomain(chacDataFmSet.get()));
	}
}
