package nts.uk.screen.at.app.query.kdl.kdl014.a;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.stamp.application.CommonSettingsStampInput;
import nts.uk.ctx.at.record.dom.stamp.application.CommonSettingsStampInputRepository;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCard;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCardRepository;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampNumber;
import nts.uk.ctx.at.record.dom.worklocation.WorkLocation;
import nts.uk.ctx.at.record.dom.worklocation.WorkLocationRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampDakokuRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampRecord;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampRecordRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.EmployeeStampInfo;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.GetListStampEmployeeService;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.StampInfoDisp;
import nts.uk.query.model.employee.EmployeeInformation;
import nts.uk.query.model.employee.EmployeeInformationQuery;
import nts.uk.query.model.employee.EmployeeInformationRepository;
import nts.uk.screen.at.app.query.kdl.kdl014.a.dto.EmpInfomationDto;
import nts.uk.screen.at.app.query.kdl.kdl014.a.dto.Kdl014EmpParamDto;
import nts.uk.screen.at.app.query.kdl.kdl014.a.dto.ReferToTheStampingResultsDto;
import nts.uk.shr.com.context.AppContexts;

/**UKDesign.UniversalK.就業.KDL_ダイアログ.KDL014_打刻参照ダイアログ.メニュー別OCD.打刻実績を参照する.打刻実績の取得処理*/
@Stateless
public class ReferToTheStampingResults {

	@Inject
	private StampCardRepository stampCardRepository;

	@Inject
	private StampRecordRepository stampRecordRepository;

	@Inject
	private StampDakokuRepository stampDakokuRepository;

	@Inject
	private EmployeeInformationRepository empInfoRepo;

	@Inject
	private CommonSettingsStampInputRepository commonSettingsStampInputRepo;
	
	@Inject
	private WorkLocationRepository workLocationRepo;
	

	public ReferToTheStampingResultsDto get(Kdl014EmpParamDto param) {
		
		// 1.取得する(@Require, 社員ID, 年月日) -> 社員の打刻情報 / EmployeeStampInfo
		GetListStampEmployeeService.Require require = new RequireImpl(stampCardRepository, stampRecordRepository, stampDakokuRepository);

		List<String> employeeIds = param.getListEmp().stream().map(c -> c.getEmployeeId()).collect(Collectors.toList());
		List<EmployeeStampInfo> listEmployeeStampInfo = new ArrayList<>(); // list 社員の打刻情報

		DatePeriod period = new DatePeriod(param.getStart(), param.getEnd());
		
		for (String empId : employeeIds) {
			period.datesBetween().forEach(date -> {
				Optional<EmployeeStampInfo> optEmStampInfo = GetListStampEmployeeService.get(require, empId, date);
				if (optEmStampInfo.isPresent()) {
					listEmployeeStampInfo.add(optEmStampInfo.get());
				}
			});
		}
		
		// 2.<call> 社員の情報を取得する -> List<社員情報>
		EmployeeInformationQuery infoQuery = EmployeeInformationQuery.builder()
				.employeeIds(employeeIds)
				.referenceDate(param.getEnd())
				.toGetClassification(false)
				.toGetDepartment(false)
				.toGetEmployment(false)
				.toGetEmploymentCls(false)
				.toGetPosition(false)
				.toGetWorkplace(false).build();

		List<EmployeeInformation> listEmpInfo = empInfoRepo.find(infoQuery);

		// 3.get*(List<社員の打刻情報．勤務場所コード＞) -> List<勤務場所>
		List<String> listWorkLocationCode = new ArrayList<>();
			
		for (EmployeeStampInfo r : listEmployeeStampInfo) {
			for (StampInfoDisp d : r.getListStampInfoDisp()) {
				for (Stamp stamp: d.getStamp()) {
					if(stamp.getRefActualResults().getWorkLocationCD().isPresent()) {
						listWorkLocationCode.add(stamp.getRefActualResults().getWorkLocationCD().get().v());
					}
				}
			}
		}
		
		listWorkLocationCode = listWorkLocationCode.stream().distinct().collect(Collectors.toList());
		
		List<WorkLocation> workLocationList = workLocationRepo.findByCodes(AppContexts.user().companyId(), listWorkLocationCode);
		
		// 4.get(会社ID) -> GoogleMap利用するか、マップ表示アドレス
		Optional<CommonSettingsStampInput> optSettingStampInput = commonSettingsStampInputRepo.get(AppContexts.user().companyId());
		
		ReferToTheStampingResultsDto result = new ReferToTheStampingResultsDto();
		
		if(optSettingStampInput.isPresent()) {
			CommonSettingsStampInput input = optSettingStampInput.get();
			result.setDisplay(input.isGooglemap());
			if (input.getMapAddres().isPresent()) {
				result.setAddress(input.getMapAddres().get().v());
			}
		}
		List<EmpInfomationDto> listEmps = new ArrayList<>();
		
		for (EmployeeInformation empInfo : listEmpInfo) {
			List<EmployeeStampInfo> employeeStampListInfo = listEmployeeStampInfo.stream().filter(c->c.getEmployeeId().equals(empInfo.getEmployeeId())).collect(Collectors.toList());
			for (EmployeeStampInfo stampInfo : employeeStampListInfo) {
				for (StampInfoDisp stamp: stampInfo.getListStampInfoDisp()) {
					for (Stamp st : stamp.getStamp()) {
						Optional<WorkLocation> wl = Optional.empty();
						if(st.getRefActualResults().getWorkLocationCD().isPresent()) {
							wl = workLocationList.stream().filter(c->c.getWorkLocationCD().v().equals(st.getRefActualResults().getWorkLocationCD().get().v())).findFirst();
						}
						EmpInfomationDto em = new EmpInfomationDto(
								empInfo.getEmployeeCode(),
								empInfo.getBusinessName(),
								st.getStampDateTime(),
								st.getRelieve().getStampMeans().value,
								stamp.getStampAtr(),
								wl.isPresent()?wl.get().getWorkLocationName().v() : null,
								st.getLocationInfor().isPresent()?st.getLocationInfor().get().getPositionInfor() : null);
						listEmps.add(em);
					}
				}
			}
		}
		
		result.setListEmps(listEmps);
		
		if(param.getMode() == 0 && param.getListEmp().size() == 1 && result.getListEmps().isEmpty()) throw new BusinessException("Msg_1617");
		
		if(param.getMode() == 1 && result.getListEmps().isEmpty()) throw new BusinessException("Msg_1617");
		
		return result;
	}

	@AllArgsConstructor
	private static class RequireImpl implements GetListStampEmployeeService.Require {
		@Inject
		private StampCardRepository stampCardRepository;
		@Inject
		private StampRecordRepository stampRecordRepository;
		@Inject
		private StampDakokuRepository stampDakokuRepository;

		@Override
		public List<StampCard> getListStampCard(String sid) {
			return stampCardRepository.getListStampCard(sid);
		}

		@Override
		public List<StampRecord> getStampRecord(List<StampNumber> stampNumbers, GeneralDate date) {
			return stampRecordRepository.get(stampNumbers, date);
		}

		@Override
		public List<Stamp> getStamp(List<StampNumber> stampNumbers, GeneralDate date) {
			return stampDakokuRepository.get(AppContexts.user().contractCode(), stampNumbers, date);
		}

	}
}
