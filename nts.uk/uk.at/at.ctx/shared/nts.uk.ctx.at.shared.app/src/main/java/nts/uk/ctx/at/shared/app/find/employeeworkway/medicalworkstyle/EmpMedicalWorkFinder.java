package nts.uk.ctx.at.shared.app.find.employeeworkway.medicalworkstyle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalworkstyle.EmpMedicalWorkFormHisItem;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalworkstyle.EmpMedicalWorkStyleHistory;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalworkstyle.EmpMedicalWorkStyleHistoryRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.pereg.app.ComboBoxObject;
import nts.uk.shr.pereg.app.find.PeregFinder;
import nts.uk.shr.pereg.app.find.PeregQuery;
import nts.uk.shr.pereg.app.find.PeregQueryByListEmp;
import nts.uk.shr.pereg.app.find.dto.DataClassification;
import nts.uk.shr.pereg.app.find.dto.GridPeregDomainBySidDto;
import nts.uk.shr.pereg.app.find.dto.GridPeregDomainDto;
import nts.uk.shr.pereg.app.find.dto.PeregDomainDto;

@Stateless
public class EmpMedicalWorkFinder implements PeregFinder<EmpMedicalWorkDto>{
	
	@Inject
	private EmpMedicalWorkStyleHistoryRepository empMedicalWorkRepo;

	@Override
	public String targetCategoryCode() {
		return "CS00098";
	}

	@Override
	public Class<EmpMedicalWorkDto> dtoClass() {
		return EmpMedicalWorkDto.class;
	}

	@Override
	public DataClassification dataType() {
		return DataClassification.EMPLOYEE;
	}

	@Override
	public PeregDomainDto getSingleData(PeregQuery query) {
		Optional<EmpMedicalWorkStyleHistory> emwHistOp = getEmpMedicalWorkHist(query);
		if(!emwHistOp.isPresent()) return null;
		Optional<EmpMedicalWorkFormHisItem> emwHistItemOp = empMedicalWorkRepo.getItemByHistId(emwHistOp.get().getListDateHistoryItem().get(0).identifier());
		if(!emwHistItemOp.isPresent()) return null;
		return EmpMedicalWorkDto.createEmpMedicalWorkDto(emwHistOp.get().getEmpID(), emwHistOp.get().getListDateHistoryItem().get(0), emwHistItemOp.get());
	}
	
	public Optional<EmpMedicalWorkStyleHistory> getEmpMedicalWorkHist(PeregQuery query) {
		if(query.getInfoId() == null){
			return empMedicalWorkRepo.getHist(query.getEmployeeId(), query.getStandardDate());
		}else{
			return empMedicalWorkRepo.getHistByHistId(query.getInfoId());
		}
	}

	@Override
	public List<PeregDomainDto> getListData(PeregQuery query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ComboBoxObject> getListFirstItems(PeregQuery query) {
		Optional<EmpMedicalWorkStyleHistory> emwHistOp = empMedicalWorkRepo.getHistBySidDesc(AppContexts.user().companyId(), query.getEmployeeId());
		if(!emwHistOp.isPresent()) return new ArrayList<>();
		List<DateHistoryItem> hists = emwHistOp.get().getListDateHistoryItem();
		if(hists.size() == 0) return new ArrayList<>();
		return hists.stream()
				.sorted((a, b) -> b.start().compareTo(a.start()))
				.map(x -> ComboBoxObject.toComboBoxObject(x.identifier(), x.start().toString(), 
						x.end().equals(GeneralDate.max()) 
						//&& query.getCtgType() == 3 
						? "" : x.end().toString()))
				.collect(Collectors.toList());
	}

	@Override
	public List<GridPeregDomainDto> getAllData(PeregQueryByListEmp query) {
		String cid = AppContexts.user().companyId();

		List<GridPeregDomainDto> result = new ArrayList<>();

		List<String> sids = query.getEmpInfos().stream().map(c -> c.getEmployeeId()).collect(Collectors.toList());
		
		query.getEmpInfos().forEach(c -> {
			result.add(new GridPeregDomainDto(c.getEmployeeId(), c.getPersonId(), null));
		});
		
		List<EmpMedicalWorkStyleHistory> emwHistList = empMedicalWorkRepo.getHistBySidsAndCid(sids, query.getStandardDate(), cid);
		
		Map<String, DateHistoryItem> dateHistList = new HashMap<>();

		emwHistList.stream().forEach(c -> {
			dateHistList.put(c.getEmpID(), c.getListDateHistoryItem().get(0));
		});
		
		List<String> historyIds = dateHistList.values().stream().map(c -> c.identifier()).collect(Collectors.toList());
		
		List<EmpMedicalWorkFormHisItem> emwHistItemList = empMedicalWorkRepo.getItemByHistIdList(historyIds);
		
		result.stream().forEach(c -> {
			Optional<EmpMedicalWorkFormHisItem> emwHistItemOp = emwHistItemList.stream()
					.filter(emp -> emp.getEmpID().equals(c.getEmployeeId())).findFirst();
			if (emwHistItemOp.isPresent()) {
				DateHistoryItem dateHistItem = dateHistList.get(c.getEmployeeId());
				c.setPeregDomainDto(dateHistItem != null
						? EmpMedicalWorkDto.createEmpMedicalWorkDto(c.getEmployeeId(), dateHistItem, emwHistItemOp.get())
						: null);
			}
		});
		
		return result;
	}

	@Override
	public List<GridPeregDomainBySidDto> getListData(PeregQueryByListEmp query) {
		// TODO Auto-generated method stub
		return null;
	}

}
