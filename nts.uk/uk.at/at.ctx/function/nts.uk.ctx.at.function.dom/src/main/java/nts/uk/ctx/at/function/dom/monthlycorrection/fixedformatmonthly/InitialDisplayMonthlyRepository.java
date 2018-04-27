package nts.uk.ctx.at.function.dom.monthlycorrection.fixedformatmonthly;

import java.util.List;
import java.util.Optional;

public interface InitialDisplayMonthlyRepository {

	List<InitialDisplayMonthly> getAllInitialDisMon(String companyID);
	
	Optional<InitialDisplayMonthly> getInitialDisplayMon(String companyID,String monthlyPfmFormatCode);
	
	void deleteInitialDisplayMonthly(String companyID,String monthlyPfmFormatCode);
	
	void updateInitialDisplayMonthly(InitialDisplayMonthly initialDisplayMonthly);
	
	void addInitialDisplayMonthly(InitialDisplayMonthly initialDisplayMonthly);
	
	void deleteByCid(String companyID);
}
