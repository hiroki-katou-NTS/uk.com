package nts.uk.ctx.at.schedule.app.find.schedule.employeeinfo.rank;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.schedule.app.query.RankDto;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.EmployeeRankRepository;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.RankRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author hieult
 * UKDesign.UniversalK.就業.KSU_スケジュール.KSU001_個人スケジュール修正(職場別).M：ランク分け.メニュー別OCD.起動時
 */
@Stateless
public class GetRankEmpFinder {

	@Inject
	private RankRepository rankRepository;

	@Inject
	private EmployeeRankRepository employeeRankRepository;

	/**
	 * 
	 * @param listEmpId
	 * @return
	 */
	public RankDivisionDto getRankbyPriorityOrder(List<String> listEmpId) {
		// ランクマスタを優先順に並べて取得する	
		String companyId = AppContexts.user().companyId();
		
		List<RankDto> listRankDto = rankRepository.getListRankOrderbyPriority(companyId).stream()
				.map(x -> new RankDto(x.getRankCode(), x.getRankSymbol(), x.getPriority())).collect(Collectors.toList());
		
		// Query ランクマスタを優先順に並べて取得する
		// 優先順でランクリストを取得する(ログイン会社ID)
		List<EmployeeRankDto> listEmpRankDto = employeeRankRepository.getAll(listEmpId).stream()
				.map(x -> new EmployeeRankDto(x.getSID(), x.getEmplRankCode().v())).collect(Collectors.toList());
		RankDivisionDto data = new RankDivisionDto(listRankDto, listEmpRankDto);

		return data;

	}
}
