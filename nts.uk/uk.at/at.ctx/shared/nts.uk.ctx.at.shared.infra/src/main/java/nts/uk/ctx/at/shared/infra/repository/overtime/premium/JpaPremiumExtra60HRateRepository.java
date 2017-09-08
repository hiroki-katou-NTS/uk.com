/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.overtime.premium;

import java.util.List;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.overtime.premium.extra.PremiumExtra60HRate;
import nts.uk.ctx.at.shared.dom.overtime.premium.extra.PremiumExtra60HRateRepository;

/**
 * The Class JpaPremiumExtra60HRateRepository.
 */
public class JpaPremiumExtra60HRateRepository extends JpaRepository
		implements PremiumExtra60HRateRepository {

	@Override
	public List<PremiumExtra60HRate> findAll(String companyId) {
		// TODO Auto-generated method stub
		return null;
	}

}
