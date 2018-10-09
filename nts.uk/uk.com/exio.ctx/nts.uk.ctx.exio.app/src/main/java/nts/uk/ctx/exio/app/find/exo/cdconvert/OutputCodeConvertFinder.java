package nts.uk.ctx.exio.app.find.exo.cdconvert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.uk.ctx.exio.dom.exo.cdconvert.CdConvertDetail;
import nts.uk.ctx.exio.dom.exo.cdconvert.OutputCodeConvert;
import nts.uk.ctx.exio.dom.exo.cdconvert.OutputCodeConvertRepository;
import nts.uk.ctx.exio.dom.exo.commonalgorithm.AcquisitionOutputConvertCode;
import nts.uk.ctx.exio.dom.exo.dataformat.dataformatsetting.CharacterDataFmSetting;
import nts.uk.ctx.exio.dom.exo.dataformat.init.ChacDataFmSet;
import nts.uk.ctx.exio.dom.exo.dataformat.init.DataFormatSettingRepository;
import nts.uk.ctx.exio.dom.exo.outputitem.StandardOutputItemRepository;
import nts.uk.shr.com.context.AppContexts;

/*
 * 出力コード変換
 */
@Stateless
public class OutputCodeConvertFinder {

	@Inject
	private AcquisitionOutputConvertCode acquisitionOutputConvertCode;
	
	@Inject
	private OutputCodeConvertRepository repository;
	
	@Inject
	private StandardOutputItemRepository standardOutputItemRepository;
	
	@Inject
	private DataFormatSettingRepository dataFormatSettingRepository;

	public List<OutputCodeConvertDTO> getOutputCodeConvertByCid() {
		return acquisitionOutputConvertCode.getOutputCodeConverts(null)
				.stream().map(o -> OutputCodeConvertDTO.fromDomain(o))
				.collect(Collectors.toList());
	}
	
	
	public OutputCodeConvertDTO getOutputCodeConvertByConvertCode(String convertCode){
		String companyId = AppContexts.user().companyId();
		Optional<OutputCodeConvertDTO> acceptCode = this.repository.getOutputCodeConvertById(companyId, convertCode)
				.map(item -> OutputCodeConvertDTO.fromDomain(item));
		if (acceptCode.isPresent()) {
			return acceptCode.get();
		} else {
			return null;
		}
	}

	public void checkBeforeRemove(String convertCode) {
		String companyId = AppContexts.user().companyId();
		// ドメインモデル「出力項目(定型).データ形式設定_文字型」を取得する
		List<CharacterDataFmSetting> charFormats = standardOutputItemRepository
				.getCharacterDataFmSettingByByConvertCd(companyId, convertCode);
		if (!charFormats.isEmpty()) {
			throw new BusinessException("Msg_659");
		}
		// ドメインモデル「出力項目(ユーザ).データ形式設定_文字型」を取得する
		// TODO

		// ドメインモデル「データ形式初期値.データ形式設定_文字型」を取得する
		List<ChacDataFmSet> charFormatInit = dataFormatSettingRepository.getChacDataFmSetByConvertCd(companyId,
				convertCode);
		if (!charFormatInit.isEmpty()) {
			throw new BusinessException("Msg_659");
		}
	}
}
