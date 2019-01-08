package nts.uk.ctx.exio.dom.exo.exechist;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.exio.dom.exo.category.ExOutCtg;
import nts.uk.ctx.exio.dom.exo.condset.CondSet;

@NoArgsConstructor
@Getter
public class ExecHistResult {
	/**
	 * 実行期間.開始日付
	 */
	@Setter
	private GeneralDate startDate;

	/**
	 * 実行期間.終了日付
	 */
	@Setter
	private GeneralDate endDate;

	/**
	 * 外部出力カテゴリ（リスト
	 */
	private List<Integer> exOutCtgIdList;

	/**
	 * 条件設定（リスト）
	 */
	@Setter
	private List<CondSet> condSetList;

	/**
	 * 実行履歴一覧
	 */
	@Setter
	List<ExecHist> execHistList;

	public void setExOutCtgIdList(List<ExOutCtg> exOutCtgList) {
		this.exOutCtgIdList = exOutCtgList.stream().map(i -> i.getCategoryId().v()).collect(Collectors.toList());
	}
}
