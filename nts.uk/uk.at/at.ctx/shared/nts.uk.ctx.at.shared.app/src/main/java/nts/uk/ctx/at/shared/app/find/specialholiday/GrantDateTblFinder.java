package nts.uk.ctx.at.shared.app.find.specialholiday;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHolidayCode;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.ElapseYear;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.ElapseYearRepository;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.GrantDateTbl;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.GrantDateTblRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * Grant Date Tbl Finder
 * @author tanlv
 *
 */
@Stateless
public class GrantDateTblFinder {

	@Inject
	private ElapseYearRepository elapseYearRepository;
	
	@Inject
	private GrantDateTblRepository grantDateTblRepository;

	/**
	 * Find all Grant Date Table data by Special Holiday Code
	 * @param specialHolidayCode
	 * @return
	 */
	public List<GrantDateTblDto> findBySphdCd(int specialHolidayCode) {
		String companyId = AppContexts.user().companyId();

		return this.grantDateTblRepository.findBySphdCd(companyId, specialHolidayCode).stream().map(c -> GrantDateTblDto.fromDomain(c))
				.collect(Collectors.toList());
	}
	
	

	/**
	 * Find Elapse
	 * @param specialHolidayCode
	 * @return
	 */
	public ElapseYearDto findElapseByCd(int specialHolidayCode) {
		String companyId = AppContexts.user().companyId();

		Optional<ElapseYear> elapseYear = elapseYearRepository.findByCode(new CompanyId(companyId), new SpecialHolidayCode(specialHolidayCode));
		
		if (elapseYear.isPresent()) {
			return ElapseYearDto.fromDomain(elapseYear.get());
		}
		
		return new ElapseYearDto();

	}
	
	/**
	 * Find GrantDate
	 * @param specialHolidayCode
	 * @param grantDateCode
	 * @return
	 */
	public GrantDateTblDto findByGrantDateCd(int specialHolidayCode, String grantDateCode) {
		String companyId = AppContexts.user().companyId();
		
		Optional<GrantDateTbl> grantDateTbl = grantDateTblRepository.findByCode(companyId, specialHolidayCode, grantDateCode);
		
		if (grantDateTbl.isPresent()) {
			return GrantDateTblDto.fromDomain(grantDateTbl.get());
		} 
		
		return new GrantDateTblDto();
	}
}
