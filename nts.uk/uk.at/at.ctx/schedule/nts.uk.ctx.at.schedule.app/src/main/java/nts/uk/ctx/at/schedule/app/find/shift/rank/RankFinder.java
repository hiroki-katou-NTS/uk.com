package nts.uk.ctx.at.schedule.app.find.shift.rank;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.schedule.dom.shift.rank.RankRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author Trung Tran
 *
 */
@Stateless
public class RankFinder {

	@Inject
	private RankRepository rankRepository;
	/**
	 * get all rank 
	 * @return list rankDto
	 */
	public List<RankDto> getAllListRank() {
		String companyId = AppContexts.user().companyId();
		return rankRepository.getListRank(companyId).stream().map((x) -> RankDto.fromDomain(x))
				.collect(Collectors.toList());
	}
}
