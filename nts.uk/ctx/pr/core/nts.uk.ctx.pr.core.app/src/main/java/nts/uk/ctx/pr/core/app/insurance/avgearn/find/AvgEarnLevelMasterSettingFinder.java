package nts.uk.ctx.pr.core.app.insurance.avgearn.find;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.core.dom.insurance.avgearn.AvgEarnLevelMasterSettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class AvgEarnLevelMasterSettingFinder.
 */
@Stateless
public class AvgEarnLevelMasterSettingFinder {

	/** The repository. */
	@Inject
	private AvgEarnLevelMasterSettingRepository repository;

	/**
	 * Find all.
	 *
	 * @return the list
	 */
	public List<AvgEarnLevelMasterSettingDto> findAll() {
		String companyCode = AppContexts.user().companyCode();
		return repository.findAll(companyCode).stream().map(AvgEarnLevelMasterSettingDto::fromDomain)
				.collect(Collectors.toList());
	}
}
