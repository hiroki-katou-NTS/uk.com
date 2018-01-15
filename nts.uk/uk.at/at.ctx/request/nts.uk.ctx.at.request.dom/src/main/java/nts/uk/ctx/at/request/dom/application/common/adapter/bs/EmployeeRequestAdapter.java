package nts.uk.ctx.at.request.dom.application.common.adapter.bs;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.ConcurrentEmployeeRequest;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.PesionInforImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.SEmpHistImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.SWkpHistImport;

public interface EmployeeRequestAdapter {
	
	/**
	 * 所属職場を含む上位職場を取得
	 *
	 * @param companyId the company id
	 * @param sid the employee id
	 * @param date the date
	 * @return the list
	 */
	// RequestList #65
	List<String> findWpkIdsBySid(String companyId, String sid, GeneralDate date);

	/**
	 * Get employee Name
	 * @param sID
	 * @return
	 */
	String getEmployeeName(String sID);
	
	
	PesionInforImport getEmployeeInfor(String sID);
	
	
	String empEmail(String sID);
	
	List<ConcurrentEmployeeRequest> getConcurrentEmployee(String companyId, String jobId, GeneralDate baseDate);
	
	SEmpHistImport getEmpHist(String companyId, String employeeId,
			GeneralDate baseDate);
	SWkpHistImport getSWkpHistByEmployeeID(String employeeId, GeneralDate baseDate);
}
