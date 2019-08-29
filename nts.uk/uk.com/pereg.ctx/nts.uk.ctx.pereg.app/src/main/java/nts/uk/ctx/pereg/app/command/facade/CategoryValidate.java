package nts.uk.ctx.pereg.app.command.facade;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.businesstype.BusinessTypeOfEmployeeHistory;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.businesstype.repository.BusinessTypeEmpOfHistoryRepository;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCard;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCardRepository;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingCondition;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionRepository;
import nts.uk.ctx.bs.employee.dom.classification.affiliate.AffClassHistory;
import nts.uk.ctx.bs.employee.dom.classification.affiliate.AffClassHistoryRepository;
import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyHist;
import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyHistByEmployee;
import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyHistItem;
import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyHistRepository;
import nts.uk.ctx.bs.employee.dom.employment.history.DateHistItem;
import nts.uk.ctx.bs.employee.dom.employment.history.EmploymentHistoryRepository;
import nts.uk.ctx.bs.employee.dom.jobtitle.affiliate.AffJobTitleHistory;
import nts.uk.ctx.bs.employee.dom.jobtitle.affiliate.AffJobTitleHistoryRepository;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistory;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistoryRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.pereg.app.ItemValue;
import nts.uk.shr.pereg.app.command.MyCustomizeException;
import nts.uk.shr.pereg.app.command.PeregInputContainerCps003;

@Stateless
public class CategoryValidate {
	@Inject
	private AffCompanyHistRepository affCompanyHistRepository;
	
	@Inject
	private AffClassHistoryRepository affClassHistRepo;
	
	@Inject
	private EmploymentHistoryRepository employmentHistoryRepo;
	
	@Inject
	private AffJobTitleHistoryRepository affJobTitleRepo;
	
	@Inject
	private AffWorkplaceHistoryRepository affWrkplcHistRepo;
	
	@Inject
	private WorkingConditionRepository wcRepo;
	
	@Inject
	private BusinessTypeEmpOfHistoryRepository typeEmployeeOfHistoryRepos;
	
	@Inject
	private StampCardRepository stampCardRepo;
	
	private static final String JP_SPACE = "　";
	
	private final static List<String> itemSpecialLst = Arrays.asList("IS00003", "IS00004","IS00015","IS00016");
	
	public void historyValidate(List<MyCustomizeException> result, List<PeregInputContainerCps003> containerAdds, GeneralDate baseDate, int modeUpdate) {
		String cid = AppContexts.user().companyId();
		if (modeUpdate == 2) {
			if (containerAdds.get(0).getInputs().getCategoryCd().equals("CS00003")) {
				Map<String, String> pidSids = containerAdds.stream()
						.collect(Collectors.toMap(c -> c.getPersonId(), c -> c.getEmployeeId()));
				List<AffCompanyHist> affCompanyHistLst = this.affCompanyHistRepository
						.getAffComHistOfEmployeeListAndBaseDateV2(new ArrayList<>(pidSids.values()), baseDate);
				List<String> sidError = new ArrayList<>();
				for (AffCompanyHist affCompanyHist : affCompanyHistLst) {
					PeregInputContainerCps003 container = containerAdds.stream()
							.filter(c -> c.getPersonId().equals(affCompanyHist.getPId())).findFirst().get();
					ItemValue start = container.getInputs().getItems().stream().filter(c -> c.itemCode().equals("IS00020")).findFirst().get();
					ItemValue end = container.getInputs().getItems().stream().filter(c -> c.itemCode().equals("IS00021")).findFirst().get();
					AffCompanyHistByEmployee employee = affCompanyHist.getAffCompanyHistByEmployee(container.getEmployeeId());
					Optional<AffCompanyHistItem> historyOpt = employee.getHistory();
					if(historyOpt.isPresent()) {
						if(end.valueAfter() != null) {
							sidError.add(container.getEmployeeId());
							int index = containerAdds.indexOf(container);
							containerAdds.remove(index);
							continue;
						}
						
						if(modeUpdate == 2) {
							if(GeneralDate.fromString(start.valueAfter(), "yyyy/MM/dd").before(historyOpt.get().start())) {
								sidError.add(container.getEmployeeId());
								int index = containerAdds.indexOf(container);
								containerAdds.remove(index);
								continue;
							}
						}
					}
				}
				if (!sidError.isEmpty()) {
					MyCustomizeException exeption = new MyCustomizeException("Msg_852", sidError, "");
					result.add(exeption);
				}
			}
		}

		if(CollectionUtil.isEmpty(containerAdds)) return;
		if(containerAdds.get(0).getInputs().getCategoryCd().equals("CS00004")) {
			List<String> sids = containerAdds.stream().map(c -> c.getEmployeeId()).collect(Collectors.toList());
			List<AffClassHistory> sidExist = this.affClassHistRepo
					.getHistoryByEmployeeListWithBaseDate(cid, sids, baseDate);
			List<String> sidError = new ArrayList<>();
			for(AffClassHistory history: sidExist) {
				PeregInputContainerCps003 container = containerAdds.stream().filter(c -> c.getEmployeeId().equals(history.getEmployeeId())).findFirst().get();
				ItemValue start = container.getInputs().getItems().stream().filter(c -> c.itemCode().equals("IS00026")).findFirst().get();
				ItemValue end = container.getInputs().getItems().stream().filter(c -> c.itemCode().equals("IS00027")).findFirst().get();
				DateHistoryItem historyItem = history.getPeriods().get(0);
				if(!end.valueAfter().equals("9999/12/31")) {
					sidError.add(history.getEmployeeId());
					int index = containerAdds.indexOf(container);
					containerAdds.remove(index);
					continue;
				}
				
				if(modeUpdate == 2) {
					if(GeneralDate.fromString(start.valueAfter(), "yyyy/MM/dd").before(historyItem.start())) {
						sidError.add(history.getEmployeeId());
						int index = containerAdds.indexOf(container);
						containerAdds.remove(index);
						continue;
					}
				}

			}
			if(!sidError.isEmpty()) {
				MyCustomizeException exeption = new MyCustomizeException("Msg_102", sidError, "");
				result.add(exeption);
			}
		}
		
		if(CollectionUtil.isEmpty(containerAdds)) return;
		if(containerAdds.get(0).getInputs().getCategoryCd().equals("CS00014")) {
			List<String> sidError = new ArrayList<>();
			List<String> sids = containerAdds.stream().map(c -> c.getEmployeeId()).collect(Collectors.toList());
			Map<String, DateHistItem> dateHistBySid = this.employmentHistoryRepo.getBySIdAndate(sids, baseDate);
			for (Map.Entry<String, DateHistItem> entry : dateHistBySid.entrySet()) {
				PeregInputContainerCps003 container = containerAdds.stream().filter(c -> c.getEmployeeId().equals(entry.getKey())).findFirst().get();
				ItemValue start = container.getInputs().getItems().stream().filter(c -> c.itemCode().equals("IS00066")).findFirst().get();
				ItemValue end = container.getInputs().getItems().stream().filter(c -> c.itemCode().equals("IS00067")).findFirst().get();
				if(!end.valueAfter().equals("9999/12/31")) {
					sidError.add(entry.getKey());
					int index = containerAdds.indexOf(container);
					containerAdds.remove(index);
					continue;
				}
				if(modeUpdate == 2) {
					if(GeneralDate.fromString(start.valueAfter(), "yyyy/MM/dd").before(entry.getValue().getPeriod().start())) {
						sidError.add(entry.getKey());
						int index = containerAdds.indexOf(container);
						containerAdds.remove(index);
						continue;
					}
				}
			}
			if(!sidError.isEmpty()) {
				MyCustomizeException exeption = new MyCustomizeException("Msg_102", sidError, "");
				result.add(exeption);
			}
		}
		if(CollectionUtil.isEmpty(containerAdds)) return;
		if(containerAdds.get(0).getInputs().getCategoryCd().equals("CS00016")) {
			List<String> sids = containerAdds.stream().map(c -> c.getEmployeeId()).collect(Collectors.toList());
			List<AffJobTitleHistory> sidExits = this.affJobTitleRepo.getListByListHidSid(sids, baseDate);
			List<String> sidError = new ArrayList<>();
			for(AffJobTitleHistory history: sidExits) {
				PeregInputContainerCps003 container = containerAdds.stream().filter(c -> c.getEmployeeId().equals(history.getEmployeeId())).findFirst().get();
				ItemValue start = container.getInputs().getItems().stream().filter(c -> c.itemCode().equals("IS00077")).findFirst().get();
				ItemValue end = container.getInputs().getItems().stream().filter(c -> c.itemCode().equals("IS00078")).findFirst().get();
				DateHistoryItem historyItem = history.getHistoryItems().get(0);
				if(!end.valueAfter().equals("9999/12/31")) {
					sidError.add(history.getEmployeeId());
					int index = containerAdds.indexOf(container);
					containerAdds.remove(index);
					continue;
				}
				if(modeUpdate == 2) {
					if(GeneralDate.fromString(start.valueAfter(), "yyyy/MM/dd").before(historyItem.start())) {
						sidError.add(history.getEmployeeId());
						int index = containerAdds.indexOf(container);
						containerAdds.remove(index);
						continue;
					}
				}

			}
			if(!sidError.isEmpty()) {
				MyCustomizeException exeption = new MyCustomizeException("Msg_102", sidError, "");
				result.add(exeption);
			}
		}
		if(CollectionUtil.isEmpty(containerAdds)) return;
		if(containerAdds.get(0).getInputs().getCategoryCd().equals("CS00017")) {
			List<String> sids = containerAdds.stream().map(c -> c.getEmployeeId()).collect(Collectors.toList());
			List<AffWorkplaceHistory> sidExits =  this.affWrkplcHistRepo
					.getWorkplaceHistoryByEmpIdsAndDate(baseDate, sids);
			List<String> sidError = new ArrayList<>();
			for(AffWorkplaceHistory history: sidExits) {
				PeregInputContainerCps003 container = containerAdds.stream().filter(c -> c.getEmployeeId().equals(history.getEmployeeId())).findFirst().get();
				ItemValue start = container.getInputs().getItems().stream().filter(c -> c.itemCode().equals("IS00082")).findFirst().get();
				ItemValue end = container.getInputs().getItems().stream().filter(c -> c.itemCode().equals("IS00083")).findFirst().get();
				DateHistoryItem historyItem = history.getHistoryItems().get(0);
				if(!end.valueAfter().equals("9999/12/31")) {
					sidError.add(history.getEmployeeId());
					int index = containerAdds.indexOf(container);
					containerAdds.remove(index);
					continue;
				}
				
				if(modeUpdate == 2) {
					if(GeneralDate.fromString(start.valueAfter(), "yyyy/MM/dd").before(historyItem.start())) {
						sidError.add(history.getEmployeeId());
						int index = containerAdds.indexOf(container);
						containerAdds.remove(index);
						continue;
					}
				}
			}
			if(!sidExits.isEmpty()) {
				MyCustomizeException exeption = new MyCustomizeException("Msg_102", sidError, "");
				result.add(exeption);
			}
		}
		
		if(CollectionUtil.isEmpty(containerAdds)) return;
		if (containerAdds.get(0).getInputs().getCategoryCd().equals("CS00020")
				|| containerAdds.get(0).getInputs().getCategoryCd().equals("CS00070")) {
			List<String> sids = containerAdds.stream().map(c -> c.getEmployeeId()).collect(Collectors.toList());
			List<WorkingCondition> sidExits =  this.wcRepo
					.getBySidsAndCid(sids, baseDate, cid);
			List<String> sidError = new ArrayList<>();
			for(WorkingCondition history: sidExits) {
				PeregInputContainerCps003 container = containerAdds.stream().filter(c -> c.getEmployeeId().equals(history.getEmployeeId())).findFirst().get();
				ItemValue start = container.getInputs().getItems().stream().filter(c -> c.itemCode().equals("IS00120")).findFirst().get();
				ItemValue end = container.getInputs().getItems().stream().filter(c -> c.itemCode().equals("IS00121")).findFirst().get();
				DateHistoryItem historyItem = history.getDateHistoryItem().get(0);
				if(!end.valueAfter().equals("9999/12/31")) {
					sidError.add(history.getEmployeeId());
					int index = containerAdds.indexOf(container);
					containerAdds.remove(index);
					continue;
				}
				if(modeUpdate == 2) {
					if(GeneralDate.fromString(start.valueAfter(), "yyyy/MM/dd").before(historyItem.start())) {
						sidError.add(history.getEmployeeId());
						int index = containerAdds.indexOf(container);
						containerAdds.remove(index);
						continue;
					}
				}
			}
			if(!sidError.isEmpty()) {
				MyCustomizeException exeption = new MyCustomizeException("Msg_102", sidError, "");
				result.add(exeption);
			}
		}
		
		if(CollectionUtil.isEmpty(containerAdds)) return;
		if (containerAdds.get(0).getInputs().getCategoryCd().equals("CS00070")) {
			List<String> sids = containerAdds.stream().map(c -> c.getEmployeeId()).collect(Collectors.toList());
			List<WorkingCondition> sidExits =  this.wcRepo
					.getBySidsAndCid(sids, baseDate, cid);
			List<String> sidError = new ArrayList<>();
			for(WorkingCondition history: sidExits) {
				PeregInputContainerCps003 container = containerAdds.stream().filter(c -> c.getEmployeeId().equals(history.getEmployeeId())).findFirst().get();
				ItemValue start = container.getInputs().getItems().stream().filter(c -> c.itemCode().equals("IS00781")).findFirst().get();
				ItemValue end = container.getInputs().getItems().stream().filter(c -> c.itemCode().equals("IS00782")).findFirst().get();
				DateHistoryItem historyItem = history.getDateHistoryItem().get(0);
				if(!end.valueAfter().equals("9999/12/31")) {
					sidError.add(history.getEmployeeId());
					int index = containerAdds.indexOf(container);
					containerAdds.remove(index);
					continue;
				}
				if(modeUpdate == 2) {
					if(GeneralDate.fromString(start.valueAfter(), "yyyy/MM/dd").before(historyItem.start())) {
						sidError.add(history.getEmployeeId());
						int index = containerAdds.indexOf(container);
						containerAdds.remove(index);
						continue;
					}
				}
			}
			if(!sidError.isEmpty()) {
				MyCustomizeException exeption = new MyCustomizeException("Msg_102", sidError, "");
				result.add(exeption);
			}
		}
		
		if(CollectionUtil.isEmpty(containerAdds)) return;
		if (containerAdds.get(0).getInputs().getCategoryCd().equals("CS00021")) {
			List<String> sids = containerAdds.stream().map(c -> c.getEmployeeId()).collect(Collectors.toList());
			List<BusinessTypeOfEmployeeHistory> sidExits =  this.typeEmployeeOfHistoryRepos.findByBaseDate(baseDate, sids);
			List<String> sidError = new ArrayList<>();
			for(BusinessTypeOfEmployeeHistory history: sidExits) {
				PeregInputContainerCps003 container = containerAdds.stream().filter(c -> c.getEmployeeId().equals(history.getEmployeeId())).findFirst().get();
				ItemValue start = container.getInputs().getItems().stream().filter(c -> c.itemCode().equals("IS00255")).findFirst().get();
				ItemValue end = container.getInputs().getItems().stream().filter(c -> c.itemCode().equals("IS00256")).findFirst().get();
				DateHistoryItem historyItem = history.getHistory().get(0);
				if(!end.valueAfter().equals("9999/12/31")) {
					sidError.add(history.getEmployeeId());
					int index = containerAdds.indexOf(container);
					containerAdds.remove(index);
					continue;
				}
				if(modeUpdate == 2) {
					if(GeneralDate.fromString(start.valueAfter(), "yyyy/MM/dd").before(historyItem.start())) {
						sidError.add(history.getEmployeeId());
						int index = containerAdds.indexOf(container);
						containerAdds.remove(index);
						continue;
					}
				}
			}
			
			if(!sidError.isEmpty()) {
				MyCustomizeException exeption = new MyCustomizeException("Msg_102", sidError, "");
				result.add(exeption);
			}
		}
		
	}
	
	/**
	 * CS0002
	 * @param indexs
	 * @param result
	 * @param containerAdds
	 */
	public void validateSpaceCS0002(List<String> indexs, List<MyCustomizeException> result, List<PeregInputContainerCps003> containerAdds) {
		if(containerAdds.get(0).getInputs().getCategoryCd().equals("CS00002")) {
			for(int i = 0; i < containerAdds.size(); i++ ) {
				if(!containerAdds.isEmpty()) {
					List<MyCustomizeException> errorlst = new ArrayList<>();
					for(ItemValue item: containerAdds.get(i).getInputs().getItems()) {
						MyCustomizeException exception = validateItemOfCS0002(item, containerAdds.get(i).getEmployeeId());
						if(exception != null) {
							errorlst.add(exception);
							
						}
					}
					if(errorlst.size() > 0) {
						result.addAll(errorlst);
						indexs.add(String.valueOf(i));
					}	
				}
			}
		}
	}
	
	public void validateItemOfCS0069(List<MyCustomizeException> result, List<PeregInputContainerCps003> containerAdds){
		if (containerAdds.get(0).getInputs().getCategoryCd().equals("CS00069")) {
			List<String> indexs = new ArrayList<>();
			List<String> sidErrors = new ArrayList<>();
			String contractCode = AppContexts.user().contractCode();
			for (int i = 0; i < containerAdds.size(); i++) {
				if (!containerAdds.isEmpty()) {
					for (ItemValue item : containerAdds.get(i).getInputs().getItems()) {
						Optional<StampCard> stampCard = this.stampCardRepo.getByCardNoAndContractCode(item.valueAfter(),
								contractCode);
						if (stampCard.isPresent()) {
							sidErrors.add(containerAdds.get(i).getEmployeeId());
						}
					}
					if (sidErrors.size() > 0) {
						indexs.add(String.valueOf(i));
					}
				}
			}
			if (sidErrors.size() > 0) {
				result.add(new MyCustomizeException("Msg_1106", sidErrors));
			}

			if (!indexs.isEmpty()) {
				indexs.stream().forEach(i -> {
					containerAdds.remove(Integer.valueOf(i).intValue());
				});
			}
		}

	}
	
	/**
	 * validate những item đặc biệt của category CS0002
	 * return true = error, false = no error
	 * validateItemOfCS0002
	 * new MyCustomizeException("Msg_852", sids, "所属会社履歴項目の期間")
	 * @param itemDto
	 * @param value
	*/
	private MyCustomizeException validateItemOfCS0002(ItemValue itemValue, String sid){
		for (String itemCode : itemSpecialLst) {
			if (itemValue.itemCode().equals(itemCode)) {
				if (itemValue.valueAfter().startsWith(JP_SPACE) ||itemValue.valueAfter().endsWith(JP_SPACE)
						|| !itemValue.valueAfter().contains(JP_SPACE)) {
					return new MyCustomizeException("Msg_924", Arrays.asList(sid), itemValue.itemName());
				}
			}
		}
		return null;
	}
}
