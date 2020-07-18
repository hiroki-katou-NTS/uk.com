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
 * UKDesign.UniversalK.就業.KDP_打刻.KMP001_IDカードの登録.B：カードNO指定による個人の登録.メニュー別OCD.カードNOから抽出した社員情報を取得する
 * 
 * @author chungnt
 *
 */
@Stateless
public class EmployeeInformationFromCardNo {

	@Inject
	private StampCardRepository stampCardRepo;

	@Inject
	private EmployeeInformationRepository employeeInformation;

	@Inject
	private EmployeeDataMngInfoRepository employeeData;

	public EmployeeInformationFromCardNoDto getEmployee(String cardNumber) {

		return null;
	}

	public EmployeeInformationFromCardNoDto getAll() {
		EmployeeInformationFromCardNoDto result = new EmployeeInformationFromCardNoDto();
		String companyId = AppContexts.user().companyId();
		List<String> employeeIds = new ArrayList<>();
		List<String> empIds = new ArrayList<>();
		List<StampCard> stampCards = new ArrayList<>();
		GeneralDate generalDate = GeneralDate.today();
		List<EmployeeInformation> empInfoList = new ArrayList<>();

		List<EmployeeDataMngInfo> mngInfos = employeeData.getAllByCid(companyId);

		if (mngInfos != null) {
			employeeIds = mngInfos.stream().map(m -> m.getEmployeeId()).collect(Collectors.toList());
		}

		if (employeeIds != null) {
			stampCards = stampCardRepo.getLstStampCardByLstSid(employeeIds);

			if (stampCards != null) {
				result.setStampCards(stampCards.stream().map(m -> {
					StampCardDto cardDto = new StampCardDto(m.getEmployeeId(), m.getStampNumber().v());
					return cardDto;
				}).collect(Collectors.toList()));
			}
		}

		if (result.getStampCards() != null) {
			empIds = result.getStampCards().stream().map(m -> m.getEmployeeId()).distinct()
					.collect(Collectors.toList());

			empInfoList = employeeInformation.find(EmployeeInformationQuery.builder().employeeIds(empIds)
					.referenceDate(generalDate).toGetWorkplace(false).toGetDepartment(false).toGetPosition(false)
					.toGetEmployment(false).toGetClassification(false).toGetEmploymentCls(false).build());

			result.setEmployeeInfo(empInfoList.stream().map(m -> {
				EmployeeInfoDto employeeInfoDto = new EmployeeInfoDto(m.getEmployeeId(), m.getEmployeeCode(),
						m.getBusinessName());
				return employeeInfoDto;
			}).collect(Collectors.toList()));
		}

		return result;
	}
}
