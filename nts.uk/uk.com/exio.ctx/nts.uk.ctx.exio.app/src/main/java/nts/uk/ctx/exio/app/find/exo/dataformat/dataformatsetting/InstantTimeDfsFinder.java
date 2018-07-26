package nts.uk.ctx.exio.app.find.exo.dataformat.dataformatsetting;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.exio.dom.exo.dataformat.dataformatsetting.InstantTimeDataFmSetting;
import nts.uk.ctx.exio.dom.exo.outputitem.StandardOutputItemRepository;

@Stateless
/**
 * 時刻型データ形式設定
 */
public class InstantTimeDfsFinder {

	@Inject
	private StandardOutputItemRepository finder;

	public InstantTimeDfsDto getInstantTimeDfs(String cid, String conditionSettingCode, String outputItemCode) {
		Optional<InstantTimeDataFmSetting> dataOpt = finder.getInstantTimeDataFmSettingByID(cid, conditionSettingCode,
				outputItemCode);
		return dataOpt.map(i -> InstantTimeDfsDto.fromDomain(i)).orElse(null);
	}

}
