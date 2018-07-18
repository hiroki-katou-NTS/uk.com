package nts.uk.ctx.exio.dom.exo.commonalgorithm;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.exio.dom.exo.cdconvert.OutputCodeConvert;
import nts.uk.ctx.exio.dom.exo.cdconvert.OutputCodeConvertRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class AcquisitionOutputConvertCode {

	@Inject
	private OutputCodeConvertRepository outputCodeConvertRepository;

	/*
	 * アルゴリズム「外部出力取得コード変換」を実行する
	 */
	public List<OutputCodeConvert> getOutputCodeConverts(String convertCode) {
		String cid = AppContexts.user().companyId();
		return outputCodeConvertRepository.getOutputCodeConvertByCid(cid).stream().collect(Collectors.toList());
//		return convertCode == null
//				? outputCodeConvertRepository.getOutputCodeConvertByCid(cid).stream().collect(Collectors.toList())
//				: outputCodeConvertRepository.getOutputCodeConvertByCidAndConvertCode(cid, convertCode).stream()
//						.collect(Collectors.toList());
	}
}
