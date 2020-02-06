package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empsocialinsgradehis;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.shared.dom.adapter.employment.SEmpHistImport;
import nts.uk.ctx.pr.shared.dom.adapter.employment.SystemEmploymentAdapter;
import nts.uk.ctx.pr.shared.dom.adapter.wageprovision.processdatecls.CurrentProcessDateAdapter;
import nts.uk.ctx.pr.shared.dom.adapter.wageprovision.processdatecls.CurrentProcessDateImport;
import nts.uk.ctx.pr.shared.dom.adapter.wageprovision.processdatecls.EmpTiedProYearAdapter;
import nts.uk.ctx.pr.shared.dom.adapter.wageprovision.processdatecls.EmpTiedProYearImport;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.arc.time.calendar.period.YearMonthPeriod;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

@Stateless
public class EmpSocialInsGradeService {
    @Inject
    private EmpTiedProYearAdapter empTiedProYearAdapter;

    @Inject
    private CurrentProcessDateAdapter currentProcessDateAdapter;

    @Inject
    private SystemEmploymentAdapter systemEmploymentAdapter;

    @Inject
    private EmpSocialInsGradeRepository repository;

    private static final String PASS_HISTORY = "過去の履歴";
    private static final String PRESENT_HISTORY = "現在の給与処理に適用される履歴";
    private static final String FUTURE_HISTORY = "未来の履歴";

    /**
     * 選択履歴が次回給与適用等級か判定する
     *
     * @param history  EmpSocialInsGradeHis
     * @param baseDate the standard date
     * @return current grade
     */
    public String getCurrentGrade(EmpSocialInsGradeHis history, GeneralDate baseDate) {
        String cid = AppContexts.user().companyId();
        // get employment code
        String employmentCode = systemEmploymentAdapter.findSEmpHistBySid(cid, history.getEmployeeId(), baseDate)
                .map(SEmpHistImport::getEmploymentCode).orElse("");
        // get current year month from employment code
        YearMonth currentYm = getProcessYear(cid, employmentCode);

        YearMonthPeriod period = history.items().get(0).span();
        if (period.start().greaterThan(currentYm)) {
            return FUTURE_HISTORY;
        } else if (period.end().lessThan(currentYm)) {
            return PASS_HISTORY;
        } else {
            return PRESENT_HISTORY;
        }
    }

    /**
     * 選択履歴が次回給与適用等級か判定する
     *
     * @param histories list EmpSocialInsGradeHis
     * @param baseDate  the standard date
     * @return map (key, value) = (sid, currentGrade)
     */
    public Map<String, String> getMapCurrentGrade(Map<String,  List<EmpSocialInsGradeHis>> histories, GeneralDate baseDate) {
    	
        String cid = AppContexts.user().companyId();

        // Get map<sid, employment hist>
        Map<String, SEmpHistImport> mapEmpHists = systemEmploymentAdapter.findSEmpHistByListSid(cid, new ArrayList<>(histories.keySet()), baseDate);

        // List<employment code> to find current year month
        List<String> employmentCodes = mapEmpHists.values().stream().distinct()
                .map(SEmpHistImport::getEmploymentCode).collect(Collectors.toList());

        // Map<employment code, current year month>
        Map<String, YearMonth> mapYearMonth = getMapProcessYear(cid, employmentCodes);

        // Map<sid, currentGrade>
        Map<String, String> result = new HashMap<>();
        
        histories.keySet().forEach(k -> {
        	
            String currentGrade;
            
            YearMonthPeriod period = histories.get(k).get(0).items().get(0).span();
            
            String employmentCode = mapEmpHists.containsKey(k) ? (mapEmpHists.get(k).getEmploymentCode()== null? "":mapEmpHists.get(k).getEmploymentCode())  : "";
            
            YearMonth currentYm = mapYearMonth.get(employmentCode);
            
            if (period.start().greaterThan(currentYm)) {
            	
                currentGrade = FUTURE_HISTORY;
                
            } else if (period.end().lessThan(currentYm)) {
            	
                currentGrade = PASS_HISTORY;
                
            } else {
            	
                currentGrade = PRESENT_HISTORY;
                
            }
            
            result.put(k, currentGrade);
            
        });
        
        return result;
    }

    /**
     * 雇用から現在処理年月を取得する
     *
     * @param employmentCode the employment code
     * @return current processing date
     */
    public YearMonth getProcessYear(String cid, String employmentCode) {
        // Get processing category no
        Optional<EmpTiedProYearImport> empTiedProYear = empTiedProYearAdapter.getEmpTiedProYearByEmployment(cid, employmentCode);
        if (empTiedProYear.isPresent()) {
            // Get current year month from processing category no
            Optional<CurrentProcessDateImport> currProcessDates = currentProcessDateAdapter.getCurrProcessDateByKey(cid, empTiedProYear.get().getProcessCateNo());
            if (currProcessDates.isPresent()) return currProcessDates.get().getGiveCurrTreatYear();
        }
        return GeneralDate.today().yearMonth();
    }

    /**
     * 雇用から現在処理年月を取得する
     *
     * @param employmentCodes list employment code
     * @return map (key, value) = (employment code, current processing date)
     */
    public Map<String, YearMonth> getMapProcessYear(String cid, List<String> employmentCodes) {
    	
    	Map<String, YearMonth> results = new HashMap<String, YearMonth>();
    	
    	employmentCodes.stream().forEach(c ->{
    		
    		YearMonth yearMonth =  getProcessYear(cid, c);
    		
    		results.put(c, yearMonth);
    		
    	});
    	
        return results;
    }

    /**
     * Insert 2 domains to database
     *
     * @param history 社員社会保険等級履歴
     * @param info    社員社会保険等級情報
     */
    public void add(EmpSocialInsGradeHis history, EmpSocialInsGradeInfo info) {
        if (history.getYearMonthHistoryItems().isEmpty()) {
            return;
        }
        // Insert last element
        YearMonthHistoryItem lastItem = history.items().get(history.items().size() - 1);
        history.items().clear();
        history.items().add(lastItem);

        repository.add(history, info);
    }
    
    /**
     * Insert 2 domains to database
     * cps003
     * @param history 社員社会保険等級履歴
     * @param info    社員社会保険等級情報
     */
    public void addAll(List<EmpSocialInsGradeHisInter> params) {
    	
    	List<EmpSocialInsGradeHisInter> inserts = new ArrayList<>();
    	
		params.stream().forEach(c -> {

			EmpSocialInsGradeHis history = c.getHistory();
			
			if (history.getYearMonthHistoryItems().isEmpty()) {
				return;
			}
			
			// Insert last element
	        YearMonthHistoryItem lastItem = history.items().get(history.items().size() - 1);
	       
	        history.items().clear();
	        
	        history.items().add(lastItem);
	        
	        inserts.add(new EmpSocialInsGradeHisInter(history, c.getInfo(), null));
	        
		});
		
		if(!inserts.isEmpty()) {
			
			 repository.addAll(inserts);
			
		}
    }

    /**
     * Update 2 domains to database
     *
     * @param history 社員社会保険等級履歴
     * @param info    社員社会保険等級情報
     * @param item itemToBeUpdate
     */
    public void update(EmpSocialInsGradeHis history, EmpSocialInsGradeInfo info, YearMonthHistoryItem item) {
        if (history.getYearMonthHistoryItems().isEmpty()) {
            return;
        }
        YearMonthHistoryItem itemToBeUpdate = history.items()
                .stream()
                .filter(e -> e.identifier().equals(item.identifier()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Invalid QqsmtSyahoGraHist"));

        history.items().clear();
        history.items().add(itemToBeUpdate);

        repository.update(history, info);
    }
    
    /**
     * Update 2 domains to database
     * cps003
     * @param history 社員社会保険等級履歴
     * @param info    社員社会保険等級情報
     * @param item itemToBeUpdate
     */
    public void updateAll(List<EmpSocialInsGradeHisInter> params) {
    	
    	List<EmpSocialInsGradeHisInter> update = new ArrayList<>();
    	
    	params.stream().forEach(c ->{
    		
    		 EmpSocialInsGradeHis history = c.getHistory();
    		 
    		 if (history.getYearMonthHistoryItems().isEmpty()) {
    			 
    	            return;
    	            
    	        }
    		 
    		 Optional<YearMonthHistoryItem> itemToBeUpdate = history.items()
    				 
    	                .stream()
    	                
    	                .filter(e -> e.identifier().equals(c.getItem().identifier()))
    	                
    	                .findFirst();
    		 
    		 if(itemToBeUpdate.isPresent()) {
    			 
    			 history.items().clear();
     	        
        		 history.items().add(itemToBeUpdate.get());
        		 
        		 update.add(new EmpSocialInsGradeHisInter(history, c.getInfo(), null));
    		 }
    		 
    	});
        
        
    	if(!update.isEmpty()) {
    		
    		repository.updateAll(update);
    	}
        

        
    }
}