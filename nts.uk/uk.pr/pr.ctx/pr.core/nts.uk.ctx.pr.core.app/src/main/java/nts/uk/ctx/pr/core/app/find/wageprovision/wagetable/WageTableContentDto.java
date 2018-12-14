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
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.ElementSetting;
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

	private List<ThreeDmsElementItemDto> list3dElements;
	
	private List<ThreeDmsElementItemDto> listWorkElements;

	public WageTableContentDto(WageTableContent domain, Optional<WageTable> domainOtp,
			WageTableContentCreater wageContentCreater) {
		Comparator<ElementItemDto> comparator1 = Comparator
				.comparing(ElementItemDto::getMasterCode, Comparator.nullsLast(Comparator.naturalOrder()))
				.thenComparing(ElementItemDto::getFrameNumber, Comparator.nullsLast(Comparator.naturalOrder()));
		this.historyID = domain.getHistoryID();
		if (!domain.getPayments().isEmpty()) {
			// one dimension
			if (domainOtp.get().getElementSetting() == ElementSetting.ONE_DIMENSION) {
				Map<String, String> mapMaster = new HashMap<>();
				if (domainOtp.isPresent() && domainOtp.get().getElementInformation().getOneDimensionalElement()
						.getMasterNumericAtr().get() == MasterNumericAtr.MASTER_ITEM) {
					mapMaster = wageContentCreater.getMasterItems(domainOtp.get().getElementInformation()
							.getOneDimensionalElement().getFixedElement().get().value, AppContexts.user().companyId());
				}
				this.list1dElements = this.getOneDmsElemItemDto(domain.getPayments(), mapMaster, true);
				Collections.sort(this.list1dElements, comparator1);
			}
			// two dimensions
			else if (domainOtp.get().getElementSetting() == ElementSetting.TWO_DIMENSION) {
				Map<ElementItem, List<ElementsCombinationPaymentAmount>> mapPayments = domain.getPayments().stream()
						.collect(Collectors.groupingBy(i -> i.getElementAttribute().getFirstElementItem()));
				Map<String, String> mapMaster1 = new HashMap<>();
				if (domainOtp.isPresent() && domainOtp.get().getElementInformation().getOneDimensionalElement()
						.getMasterNumericAtr().get() == MasterNumericAtr.MASTER_ITEM) {
					mapMaster1 = wageContentCreater.getMasterItems(domainOtp.get().getElementInformation()
							.getOneDimensionalElement().getFixedElement().get().value, AppContexts.user().companyId());
				}
				Map<String, String> mapMaster2 = new HashMap<>();
				if (domainOtp.isPresent() && domainOtp.get().getElementInformation().getTwoDimensionalElement().get()
						.getMasterNumericAtr().get() == MasterNumericAtr.MASTER_ITEM) {
					mapMaster2 = wageContentCreater.getMasterItems(domainOtp.get().getElementInformation()
							.getTwoDimensionalElement().get().getFixedElement().get().value,
							AppContexts.user().companyId());
				}
				this.list2dElements = this.getTwoDmsElemItemDto(mapPayments, mapMaster1, mapMaster2, comparator1);
				Comparator<TwoDmsElementItemDto> comparator2 = Comparator
						.comparing(TwoDmsElementItemDto::getMasterCode, Comparator.nullsLast(Comparator.naturalOrder()))
						.thenComparing(TwoDmsElementItemDto::getFrameNumber,
								Comparator.nullsLast(Comparator.naturalOrder()));
				Collections.sort(this.list2dElements, comparator2);
			}
			// three dimension
			else if (domainOtp.get().getElementSetting() == ElementSetting.THREE_DIMENSION) {
				this.list3dElements = new ArrayList<>();
				Map<ElementItem, List<ElementsCombinationPaymentAmount>> mapPaymentsBy3 = domain.getPayments().stream()
						.collect(Collectors.groupingBy(i -> i.getElementAttribute().getThirdElementItem().get()));
				Map<String, String> mapMaster3 = new HashMap<>();
				if (domainOtp.isPresent() && domainOtp.get().getElementInformation().getOneDimensionalElement()
						.getMasterNumericAtr().get() == MasterNumericAtr.MASTER_ITEM) {
					mapMaster3 = wageContentCreater.getMasterItems(domainOtp.get().getElementInformation()
							.getOneDimensionalElement().getFixedElement().get().value, AppContexts.user().companyId());
				}
				Comparator<TwoDmsElementItemDto> comparator2 = Comparator
						.comparing(TwoDmsElementItemDto::getMasterCode, Comparator.nullsLast(Comparator.naturalOrder()))
						.thenComparing(TwoDmsElementItemDto::getFrameNumber,
								Comparator.nullsLast(Comparator.naturalOrder()));
				Map<String, String> mapMaster1 = new HashMap<>();
				if (domainOtp.isPresent() && domainOtp.get().getElementInformation().getOneDimensionalElement()
						.getMasterNumericAtr().get() == MasterNumericAtr.MASTER_ITEM) {
					mapMaster1 = wageContentCreater.getMasterItems(domainOtp.get().getElementInformation()
							.getOneDimensionalElement().getFixedElement().get().value, AppContexts.user().companyId());
				}
				Map<String, String> mapMaster2 = new HashMap<>();
				if (domainOtp.isPresent() && domainOtp.get().getElementInformation().getTwoDimensionalElement().get()
						.getMasterNumericAtr().get() == MasterNumericAtr.MASTER_ITEM) {
					mapMaster2 = wageContentCreater.getMasterItems(domainOtp.get().getElementInformation()
							.getTwoDimensionalElement().get().getFixedElement().get().value,
							AppContexts.user().companyId());
				}
				for (ElementItem key : mapPaymentsBy3.keySet()) {
					List<ElementsCombinationPaymentAmount> value = mapPaymentsBy3.get(key);
					Map<ElementItem, List<ElementsCombinationPaymentAmount>> mapPaymentsBy1 = value.stream()
							.collect(Collectors.groupingBy(i -> i.getElementAttribute().getFirstElementItem()));
					List<TwoDmsElementItemDto> listTwoDms = this.getTwoDmsElemItemDto(mapPaymentsBy1, mapMaster1,
							mapMaster2, comparator1);
					Collections.sort(listTwoDms, comparator2);
					if (key.getMasterElementItem().isPresent()) {
						String masterCode = key.getMasterElementItem().get().getMasterCode().v();
						String masterName = mapMaster3.get(masterCode);
						this.list3dElements.add(new ThreeDmsElementItemDto(masterCode,
								masterName == null ? masterCode : masterName, null, null, null, listTwoDms));
					} else if (key.getNumericElementItem().isPresent()) {
						int frameNumber = key.getNumericElementItem().get().getFrameNumber().v();
						int frameLower = key.getNumericElementItem().get().getFrameLowerLimit().v();
						int frameUpper = key.getNumericElementItem().get().getFrameUpperLimit().v();
						this.list3dElements.add(new ThreeDmsElementItemDto(null, null, frameNumber, frameLower,
								frameUpper, listTwoDms));
					}
				}
				Comparator<ThreeDmsElementItemDto> comparator3 = Comparator
						.comparing(ThreeDmsElementItemDto::getMasterCode,
								Comparator.nullsLast(Comparator.naturalOrder()))
						.thenComparing(ThreeDmsElementItemDto::getFrameNumber,
								Comparator.nullsLast(Comparator.naturalOrder()));
				Collections.sort(this.list3dElements, comparator3);
			}
			// fine work ()
			else if (domainOtp.get().getElementSetting() == ElementSetting.FINE_WORK) {
				this.listWorkElements = new ArrayList<>();
				Map<ElementItem, List<ElementsCombinationPaymentAmount>> mapPaymentsBy3 = domain.getPayments().stream()
						.collect(Collectors.groupingBy(i -> i.getElementAttribute().getThirdElementItem().get()));
				Map<String, String> mapMaster3 = new HashMap<>();
				if (domainOtp.isPresent() && domainOtp.get().getElementInformation().getOneDimensionalElement()
						.getMasterNumericAtr().get() == MasterNumericAtr.MASTER_ITEM) {
					mapMaster3 = wageContentCreater.getMasterItems(domainOtp.get().getElementInformation()
							.getOneDimensionalElement().getFixedElement().get().value, AppContexts.user().companyId());
				}
				Comparator<TwoDmsElementItemDto> comparator2 = Comparator
						.comparing(TwoDmsElementItemDto::getMasterCode, Comparator.nullsLast(Comparator.naturalOrder()))
						.thenComparing(TwoDmsElementItemDto::getFrameNumber,
								Comparator.nullsLast(Comparator.naturalOrder()));
				Map<String, String> mapMaster1 = new HashMap<>();
				if (domainOtp.isPresent() && domainOtp.get().getElementInformation().getOneDimensionalElement()
						.getMasterNumericAtr().get() == MasterNumericAtr.MASTER_ITEM) {
					mapMaster1 = wageContentCreater.getMasterItems(domainOtp.get().getElementInformation()
							.getOneDimensionalElement().getFixedElement().get().value, AppContexts.user().companyId());
				}
				Map<String, String> mapMaster2 = new HashMap<>();
				if (domainOtp.isPresent() && domainOtp.get().getElementInformation().getTwoDimensionalElement().get()
						.getMasterNumericAtr().get() == MasterNumericAtr.MASTER_ITEM) {
					mapMaster2 = wageContentCreater.getMasterItems(domainOtp.get().getElementInformation()
							.getTwoDimensionalElement().get().getFixedElement().get().value,
							AppContexts.user().companyId());
				}
				for (ElementItem key : mapPaymentsBy3.keySet()) {
					List<ElementsCombinationPaymentAmount> value = mapPaymentsBy3.get(key);
					Map<ElementItem, List<ElementsCombinationPaymentAmount>> mapPaymentsBy1 = value.stream()
							.collect(Collectors.groupingBy(i -> i.getElementAttribute().getFirstElementItem()));
					List<TwoDmsElementItemDto> listTwoDms = this.getTwoDmsElemItemDto(mapPaymentsBy1, mapMaster1,
							mapMaster2, comparator1);
					Collections.sort(listTwoDms, comparator2);
					if (key.getMasterElementItem().isPresent()) {
						String masterCode = key.getMasterElementItem().get().getMasterCode().v();
						String masterName = mapMaster3.get(masterCode);
						this.listWorkElements.add(new ThreeDmsElementItemDto(masterCode,
								masterName == null ? masterCode : masterName, null, null, null, listTwoDms));
					} else if (key.getNumericElementItem().isPresent()) {
						int frameNumber = key.getNumericElementItem().get().getFrameNumber().v();
						int frameLower = key.getNumericElementItem().get().getFrameLowerLimit().v();
						int frameUpper = key.getNumericElementItem().get().getFrameUpperLimit().v();
						this.listWorkElements.add(new ThreeDmsElementItemDto(null, null, frameNumber, frameLower,
								frameUpper, listTwoDms));
					}
				}
				Comparator<ThreeDmsElementItemDto> comparator3 = Comparator
						.comparing(ThreeDmsElementItemDto::getMasterCode,
								Comparator.nullsLast(Comparator.naturalOrder()))
						.thenComparing(ThreeDmsElementItemDto::getFrameNumber,
								Comparator.nullsLast(Comparator.naturalOrder()));
				Collections.sort(this.listWorkElements, comparator3);
			}
		}
	}

	private List<ElementItemDto> getOneDmsElemItemDto(List<ElementsCombinationPaymentAmount> listPayment,
			Map<String, String> mapMaster, boolean isFirst) {
		List<ElementItemDto> result = new ArrayList<>();
		for (ElementsCombinationPaymentAmount payment : listPayment) {
			if (payment.getElementAttribute().getFirstElementItem().getMasterElementItem().isPresent()) {
				String masterCode = payment.getElementAttribute().getFirstElementItem().getMasterElementItem().get()
						.getMasterCode().v();
				String masterName = mapMaster.get(masterCode);
				ElementItemDto item = new ElementItemDto(masterCode, masterName == null ? masterCode : masterName, null,
						null, null, payment.getWageTablePaymentAmount().v());
				result.add(item);
			} else if (payment.getElementAttribute().getFirstElementItem().getNumericElementItem().isPresent()) {
				int frameNumber = isFirst
						? payment.getElementAttribute().getFirstElementItem().getNumericElementItem().get()
								.getFrameNumber().v()
						: payment.getElementAttribute().getSecondElementItem().get().getNumericElementItem().get()
								.getFrameNumber().v();
				int lowerLimit = isFirst
						? payment.getElementAttribute().getFirstElementItem().getNumericElementItem().get()
								.getFrameLowerLimit().v()
						: payment.getElementAttribute().getSecondElementItem().get().getNumericElementItem().get()
								.getFrameLowerLimit().v();
				int upperLimit = isFirst
						? payment.getElementAttribute().getFirstElementItem().getNumericElementItem().get()
								.getFrameUpperLimit().v()
						: payment.getElementAttribute().getSecondElementItem().get().getNumericElementItem().get()
								.getFrameUpperLimit().v();
				ElementItemDto item = new ElementItemDto(null, null, frameNumber, lowerLimit, upperLimit,
						payment.getWageTablePaymentAmount().v());
				result.add(item);
			}
		}
		return result;
	}

	private List<TwoDmsElementItemDto> getTwoDmsElemItemDto(
			Map<ElementItem, List<ElementsCombinationPaymentAmount>> mapPayments, Map<String, String> mapMaster1,
			Map<String, String> mapMaster2, Comparator<ElementItemDto> comparator) {
		List<TwoDmsElementItemDto> result = new ArrayList<>();
		for (ElementItem key : mapPayments.keySet()) {
			List<ElementsCombinationPaymentAmount> value = mapPayments.get(key);
			List<ElementItemDto> list2ndDmsElements = this.getOneDmsElemItemDto(value, mapMaster2, false);
			Collections.sort(list2ndDmsElements, comparator);
			if (key.getMasterElementItem().isPresent()) {
				String masterCode = key.getMasterElementItem().get().getMasterCode().v();
				String masterName = mapMaster1.get(masterCode);
				result.add(new TwoDmsElementItemDto(masterCode, masterName == null ? masterCode : masterName, null,
						null, null, list2ndDmsElements));
			} else if (key.getNumericElementItem().isPresent()) {
				int frameNumber = key.getNumericElementItem().get().getFrameNumber().v();
				int frameLower = key.getNumericElementItem().get().getFrameLowerLimit().v();
				int frameUpper = key.getNumericElementItem().get().getFrameUpperLimit().v();
				result.add(
						new TwoDmsElementItemDto(null, null, frameNumber, frameLower, frameUpper, list2ndDmsElements));
			}
		}
		return result;
	}

}
