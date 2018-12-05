package nts.uk.ctx.pr.core.app.find.wageprovision.wagetable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.ElementItem;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.ElementsCombinationPaymentAmount;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.WageTableContent;

@NoArgsConstructor
@Data
public class WageTableContentDto {

	private String historyID;

	private List<ElementItemDto> list1dElements;

	private List<TwoDmsElementItemDto> list2dElements;

	private List<ElementItemDto> list3dElements;

	public static WageTableContentDto fromDomainToDto(WageTableContent domain) {
		WageTableContentDto dto = new WageTableContentDto();
		dto.historyID = domain.getHistoryID();
		if (!domain.getPayments().isEmpty()) {
			if (!domain.getPayments().get(0).getElementAttribute().getSecondElementItem().isPresent()
					&& !domain.getPayments().get(0).getElementAttribute().getThirdElementItem().isPresent()) {
				dto.list1dElements = new ArrayList<>();
				for (ElementsCombinationPaymentAmount payment : domain.getPayments()) {
					if (payment.getElementAttribute().getFirstElementItem().getMasterElementItem().isPresent()) {
						String masterName = null;
						ElementItemDto item = new ElementItemDto(
								payment.getElementAttribute().getFirstElementItem().getMasterElementItem().get()
										.getMasterCode().v(),
								masterName, null, null, null, payment.getWageTablePaymentAmount().v());
						dto.list1dElements.add(item);
					} else if (payment.getElementAttribute().getFirstElementItem().getNumericElementItem()
							.isPresent()) {
						ElementItemDto item = new ElementItemDto(null, null,
								payment.getElementAttribute().getFirstElementItem().getNumericElementItem().get()
										.getFrameNumber().v(),
								payment.getElementAttribute().getFirstElementItem().getNumericElementItem().get()
										.getFrameLowerLimit().v(),
								payment.getElementAttribute().getFirstElementItem().getNumericElementItem().get()
										.getFrameUpperLimit().v(),
								payment.getWageTablePaymentAmount().v());
						dto.list1dElements.add(item);
					}
				}
				dto.list1dElements.sort((c1, c2) -> c1.getFrameLowerLimit().compareTo(c2.getFrameLowerLimit()));
			} else if (domain.getPayments().get(0).getElementAttribute().getSecondElementItem().isPresent()
					&& !domain.getPayments().get(0).getElementAttribute().getThirdElementItem().isPresent()) {
				dto.list2dElements = new ArrayList<>();
				Map<ElementItem, List<ElementsCombinationPaymentAmount>> mapPayments = domain.getPayments().stream()
						.collect(Collectors.groupingBy(i -> i.getElementAttribute().getFirstElementItem()));
				mapPayments.entrySet().forEach(p -> {
					List<ElementItemDto> list2ndDmsElements = new ArrayList<>();
					for (ElementsCombinationPaymentAmount payment : p.getValue()) {
						if (payment.getElementAttribute().getSecondElementItem().get().getMasterElementItem()
								.isPresent()) {
							String masterName = null;
							ElementItemDto item = new ElementItemDto(
									payment.getElementAttribute().getSecondElementItem().get().getMasterElementItem()
											.get().getMasterCode().v(),
									masterName, null, null, null, payment.getWageTablePaymentAmount().v());
							list2ndDmsElements.add(item);
						} else if (payment.getElementAttribute().getSecondElementItem().get().getNumericElementItem()
								.isPresent()) {
							ElementItemDto item = new ElementItemDto(null, null,
									payment.getElementAttribute().getSecondElementItem().get().getNumericElementItem()
											.get().getFrameNumber().v(),
									payment.getElementAttribute().getSecondElementItem().get().getNumericElementItem()
											.get().getFrameLowerLimit().v(),
									payment.getElementAttribute().getSecondElementItem().get().getNumericElementItem()
											.get().getFrameUpperLimit().v(),
									payment.getWageTablePaymentAmount().v());
							list2ndDmsElements.add(item);
						}
					}
					list2ndDmsElements.sort((c1, c2) -> c1.getFrameLowerLimit().compareTo(c2.getFrameLowerLimit()));
					if (p.getKey().getMasterElementItem().isPresent()) {
						String masterName = null;
						dto.list2dElements.add(
								new TwoDmsElementItemDto(p.getKey().getMasterElementItem().get().getMasterCode().v(),
										masterName, null, null, null, list2ndDmsElements));
					} else if (p.getKey().getNumericElementItem().isPresent()) {
						dto.list2dElements.add(new TwoDmsElementItemDto(null, null,
								p.getKey().getNumericElementItem().get().getFrameNumber().v(),
								p.getKey().getNumericElementItem().get().getFrameLowerLimit().v(),
								p.getKey().getNumericElementItem().get().getFrameUpperLimit().v(), list2ndDmsElements));
					}
				});
				dto.list2dElements.sort((c1, c2) -> c1.getFrameLowerLimit().compareTo(c2.getFrameLowerLimit()));
			}
		}
		return dto;
	}

}
