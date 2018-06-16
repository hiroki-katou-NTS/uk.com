package nts.uk.ctx.at.shared.dom.remainingnumber.paymana;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.ComSubstVacation;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.ComSubstVacationRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.EmpSubstVacation;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.EmpSubstVacationRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class FurikyuMngDataExtractionService {
	@Inject
	private SubstitutionOfHDManaDataRepository substitutionOfHDManaDataRepository;
	
	@Inject
	private PayoutManagementDataRepository payoutManagementDataRepository;
	
	@Inject
	private PayoutSubofHDManaRepository payoutSubofHDManaRepository;
	
	@Inject
	private SysEmploymentHisAdapter sysEmploymentHisAdapter;
	
	@Inject
	private EmpSubstVacationRepository empSubstVacationRepository;
	
	@Inject
	private ComSubstVacationRepository comSubstVacationRepository;
	
	@Inject 
	private ClosureEmploymentRepository closureEmploymentRepository;
	
	public FurikyuMngDataExtractionData getFurikyuMngDataExtraction(String sid, GeneralDate startDate, GeneralDate endDate, boolean isPeriod) {
		List<PayoutManagementData> payoutManagementData;
		List<PayoutSubofHDManagement> payoutSubofHDManagementLinkToPayout = new ArrayList<PayoutSubofHDManagement>();
		List<SubstitutionOfHDManagementData> substitutionOfHDManagementData;
		List<PayoutSubofHDManagement> payoutSubofHDManagementLinkToSub = new ArrayList<PayoutSubofHDManagement>();
		Double numberOfDayLeft;
		int expirationDate;
		Integer closureId;
		String cid = AppContexts.user().companyId();
		String empCD = null;
		boolean haveEmploymentCode = false;
		
		// select 過去の結果
		if(isPeriod) {
			payoutManagementData = payoutManagementDataRepository.getBySidPeriodAndInSub(sid, startDate, endDate);
			substitutionOfHDManagementData = substitutionOfHDManaDataRepository.getBySidPeriodAndInPayout(sid, startDate, endDate);
		// select 現在の残数状況
		} else {
			payoutManagementData = payoutManagementDataRepository.getBySidStateAndInSub(sid);
			substitutionOfHDManagementData = substitutionOfHDManaDataRepository.getBySidRemainDayAndInPayout(sid);
		}
		
		if (!payoutManagementData.isEmpty()){
			List<String> listPayoutID = payoutManagementData.stream().map(x ->{
				return x.getPayoutId();
			}).collect(Collectors.toList());
			
			payoutSubofHDManagementLinkToPayout = payoutSubofHDManaRepository.getByListPayoutID(listPayoutID);
		}
		
		if (!substitutionOfHDManagementData.isEmpty()){
			List<String> listSubID = substitutionOfHDManagementData.stream().map(x ->{
				return x.getSubOfHDID();
			}).collect(Collectors.toList());
			
			payoutSubofHDManagementLinkToSub = payoutSubofHDManaRepository.getByListSubID(listSubID);
		}
		
		if (sysEmploymentHisAdapter.findSEmpHistBySid(cid, sid, GeneralDate.legacyDate(new Date())).isPresent()) {
			empCD = sysEmploymentHisAdapter.findSEmpHistBySid(cid, sid, GeneralDate.legacyDate(new Date())).get().getEmploymentCode();
		}
		
		if(empCD != null) {
			haveEmploymentCode = true;
		}
		
		numberOfDayLeft = getNumberOfDayLeft(sid);
		expirationDate = getExpirationDate(sid, empCD);
		closureId = getClosureId(sid, empCD);
		
		return new FurikyuMngDataExtractionData(payoutManagementData, substitutionOfHDManagementData, payoutSubofHDManagementLinkToPayout, payoutSubofHDManagementLinkToSub, expirationDate, numberOfDayLeft, closureId, haveEmploymentCode);
	}
	
	public Double getNumberOfDayLeft(String sID) {
		String cid = AppContexts.user().companyId();
		Double totalUnUseDay = 0.0;
		Double  totalUndeliveredDay = 0.0;
		
		List<PayoutManagementData> payoutManagementData = payoutManagementDataRepository.getSidWithCod(cid, sID, 0);
		List<SubstitutionOfHDManagementData> substitutionOfHDManagementData = substitutionOfHDManaDataRepository.getBysiDRemCod(cid, sID);
		
		for (PayoutManagementData item : payoutManagementData) {
			totalUnUseDay += item.getUnUsedDays().v();
		}
		
		for (SubstitutionOfHDManagementData item : substitutionOfHDManagementData) {
			totalUndeliveredDay += item.getRemainDays().v();
		}
		
		return totalUnUseDay - totalUndeliveredDay;
	}
	
	public int getExpirationDate(String sid, String empCD) {
		String cid = AppContexts.user().companyId();
		EmpSubstVacation empSubstVacation;
		ComSubstVacation comSubstVacation;
		int expirationDate = 0;
		
		// get scd
		if (empCD != null) {
			if(empSubstVacationRepository.findById(cid, empCD).isPresent()) {
				empSubstVacation = empSubstVacationRepository.findById(cid, empCD).get();
				expirationDate = empSubstVacation.getSetting().getExpirationDate().value;
			} else if (comSubstVacationRepository.findById(cid).isPresent()){
				comSubstVacation = comSubstVacationRepository.findById(cid).get();
				expirationDate = comSubstVacation.getSetting().getExpirationDate().value;
			}
		} else {
			if (comSubstVacationRepository.findById(cid).isPresent()){
				comSubstVacation = comSubstVacationRepository.findById(cid).get();
				expirationDate = comSubstVacation.getSetting().getExpirationDate().value;
			}
		}
		
		return expirationDate;
	}
	
	public Integer getClosureId(String sid, String empCD) {
		String cid = AppContexts.user().companyId();
		Integer closureId = null;
		
		if (empCD != null) {
			if(closureEmploymentRepository.findByEmploymentCD(cid, empCD).isPresent()) {
				closureId = closureEmploymentRepository.findByEmploymentCD(cid, empCD).get().getClosureId();
			}
		}
		
		return closureId;
	}
}
