package nts.uk.ctx.at.shared.app.find.specialholiday;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

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
	private GrantDateTblRepository repo;
	
	/**
	 * Find all Grant Date Table data by Special Holiday Code
	 * @param specialHolidayCode
	 * @return
	 */
	public List<GrantDateTblDto> findBySphdCd(int specialHolidayCode) {
		String companyId = AppContexts.user().companyId();
		
		return this.repo.findBySphdCd(companyId, specialHolidayCode).stream().map(c -> GrantDateTblDto.fromDomain(c))
				.collect(Collectors.toList());
	}
	
	/**
	 * Find Elapse
	 * @param specialHolidayCode
	 * @param grantDateCode
	 * @return
	 */
	public List<ElapseYearDto> findByGrantDateCd(int specialHolidayCode, String grantDateCode) {
		String companyId = AppContexts.user().companyId();
		
		return this.repo.findElapseByGrantDateCd(companyId, specialHolidayCode, grantDateCode).stream().map(c -> ElapseYearDto.fromDomain(c))
				.collect(Collectors.toList());
	}
}
