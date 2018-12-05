package nts.uk.ctx.pr.core.app.command.wageprovision.wagetable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.ElementsCombinationPaymentAmount;
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
	private List<ElementItemCommand> oneDimensionPayment;

	private List<TwoDmsElementItemCommand> twoDimensionPayment;

	/**
	 * 資格グループ設定
	 */
	private List<QualificationGroupSettingContentCommand> qualificationGroupSetting;

	public WageTableContent fromCommandToDomain() {
		List<ElementsCombinationPaymentAmount> listPayments = new ArrayList<>();
		if (oneDimensionPayment != null) {
			for (ElementItemCommand c : oneDimensionPayment) {
				ElementsCombinationPaymentAmount payment = new ElementsCombinationPaymentAmount(
						IdentifierUtil.randomUniqueId(), c.getPaymentAmount(), c.getMasterCode(), c.getFrameNumber(),
						c.getFrameLowerLimit(), c.getFrameUpperLimit(), null, null, null, null, null, null, null, null);
				listPayments.add(payment);
			}
		} else if (twoDimensionPayment != null) {
			for (TwoDmsElementItemCommand r : twoDimensionPayment) {
				for (ElementItemCommand c : r.getListSecondDms()) {
					ElementsCombinationPaymentAmount payment = new ElementsCombinationPaymentAmount(
							IdentifierUtil.randomUniqueId(), c.getPaymentAmount(), r.getMasterCode(),
							r.getFrameNumber(), r.getFrameLowerLimit(), r.getFrameUpperLimit(), c.getMasterCode(),
							c.getFrameNumber(), c.getFrameLowerLimit(), c.getFrameUpperLimit(), null, null, null, null);
					listPayments.add(payment);
				}
			}
		}
		return new WageTableContent(historyID, listPayments,
				qualificationGroupSetting.isEmpty() ? Optional.empty()
						: Optional.of(qualificationGroupSetting.stream().map(i -> i.fromCommandToDomain())
								.collect(Collectors.toList())));
	}
}
