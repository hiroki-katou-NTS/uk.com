package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.incentive;

import java.util.Optional;

import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkLocationCD;

public interface IncentiveUnitPriceSetRepo {

	public Optional<IncentiveUnitPriceUsageSet> findUsageSet(String companyId);

	public void update(IncentiveUnitPriceUsageSet domain);
	
	public void insert(IncentiveUnitPriceUsageSet domain);

	public Optional<IncentiveUnitPriceSetByCom> findComSet(String companyId);
	
	public void update(IncentiveUnitPriceSetByCom domain);
	
	public void insert(IncentiveUnitPriceSetByCom domain);
	
	public Optional<IncentiveUnitPriceSetByWkp> findWorkPlaceSet(String companyId, String workplaceId);
	
	public void update(IncentiveUnitPriceSetByWkp domain);
	
	public void insert(IncentiveUnitPriceSetByWkp domain);
	
	public Optional<IncentiveUnitPriceSetByWlc> findWorkLocationSet(String companyId, WorkLocationCD locationCd);
	
	public void update(IncentiveUnitPriceSetByWlc domain);
	
	public void insert(IncentiveUnitPriceSetByWlc domain);
}
