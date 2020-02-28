package nts.uk.ctx.hr.develop.dom.humanresourcedev.hryear;


import java.util.Optional;

public interface HrPeriodRegulationRepository {
	
	Optional<HrPeriodRegulation> getByKeyAndDate(String companyId,String historyId);
	
}
