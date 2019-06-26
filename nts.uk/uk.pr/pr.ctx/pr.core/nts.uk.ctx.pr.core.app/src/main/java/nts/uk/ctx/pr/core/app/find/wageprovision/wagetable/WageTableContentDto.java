package nts.uk.ctx.pr.core.app.find.wageprovision.wagetable;

import java.math.BigDecimal;
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
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.ElementRangeSetting;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.ElementSetting;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.ElementType;
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

	private ElementRangeSettingDto elemRangeSet;

	private boolean brandNew = false;

	public WageTableContentDto(Optional<WageTableContent> optContent, Optional<WageTable> optWage,
			Optional<ElementRangeSetting> optSetting, WageTableContentCreater wageContentCreater) {
		this.elemRangeSet = optSetting.isPresent() ? ElementRangeSettingDto.fromDomainToDto(optSetting.get()) : null;

		if (optContent.isPresent() && !optContent.get().getPayments().isEmpty()) {
			Comparator<ElementItemDto> comparator1 = Comparator
					.comparing(ElementItemDto::getMasterCode, Comparator.nullsLast(Comparator.naturalOrder()))
					.thenComparing(ElementItemDto::getFrameNumber, Comparator.nullsLast(Comparator.naturalOrder()));
			this.historyID = optContent.get().getHistoryID();

			// one dimension
			if (optWage.get().getElementSetting() == ElementSetting.ONE_DIMENSION) {
				Map<String, String> mapMaster = new HashMap<>();
				if (optWage.isPresent()
						&& optWage.get().getElementInformation().getOneDimensionalElement().getMasterNumericAtr()
								.get() == MasterNumericAtr.MASTER_ITEM
						&& !optWage.get().getElementInformation().getOneDimensionalElement().getFixedElement()
								.get().value.equals(ElementType.FINE_WORK.value)) {
					mapMaster = wageContentCreater.getMasterItems(optWage.get().getElementInformation()
							.getOneDimensionalElement().getFixedElement().get().value, AppContexts.user().companyId());
				}
				this.list1dElements = this.getOneDmsElemItemDto(optContent.get().getPayments(), mapMaster, true,
						optWage.get().getElementInformation().getOneDimensionalElement().getFixedElement().isPresent()
								&& optWage.get().getElementInformation().getOneDimensionalElement().getFixedElement()
										.get().value.equals(ElementType.FINE_WORK.value));
				Collections.sort(this.list1dElements, comparator1);
			}
			// two dimensions
			else if (optWage.get().getElementSetting() == ElementSetting.TWO_DIMENSION) {
				Map<ElementItem, List<ElementsCombinationPaymentAmount>> mapPayments = optContent.get().getPayments()
						.stream().collect(Collectors.groupingBy(i -> i.getElementAttribute().getFirstElementItem()));
				Map<String, String> mapMaster1 = new HashMap<>();
				if (optWage.isPresent()
						&& optWage.get().getElementInformation().getOneDimensionalElement().getMasterNumericAtr()
								.get() == MasterNumericAtr.MASTER_ITEM
						&& !optWage.get().getElementInformation().getOneDimensionalElement().getFixedElement()
								.get().value.equals(ElementType.FINE_WORK.value)) {
					mapMaster1 = wageContentCreater.getMasterItems(optWage.get().getElementInformation()
							.getOneDimensionalElement().getFixedElement().get().value, AppContexts.user().companyId());
				}
				Map<String, String> mapMaster2 = new HashMap<>();
				if (optWage.isPresent()
						&& optWage.get().getElementInformation().getTwoDimensionalElement().get().getMasterNumericAtr()
								.get() == MasterNumericAtr.MASTER_ITEM
						&& !optWage.get().getElementInformation().getTwoDimensionalElement().get().getFixedElement()
								.get().value.equals(ElementType.FINE_WORK.value)) {
					mapMaster2 = wageContentCreater.getMasterItems(optWage.get().getElementInformation()
							.getTwoDimensionalElement().get().getFixedElement().get().value,
							AppContexts.user().companyId());
				}
				this.list2dElements = this.getTwoDmsElemItemDto(mapPayments, mapMaster1, mapMaster2, comparator1,
						optWage.get().getElementInformation().getOneDimensionalElement().getFixedElement().isPresent()
								&& optWage.get().getElementInformation().getOneDimensionalElement().getFixedElement()
										.get().value.equals(ElementType.FINE_WORK.value),
						optWage.get().getElementInformation().getTwoDimensionalElement().get().getFixedElement()
								.isPresent()
								&& optWage.get().getElementInformation().getTwoDimensionalElement().get()
										.getFixedElement().get().value.equals(ElementType.FINE_WORK.value));
				Comparator<TwoDmsElementItemDto> comparator2 = Comparator
						.comparing(TwoDmsElementItemDto::getMasterCode, Comparator.nullsLast(Comparator.naturalOrder()))
						.thenComparing(TwoDmsElementItemDto::getFrameNumber,
								Comparator.nullsLast(Comparator.naturalOrder()));
				Collections.sort(this.list2dElements, comparator2);
			}
			// three dimension
			else if (optWage.get().getElementSetting() == ElementSetting.THREE_DIMENSION
					|| optWage.get().getElementSetting() == ElementSetting.FINE_WORK) {
				ElementRangeSettingDto elemRange = ElementRangeSettingDto.fromDomainToDto(optSetting.get());
				elemRange.setWageTableCode(optWage.get().getWageTableCode().v());
				
				WageTableContentDto temp = wageContentCreater.createThreeDimensionWageTable(elemRange);
				this.list3dElements = temp.list3dElements;
				this.list2dElements = temp.list2dElements;
				Map<ElementItem, List<ElementsCombinationPaymentAmount>> mapPaymentsBy3 = optContent.get().getPayments()
						.stream()
						.collect(Collectors.groupingBy(i -> i.getElementAttribute().getThirdElementItem().get()));
				Comparator<TwoDmsElementItemDto> comparator2 = Comparator
						.comparing(TwoDmsElementItemDto::getMasterCode, Comparator.nullsLast(Comparator.naturalOrder()))
						.thenComparing(TwoDmsElementItemDto::getFrameNumber,
								Comparator.nullsLast(Comparator.naturalOrder()));
				Map<String, String> mapMaster1 = new HashMap<>();
				if (optWage.isPresent()
						&& optWage.get().getElementInformation().getOneDimensionalElement().getMasterNumericAtr()
								.get() == MasterNumericAtr.MASTER_ITEM
						&& !optWage.get().getElementInformation().getOneDimensionalElement().getFixedElement()
								.get().value.equals(ElementType.FINE_WORK.value)) {
					mapMaster1 = wageContentCreater.getMasterItems(optWage.get().getElementInformation()
							.getOneDimensionalElement().getFixedElement().get().value, AppContexts.user().companyId());
				}
				Map<String, String> mapMaster2 = new HashMap<>();
				if (optWage.isPresent()
						&& optWage.get().getElementInformation().getTwoDimensionalElement().get().getMasterNumericAtr()
								.get() == MasterNumericAtr.MASTER_ITEM
						&& !optWage.get().getElementInformation().getTwoDimensionalElement().get().getFixedElement()
								.get().value.equals(ElementType.FINE_WORK.value)) {
					mapMaster2 = wageContentCreater.getMasterItems(optWage.get().getElementInformation()
							.getTwoDimensionalElement().get().getFixedElement().get().value,
							AppContexts.user().companyId());
				}
				ThreeDmsElementItemDto thDms = this.list3dElements.get(0);
				for (ElementItem key : mapPaymentsBy3.keySet()) {
					if ((key.getMasterElementItem().isPresent()
							&& key.getMasterElementItem().get().getMasterCode().equals(thDms.getMasterCode()))
							|| (key.getNumericElementItem().isPresent()
									&& key.getNumericElementItem().get().getFrameNumber() == thDms.getFrameNumber())) {
						List<ElementsCombinationPaymentAmount> value = mapPaymentsBy3.get(key);
						Map<ElementItem, List<ElementsCombinationPaymentAmount>> mapPaymentsBy1 = value.stream()
								.collect(Collectors.groupingBy(i -> i.getElementAttribute().getFirstElementItem()));
						List<TwoDmsElementItemDto> listTwoDms = this.getTwoDmsElemItemDto(mapPaymentsBy1, mapMaster1,
								mapMaster2, comparator1,
								optWage.get().getElementInformation().getOneDimensionalElement().getFixedElement()
										.isPresent()
										&& optWage.get().getElementInformation().getOneDimensionalElement()
												.getFixedElement().get().value.equals(ElementType.FINE_WORK.value),
								optWage.get().getElementInformation().getTwoDimensionalElement().get().getFixedElement()
										.isPresent()
										&& optWage.get().getElementInformation().getTwoDimensionalElement().get()
												.getFixedElement().get().value.equals(ElementType.FINE_WORK.value));
						Collections.sort(listTwoDms, comparator2);
						thDms.setListFirstDms(listTwoDms);
						break;
					}
				}
			}
		}
	}

	public WageTableContentDto(List<ElementsCombinationPaymentAmount> payments, Optional<WageTable> optWage,
			WageTableContentCreater wageContentCreater) {
		if (!payments.isEmpty()) {
			Comparator<ElementItemDto> comparator1 = Comparator
					.comparing(ElementItemDto::getMasterCode, Comparator.nullsLast(Comparator.naturalOrder()))
					.thenComparing(ElementItemDto::getFrameNumber, Comparator.nullsLast(Comparator.naturalOrder()));

			Map<ElementItem, List<ElementsCombinationPaymentAmount>> mapPayments = payments.stream()
					.collect(Collectors.groupingBy(i -> i.getElementAttribute().getFirstElementItem()));
			Map<String, String> mapMaster1 = new HashMap<>();
			if (optWage.isPresent()
					&& optWage.get().getElementInformation().getOneDimensionalElement().getMasterNumericAtr()
							.get() == MasterNumericAtr.MASTER_ITEM
					&& !optWage.get().getElementInformation().getOneDimensionalElement().getFixedElement().get().value
							.equals(ElementType.FINE_WORK.value)) {
				mapMaster1 = wageContentCreater.getMasterItems(
						optWage.get().getElementInformation().getOneDimensionalElement().getFixedElement().get().value,
						AppContexts.user().companyId());
			}
			Map<String, String> mapMaster2 = new HashMap<>();
			if (optWage.isPresent()
					&& optWage.get().getElementInformation().getTwoDimensionalElement().get().getMasterNumericAtr()
							.get() == MasterNumericAtr.MASTER_ITEM
					&& !optWage.get().getElementInformation().getTwoDimensionalElement().get().getFixedElement()
							.get().value.equals(ElementType.FINE_WORK.value)) {
				mapMaster2 = wageContentCreater.getMasterItems(optWage.get().getElementInformation()
						.getTwoDimensionalElement().get().getFixedElement().get().value,
						AppContexts.user().companyId());
			}
			this.list2dElements = this.getTwoDmsElemItemDto(mapPayments, mapMaster1, mapMaster2, comparator1, optWage
					.get().getElementInformation().getOneDimensionalElement().getFixedElement().isPresent()
					&& optWage.get().getElementInformation().getOneDimensionalElement().getFixedElement().get().value
							.equals(ElementType.FINE_WORK.value),
					optWage.get().getElementInformation().getTwoDimensionalElement().get().getFixedElement().isPresent()
							&& optWage.get().getElementInformation().getTwoDimensionalElement().get().getFixedElement()
									.get().value.equals(ElementType.FINE_WORK.value));
			Comparator<TwoDmsElementItemDto> comparator2 = Comparator
					.comparing(TwoDmsElementItemDto::getMasterCode, Comparator.nullsLast(Comparator.naturalOrder()))
					.thenComparing(TwoDmsElementItemDto::getFrameNumber,
							Comparator.nullsLast(Comparator.naturalOrder()));
			Collections.sort(this.list2dElements, comparator2);

		}
	}

	private List<ElementItemDto> getOneDmsElemItemDto(List<ElementsCombinationPaymentAmount> listPayment,
			Map<String, String> mapMaster, boolean isFirst, boolean isWorkLevel) {
		List<ElementItemDto> result = new ArrayList<>();
		for (ElementsCombinationPaymentAmount payment : listPayment) {
			if (isFirst) {
				if (payment.getElementAttribute().getFirstElementItem().getMasterElementItem().isPresent()) {
					String masterCode = payment.getElementAttribute().getFirstElementItem().getMasterElementItem().get()
							.getMasterCode();
					String masterName = isWorkLevel ? masterCode : mapMaster.get(masterCode);
					ElementItemDto item = new ElementItemDto(masterCode, masterName, null, null, null,
							payment.getWageTablePaymentAmount().v());
					result.add(item);
				} else if (payment.getElementAttribute().getFirstElementItem().getNumericElementItem().isPresent()) {
					long frameNumber = payment.getElementAttribute().getFirstElementItem().getNumericElementItem().get()
							.getFrameNumber();
					BigDecimal lowerLimit = payment.getElementAttribute().getFirstElementItem().getNumericElementItem()
							.get().getFrameLowerLimit();
					BigDecimal upperLimit = payment.getElementAttribute().getFirstElementItem().getNumericElementItem()
							.get().getFrameUpperLimit();
					ElementItemDto item = new ElementItemDto(null, null, frameNumber, lowerLimit, upperLimit,
							payment.getWageTablePaymentAmount().v());
					result.add(item);
				}
			} else {
				if (payment.getElementAttribute().getSecondElementItem().get().getMasterElementItem().isPresent()) {
					String masterCode = payment.getElementAttribute().getSecondElementItem().get()
							.getMasterElementItem().get().getMasterCode();
					String masterName = isWorkLevel ? masterCode : mapMaster.get(masterCode);
					ElementItemDto item = new ElementItemDto(masterCode, masterName, null, null, null,
							payment.getWageTablePaymentAmount().v());
					result.add(item);
				} else if (payment.getElementAttribute().getSecondElementItem().get().getNumericElementItem()
						.isPresent()) {
					long frameNumber = payment.getElementAttribute().getSecondElementItem().get()
							.getNumericElementItem().get().getFrameNumber();
					BigDecimal lowerLimit = payment.getElementAttribute().getSecondElementItem().get()
							.getNumericElementItem().get().getFrameLowerLimit();
					BigDecimal upperLimit = payment.getElementAttribute().getSecondElementItem().get()
							.getNumericElementItem().get().getFrameUpperLimit();
					ElementItemDto item = new ElementItemDto(null, null, frameNumber, lowerLimit, upperLimit,
							payment.getWageTablePaymentAmount().v());
					result.add(item);
				}
			}
		}
		return result;
	}

	private List<TwoDmsElementItemDto> getTwoDmsElemItemDto(
			Map<ElementItem, List<ElementsCombinationPaymentAmount>> mapPayments, Map<String, String> mapMaster1,
			Map<String, String> mapMaster2, Comparator<ElementItemDto> comparator, boolean isFirstWorkLevel,
			boolean isSecondWorkLevel) {
		List<TwoDmsElementItemDto> result = new ArrayList<>();
		for (ElementItem key : mapPayments.keySet()) {
			List<ElementsCombinationPaymentAmount> value = mapPayments.get(key);
			List<ElementItemDto> list2ndDmsElements = this.getOneDmsElemItemDto(value, mapMaster2, false,
					isSecondWorkLevel);
			Collections.sort(list2ndDmsElements, comparator);
			if (key.getMasterElementItem().isPresent()) {
				String masterCode = key.getMasterElementItem().get().getMasterCode();
				String masterName = isFirstWorkLevel ? masterCode : mapMaster1.get(masterCode);
				result.add(new TwoDmsElementItemDto(masterCode, masterName, null, null, null, list2ndDmsElements));
			} else if (key.getNumericElementItem().isPresent()) {
				long frameNumber = key.getNumericElementItem().get().getFrameNumber();
				BigDecimal frameLower = key.getNumericElementItem().get().getFrameLowerLimit();
				BigDecimal frameUpper = key.getNumericElementItem().get().getFrameUpperLimit();
				result.add(
						new TwoDmsElementItemDto(null, null, frameNumber, frameLower, frameUpper, list2ndDmsElements));
			}
		}
		return result;
	}

}
