package nts.uk.ctx.pr.core.app.command.wageprovision.wagetable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.pr.core.app.find.wageprovision.wagetable.WageTableQualificationDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.wagetable.WageTableQualificationInfoDto;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.ElementsCombinationPaymentAmount;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.QualificationGroupSettingContent;
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

	private List<ThreeDmsElementItemCommand> threeDimensionPayment;
	
	private List<ThreeDmsElementItemCommand> workLevelPayment;
	
	private boolean brandNew;

	/**
	 * 資格グループ設定
	 */
	private List<WageTableQualificationDto> wageTableQualifications;

	public WageTableContent fromCommandToDomain() {
		List<ElementsCombinationPaymentAmount> listPayments = new ArrayList<>();
		List<QualificationGroupSettingContent> qualificationGroupSettings = new ArrayList<>();
		if (oneDimensionPayment != null) {
			for (ElementItemCommand c : oneDimensionPayment) {
				ElementsCombinationPaymentAmount payment = new ElementsCombinationPaymentAmount(
						IdentifierUtil.randomUniqueId(), c.getPaymentAmount() == null ? 0 : c.getPaymentAmount(),
						c.getMasterCode(), c.getFrameNumber(), c.getFrameLowerLimit(), c.getFrameUpperLimit(), null,
						null, null, null, null, null, null, null);
				listPayments.add(payment);
			}
		} else if (twoDimensionPayment != null) {
			for (TwoDmsElementItemCommand r : twoDimensionPayment) {
				for (ElementItemCommand c : r.getListSecondDms()) {
					ElementsCombinationPaymentAmount payment = new ElementsCombinationPaymentAmount(
							IdentifierUtil.randomUniqueId(), c.getPaymentAmount() == null ? 0 : c.getPaymentAmount(),
							r.getMasterCode(), r.getFrameNumber(), r.getFrameLowerLimit(), r.getFrameUpperLimit(),
							c.getMasterCode(), c.getFrameNumber(), c.getFrameLowerLimit(), c.getFrameUpperLimit(), null,
							null, null, null);
					listPayments.add(payment);
				}
			}
		} else if (threeDimensionPayment != null) {
			for (ThreeDmsElementItemCommand h : threeDimensionPayment) {
				for (TwoDmsElementItemCommand r : h.getListFirstDms()) {
					for (ElementItemCommand c : r.getListSecondDms()) {
						ElementsCombinationPaymentAmount payment = new ElementsCombinationPaymentAmount(
								IdentifierUtil.randomUniqueId(),
								c.getPaymentAmount() == null ? 0 : c.getPaymentAmount(), r.getMasterCode(),
								r.getFrameNumber(), r.getFrameLowerLimit(), r.getFrameUpperLimit(), c.getMasterCode(),
								c.getFrameNumber(), c.getFrameLowerLimit(), c.getFrameUpperLimit(), h.getMasterCode(),
								h.getFrameNumber(), h.getFrameLowerLimit(), h.getFrameUpperLimit());
						listPayments.add(payment);
					}
				}
			}
		} else if (wageTableQualifications != null) {
			wageTableQualifications.forEach(wageTableQualification -> {
				wageTableQualification.getEligibleQualificationCode()
						.forEach(eligible -> listPayments
								.add(new ElementsCombinationPaymentAmount(IdentifierUtil.randomUniqueId(),
										eligible.getWageTablePaymentAmount(), eligible.getQualificationCode(), null,
										null, null, null, null, null, null, null, null, null, null)));
				qualificationGroupSettings.add(new QualificationGroupSettingContent(
						wageTableQualification.getQualificationGroupCode(),
						wageTableQualification.getQualificationGroupName(), wageTableQualification.getPaymentMethod(),
						wageTableQualification.getEligibleQualificationCode().stream()
								.map(WageTableQualificationInfoDto::getQualificationCode)
								.collect(Collectors.toList())));
			});
		} else if (workLevelPayment != null) {
			for (ThreeDmsElementItemCommand h : workLevelPayment) {
				for (TwoDmsElementItemCommand r : h.getListFirstDms()) {
					for (ElementItemCommand c : r.getListSecondDms()) {
						ElementsCombinationPaymentAmount payment = new ElementsCombinationPaymentAmount(
								IdentifierUtil.randomUniqueId(),
								c.getPaymentAmount() == null ? 0 : c.getPaymentAmount(), r.getMasterCode(),
								r.getFrameNumber(), r.getFrameLowerLimit(), r.getFrameUpperLimit(), c.getMasterCode(),
								c.getFrameNumber(), c.getFrameLowerLimit(), c.getFrameUpperLimit(), h.getMasterCode(),
								h.getFrameNumber(), h.getFrameLowerLimit(), h.getFrameUpperLimit());
						listPayments.add(payment);
					}
				}
			}
		}

		return new WageTableContent(historyID, listPayments,
				qualificationGroupSettings.isEmpty() ? Optional.empty() : Optional.of(qualificationGroupSettings));
	}
}