package nts.uk.ctx.at.function.dom.adapter;

import java.util.List;

public interface DivergenceTimeAdapter {

	List<DivergenceTimeAdapterDto> getDivergenceTimeName(String companyId, List<Integer> divTimeIds);
	
	List<DivergenceTimeAdapterDto> findByCompanyAndUseDistination(String companyId, int useDistination);

	List<DivergenceTimeAdapterDto> findByCompanyAndUseDistination(String companyId, int useDistination);

}
