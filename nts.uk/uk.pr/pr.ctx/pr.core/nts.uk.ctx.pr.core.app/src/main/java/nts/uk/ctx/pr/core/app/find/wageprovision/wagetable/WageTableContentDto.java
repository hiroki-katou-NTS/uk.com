package nts.uk.ctx.pr.core.app.find.wageprovision.wagetable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.ElementItem;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.ElementsCombinationPaymentAmount;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.MasterNumericAtr;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.WageTable;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.WageTableContent;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;

@NoArgsConstructor
@Data
public class WageTableContentDto {

	private String historyID;

	private List<ElementItemDto> list1dElements;

	private List<TwoDmsElementItemDto> list2dElements;

	private List<ElementItemDto> list3dElements;

	private List<WageTableRowDto> listData;

	private List<WageTableHeaderDto> listHeader;

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
				dto.listData = new ArrayList<>();
				Map<ElementItem, List<ElementsCombinationPaymentAmount>> mapPayments = domain.getPayments().stream()
						.collect(Collectors.groupingBy(i -> i.getElementAttribute().getFirstElementItem()));
				Map<String, String> mapMaster1 = new HashMap<>();
				if (domainOtp.isPresent() && domainOtp.get().getElementInformation().getOneDimensionalElement()
						.getMasterNumericAtr().get() == MasterNumericAtr.MASTER_ITEM) {
					mapMaster1 = wageContentCreater.getMasterItems(domainOtp.get().getElementInformation()
							.getOneDimensionalElement().getFixedElement().get().value, AppContexts.user().companyId());
				}
				for (ElementItem key : mapPayments.keySet()) {
					dto.listHeader = new ArrayList<>();
					List<ElementsCombinationPaymentAmount> value = mapPayments.get(key);
					List<ElementItemDto> list2ndDmsElements = new ArrayList<>();
					Map<String, String> mapMaster2 = new HashMap<>();
					if (domainOtp.isPresent() && domainOtp.get().getElementInformation().getTwoDimensionalElement()
							.get().getMasterNumericAtr().get() == MasterNumericAtr.MASTER_ITEM) {
						mapMaster2 = wageContentCreater.getMasterItems(domainOtp.get().getElementInformation()
								.getTwoDimensionalElement().get().getFixedElement().get().value,
								AppContexts.user().companyId());
					}
					Set<WageTableCellDto> cellData = new HashSet<>();
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
							cellData.add(new WageTableCellDto("2nd-dms-master-" + masterCode,
									payment.getWageTablePaymentAmount().v()));
							dto.listHeader.add(new WageTableHeaderDto(masterName, "2nd-dms-master-" + masterCode, "number",
									"150px", false, null));
						} else if (payment.getElementAttribute().getSecondElementItem().get().getNumericElementItem()
								.isPresent()) {
							int frameNumber = payment.getElementAttribute().getSecondElementItem().get()
									.getNumericElementItem().get().getFrameNumber().v();
							int frameLower = payment.getElementAttribute().getSecondElementItem().get()
									.getNumericElementItem().get().getFrameLowerLimit().v();
							int frameUpper = payment.getElementAttribute().getSecondElementItem().get()
									.getNumericElementItem().get().getFrameUpperLimit().v();
							ElementItemDto item = new ElementItemDto(null, null, frameNumber, frameLower, frameUpper,
									payment.getWageTablePaymentAmount().v());
							list2ndDmsElements.add(item);
							cellData.add(new WageTableCellDto("2nd-dms-numeric-" + frameNumber,
									payment.getWageTablePaymentAmount().v()));
							dto.listHeader.add(
									new WageTableHeaderDto(frameLower + TextResource.localize("QMM016_31") + frameUpper,
											"2nd-dms-numeric-" + frameNumber, "number", "150px", false, null));
						}
					}
					Collections.sort(list2ndDmsElements, comparator);
					if (key.getMasterElementItem().isPresent()) {
						String masterCode = key.getMasterElementItem().get().getMasterCode().v();
						String masterName = mapMaster1.get(masterCode);
						dto.list2dElements.add(new TwoDmsElementItemDto(masterCode,
								masterName == null ? masterCode : masterName, null, null, null, list2ndDmsElements));
						dto.listData.add(new WageTableRowDto("1st-dms-master-" + masterCode, masterName, cellData));
					} else if (key.getNumericElementItem().isPresent()) {
						int frameNumber = key.getNumericElementItem().get().getFrameNumber().v();
						int frameLower = key.getNumericElementItem().get().getFrameLowerLimit().v();
						int frameUpper = key.getNumericElementItem().get().getFrameUpperLimit().v();
						dto.list2dElements.add(new TwoDmsElementItemDto(null, null, frameNumber, frameLower, frameUpper,
								list2ndDmsElements));
						dto.listData.add(new WageTableRowDto("1st-dms-numeric-" + frameNumber,
								frameLower + TextResource.localize("QMM016_31") + frameUpper, cellData));
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
