package nts.uk.ctx.at.record.pub.divergencetime;

import java.util.List;

public interface DivergenceTimePub {
	List<DivergenceTimePubDto> getDivergenceTimeName(String companyId, List<Integer> divTimeIds);
	
	List<DivergenceTimePubDto> findByCompanyAndUseDistination(String comapanyId, int useDistination);
}
