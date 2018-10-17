package nts.uk.ctx.exio.app.find.exo.dataformat.dataformatsetting;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.exio.dom.exo.dataformat.dataformatsetting.TimeDataFmSetting;
import nts.uk.ctx.exio.dom.exo.outputitem.StandardOutputItemRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
/**
 * 時間型データ形式設定
 */
public class TimeDfsFinder {

	@Inject
	private StandardOutputItemRepository finder;

	public TimeDfsDto getTimeDfs(String conditionSettingCode, String outputItemCode) {
		String cid = AppContexts.user().companyId();
		Optional<TimeDataFmSetting> dataOpt = finder.getTimeDataFmSettingByID(cid, conditionSettingCode,
				outputItemCode);
		return dataOpt.map(i -> TimeDfsDto.fromDomain(i)).orElse(null);
	}

}
