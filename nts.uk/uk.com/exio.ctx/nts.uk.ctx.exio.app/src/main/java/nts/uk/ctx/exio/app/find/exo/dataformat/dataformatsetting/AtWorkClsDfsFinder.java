package nts.uk.ctx.exio.app.find.exo.dataformat.dataformatsetting;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.exio.dom.exo.dataformat.dataformatsetting.AwDataFormatSetting;
import nts.uk.ctx.exio.dom.exo.outputitem.StandardOutputItemRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
/**
 * 在職区分型データ形式設定
 */
public class AtWorkClsDfsFinder {

	@Inject
	private StandardOutputItemRepository finder;

	public AtWorkClsDfsDto getAtWorkClsDfs(String conditionSettingCode, String outputItemCode) {
		String cid = AppContexts.user().companyId();
		Optional<AwDataFormatSetting> dataOpt = finder.getAwDataFormatSettingByID(cid, conditionSettingCode,
				outputItemCode);
		return dataOpt.map(i -> AtWorkClsDfsDto.fromDomain(i)).orElse(null);
	}
}
