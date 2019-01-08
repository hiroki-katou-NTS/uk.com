package nts.uk.ctx.exio.app.find.exo.awdataformatsetting;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.exio.dom.exo.dataformat.init.AwDataFormatSet;
import nts.uk.ctx.exio.dom.exo.dataformat.init.DataFormatSettingRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class AWDataScreenService {

	@Inject
	DataFormatSettingRepository awDataFormatSetRepository;

	public AwDataFormatDTO getAWData() {
		String cid = AppContexts.user().companyId();
		Optional<AwDataFormatSet> awDataFormatSet = awDataFormatSetRepository.getAwDataFormatSetById(cid);
		if (awDataFormatSet.isPresent()) {
			return AwDataFormatDTO.fromDomain(awDataFormatSet.get());
		}
		return null;
	}
}
