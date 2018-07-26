package nts.uk.ctx.exio.app.find.exo.dataformat.dataformatsetting;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.exio.dom.exo.dataformat.dataformatsetting.DateFormatSetting;
import nts.uk.ctx.exio.dom.exo.outputitem.StandardOutputItemRepository;

@Stateless
/**
 * 日付型データ形式設定
 */
public class DateDfsFinder {

	@Inject
	private StandardOutputItemRepository finder;

	public DateDfsDto getDateDfs(String cid, String conditionSettingCode, String outputItemCode) {
		Optional<DateFormatSetting> dataOpt = finder.getDateFormatSettingByID(cid, conditionSettingCode,
				outputItemCode);
		return dataOpt.map(i -> DateDfsDto.fromDomain(i)).orElse(null);
	}

}
