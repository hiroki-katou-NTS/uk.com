package nts.uk.ctx.exio.dom.exo.exoutsummaryservice;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

@Stateless
public interface RegulationInfoEmployeeAdapter {

	List<RegulationInfoEmployeeImport> findEmployees(RegulationInfoEmployeeQueryImport importQuery);
	
	/**
	 * 外部受入からRQ18.「会社ID」「社員コード」より社員基本情報を取得を実行する
	 * @param cid
	 * @param sid
	 * @return
	 */
	public Optional<EmployeeInforExoImport> getEmployeeInforByCid(String cid, String sid);
}
