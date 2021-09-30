package nts.uk.ctx.at.record.pubimp.monthly.vacation.annualleave;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.GetAnnualLeaveUsedNumberFromRemDataService;
import nts.uk.ctx.at.record.pub.monthly.vacation.annualleave.GetAnnualLeaveUsedNumberFromRemDataPub;
import nts.uk.ctx.at.record.pub.monthly.vacation.annualleave.dtoexport.annual.GrantRemainRegisterTypeExport;
import nts.uk.ctx.at.record.pub.monthly.vacation.annualleave.dtoexport.annual.LeaveExpirationStatusExport;
import nts.uk.ctx.at.record.pub.monthly.vacation.annualleave.dtoexport.annual.LeaveGrantNumberExport;
import nts.uk.ctx.at.record.pub.monthly.vacation.annualleave.dtoexport.annual.LeaveGrantRemainingDataExport;
import nts.uk.ctx.at.record.pub.monthly.vacation.annualleave.dtoexport.annual.LeaveNumberInfoExport;
import nts.uk.ctx.at.record.pub.monthly.vacation.annualleave.dtoexport.annual.LeaveOverNumberExport;
import nts.uk.ctx.at.record.pub.monthly.vacation.annualleave.dtoexport.annual.LeaveRemainingNumberExport;
import nts.uk.ctx.at.record.pub.monthly.vacation.annualleave.dtoexport.annual.LeaveUsedNumberExport;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.GrantRemainRegisterType;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.LeaveExpirationStatus;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.LeaveGrantRemainingData;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveGrantNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveNumberInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveOverNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveRemainingNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveUsedNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveUsedPercent;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSettingRepository;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemRepository;

@Stateless
public class GetAnnualLeaveUsedNumberFromRemDataPubImpl implements GetAnnualLeaveUsedNumberFromRemDataPub {

	@Inject
	private WorkingConditionItemRepository workingConditionItemRepository;

	@Inject
	private AnnualPaidLeaveSettingRepository annualPaidLeaveSettingRepository;

	/**
	 * 付与残数データから使用数を消化する
	 * 
	 * @param companyId     会社ID
	 * @param employeeId    社員ID
	 * @param remainingData 年休付与残数データ
	 * @param usedNumber    使用数
	 * @return 年休付与残数データ(List)
	 */
	@Override
	public List<LeaveGrantRemainingDataExport> getAnnualLeaveGrantRemData(String companyId, String employeeId,
			List<LeaveGrantRemainingDataExport> remainingData, LeaveUsedNumberExport usedNumber) {

		LeaveUsedNumber usedNumberDom = LeaveUsedNumber.createFromJavaType(
				usedNumber.getDays(),
				usedNumber.getMinutes().orElse(null), 
				usedNumber.getStowageDays().orElse(null), 
				usedNumber.leaveOverLimitNumber.map(c->c.numberOverDays).orElse(null), 
				usedNumber.leaveOverLimitNumber.map(c->c.timeOver.orElse(null)).orElse(null));
		usedNumberDom.setLeaveOverLimitNumber(usedNumber.getLeaveOverLimitNumber()
				.map(x -> new LeaveOverNumber(x.getNumberOverDays(), x.getTimeOver().orElse(null))));

		return GetAnnualLeaveUsedNumberFromRemDataService
				.getAnnualLeaveGrantRemainingData(companyId, employeeId,
						remainingData.stream().map(x -> convertToDomain(x)).collect(Collectors.toList()), usedNumberDom,
						new RequireImpl(workingConditionItemRepository, annualPaidLeaveSettingRepository))
				.stream().map(x -> convert(x)).collect(Collectors.toList());

	}

	private LeaveGrantRemainingDataExport convert(LeaveGrantRemainingData domain) {

		return new LeaveGrantRemainingDataExport(domain.getEmployeeId(), domain.getDeadline(), domain.getDeadline(),
				EnumAdaptor.valueOf(domain.getExpirationStatus().value, LeaveExpirationStatusExport.class),
				EnumAdaptor.valueOf(domain.getRegisterType().value, GrantRemainRegisterTypeExport.class),
				new LeaveNumberInfoExport(
						new LeaveGrantNumberExport(domain.getDetails().getGrantNumber().getDays().v(),
								domain.getDetails().getGrantNumber().getMinutes().map(x -> x.v())),
						new LeaveUsedNumberExport(domain.getDetails().getUsedNumber().getDays().v(),
								domain.getDetails().getUsedNumber().getMinutes().map(x -> x.v()),
								domain.getDetails().getUsedNumber().getStowageDays().map(x -> x.v()),
								domain.getDetails().getUsedNumber().getLeaveOverLimitNumber()
										.map(x -> new LeaveOverNumberExport(x.numberOverDays.v(),
												x.timeOver.map(y -> y.v())))),
						new LeaveRemainingNumberExport(domain.getDetails().getRemainingNumber().getDays().v(),
								domain.getDetails().getRemainingNumber().getMinutes().map(x -> x.v())),
						domain.getDetails().getUsedPercent().v()));

	}

	private LeaveGrantRemainingData convertToDomain(LeaveGrantRemainingDataExport data) {
		LeaveGrantRemainingData result = new LeaveGrantRemainingData();
		result.setEmployeeId(data.getEmployeeId());
		result.setGrantDate(data.getGrantDate());
		result.setDeadline(data.getDeadline());
		result.setExpirationStatus(EnumAdaptor.valueOf(data.getExpirationStatus().value, LeaveExpirationStatus.class));
		result.setRegisterType(EnumAdaptor.valueOf(data.getRegisterType().value, GrantRemainRegisterType.class));

		LeaveUsedNumber leaveUsedNumberDom = LeaveUsedNumber.createFromJavaType(
				data.getDetails().getUsedNumber().getDays(),
				data.getDetails().getUsedNumber().getMinutes().orElse(null),
				data.getDetails().getUsedNumber().getStowageDays().orElse(null),
				data.getDetails().getUsedNumber().leaveOverLimitNumber.map(c->c.numberOverDays).orElse(null), 
				data.getDetails().getUsedNumber().leaveOverLimitNumber.map(c->c.timeOver.orElse(null)).orElse(null));
		leaveUsedNumberDom.setLeaveOverLimitNumber(data.getDetails().getUsedNumber().getLeaveOverLimitNumber()
				.map(x -> LeaveOverNumber.createFromJavaType(x.getNumberOverDays(), x.getTimeOver().orElse(null))));

		result.setDetails(new LeaveNumberInfo(
				LeaveGrantNumber.createFromJavaType(data.getDetails().getGrantNumber().getDays(),
						data.getDetails().getGrantNumber().getMinutes().orElse(null)),
				leaveUsedNumberDom,
				LeaveRemainingNumber.createFromJavaType(data.getDetails().getRemainingNumber().getDays(),
						data.getDetails().getRemainingNumber().getMinutes().orElse(null)),
				new LeaveUsedPercent(data.getDetails().getUsedPercent())));
		return result;
	}

	@AllArgsConstructor
	public class RequireImpl implements GetAnnualLeaveUsedNumberFromRemDataService.RequireM3 {

		private final WorkingConditionItemRepository workingConditionItemRepository;

		private final AnnualPaidLeaveSettingRepository annualPaidLeaveSettingRepository;

		@Override
		public AnnualPaidLeaveSetting annualPaidLeaveSetting(String companyId) {
			return annualPaidLeaveSettingRepository.findByCompanyId(companyId);
		}

		@Override
		public Optional<WorkingConditionItem> workingConditionItem(String employeeId, GeneralDate baseDate) {
			return workingConditionItemRepository.getBySidAndStandardDate(employeeId, baseDate);
		}

	}
}
