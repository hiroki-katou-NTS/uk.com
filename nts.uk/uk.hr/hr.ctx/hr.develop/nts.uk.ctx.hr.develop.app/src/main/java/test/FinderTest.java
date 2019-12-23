package test;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.hr.develop.dom.empregulationhistory.algorithm.EmploymentRegulationHistoryRepository;
import nts.uk.ctx.hr.develop.dom.empregulationhistory.dto.RegulationHistoryDto;
import test.dto.EmpRegulationHistListDto;
import test.dto.EmploymentRegulationHistoryDto;

@Stateless
public class FinderTest {

	@Inject
	private EmploymentRegulationHistoryRepository repo;
	
	//最新履歴の履歴IDの取得
	public Optional<EmploymentRegulationHistoryDto> getLatestEmpRegulationHist(String cId) {
		Optional<RegulationHistoryDto> domain = repo.getLatestEmpRegulationHist(cId);
		if(domain.isPresent()) {
			return Optional.of(new EmploymentRegulationHistoryDto(domain.get().getHistoryId(), domain.get().getBaseDate()));
		}
		return Optional.empty();
	};
	
	//就業規則の履歴の取得
	public EmpRegulationHistListDto getEmpRegulationHistList(String cId){
		return new EmpRegulationHistListDto(repo.getEmpRegulationHistList(cId));
	};
	
	//就業規則の履歴の追加
	public String addEmpRegulationHist(String cId, GeneralDate startDate) {
		return repo.addEmpRegulationHist(cId, startDate);
	};
	
	//就業規則の履歴の更新
	public void updateEmpRegulationHist(String cId, String historyId, GeneralDate startDate) {
		repo.updateEmpRegulationHist(cId, historyId, startDate);
	};
	
	//就業規則の履歴の削除
	public void removeEmpRegulationHist(String cId, String historyId) {
		repo.removeEmpRegulationHist(cId, historyId);
	};
	
}
