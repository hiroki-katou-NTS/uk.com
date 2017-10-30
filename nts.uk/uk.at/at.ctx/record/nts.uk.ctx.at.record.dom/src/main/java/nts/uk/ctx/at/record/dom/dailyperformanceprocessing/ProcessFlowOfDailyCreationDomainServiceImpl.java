package nts.uk.ctx.at.record.dom.dailyperformanceprocessing;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.workrecord.log.EmpCalAndSumExeLog;
import nts.uk.ctx.at.record.dom.workrecord.log.EmpCalAndSumExeLogRepository;
import nts.uk.ctx.at.record.dom.workrecord.log.TargetPerson;
import nts.uk.ctx.at.record.dom.workrecord.log.TargetPersonRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

@Stateless
public class ProcessFlowOfDailyCreationDomainServiceImpl implements ProcessFlowOfDailyCreationDomainService{
	
	@Inject
	private EmpCalAndSumExeLogRepository empCalAndSumExeLogRepository;
	
	@Inject
	private TargetPersonRepository targetPersonRepository;
	
	@Inject
	private CreateDailyResultDomainService createDailyResultDomainService;

	@Override
	public boolean processFlowOfDailyCreation(int executionAttr, BigDecimal startDate, BigDecimal endDate,
			String executionID, String empCalAndSumExecLogID, int reCreateAttr) {
		
		LoginUserContext login = AppContexts.user();
		String companyId = login.companyId();
		String employeeID = AppContexts.user().employeeId();

		//①実行方法を取得する
		List<EmpCalAndSumExeLog> empCalAndSumExeLogs = this.empCalAndSumExeLogRepository.getAllEmpCalAndSumExeLog(companyId);
		
		//②対象者を取得する
		List<TargetPerson> targetPersons = new ArrayList<>();
		//パラメータ「実行区分」＝手動　の場合 (Manual)
		if(executionAttr == 0){
			targetPersons = this.targetPersonRepository.getTargetPersonById(empCalAndSumExecLogID);
		} 
		//パラメータ「実行区分」＝自動　の場合 (Auto)
		else if(executionAttr == 1){
			//ドメインモデル「社員」を取得する
			// TODO - phase 2
		}
		
		// get List personId
		List<String> personIdList = targetPersons.stream().map(f -> {
			return f.getEmployeeId();
		}).collect(Collectors.toList());
		
		//各処理の実行
		//this.createDailyResultDomainService.createDailyResult(personIdList, reCreateAttr, startDate, endDate, executionAttr, empCalAndSumExecLogID);
		
		return false;
	}

}
