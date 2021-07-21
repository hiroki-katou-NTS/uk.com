package nts.uk.ctx.bs.employee.app.command.employee.data.management;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfo;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfoRepository;

/**
 * 社員データ管理情報を取得する
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.基幹.社員.社員データ管理.App.社員データ管理情報を取得する
 * 
 * @author chungnt
 *
 */

@Stateless
public class EmployeeManagementInfor {

	@Inject
	private EmployeeDataMngInfoRepository employeeRepo;

	public Optional<EmployeeDataMngInfo> getEmployeeManagementInfor(String cid, String employeeCode) {

		return employeeRepo.findByEmployeCD(employeeCode, cid);
	}
}
