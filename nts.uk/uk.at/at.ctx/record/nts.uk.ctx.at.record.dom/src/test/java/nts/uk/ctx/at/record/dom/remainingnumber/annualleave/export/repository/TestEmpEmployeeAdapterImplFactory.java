package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.repository;

import nts.uk.ctx.at.shared.dom.adapter.employee.EmpEmployeeAdapter;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSettingRepository;

/**
 * 社員
 * @author masaaki_jinno
 *
 */
public class TestEmpEmployeeAdapterImplFactory {

	static public EmpEmployeeAdapter create(String caseNo){
		
		switch ( caseNo ){
			case "1": return new TestEmpEmployeeAdapterImpl_1();
			
			default:
				return null;
		}
	}

}
