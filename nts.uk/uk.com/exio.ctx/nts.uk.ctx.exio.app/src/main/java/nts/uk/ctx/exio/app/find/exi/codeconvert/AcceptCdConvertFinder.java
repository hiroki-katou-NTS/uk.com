package nts.uk.ctx.exio.app.find.exi.codeconvert;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.exio.dom.exi.codeconvert.AcceptCdConvertRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
/**
 * 受入コード変換
 */
public class AcceptCdConvertFinder {

	@Inject
	private AcceptCdConvertRepository finder;

	/**
	 * Get accept code by companyId
	 * 
	 * @return
	 */
	public List<AcceptCdConvertDto> getAcceptCdConvertByCompanyId() {
		String companyId = AppContexts.user().companyId();
		return finder.getAcceptCdConvertByCompanyId(companyId).stream().map(item -> AcceptCdConvertDto.fromDomain(item))
				.collect(Collectors.toList());
	}

	/**
	 * Get Accept code convert information
	 * 
	 * @param convertCd
	 * @return AcceptCdConvertDto
	 */
	public AcceptCdConvertDto getAcceptCdConvertById(String convertCd) {
		String companyId = AppContexts.user().companyId();
		Optional<AcceptCdConvertDto> acceptCode = this.finder.getAcceptCdConvertById(companyId, convertCd)
				.map(item -> AcceptCdConvertDto.fromDomain(item));
		if (acceptCode.isPresent()) {
			return acceptCode.get();
		} else {
			return null;
		}
	}

}
