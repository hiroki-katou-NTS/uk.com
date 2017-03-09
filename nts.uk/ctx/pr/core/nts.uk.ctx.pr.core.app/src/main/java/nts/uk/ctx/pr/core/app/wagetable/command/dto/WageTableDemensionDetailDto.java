/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.wagetable.command.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.wagetable.DemensionNo;
import nts.uk.ctx.pr.core.dom.wagetable.ElementType;
import nts.uk.ctx.pr.core.dom.wagetable.WtElementRefNo;
import nts.uk.ctx.pr.core.dom.wagetable.element.CertifyMode;
import nts.uk.ctx.pr.core.dom.wagetable.element.CodeItem;
import nts.uk.ctx.pr.core.dom.wagetable.element.ElementMode;
import nts.uk.ctx.pr.core.dom.wagetable.element.LevelMode;
import nts.uk.ctx.pr.core.dom.wagetable.element.RangeItem;
import nts.uk.ctx.pr.core.dom.wagetable.element.RefMode;
import nts.uk.ctx.pr.core.dom.wagetable.element.StepMode;
import nts.uk.ctx.pr.core.dom.wagetable.history.WageTableDetailGetMemento;
import nts.uk.ctx.pr.core.dom.wagetable.history.WageTableDetailSetMemento;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class WageTableDemensionDetailDto.
 */
@Setter
@Getter
public class WageTableDemensionDetailDto
		implements WageTableDetailGetMemento, WageTableDetailSetMemento {

	/** The demension no. */
	private Integer demensionNo;

	/** The element mode setting. */
	private ElementModeDto elementModeSetting;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.wagetable.history.WageTableDetailGetMemento#
	 * getDemensionNo()
	 */
	@Override
	public DemensionNo getDemensionNo() {
		return DemensionNo.valueOf(this.demensionNo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.wagetable.history.WageTableDetailGetMemento#
	 * getElementModeSetting()
	 */
	@Override
	public ElementMode getElementModeSetting() {
		// TODO: get from???
		String companyCode = AppContexts.user().companyCode();

		if (elementModeSetting instanceof RefModeDto) {
			RefModeDto refModeDto = (RefModeDto) this.elementModeSetting;

			List<CodeItem> codeItems = refModeDto.getItems().stream()
					.map(item -> new CodeItem(item.getReferenceCode(), item.getUuid()))
					.collect(Collectors.toList());

			return new RefMode(ElementType.valueOf(refModeDto.getType()),
					new CompanyCode(companyCode), new WtElementRefNo(refModeDto.getRefNo()),
					codeItems);
		}

		if (elementModeSetting instanceof StepModeDto) {
			StepModeDto stepModeDto = (StepModeDto) this.elementModeSetting;

			List<RangeItem> rangeItems = stepModeDto
					.getItems().stream().map(item -> new RangeItem(item.getOrderNumber(),
							item.getStartVal(), item.getEndVal(), item.getUuid()))
					.collect(Collectors.toList());

			return new StepMode(ElementType.valueOf(stepModeDto.getType()),
					BigDecimal.valueOf(stepModeDto.getLowerLimit()),
					BigDecimal.valueOf(stepModeDto.getUpperLimit()),
					BigDecimal.valueOf(stepModeDto.getInterval()), rangeItems);
		}

		if (elementModeSetting instanceof LevelModeDto) {
			LevelModeDto levelModeDto = (LevelModeDto) this.elementModeSetting;

			List<CodeItem> codeItems = levelModeDto.getItems().stream()
					.map(item -> new CodeItem(item.getReferenceCode(), item.getUuid()))
					.collect(Collectors.toList());

			return new LevelMode(ElementType.valueOf(levelModeDto.getType()), codeItems);
		}

		if (elementModeSetting instanceof CertifyModeDto) {
			CertifyModeDto levelModeDto = (CertifyModeDto) this.elementModeSetting;

			List<CodeItem> codeItems = levelModeDto.getItems().stream()
					.map(item -> new CodeItem(item.getReferenceCode(), item.getUuid()))
					.collect(Collectors.toList());

			return new CertifyMode(ElementType.valueOf(levelModeDto.getType()), codeItems);
		}

		return null;
	}

	@Override
	public void setDemensionNo(DemensionNo demensionNo) {
		this.demensionNo = demensionNo.value;
	}

	@Override
	public void setElementModeSetting(ElementMode elementModeSetting) {

		switch (elementModeSetting.getElementType()) {
		case LEVEL: {
			LevelMode levelMode = ((LevelMode) elementModeSetting);
			List<CodeItemDto> items = levelMode.getItems()
					.stream().map(item -> CodeItemDto.builder()
							.referenceCode(item.getReferenceCode()).uuid(item.getUuid()).build())
					.collect(Collectors.toList());
			this.elementModeSetting = LevelModeDto.builder().items(items).build();
			break;
		}

		case CERTIFICATION: {
			CertifyMode certifyMode = ((CertifyMode) elementModeSetting);
			List<CodeItemDto> items = certifyMode.getItems()
					.stream().map(item -> CodeItemDto.builder()
							.referenceCode(item.getReferenceCode()).uuid(item.getUuid()).build())
					.collect(Collectors.toList());
			this.elementModeSetting = CertifyModeDto.builder().items(items).build();
			break;
		}

		default:
			// Do nothing
			break;
		}

		if (elementModeSetting.getElementType().isCodeMode) {
			RefMode refMode = ((RefMode) elementModeSetting);
			List<CodeItemDto> items = refMode.getItems()
					.stream().map(item -> CodeItemDto.builder()
							.referenceCode(item.getReferenceCode()).uuid(item.getUuid()).build())
					.collect(Collectors.toList());
			this.elementModeSetting = RefModeDto.builder().items(items).build();
			return;
		}

		if (elementModeSetting.getElementType().isRangeMode) {
			StepMode stepMode = ((StepMode) elementModeSetting);
			List<RangeItemDto> items = stepMode.getItems().stream()
					.map(item -> RangeItemDto.builder().orderNumber(item.getOrderNumber())
							.startVal(item.getStartVal()).endVal(item.getEndVal())
							.uuid(item.getUuid()).build())
					.collect(Collectors.toList());
			this.elementModeSetting = StepModeDto.builder()
					.lowerLimit(stepMode.getLowerLimit().doubleValue())
					.upperLimit(stepMode.getUpperLimit().doubleValue())
					.interval(stepMode.getInterval().doubleValue()).items(items).build();
		}

	}
}
