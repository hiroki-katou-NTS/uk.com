package nts.uk.screen.at.app.query.kdp.kdp001.a;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.app.find.stamp.management.personalengraving.dto.StampDataOfEmployeesDto;
import nts.uk.ctx.at.record.app.find.stamp.management.personalengraving.dto.StampToSuppressDto;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCard;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCardRepository;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampNumber;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampDakokuRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampMeans;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampRecord;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampRecordRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.EmployeeStampInfo;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.GetListStampEmployeeService;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.GetStampTypeToSuppressService;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.StampToSuppress;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.PortalStampSettings;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.PortalStampSettingsRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.SettingsSmartphoneStamp;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.SettingsSmartphoneStampRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampSetPerRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampSettingPerson;
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

	@Inject
	private StampSetPerRepository stampSetPerRepo;

	@Inject
	private SettingsSmartphoneStampRepository settingsSmartphoneStampRepo;

	@Inject
	private PortalStampSettingsRepository portalStampSettingsrepo;

	@Inject
	private StampRecordRepository stampRecordRepo;

	@Inject
	private StampDakokuRepository stampRepo;

	@Inject
	private StampCardRepository stampCardRepo;

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

		GetListStampEmployeeServiceRequireImpl empRequire = new GetListStampEmployeeServiceRequireImpl(stampRecordRepo,
				stampRepo, stampCardRepo);

		List<EmpInfoPotalStampDto> empInfos = new ArrayList<EmpInfoPotalStampDto>();
		period.datesBetween().forEach(date -> {
			EmpInfoPotalStampDto infoPotal = null;

			Optional<EmployeeStampInfo> empInfoOpt = this.stampEmployeeService.get(empRequire, employeeId, date);

			if (empInfoOpt.isPresent()) {
				infoPotal = new EmpInfoPotalStampDto();
				infoPotal.setStampDataOfEmp(new StampDataOfEmployeesDto(empInfoOpt.get()));

			}
			StampToSuppress stampToSuppress = null;

			if (settingOpt.get().getSuppressStampBtn() == true) {
				GetStampTypeToSuppressServiceRequireImpl stampTypeRequire = new GetStampTypeToSuppressServiceRequireImpl(
						stampSetPerRepo, settingsSmartphoneStampRepo, portalStampSettingsrepo, stampRecordRepo,
						stampRepo, stampCardRepo);
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

	@AllArgsConstructor
	private class GetListStampEmployeeServiceRequireImpl implements GetListStampEmployeeService.Require {

		@Inject
		private StampRecordRepository stampRecordRepo;

		@Inject
		private StampDakokuRepository stampRepo;

		@Inject
		private StampCardRepository stampCardRepo;

		@Override
		public List<StampRecord> getStampRecord(List<StampNumber> stampNumbers, GeneralDate date) {
			return this.stampRecordRepo.get(stampNumbers, date);
		}

		@Override
		public List<Stamp> getStamp(List<StampNumber> stampNumbers, GeneralDate date) {
			String contractCode = AppContexts.user().contractCode();
			return this.stampRepo.get(contractCode, stampNumbers, date);
		}

		@Override
		public List<StampCard> getListStampCard(String sid) {
			return this.stampCardRepo.getListStampCard(sid);
		}

	}

	@AllArgsConstructor
	private class GetStampTypeToSuppressServiceRequireImpl implements GetStampTypeToSuppressService.Require {

		@Inject
		private StampSetPerRepository stampSetPerRepo;

		@Inject
		private SettingsSmartphoneStampRepository settingsSmartphoneStampRepo;

		@Inject
		private PortalStampSettingsRepository portalStampSettingsrepo;

		@Inject
		private StampRecordRepository stampRecordRepo;

		@Inject
		private StampDakokuRepository stampRepo;

		@Inject
		private StampCardRepository stampCardRepo;

		@Override
		public List<StampCard> getListStampCard(String sid) {
			return this.stampCardRepo.getListStampCard(sid);
		}

		@Override
		public List<StampRecord> getStampRecord(List<StampNumber> stampNumbers, GeneralDate date) {
			return this.stampRecordRepo.get(stampNumbers, date);
		}

		@Override
		public List<Stamp> getStamp(List<StampNumber> stampNumbers, GeneralDate date) {
			String contractCode = AppContexts.user().contractCode();
			return this.stampRepo.get(contractCode, stampNumbers, date);
		}

		@Override
		public Optional<StampSettingPerson> getStampSet() {
			String companyId = AppContexts.user().companyId();
			return this.stampSetPerRepo.getStampSet(companyId);
		}

		@Override
		public Optional<SettingsSmartphoneStamp> getSettingsSmartphoneStamp(String comppanyID) {
			return this.settingsSmartphoneStampRepo.get(comppanyID);
		}

		@Override
		public Optional<PortalStampSettings> getPortalStampSettings(String comppanyID) {
			return this.portalStampSettingsrepo.get(comppanyID);
		}

	}

}
