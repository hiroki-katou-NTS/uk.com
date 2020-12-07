package nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.repo;

import java.util.List;

import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.ContractCode;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.EmpInfoTerComAbPeriod;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;

/**
 * 
 * @author dungbn
 * 就業情報端末通信異常期間Repository
 */
public interface EmpInfoTerComAbPeriodRepository {

	// [1]  insert(就業情報端末通信異常期間)
	void insert(EmpInfoTerComAbPeriod empInfoTerComAbPeriod);
	
	// [2]  update(就業情報端末通信異常期間)
	void update(EmpInfoTerComAbPeriod empInfoTerComAbPeriod);
	
	// [３]  delete(就業情報端末通信異常期間)
	void delete(EmpInfoTerComAbPeriod empInfoTerComAbPeriod);
	
	// [4] 期間で取得する
	List<EmpInfoTerComAbPeriod> getInPeriod(ContractCode contractCode, EmpInfoTerminalCode code, GeneralDateTime start, GeneralDateTime end);
}
