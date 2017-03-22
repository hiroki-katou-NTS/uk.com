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
import nts.uk.ctx.pr.core.dom.wagetable.ElementCount;
import nts.uk.ctx.pr.core.dom.wagetable.ElementType;
import nts.uk.ctx.pr.core.dom.wagetable.WtCode;
import nts.uk.ctx.pr.core.dom.wagetable.WtHead;
import nts.uk.ctx.pr.core.dom.wagetable.WtHeadGetMemento;
import nts.uk.ctx.pr.core.dom.wagetable.WtName;
import nts.uk.ctx.pr.core.dom.wagetable.element.WtElement;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.primitive.Memo;

/**
 * The Class WageTableHeadDto.
 */
@Getter
@Setter
public class WtHeadDto {

	/** The code. */
	private String code;

	/** The name. */
	private String name;

	/** The mode. */
	private Integer mode;

	/** The memo. */
	private String memo;

	/** The elements. */
	private List<WtElementDto> elements;

	/**
	 * To domain.
	 *
	 * @return the wt head
	 */
	public WtHead toDomain() {
		WtHeadDto dto = this;

		// Transfer data
		WtHead wageTableHead = new WtHead(new WtHeadDtoMemento(dto));

		return wageTableHead;
	}

	/**
	 * The Class WageTableHeadDtoMemento.
	 */
	private class WtHeadDtoMemento implements WtHeadGetMemento {

		/** The dto. */
		protected WtHeadDto dto;

		/**
		 * Instantiates a new wage table head dto memento.
		 *
		 * @param companyCode
		 *            the company code
		 * @param dto
		 *            the dto
		 */
		public WtHeadDtoMemento(WtHeadDto dto) {
			this.dto = dto;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.pr.core.dom.wagetable.WageTableHeadGetMemento#
		 * getCompanyCode()
		 */
		@Override
		public CompanyCode getCompanyCode() {
			return new CompanyCode(AppContexts.user().companyCode());
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * nts.uk.ctx.pr.core.dom.wagetable.WageTableHeadGetMemento#getCode()
		 */
		@Override
		public WtCode getCode() {
			return new WtCode(this.dto.getCode());
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * nts.uk.ctx.pr.core.dom.wagetable.WageTableHeadGetMemento#getName()
		 */
		@Override
		public WtName getName() {
			return new WtName(this.dto.getName());
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * nts.uk.ctx.pr.core.dom.wagetable.WageTableHeadGetMemento#getMemo()
		 */
		@Override
		public Memo getMemo() {
			return new Memo(this.dto.getMemo());
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.pr.core.dom.wagetable.WtHeadGetMemento#getMode()
		 */
		@Override
		public ElementCount getMode() {
			return ElementCount.valueOf(this.dto.getMode());
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.pr.core.dom.wagetable.WtHeadGetMemento#getElements()
		 */
		@Override
		public List<WtElement> getElements() {
			return this.dto.getElements().stream()
					.map(item -> new WtElement(DemensionNo.valueOf(item.getDemensionNo()),
							ElementType.valueOf(item.getType()), item.getReferenceCode()))
					.collect(Collectors.toList());
		}
	}
}
