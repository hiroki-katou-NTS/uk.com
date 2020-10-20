package nts.uk.ctx.at.record.pubimp.monthly.agreement;

import java.util.Map;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.Year;
import nts.uk.ctx.at.record.dom.monthly.agreement.export.AggregateAgreementTimeByYM;
import nts.uk.ctx.at.record.dom.monthly.agreement.export.AggregateAgreementTimeByYear;
import nts.uk.ctx.at.record.dom.require.RecordDomRequireService;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementDomainService;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementOperationSettingRepository;
import nts.uk.ctx.at.record.pub.monthly.agreement.AggregateAgreementTimePub;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreMaxAverageTimeMulti;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeOfManagePeriod;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeOfManagePeriodRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeYear;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.setting.AgreementOperationSetting;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.timesetting.BasicAgreementSetting;

@Stateless
public class AggregateAgreementTimePubImpl implements AggregateAgreementTimePub {

	@Inject
	private AgreementTimeOfManagePeriodRepository agreementTimeOfManagePeriodRepo;
	
	@Inject
	private RecordDomRequireService requireService;
	
	@Inject
	private AgreementOperationSettingRepository agreementOperationSettingRepo;
	
	@Override
	public AgreementTimeYear aggregate(String sid, GeneralDate baseDate, Year year,
			Map<YearMonth, AttendanceTimeMonth> agreementTimes) {
		
		return AggregateAgreementTimeByYear.aggregate(createRequireY(), sid, baseDate, year, agreementTimes);
	}

	@Override
	public AgreMaxAverageTimeMulti aggregate(String sid, GeneralDate baseDate, YearMonth ym,
			Map<YearMonth, AttendanceTimeMonth> agreementTimes) {
		
		return AggregateAgreementTimeByYM.aggregate(createRequireYM(), sid, baseDate, ym, agreementTimes);
	}
	
	private AggregateAgreementTimeByYear.RequireM1 createRequireY() {
		
		return new AggregateAgreementTimeByYear.RequireM1() {
			
			@Override
			public Optional<AgreementTimeOfManagePeriod> agreementTimeOfManagePeriod(String sid, YearMonth ym) {
				
				return agreementTimeOfManagePeriodRepo.find(sid, ym);
			}
			
			@Override
			public BasicAgreementSetting basicAgreementSetting(String cid, String sid, GeneralDate baseDate, Year year) {
				
				return AgreementDomainService.getBasicSet(requireService.createRequire(), cid, sid, baseDate, year);
			}
			
			@Override
			public Optional<AgreementOperationSetting> agreementOperationSetting(String cid) {
				
				return agreementOperationSettingRepo.find(cid);
			}
		};
	}

	private AggregateAgreementTimeByYM.RequireM1 createRequireYM() {
		
		return new AggregateAgreementTimeByYM.RequireM1() {
			
			@Override
			public Optional<AgreementTimeOfManagePeriod> agreementTimeOfManagePeriod(String sid, YearMonth ym) {
				
				return agreementTimeOfManagePeriodRepo.find(sid, ym);
			}
			
			@Override
			public BasicAgreementSetting basicAgreementSetting(String cid, String sid, GeneralDate baseDate) {
				
				return AgreementDomainService.getBasicSet(requireService.createRequire(), cid, sid, baseDate);
			}
		};
	}
}
