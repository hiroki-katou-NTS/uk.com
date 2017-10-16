package nts.uk.ctx.at.schedule.app.find.shift.rank.ranksetting;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.schedule.dom.shift.rank.ranksetting.RankSetRepository;

/**
 * 
 * @author Trung Tran
 *
 */
@Stateless
public class RankSetFinder {
	@Inject
	RankSetRepository rankSetRepository;

	public List<RankSetDto> findAllRankSet(List<String> employeeIds) {
		if (employeeIds.size() > 0) {
			return rankSetRepository.getListRankSet(employeeIds).stream().map((x) -> RankSetDto.fromDomain(x))
					.collect(Collectors.toList());
		}
		return null;
	}
}
