package nts.uk.ctx.pr.core.app.find.wageprovision.wagetable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

/**
 * 
 * @author HungTT
 *
 */

@Stateless
public class WageTableContentCreater {

	public WageTableContentDto createOneDimensionWageTable(ElementRangeSettingDto params) {
		WageTableContentDto dto = new WageTableContentDto();
		dto.setHistoryID(params.getHistoryID());
		List<ElementItemDto> payments = new ArrayList<>();
		if (params.getFirstElementRange() == null) {
			// master item
			// dummy code
			payments = getMasterElements();
		} else {
			// numeric item
			payments = getNumericRange(params.getFirstElementRange());
		}
		dto.setList1dElements(payments);
		return dto;
	}

	public WageTableContentDto createTwoDimensionWageTable(ElementRangeSettingDto params) {
		WageTableContentDto dto = new WageTableContentDto();
		dto.setHistoryID(params.getHistoryID());
		List<TwoDmsElementItemDto> payments = new ArrayList<>();
		if (params.getFirstElementRange() == null) {
			// master item
			payments = getMasterElements().stream().map(i -> new TwoDmsElementItemDto(i)).collect(Collectors.toList());
		} else {
			// numeric item
			payments = getNumericRange(params.getFirstElementRange()).stream().map(i -> new TwoDmsElementItemDto(i))
					.collect(Collectors.toList());
		}
		List<ElementItemDto> list2nd = params.getSecondElementRange() == null ? getMasterElements() : getNumericRange(params.getSecondElementRange());
		for (TwoDmsElementItemDto t : payments) {
			t.setListSecondDms(list2nd);
		}
		dto.setList2dElements(payments);
		return dto;
	}

	public WageTableContentDto createThreeDimensionWageTable(ElementRangeSettingDto params) {
		// WageTableContentDto dto = new WageTableContentDto();
		// dto.setHistoryID(params.getHistoryID());
		// List<ElementsCombinationPaymentAmountDto> payments = new
		// ArrayList<>();
		// if (params.getFirstElementRange() == null) {
		// // master item
		// payments.addAll(getMasterElements());
		// } else {
		// // numeric item
		// payments.addAll(getNumericRange(params.getFirstElementRange()));
		// }
		// if (params.getSecondElementRange() == null) {
		// // master item
		// payments.addAll(getMasterElements());
		// } else {
		// // numeric item
		// payments.addAll(getNumericRange(params.getSecondElementRange()));
		// }
		// if (params.getThirdElementRange() == null) {
		// // master item
		// payments.addAll(getMasterElements());
		// } else {
		// // numeric item
		// payments.addAll(getNumericRange(params.getThirdElementRange()));
		// }
		// dto.setPayments(payments);
		return null;
	}

	private List<ElementItemDto> getNumericRange(ElementRangeDto rangeDto) {
		List<ElementItemDto> result = new ArrayList<>();
		int frameNum = 1;
		while (rangeDto.getRangeLowerLimit() + rangeDto.getStepIncrement() < rangeDto.getRangeUpperLimit()) {
			result.add(new ElementItemDto(null, null, frameNum, rangeDto.getRangeLowerLimit(),
					rangeDto.getRangeLowerLimit() + rangeDto.getStepIncrement() - 1, null));
			rangeDto.setRangeLowerLimit(rangeDto.getRangeLowerLimit() + rangeDto.getStepIncrement());
			frameNum++;
		}
		result.add(new ElementItemDto(null, null, frameNum, rangeDto.getRangeLowerLimit(),
				rangeDto.getRangeUpperLimit(), null));
		return result;
	}

	private List<ElementItemDto> getMasterElements() {
		List<ElementItemDto> result = new ArrayList<>();
		// dummy code
		for (int i = 0; i < 10; i++) {
			result.add(new ElementItemDto("DUM" + i, "Dummy Name " + i, null, null, null, null));
		}
		return result;
	}

}
