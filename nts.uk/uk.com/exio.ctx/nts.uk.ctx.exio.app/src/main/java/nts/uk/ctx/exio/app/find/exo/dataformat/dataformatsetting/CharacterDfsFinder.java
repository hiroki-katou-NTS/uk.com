package nts.uk.ctx.exio.app.find.exo.dataformat.dataformatsetting;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.exio.dom.exo.dataformat.dataformatsetting.CharacterDataFmSetting;
import nts.uk.ctx.exio.dom.exo.outputitem.StandardOutputItemRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
/**
 * 文字型データ形式設定
 */
public class CharacterDfsFinder {

	@Inject
	private StandardOutputItemRepository finder;

	public CharacterDfsDto getCharacterDfs(String conditionSettingCode, String outputItemCode) {
		String cid = AppContexts.user().companyId();
		Optional<CharacterDataFmSetting> dataOpt = finder.getCharacterDataFmSettingByID(cid, conditionSettingCode,
				outputItemCode);
		return dataOpt.map(i -> CharacterDfsDto.fromDomain(i)).orElse(null);
	}

}
