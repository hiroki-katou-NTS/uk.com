package nts.uk.ctx.exio.app.find.exo.dataformat;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.exio.dom.exo.dataformat.init.DataFormatSettingRepository;

@Stateless
public class PerformSettingByInTimeFinder {
	@Inject
	private DataFormatSettingRepository finder;

	public PerformSettingByInTimeDto getInTimeDataFmSetByCid(String cid) {

		if (finder.getInTimeDataFmSetByCid(cid).isPresent()) {
			return PerformSettingByInTimeDto.fromDomain(finder.getInTimeDataFmSetByCid(cid).get());
		}
		return null;
	}
}
