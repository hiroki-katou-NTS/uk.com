package nts.uk.screen.com.app.repository.company;

import java.util.List;

/**
 * Company Query Repository
 * @author yennth
 *
 */
public interface CompanyQueryRepository {
	/**
	 * 
	 * @return
	 */
	List<CompanyQueryDto> findAll();
}
