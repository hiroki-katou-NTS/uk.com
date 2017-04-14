/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.wagetable.element.service.internal;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.core.dom.wagetable.ElementType;
import nts.uk.ctx.pr.core.dom.wagetable.element.WtElement;
import nts.uk.ctx.pr.core.dom.wagetable.element.service.WtElementService;
import nts.uk.ctx.pr.core.dom.wagetable.reference.WtCodeRef;
import nts.uk.ctx.pr.core.dom.wagetable.reference.WtCodeRefRepository;
import nts.uk.ctx.pr.core.dom.wagetable.reference.WtMasterRef;
import nts.uk.ctx.pr.core.dom.wagetable.reference.WtMasterRefRepository;

/**
 * The Class WageTableHeadServiceImpl.
 */
@Stateless
public class WtElementServiceImpl implements WtElementService {

	/** The wt code ref repo. */
	@Inject
	private WtCodeRefRepository wtCodeRefRepo;

	/** The wt master ref repo. */
	@Inject
	private WtMasterRefRepository wtMasterRefRepo;

	/**
	 * Gets the demension name.
	 *
	 * @param companyCode
	 *            the company code
	 * @param referenceCode
	 *            the reference code
	 * @param type
	 *            the type
	 * @return the demension name
	 */
	public String getDemensionName(String companyCode, String referenceCode, ElementType type) {
		Optional<WtElement> optWtElement = Optional.empty();
		switch (type) {
		case MASTER_REF:
			Optional<WtMasterRef> optWtMasterRef = this.wtMasterRefRepo.findByCode(companyCode,
					referenceCode);
			return optWtMasterRef.get().getRefName();

		/** The code ref. */
		case CODE_REF:
			Optional<WtCodeRef> optWtCodeRef = this.wtCodeRefRepo.findByCode(companyCode,
					optWtElement.get().getReferenceCode());
			return optWtCodeRef.get().getRefName();

		/** The item data ref. */
		case ITEM_DATA_REF:
			return ElementType.ITEM_DATA_REF.displayName;

		/** The experience fix. */
		case EXPERIENCE_FIX:
			return ElementType.EXPERIENCE_FIX.displayName;

		/** The age fix. */
		case AGE_FIX:
			return ElementType.AGE_FIX.displayName;

		/** The family mem fix. */
		case FAMILY_MEM_FIX:
			return ElementType.FAMILY_MEM_FIX.displayName;

		// Extend element type
		/** The certification. */
		case CERTIFICATION:
			return ElementType.CERTIFICATION.displayName;

		/** The working day. */
		case WORKING_DAY:
			return ElementType.WORKING_DAY.displayName;

		/** The come late. */
		case COME_LATE:
			return ElementType.COME_LATE.displayName;

		/** The level. */
		// case LEVEL:
		default:
			return ElementType.LEVEL.displayName;
		}
	}

}
