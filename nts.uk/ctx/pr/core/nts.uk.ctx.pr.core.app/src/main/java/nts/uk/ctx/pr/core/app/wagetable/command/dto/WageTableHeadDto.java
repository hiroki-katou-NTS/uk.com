/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.wagetable.command.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.wagetable.ElementCount;
import nts.uk.ctx.pr.core.dom.wagetable.WtCode;
import nts.uk.ctx.pr.core.dom.wagetable.WtHead;
import nts.uk.ctx.pr.core.dom.wagetable.WtHeadGetMemento;
import nts.uk.ctx.pr.core.dom.wagetable.WtName;
import nts.uk.ctx.pr.core.dom.wagetable.element.WtElement;
import nts.uk.shr.com.primitive.Memo;

/**
 * Instantiates a new certification find dto.
 */
@Getter
@Setter
public class WageTableHeadDto extends WageTableDetailDto {

	/** The code. */
	private String code;

	/** The name. */
	private String name;

	/** The demension set. */
	private Integer demensionSet;

	/** The memo. */
	private String memo;

	/**
	 * To domain.
	 *
	 * @param companyCode
	 *            the company code
	 * @return the wage table history
	 */
	public WtHead toDomain(String companyCode) {
		WageTableHeadDto dto = this;

		// Transfer data
		WtHead wageTableHead = new WtHead(
				new WageTableHeadDtoMemento(new CompanyCode(companyCode), dto));

		return wageTableHead;
	}

	/**
	 * The Class WageTableHeadDtoMemento.
	 */
	private class WageTableHeadDtoMemento implements WtHeadGetMemento {

		/** The company code. */
		protected CompanyCode companyCode;

		/** The type value. */
		protected WageTableHeadDto dto;

		/**
		 * Instantiates a new jpa wage table head get memento.
		 *
		 * @param dto
		 *            the type value
		 */
		public WageTableHeadDtoMemento(CompanyCode companyCode, WageTableHeadDto dto) {
			this.companyCode = companyCode;
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
			return this.companyCode;
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

		@Override
		public ElementCount getMode() {
			return ElementCount.valueOf(this.dto.demensionSet);
		}

		@Override
		public List<WtElement> getElements() {
			// TODO Auto-generated method stub
			return null;
		}
	}
}
