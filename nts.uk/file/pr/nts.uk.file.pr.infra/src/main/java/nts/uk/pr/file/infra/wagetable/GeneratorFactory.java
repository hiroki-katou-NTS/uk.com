/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.pr.file.infra.wagetable;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.core.dom.wagetable.WtHead;
import nts.uk.ctx.pr.core.dom.wagetable.WtHeadRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * A factory for creating Generator objects.
 */
@Stateless
public class GeneratorFactory {

	/** The wt repo. */
	@Inject WtHeadRepository wtRepo;

	/**
	 * Creates a new Generator object.
	 *
	 * @param wtCode the wt code
	 * @return the generator
	 */
	public Generator createGenerator(String wtCode) {
		String ccd = AppContexts.user().companyCode();
		WtHead wtHeadOpt = this.wtRepo.findByCode(ccd, wtCode).get();
		switch (wtHeadOpt.getMode()) {
		case One:
			return new OneDemensionGenerator();

		case Two:
			return new TwoDemensionGenerator();

		case Three:
			return new ThreeDemensionGenerator();
			
		case Finework:
			return new ThreeDemensionGenerator();

		default:
			break;
		}
		
		return null;
	}
}
