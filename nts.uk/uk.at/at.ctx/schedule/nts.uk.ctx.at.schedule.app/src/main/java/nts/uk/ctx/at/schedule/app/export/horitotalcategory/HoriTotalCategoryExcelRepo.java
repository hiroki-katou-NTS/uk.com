package nts.uk.ctx.at.schedule.app.export.horitotalcategory;

import java.util.List;
/**
 * 
 * @author Hoidd
 *
 */
public interface HoriTotalCategoryExcelRepo {
	/**
	 * 
	 * @param companyId
	 * @return
	 */
	public List<HoriTotalExel> getAll(String companyId);
}
