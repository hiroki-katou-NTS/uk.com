package nts.uk.ctx.exio.dom.exo.exechist;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.exio.dom.exo.category.ExOutCtg;

@AllArgsConstructor
@Getter
@Setter
public class CondSetAndCtg {
	/**
	 * 外部出力カテゴリ（リスト
	 */
	private List<ExOutCtg> exOutCtgList;

	/**
	 * 条件設定（リスト）
	 */
	private List<CondSet> condSetList;
}
