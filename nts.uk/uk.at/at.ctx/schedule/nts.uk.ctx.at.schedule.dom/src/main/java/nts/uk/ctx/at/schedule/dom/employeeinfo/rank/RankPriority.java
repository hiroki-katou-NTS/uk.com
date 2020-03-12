package nts.uk.ctx.at.schedule.dom.employeeinfo.rank;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.objecttype.DomainAggregate;

/**
 * ランクの優先順
 * 
 * @author sonnh1
 *
 */
@Getter
public class RankPriority implements DomainAggregate {
	/**
	 * 会社ID
	 */
	private final String companyId;
	/**
	 * 優先順リスト
	 */
	private List<RankCode> listRankCd;
	
	/**
	 * 作る
	 * 
	 * @param companyId
	 * @param listRankCd 
	 */
	public RankPriority(String companyId, List<RankCode> listRankCd) {
		super();

		if (listRankCd.size() <= 0) {
			throw new BusinessException("Msg_1622");
		}

		if (listRankCd.stream().distinct().collect(Collectors.toList()).size() < listRankCd.size()) {
			throw new BusinessException("Msg_1621");
		}

		this.companyId = companyId;
		this.listRankCd = listRankCd;
	}

	/**
	 * 末尾に追加する
	 * 
	 * @param rankCd
	 */
	public void insert(RankCode rankCd) {
		if (this.listRankCd.contains(rankCd)) {
			throw new BusinessException("Msg_1621");
		}
		this.listRankCd.add(rankCd);
	}

	/**
	 * 削除する
	 * 
	 * @param rankCd
	 */
	public void delete(RankCode rankCd) {
		if (this.listRankCd.size() <= 1) {
			throw new BusinessException("Msg_1622");
		}
		
		this.listRankCd.remove(rankCd);
	}

	/**
	 * 更新する
	 * 
	 * @param rankCd
	 */
	public void update(List<RankCode> listRankCd) {
		if (listRankCd.size() <= 0) {
			throw new BusinessException("Msg_1622");
		}

		if (listRankCd.stream().distinct().collect(Collectors.toList()).size() < listRankCd.size()) {
			throw new BusinessException("Msg_1621");
		}

		this.listRankCd = listRankCd;

	}
}
