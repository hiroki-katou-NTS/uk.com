package nts.uk.screen.at.app.query.kmp.kmp001.b;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCard;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCardRepository;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfo;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfoRepository;
import nts.uk.query.model.employee.EmployeeInformation;
import nts.uk.query.model.employee.EmployeeInformationQuery;
import nts.uk.query.model.employee.EmployeeInformationRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author chungnt
 *
 */
@Stateless
public class StampCardEmployee {

	@Inject
	private StampCardRepository stampCardRepo;

	@Inject
	private EmployeeDataMngInfoRepository dataMngInfoRepo;

	@Inject
	private EmployeeInformationRepository employeeInformationRepo;

	public List<StampCardEmployeeDto> getStampCard(String stampCard) {
		List<StampCardEmployeeDto> dto = new ArrayList<>();
		String cid = AppContexts.user().companyId();
		List<EmployeeInformation> empInfoList = null;

		List<StampCard> stampCards = stampCardRepo.getListStampCardByCardNumber(stampCard);

		if (!stampCards.isEmpty()) {
			List<EmployeeDataMngInfo> dataMngInfo = dataMngInfoRepo.findByListEmployeeId(cid,
					stampCards.stream().map(m -> m.getEmployeeId()).collect(Collectors.toList()));

			if (!dataMngInfo.isEmpty()) {

				empInfoList = employeeInformationRepo.find(EmployeeInformationQuery.builder()
						.employeeIds(dataMngInfo.stream().map(m -> m.getEmployeeId()).collect(Collectors.toList()))
						.referenceDate(GeneralDate.today()).toGetWorkplace(false).toGetDepartment(false)
						.toGetPosition(false).toGetEmployment(false).toGetClassification(false)
						.toGetEmploymentCls(false).build());
				
				if (empInfoList != null && !empInfoList.isEmpty()) {
					
					empInfoList.stream().map(m -> {
						StampCardEmployeeDto card = new StampCardEmployeeDto(stampCard, m.getEmployeeId(), m.getBusinessName(), m.getEmployeeCode());
						dto.add(card);
						return card;
					}).collect(Collectors.toList());
				}
			}
		}
		return dto;
	}
}
