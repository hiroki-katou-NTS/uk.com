package nts.uk.ctx.pr.core.app.find.wageprovision.wagetable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.ElementItem;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.ElementsCombinationPaymentAmount;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.MasterNumericAtr;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.WageTable;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.WageTableContent;
import nts.uk.shr.com.context.AppContexts;

@NoArgsConstructor
@Data
public class WageTableContentDto {

	private String historyID;

	private List<ElementItemDto> list1dElements;

	private List<TwoDmsElementItemDto> list2dElements;

	private List<ElementItemDto> list3dElements;

	public static WageTableContentDto fromDomainToDto(WageTableContent domain, Optional<WageTable> domainOtp,
			WageTableContentCreater wageContentCreater) {
		WageTableContentDto dto = new WageTableContentDto();
		Comparator<ElementItemDto> comparator = Comparator
				.comparing(ElementItemDto::getMasterCode, Comparator.nullsLast(Comparator.naturalOrder()))
				.thenComparing(ElementItemDto::getFrameNumber, Comparator.nullsLast(Comparator.naturalOrder()));
		dto.historyID = domain.getHistoryID();
		if (!domain.getPayments().isEmpty()) {
			// one dimension
			if (!domain.getPayments().get(0).getElementAttribute().getSecondElementItem().isPresent()
					&& !domain.getPayments().get(0).getElementAttribute().getThirdElementItem().isPresent()) {
				dto.list1dElements = new ArrayList<>();
				Map<String, String> mapMaster = new HashMap<>();
				if (domainOtp.isPresent() && domainOtp.get().getElementInformation().getOneDimensionalElement()
						.getMasterNumericAtr().get() == MasterNumericAtr.MASTER_ITEM) {
					mapMaster = wageContentCreater.getMasterItems(domainOtp.get().getElementInformation()
							.getOneDimensionalElement().getFixedElement().get().value, AppContexts.user().companyId());
				}
				for (ElementsCombinationPaymentAmount payment : domain.getPayments()) {
					if (payment.getElementAttribute().getFirstElementItem().getMasterElementItem().isPresent()) {
						String masterCode = payment.getElementAttribute().getFirstElementItem().getMasterElementItem()
								.get().getMasterCode().v();
						String masterName = mapMaster.get(masterCode);
						ElementItemDto item = new ElementItemDto(masterCode,
								masterName == null ? masterCode : masterName, null, null, null,
								payment.getWageTablePaymentAmount().v());
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
				Collections.sort(dto.list1dElements, comparator);
			}
			// two dimensions
			else if (domain.getPayments().get(0).getElementAttribute().getSecondElementItem().isPresent()
					&& !domain.getPayments().get(0).getElementAttribute().getThirdElementItem().isPresent()) {
				dto.list2dElements = new ArrayList<>();
				Map<ElementItem, List<ElementsCombinationPaymentAmount>> mapPayments = domain.getPayments().stream()
						.collect(Collectors.groupingBy(i -> i.getElementAttribute().getFirstElementItem()));
				Map<String, String> mapMaster1 = new HashMap<>();
				if (domainOtp.isPresent() && domainOtp.get().getElementInformation().getOneDimensionalElement()
						.getMasterNumericAtr().get() == MasterNumericAtr.MASTER_ITEM) {
					mapMaster1 = wageContentCreater.getMasterItems(domainOtp.get().getElementInformation()
							.getOneDimensionalElement().getFixedElement().get().value, AppContexts.user().companyId());
				}
				for (ElementItem key : mapPayments.keySet()) {
					List<ElementsCombinationPaymentAmount> value = mapPayments.get(key);
					List<ElementItemDto> list2ndDmsElements = new ArrayList<>();
					Map<String, String> mapMaster2 = new HashMap<>();
					if (domainOtp.isPresent() && domainOtp.get().getElementInformation().getTwoDimensionalElement()
							.get().getMasterNumericAtr().get() == MasterNumericAtr.MASTER_ITEM) {
						mapMaster2 = wageContentCreater.getMasterItems(domainOtp.get().getElementInformation()
								.getTwoDimensionalElement().get().getFixedElement().get().value,
								AppContexts.user().companyId());
					}
					for (ElementsCombinationPaymentAmount payment : value) {
						if (payment.getElementAttribute().getSecondElementItem().get().getMasterElementItem()
								.isPresent()) {
							String masterCode = payment.getElementAttribute().getSecondElementItem().get()
									.getMasterElementItem().get().getMasterCode().v();
							String masterName = mapMaster2.get(masterCode);
							ElementItemDto item = new ElementItemDto(masterCode,
									masterName == null ? masterCode : masterName, null, null, null,
									payment.getWageTablePaymentAmount().v());
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
					Collections.sort(list2ndDmsElements, comparator);
					if (key.getMasterElementItem().isPresent()) {
						String masterCode = key.getMasterElementItem().get().getMasterCode().v();
						String masterName = mapMaster1.get(masterCode);
						dto.list2dElements.add(new TwoDmsElementItemDto(masterCode,
								masterName == null ? masterCode : masterName, null, null, null, list2ndDmsElements));
					} else if (key.getNumericElementItem().isPresent()) {
						dto.list2dElements.add(new TwoDmsElementItemDto(null, null,
								key.getNumericElementItem().get().getFrameNumber().v(),
								key.getNumericElementItem().get().getFrameLowerLimit().v(),
								key.getNumericElementItem().get().getFrameUpperLimit().v(), list2ndDmsElements));
					}
				}
				Comparator<TwoDmsElementItemDto> comparators = Comparator
						.comparing(TwoDmsElementItemDto::getMasterCode, Comparator.nullsLast(Comparator.naturalOrder()))
						.thenComparing(TwoDmsElementItemDto::getFrameNumber,
								Comparator.nullsLast(Comparator.naturalOrder()));
				Collections.sort(dto.list2dElements, comparators);
			}
		}
		return dto;
	}

}
