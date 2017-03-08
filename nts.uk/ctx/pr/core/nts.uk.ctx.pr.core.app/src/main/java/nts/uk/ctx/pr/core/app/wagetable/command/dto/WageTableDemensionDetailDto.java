/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.wagetable.command.dto;

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

/**
 * The Class WageTableDemensionDetailDto.
 */
@Setter
@Getter
public class WageTableDemensionDetailDto implements WageTableDetailGetMemento {

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
		CompanyCode companyCode = null;

		if (elementModeSetting instanceof RefModeDto) {
			RefModeDto refModeDto = (RefModeDto) this.elementModeSetting;

			List<CodeItem> codeItems = refModeDto.getItems().stream()
					.map(item -> new CodeItem(item.getReferenceCode(), item.getUuid()))
					.collect(Collectors.toList());

			return new RefMode(ElementType.valueOf(refModeDto.getType()), companyCode,
					new WtElementRefNo(refModeDto.getRefNo()), codeItems);
		}

		if (elementModeSetting instanceof StepModeDto) {
			StepModeDto stepModeDto = (StepModeDto) this.elementModeSetting;

			List<RangeItem> rangeItems = stepModeDto
					.getItems().stream().map(item -> new RangeItem(item.getOrderNumber(),
							item.getStartVal(), item.getEndVal(), item.getUuid()))
					.collect(Collectors.toList());

			return new StepMode(ElementType.valueOf(stepModeDto.getType()),
					stepModeDto.getLowerLimit(), stepModeDto.getUpperLimit(),
					stepModeDto.getInterval(), rangeItems);
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
}
