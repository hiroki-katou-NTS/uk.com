package nts.uk.ctx.at.schedule.app.find.shift.rank.ranksetting;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.schedule.dom.shift.rank.ranksetting.RankSetRepository;

/**
 * 
 * @author Trung Tran
 *
 */
@Stateless
public class RankSetFinder {
	@Inject
	private RankSetRepository rankSetRepository;

	/**
	 * get all rank setting
	 * @param employeeIds
	 * @return list rank setting
	 */

	public List<RankSetDto> findAllRankSet(List<String> employeeIds) {
		if (!CollectionUtil.isEmpty(employeeIds)) {
			return rankSetRepository.getListRankSet(employeeIds).stream().map((x) -> RankSetDto.fromDomain(x))
					.collect(Collectors.toList());
		}
		return null;
	}
}
