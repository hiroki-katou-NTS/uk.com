package nts.uk.ctx.at.schedule.app.find.schedule.employeeinfo.rank;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.uk.ctx.at.schedule.app.query.RankDto;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.EmployeeRankRepository;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.RankRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author hieult
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.社員情報.ランク.App.ランクの明細を取得する
 *
 */
@Stateless
public class GetRankEmpFinder {

	@Inject
	private RankRepository rankRepository;

	@Inject
	private EmployeeRankRepository employeeRankRepository;

	// ランクマスタを優先順に並べて取得する
	public RankDivisionDto getRankbyPriorityOrder(List<String> listEmpId) {

		String companyId = AppContexts.user().companyId();
		
		List<RankDto> listRankDto = rankRepository.getListRank(companyId).stream()
				.map(x -> new RankDto(x.getRankCode().v(), x.getRankSymbol().v())).collect(Collectors.toList());
		if(listRankDto.isEmpty()){
			throw new BusinessException("Msg_1643");
		}
		// Query ランクマスタを優先順に並べて取得する
		// 優先順でランクリストを取得する(ログイン会社ID)
		List<EmployeeRankDto> listEmpRankDto = employeeRankRepository.getAll(listEmpId).stream()
				.map(x -> new EmployeeRankDto(x.getSID(), x.getEmplRankCode().v())).collect(Collectors.toList());
		RankDivisionDto data = new RankDivisionDto(listRankDto, listEmpRankDto);

		return data;

	}
}
