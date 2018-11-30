package nts.uk.ctx.pr.core.app.command.wageprovision.wagetable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.WageTableContent;

/**
 * 賃金テーブル内容
 */
@Data
@NoArgsConstructor
public class WageTableContentCommand {

	private String historyID;
	/**
	 * 支払金額
	 */
	private List<ElementsCombinationPaymentAmountCommand> payment;

	/**
	 * 資格グループ設定
	 */
	private List<QualificationGroupSettingContentCommand> qualificationGroupSetting;

	public WageTableContent fromCommandToDomain() {
		return new WageTableContent(historyID,
				payment.stream().map(i -> i.fromCommandToDomain()).collect(Collectors.toList()),
				qualificationGroupSetting.isEmpty() ? Optional.empty()
						: Optional.of(qualificationGroupSetting.stream().map(i -> i.fromCommandToDomain())
								.collect(Collectors.toList())));
	}
}
