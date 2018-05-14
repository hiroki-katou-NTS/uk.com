package nts.uk.ctx.pereg.app.find.employee;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCard;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCardRepository;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfoRepository;
import nts.uk.ctx.bs.employee.dom.employeeinfo.service.EmployeeBusinessService;
import nts.uk.ctx.sys.auth.dom.user.UserRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class EmployeeInfoFinder {

	@Inject
	private EmployeeDataMngInfoRepository empDataRepo;

	@Inject
	private EmployeeBusinessService employeeBusiness;

	@Inject
	private UserRepository userRepo;

	@Inject
	private StampCardRepository stampCardRepo;

	private static final String JP_SPACE = "　";

	public String getGenerateEmplCodeAndComId(String startLetters) {
		String ComId = AppContexts.user().companyId();
		String EmpCode = employeeBusiness.generateEmplCode(startLetters);
		return EmpCode == "" ? "" : ComId + EmpCode;
	}

	public String generateEmplCode(String startLetters) {
		return employeeBusiness.generateEmplCode(startLetters);
	}

	public String generateCardNo(String startLetters) {
		return employeeBusiness.generateCardNo(startLetters);
	}

	/**
	 * validate EmpInfo
	 * EA修正履歴　-　No1159
	 * EA修正履歴　-　No1160
	 * EA修正履歴　-　No1161
	 * EA修正履歴　-　No1162
	 */
	public void validateEmpInfo(EmpInfoDto empInfo) {
		Boolean isDuplicateEmpCode = !CollectionUtil.isEmpty(this.empDataRepo
				.getEmployeeNotDeleteInCompany(AppContexts.user().companyId(), empInfo.getEmployeeCode()));
		if (isDuplicateEmpCode) {
			throw new BusinessException("Msg_345");
		}

		// check duplicate cardNo
		if (empInfo.cardNo != null) {
			String contractCode =  AppContexts.user().contractCode();
			Optional<StampCard> stampCard = this.stampCardRepo.getByCardNoAndContractCode(empInfo.cardNo , contractCode);
			if (stampCard.isPresent()) {
				throw new BusinessException("Msg_346");
			}
		}
		
		// Check duplicate loginID
		Boolean isDuplicateLoginId = !CollectionUtil.isEmpty(this.userRepo.getByLoginId(empInfo.getLoginId()));
		if (isDuplicateLoginId) {

			throw new BusinessException("Msg_757");

		}
		
		String employeeName = empInfo.getEmployeeName();
		if (employeeName.startsWith(JP_SPACE) || employeeName.endsWith(JP_SPACE) || !employeeName.contains(JP_SPACE)) {
			throw new BusinessException("Msg_924");
		}

	}

}
