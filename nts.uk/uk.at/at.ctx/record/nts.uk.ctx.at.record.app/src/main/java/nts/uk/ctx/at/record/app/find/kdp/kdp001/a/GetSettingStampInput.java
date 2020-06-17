package nts.uk.ctx.at.record.app.find.kdp.kdp001.a;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.app.find.stamp.management.personalengraving.dto.StampDataOfEmployeesDto;
import nts.uk.ctx.at.record.app.find.stamp.management.personalengraving.dto.StampToSuppressDto;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCard;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampNumber;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampMeans;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampRecord;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.EmployeeStampInfo;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.GetListStampEmployeeService;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.GetStampTypeToSuppressService;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.StampToSuppress;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.PortalStampSettings;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.PortalStampSettingsRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampSettingPerson;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author sonnlb
 *         UKDesign.UniversalK.就業.KDP_打刻.KDP001_打刻入力(ポータル).A:打刻入力(ポータル).メニュー別OCD.打刻入力(ポータル)の設定を取得する
 *         UKDesign.UniversalK.就業.KDP_打刻.KDP001_打刻入力(ポータル).A:打刻入力(ポータル).メニュー別OCD.打刻入力(ポータル)を起動する
 */
@Stateless
public class GetSettingStampInput {
	
	@Inject
	private PortalStampSettingsRepository potalSettingRepo;
	
	@Inject
	private GetListStampEmployeeService stampEmployeeService;

	@Inject
	private GetStampTypeToSuppressService stampTypeToSuppressService;

	public SettingPotalStampInputDto getSettingPotalStampInput() {
		// get ログイン会社ID
		String comppanyID = AppContexts.user().companyId();
		Optional<PortalStampSettings> settingOpt = this.potalSettingRepo.get(comppanyID);
		SettingPotalStampInputDto result = new SettingPotalStampInputDto();

		if (!settingOpt.isPresent()) {

			return null;
		}

		result.setPortalStampSettings(PortalStampSettingsDto.fromDomain(settingOpt.get()));

		// 2取得する(@Require, 社員ID, 年月日)
		String employeeId = AppContexts.user().employeeId();

		DatePeriod period = new DatePeriod(GeneralDate.today(), GeneralDate.today().addDays(-3));

		GetListStampEmployeeServiceRequireImpl empRequire = new GetListStampEmployeeServiceRequireImpl();

		List<EmpInfoPotalStampDto> empInfos = new ArrayList<EmpInfoPotalStampDto>();
		period.datesBetween().forEach(date -> {
			EmpInfoPotalStampDto infoPotal = null;
			;
			Optional<EmployeeStampInfo> empInfoOpt = this.stampEmployeeService.get(empRequire, employeeId, date);

			if (empInfoOpt.isPresent()) {
				infoPotal = new EmpInfoPotalStampDto();
				infoPotal.setStampDataOfEmp(new StampDataOfEmployeesDto(empInfoOpt.get()));

			}
			StampToSuppress stampToSuppress = null;

			if (settingOpt.get().getSuppressStampBtn() == true) {
				GetStampTypeToSuppressServiceRequireImpl stampTypeRequire = new GetStampTypeToSuppressServiceRequireImpl();
				if (infoPotal == null) {
					infoPotal = new EmpInfoPotalStampDto();
				}

				stampToSuppress = this.stampTypeToSuppressService.get(stampTypeRequire, employeeId, StampMeans.PORTAL);

				infoPotal.setStampToSuppress(new StampToSuppressDto(stampToSuppress));
			}

			if (infoPotal != null) {
				empInfos.add(infoPotal);
			}
		});

		return result;
	}

	private class GetListStampEmployeeServiceRequireImpl implements GetListStampEmployeeService.Require {

		@Override
		public List<StampCard> getListStampCard(String sid) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<StampRecord> getStampRecord(List<StampNumber> stampNumbers, GeneralDate stampDate) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<Stamp> getStamp(List<StampNumber> stampNumbers, GeneralDate stampDateTime) {
			// TODO Auto-generated method stub
			return null;
		}

	}

	private class GetStampTypeToSuppressServiceRequireImpl implements GetStampTypeToSuppressService.Require {

		@Override
		public List<StampCard> getListStampCard(String sid) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<StampRecord> getStampRecord(List<StampNumber> stampNumbers, GeneralDate date) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<Stamp> getStamp(List<StampNumber> stampNumbers, GeneralDate date) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Optional<StampSettingPerson> getStampSet() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Optional<WorkingConditionItem> findWorkConditionByEmployee(String employeeId, GeneralDate baseDate) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Optional<PredetemineTimeSetting> findByWorkTimeCode(String workTimeCode) {
			// TODO Auto-generated method stub
			return null;
		}

	}

}
