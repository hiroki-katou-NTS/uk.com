package nts.uk.ctx.exio.app.find.exo.cdconvert;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.exio.dom.exo.commonalgorithm.AcquisitionOutputConvertCode;
import nts.uk.shr.com.context.AppContexts;

/*
 * 出力コード変換
 */
@Stateless
public class OutputCodeConvertFinder {

	@Inject
	private AcquisitionOutputConvertCode acquisitionOutputConvertCode;

	public List<OutputCodeConvertDTO> getOutputCodeConvertByCid() {
		return acquisitionOutputConvertCode.getOutputCodeConverts(null)
				.stream().map(OutputCodeConvertDTO::fromDomain)
				.collect(Collectors.toList());
	}
}
