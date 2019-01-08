package nts.uk.ctx.exio.app.find.exo.charoutputsetting;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.exio.dom.exo.cdconvert.OutputCodeConvertRepository;
import nts.uk.ctx.exio.dom.exo.dataformat.init.ChacDataFmSet;
import nts.uk.ctx.exio.dom.exo.dataformat.init.DataFormatSettingRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class GetCdConvertName {

	@Inject
	DataFormatSettingRepository chacDataFmSetRepository;

	@Inject
	private OutputCodeConvertRepository repository;

	public String getCdConvertName(String cdConvertCode) {
		String cid = AppContexts.user().companyId();
		Optional<ChacDataFmSet> chacDataFmSet = chacDataFmSetRepository.getChacDataFmSetById(cid);
		if (chacDataFmSet.isPresent()) {
			if (cdConvertCode != null) {
				String[] cdConvertName = new String[1];
				repository.getOutputCodeConvertById(cid, chacDataFmSet.get().getConvertCode().get().v())
						.ifPresent(x -> {
							cdConvertName[0] = x.getConvertName().v();
						});
				return cdConvertName[0];
			}
		}
		return null;
	}

}
