package nts.uk.ctx.exio.app.find.exo.dataformat.dataformatsetting;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.exio.dom.exo.dataformat.dataformatsetting.NumberDataFmSetting;
import nts.uk.ctx.exio.dom.exo.outputitem.StandardOutputItemRepository;

@Stateless
/**
 * 数値型データ形式設定
 */
public class NumberDfsFinder {

	@Inject
	private StandardOutputItemRepository finder;

	public NumberDfsDto getNumberDfs(String cid, String conditionSettingCode, String outputItemCode) {
		Optional<NumberDataFmSetting> dataOpt = finder.getNumberDataFmSettingByID(cid, conditionSettingCode,
				outputItemCode);
		return dataOpt.map(i -> NumberDfsDto.fromDomain(i)).orElse(null);
	}

}
