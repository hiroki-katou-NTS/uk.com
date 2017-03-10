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
import nts.uk.ctx.pr.core.dom.wagetable.ElementCount;
import nts.uk.ctx.pr.core.dom.wagetable.WageTableCode;
import nts.uk.ctx.pr.core.dom.wagetable.WageTableHead;
import nts.uk.ctx.pr.core.dom.wagetable.WageTableHeadGetMemento;
import nts.uk.ctx.pr.core.dom.wagetable.WageTableName;
import nts.uk.ctx.pr.core.dom.wagetable.element.WageTableElement;
import nts.uk.ctx.pr.core.dom.wagetable.mode.DemensionalMode;
import nts.uk.ctx.pr.core.dom.wagetable.mode.FineworkDimensionalMode;
import nts.uk.ctx.pr.core.dom.wagetable.mode.OneDimensionalMode;
import nts.uk.ctx.pr.core.dom.wagetable.mode.QualificaDimensionalMode;
import nts.uk.ctx.pr.core.dom.wagetable.mode.ThreeDimensionalMode;
import nts.uk.ctx.pr.core.dom.wagetable.mode.TwoDimensionalMode;
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
	public WageTableHead toDomain(String companyCode) {
		WageTableHeadDto dto = this;

		// Transfer data
		WageTableHead wageTableHead = new WageTableHead(
				new WageTableHeadDtoMemento(new CompanyCode(companyCode), dto));

		return wageTableHead;
	}

	/**
	 * The Class WageTableHeadDtoMemento.
	 */
	private class WageTableHeadDtoMemento implements WageTableHeadGetMemento {

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
		public WageTableCode getCode() {
			return new WageTableCode(this.dto.getCode());
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * nts.uk.ctx.pr.core.dom.wagetable.WageTableHeadGetMemento#getName()
		 */
		@Override
		public WageTableName getName() {
			return new WageTableName(this.dto.getName());
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.pr.core.dom.wagetable.WageTableHeadGetMemento#
		 * getDemensionSetting()
		 */
		@Override
		public DemensionalMode getDemensionSetting() {

			List<WageTableElement> wagetableElementList = this.dto.getDemensionDetails().stream()
					.map(item -> new WageTableElement(item)).collect(Collectors.toList());

			switch (ElementCount.valueOf(this.dto.getDemensionSet())) {
			case One: {
				return new OneDimensionalMode(wagetableElementList);
			}

			case Two: {
				return new TwoDimensionalMode(wagetableElementList);
			}

			case Three: {
				return new ThreeDimensionalMode(wagetableElementList);
			}

			case Finework: {
				return new FineworkDimensionalMode(wagetableElementList);
			}

			// case Qualification:
			default:
				return new QualificaDimensionalMode(wagetableElementList);
			}
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
	}
}
