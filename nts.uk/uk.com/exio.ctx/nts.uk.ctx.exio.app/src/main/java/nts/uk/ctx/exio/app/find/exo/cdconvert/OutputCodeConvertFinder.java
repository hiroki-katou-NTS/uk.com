package nts.uk.ctx.exio.app.find.exo.cdconvert;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.exio.dom.exo.cdconvert.OutputCodeConvertRepository;
import nts.uk.ctx.exio.dom.exo.commonalgorithm.AcquisitionOutputConvertCode;
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

	public List<OutputCodeConvertDTO> getOutputCodeConvertByCid() {
		return acquisitionOutputConvertCode.getOutputCodeConverts(null)
				.stream().map(OutputCodeConvertDTO::fromDomain)
				.collect(Collectors.toList());
	}
	
	
	public OutputCodeConvertDTO getOutputCodeConvertByConvertCode(String convertCode){
		String companyId = AppContexts.user().companyId();
		Optional<OutputCodeConvertDTO> acceptCode = this.repository.getObjectOutputCodeConvertByCidAndConvertCode(companyId, convertCode)
				.map(item -> OutputCodeConvertDTO.fromDomain(item));
		if (acceptCode.isPresent()) {
			return acceptCode.get();
		} else {
			return null;
		}
	}
}
