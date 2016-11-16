package nts.uk.ctx.pr.proto.dom.paymentdata.service.internal;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.proto.dom.allot.CompanyAllotSetting;
import nts.uk.ctx.pr.proto.dom.allot.CompanyAllotSettingRepository;
import nts.uk.ctx.pr.proto.dom.allot.PersonalAllotSetting;
import nts.uk.ctx.pr.proto.dom.allot.PersonalAllotSettingRepository;
import nts.uk.ctx.pr.proto.dom.layout.LayoutMaster;
import nts.uk.ctx.pr.proto.dom.layout.LayoutMasterRepository;
import nts.uk.ctx.pr.proto.dom.layout.category.LayoutMasterCategory;
import nts.uk.ctx.pr.proto.dom.layout.category.LayoutMasterCategoryRepository;
import nts.uk.ctx.pr.proto.dom.layout.detail.LayoutMasterDetail;
import nts.uk.ctx.pr.proto.dom.layout.detail.LayoutMasterDetailRepository;
import nts.uk.ctx.pr.proto.dom.layout.line.LayoutMasterLine;
import nts.uk.ctx.pr.proto.dom.layout.line.LayoutMasterLineRepository;
import nts.uk.ctx.pr.proto.dom.paymentdata.service.PaymentDetailService;
import nts.uk.ctx.pr.proto.dom.personalinfo.employmentcontract.PersonalEmploymentContract;
import nts.uk.ctx.pr.proto.dom.personalinfo.employmentcontract.PersonalEmploymentContractRepository;
import nts.uk.shr.com.primitive.PersonId;

@RequestScoped
public class PaymentDetailServiceImpl implements PaymentDetailService {
	
	@Inject
	private PersonalAllotSettingRepository personalAllotSettingRepo;
	@Inject
	private CompanyAllotSettingRepository companyAllotSettingRepo;
	@Inject
	private LayoutMasterRepository layoutMasterRepo;
	@Inject
	private LayoutMasterDetailRepository layoutDetailMasterRepo;
	@Inject
	private LayoutMasterLineRepository layoutLineMasterRepo;
	@Inject
	private LayoutMasterCategoryRepository layoutCategoryMasterRepo;
	@Inject
	private PersonalEmploymentContractRepository personalEmploymentContractRepo;
	
	@Override
	public Double calculatePayValue(CompanyCode companyCode, PersonId personId, int baseYearMonth) {
		double payValue = 0;
		String stmtCode = null;
		int startYearMonth;
		int endYearMonth;
		
		// get allot personal setting
		Optional<PersonalAllotSetting> personalAllotSettingOp = personalAllotSettingRepo.find(companyCode.v(), personId.v(), baseYearMonth);
		
		if (!personalAllotSettingOp.isPresent()) {
			// get allot company setting
			Optional<CompanyAllotSetting> companyAllotSettingOp = companyAllotSettingRepo.find(companyCode.v(), baseYearMonth);
			stmtCode = companyAllotSettingOp.get().getPaymentDetailCode().v();
			startYearMonth = companyAllotSettingOp.get().getStartDate().v();
			endYearMonth = companyAllotSettingOp.get().getEndDate().v();
		} else {
			stmtCode = personalAllotSettingOp.get().getPaymentDetailCode().v();
			startYearMonth = personalAllotSettingOp.get().getStartDate().v();
			endYearMonth = personalAllotSettingOp.get().getEndDate().v();
		}
		
		// get layout master
		Optional<LayoutMaster> layoutHeadOp = layoutMasterRepo.find(companyCode.v(), stmtCode, startYearMonth);
		
		LayoutMaster layoutHead = layoutHeadOp.get();
		
		// get layout ctg
		List<LayoutMasterCategory> categories = layoutCategoryMasterRepo.getCategories(companyCode.v(), layoutHead.getStmtCode().v(), startYearMonth);
		
		// get layout lines
//		List<LayoutMasterLine> layoutLines = layoutLineMasterRepo.getLines(companyCode.v(), startYearMonth, stmtCode, endYearMonth, layoutAtr, stmtName);
//		
//		// get layout detail master
//		Optional<LayoutMasterDetail> layoutDetail = layoutDetailMasterRepo.find(companyCode.v(), layoutHead.getStmtCode().v(), startYearMonth, stmtCode, categoryAttribute, autoLineID, itemCode);
//		
//		// get item
//		
//		// get PayrollSystem
//		List<PersonalEmploymentContract> personalEmploymentContractList = personalEmploymentContractRepo.find(companyCode.v(), personIdList, baseYmd);
//		
//		Map<PersonId, PersonalEmploymentContract> maps = personalEmploymentContractList.stream().collect(Collectors.toMap(PersonalEmploymentContract::getPersonId, x -> x));
//		
//		PersonalEmploymentContract personal = maps.get(personId);
//		
//		if (personal.isPayrollSystemDailyOrDay()) {
//			
//		} else if (personal.isPayrollSystemDailyOrMonthly()) {
//			
//		} else {
//			throw new RuntimeException("Error system");
//		}
		
		return payValue;
	}

}
