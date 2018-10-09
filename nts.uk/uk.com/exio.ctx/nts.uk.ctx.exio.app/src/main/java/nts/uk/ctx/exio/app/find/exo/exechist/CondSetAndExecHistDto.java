package nts.uk.ctx.exio.app.find.exo.exechist;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.exio.app.find.exo.condset.CondSetDto;

@NoArgsConstructor
@Getter
@Setter
public class CondSetAndExecHistDto {
	/**
	 * 実行履歴一覧
	 */
	List<ExecHistDto> execHistList;

	/**
	 * 条件設定（リスト）
	 */
	private List<CondSetDto> condSetList;
}
