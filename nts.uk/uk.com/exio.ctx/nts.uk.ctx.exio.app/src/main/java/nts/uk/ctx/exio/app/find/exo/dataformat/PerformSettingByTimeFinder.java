package nts.uk.ctx.exio.app.find.exo.dataformat;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.exio.dom.exo.dataformat.init.DataFormatSettingRepository;

@Stateless
public class PerformSettingByTimeFinder {
	@Inject
	private DataFormatSettingRepository finder;

	public PerformSettingByTimeDto getTimeDataFmSetByCid(String cid) {

		if (finder.getTimeDataFmSetByCid(cid).isPresent()) {
			return PerformSettingByTimeDto.fromDomain(finder.getTimeDataFmSetByCid(cid).get());
		}
		return null;

	}
}
