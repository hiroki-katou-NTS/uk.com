package nts.uk.ctx.exio.app.find.exo.exechist;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.exio.app.find.exo.condset.CondSetDto;
import nts.uk.ctx.exio.dom.exo.exechist.ExecHistResult;

@NoArgsConstructor
@Getter
@Setter
public class ExecHistResultDto {
	/**
	 * 実行期間.開始日付
	 */
	private GeneralDate startDate;

	/**
	 * 実行期間.終了日付
	 */
	private GeneralDate endDate;

	/**
	 * 外部出力カテゴリ（リスト
	 */
	private List<Integer> exOutCtgIdList;

	/**
	 * 条件設定（リスト）
	 */
	private List<CondSetDto> condSetList;

	/**
	 * 実行履歴一覧
	 */
	List<ExecHistDto> execHistList;

	public static ExecHistResultDto fromDomain(ExecHistResult execHistResult) {
		ExecHistResultDto result = new ExecHistResultDto();
		result.setStartDate(execHistResult.getStartDate());
		result.setEndDate(execHistResult.getEndDate());
		result.setExOutCtgIdList(execHistResult.getExOutCtgIdList());
		result.setCondSetList(execHistResult.getCondSetList().stream().map(domain -> CondSetDto.fromDomain(domain))
				.collect(Collectors.toList()));
		result.setExecHistList(execHistResult.getExecHistList().stream().map(domain -> ExecHistDto.fromDomain(domain))
				.collect(Collectors.toList()));
		return result;
	}
}
