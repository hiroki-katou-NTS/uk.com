package nts.uk.ctx.exio.app.find.exo.dataformat;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.exio.dom.exo.dataformat.InTimeDataFmSetRepository;

@Stateless
public class PerformSettingByInTimeFinder {
	@Inject
	private InTimeDataFmSetRepository finder;

	public PerformSettingByInTimeDto getInTimeDataFmSetByCid(String cid) {

		if (finder.getInTimeDataFmSetById(cid).isPresent()) {
			return PerformSettingByInTimeDto.fromDomain(finder.getInTimeDataFmSetById(cid).get());
		} else {
			return null;
		}
	}
}
