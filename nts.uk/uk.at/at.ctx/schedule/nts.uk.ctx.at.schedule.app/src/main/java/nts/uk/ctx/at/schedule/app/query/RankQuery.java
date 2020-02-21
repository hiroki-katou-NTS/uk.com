package nts.uk.ctx.at.schedule.app.query;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.Rank;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.RankCode;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.RankPriority;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.RankRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author sonnh1
 *
 */
@Stateless
public class RankQuery {

	@Inject
	private RankRepository rankRepo;

	/**
	 * ランクの一覧を取得する
	 * 
	 * @return
	 */
	public List<RankDto> getListRank() {
		return this.rankRepo.getListRank(AppContexts.user().companyId()).stream()
				.map(x -> new RankDto(x.getRankCode().v(), x.getRankSymbol().v())).collect(Collectors.toList());
	}

	/**
	 * ランクの明細を表示する
	 * 
	 * @param rankCd
	 * @return
	 */
	public RankDto getRank(String rankCd) {
		Rank rank = this.rankRepo.getRank(AppContexts.user().companyId(), new RankCode(rankCd));

		return new RankDto(rank.getRankCode().v(), rank.getRankSymbol().v());
	}

	/**
	 * ランクリストの並び順をを取得する
	 * 
	 * @return
	 */
	public RankExport getRankAndRiority() {
		List<RankDto> lstRankDto = this.getListRank();
		List<String> lstRankCodeSorted = new ArrayList<>();

		if (!lstRankDto.isEmpty()) {
			Optional<RankPriority> optRankPriority = this.rankRepo.getRankPriority(AppContexts.user().companyId());

			if (!optRankPriority.isPresent()) {
				throw new RuntimeException("DATA OF RANK IS ERRORED!");
			}

			lstRankCodeSorted = optRankPriority.get().getListRankCd().stream().map(x -> x.v())
					.collect(Collectors.toList());
		}

		return new RankExport(lstRankDto, lstRankCodeSorted);
	}
}
