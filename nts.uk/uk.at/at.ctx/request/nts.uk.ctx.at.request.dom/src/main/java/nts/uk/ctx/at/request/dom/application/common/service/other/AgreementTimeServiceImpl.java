package nts.uk.ctx.at.request.dom.application.common.service.other;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeRequestAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.SEmpHistImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.agreement.AgreementTimeAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.agreement.AgreementTimeImport;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.AgreeOverTimeOutput;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmployment;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;

@Stateless
public class AgreementTimeServiceImpl implements AgreementTimeService {
	
	@Inject
	private AgreementTimeAdapter agreementTimeAdapter;
	
	@Inject
	private EmployeeRequestAdapter employeeAdaptor;
	
	@Inject
	private ClosureRepository closureRepository;
	
	@Inject
	private ClosureEmploymentRepository closureEmploymentRepository;

	@Override
	public AgreeOverTimeOutput getAgreementTime(String companyID, String employeeID) {
		// 社員所属雇用履歴を取得
		SEmpHistImport empHistImport = employeeAdaptor.getEmpHist(companyID, employeeID, GeneralDate.today());
		if(empHistImport==null || empHistImport.getEmploymentCode()==null){
			throw new BusinessException("Msg_426");
		}
		String employmentCD = empHistImport.getEmploymentCode();
		// 締めIDを取得する
		Optional<ClosureEmployment> closureEmployment = closureEmploymentRepository.findByEmploymentCD(companyID, employmentCD);
		if(!closureEmployment.isPresent()){
			throw new BusinessException("Msg_1134");
		}
		Optional<Closure> opClosure = closureRepository.findById(companyID, closureEmployment.get().getClosureId());
		if(!opClosure.isPresent()){
			throw new RuntimeException("khong co closure");
		}
		Closure closure = opClosure.get();
		
		// 当月の年月を取得する
		YearMonth currentMonth = closure.getClosureMonth().getProcessingYm();
		// 翌月=取得した当月.AddMonths(1)
		YearMonth nextMonth = currentMonth.addMonths(1);
		
		AgreementTimeImport detailCurrentMonth = null;
		AgreementTimeImport detailNextMonth = null;
		
		// 36協定時間の取得
		List<AgreementTimeImport> detailCurrentMonthLst = agreementTimeAdapter.getAgreementTime(companyID, Arrays.asList(employeeID), currentMonth, closure.getClosureId());
		if(!CollectionUtil.isEmpty(detailCurrentMonthLst)){
			detailCurrentMonth = detailCurrentMonthLst.get(0);
		}
		// 36協定時間の取得
		List<AgreementTimeImport> detailNextMonthLst = agreementTimeAdapter.getAgreementTime(companyID, Arrays.asList(employeeID), nextMonth, closure.getClosureId());
		if(!CollectionUtil.isEmpty(detailNextMonthLst)){
			detailNextMonth = detailNextMonthLst.get(0);
		}
		
		return new AgreeOverTimeOutput(detailCurrentMonth, detailNextMonth, currentMonth, nextMonth);
	}

}
