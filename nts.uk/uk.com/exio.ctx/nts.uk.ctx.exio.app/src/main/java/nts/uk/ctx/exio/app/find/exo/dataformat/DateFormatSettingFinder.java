package nts.uk.ctx.exio.app.find.exo.dataformat;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.exio.dom.exo.dataformat.init.DataFormatSettingRepository;
import nts.uk.ctx.exio.dom.exo.dataformat.init.DateFormatSet;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class DateFormatSettingFinder {

	@Inject
	private DataFormatSettingRepository dataFormatSettingRepository;

	public DateFormatSettingDTO getDateFormatSettingByCid() {
		String cid = AppContexts.user().companyId();
		Optional<DateFormatSet> dateFormatSetting = dataFormatSettingRepository.getDateFormatSetByCid(cid);
		return dateFormatSetting.isPresent() ? DateFormatSettingDTO.fromDomain(dateFormatSetting.get()) : null;
	}
}
