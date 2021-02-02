package nts.uk.ctx.at.shared.dom.remainingnumber.test;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.yearholidaygrant.LengthServiceTbl;

@Stateless
public interface LengthServiceTest {

	/**
	* find by companyId 
	*/
	@Inject
	public List<LengthServiceTbl> findByCompanyId(String companyId);
	
}

