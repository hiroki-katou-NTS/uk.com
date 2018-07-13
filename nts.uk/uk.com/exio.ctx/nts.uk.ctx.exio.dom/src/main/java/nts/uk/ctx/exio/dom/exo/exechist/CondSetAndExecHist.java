package nts.uk.ctx.exio.dom.exo.exechist;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.exio.dom.exo.condset.CondSet;

@AllArgsConstructor
@Getter
@Setter
public class CondSetAndExecHist {
	/**
	 * 実行履歴一覧
	 */
	List<ExecHist> execHistList;

	/**
	 * 条件設定（リスト）
	 */
	private List<CondSet> condSetList;
}
