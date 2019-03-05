package nts.uk.ctx.at.shared.app.find.workrule.closure.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.ctx.at.shared.dom.workrule.closure.CompanyId;
import nts.uk.ctx.at.shared.dom.workrule.closure.UseClassification;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClosureDto {
	/** The company id. */
	// 会社ID
	private CompanyId companyId;

	/** The closure id. */
	// 締めＩＤ
	private ClosureId closureId;

	/** The use classification. */
	// 使用区分
	private UseClassification useClassification;

	/** The month. */
	// 当月
	private int closureMonth;

	/** The closure histories. */
	// 締め変更履歴

	private List<ClosureHistoryHaveDateDto> closureHistories;

	public static ClosureDto fromDomain(Closure domain) {

		List<ClosureHistoryHaveDateDto> closureHis = new ArrayList<ClosureHistoryHaveDateDto>();
		if (!CollectionUtil.isEmpty(domain.getClosureHistories())) {
			domain.getClosureHistories().forEach(x -> {
				closureHis.add(ClosureHistoryHaveDateDto.fromDomain(x));
			});
		}

		return new ClosureDto(domain.getCompanyId(), domain.getClosureId(), domain.getUseClassification(),
				domain.getClosureMonth().getProcessingYm().v(), closureHis);

	}
}
