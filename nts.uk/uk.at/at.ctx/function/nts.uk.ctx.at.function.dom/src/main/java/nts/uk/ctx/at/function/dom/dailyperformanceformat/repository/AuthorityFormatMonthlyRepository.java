package nts.uk.ctx.at.function.dom.dailyperformanceformat.repository;

import java.util.List;

import nts.uk.ctx.at.function.dom.dailyperformanceformat.AuthorityFomatMonthly;

public interface AuthorityFormatMonthlyRepository {
	
	List<AuthorityFomatMonthly> getMonthlyDetail(String companyId, String dailyPerformanceFormatCode);
	
	void update(AuthorityFomatMonthly authorityFomatMonthly);

	void add(List<AuthorityFomatMonthly> authorityFomatMonthlies);
	
	void remove(AuthorityFomatMonthly authorityFomatMonthly);
}
