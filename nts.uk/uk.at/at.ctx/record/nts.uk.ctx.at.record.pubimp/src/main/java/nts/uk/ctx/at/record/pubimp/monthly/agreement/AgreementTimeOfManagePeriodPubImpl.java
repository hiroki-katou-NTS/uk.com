package nts.uk.ctx.at.record.pubimp.monthly.agreement;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.YearMonth;
import nts.arc.time.calendar.Year;
import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.uk.ctx.at.record.dom.monthly.agreement.export.GetAgreementTimeOfMngPeriod;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementOperationSettingRepository;
import nts.uk.ctx.at.record.pub.monthly.agreement.AgreementTimeOfManagePeriodPub;
import nts.uk.ctx.at.record.pub.monthly.agreement.export.AgreementTimeOfManagePeriodExport;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeOfManagePeriod;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeOfManagePeriodRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.setting.AgreementOperationSetting;

/**
 * 実装：管理期間の36協定時間の取得
 * @author shuichi_ishida
 */
@Stateless
public class AgreementTimeOfManagePeriodPubImpl implements AgreementTimeOfManagePeriodPub {

	@Inject
	private AgreementTimeOfManagePeriodRepository agreementTimeOfManagePeriodRepo;
	@Inject
	private AgreementOperationSettingRepository agreementOperationSettingRepo;
	
	@Override
	public List<AgreementTimeOfManagePeriodExport> get(String sid, Year year) {
		
		return GetAgreementTimeOfMngPeriod.get(createRequire(), sid, year).stream()
				.map(c -> AgreementTimeOfManagePeriodExport.copy(c)).collect(Collectors.toList());
	}
	@Override
	public Map<String, List<AgreementTimeOfManagePeriodExport>> get(
			List<String> sids, Year year) {
		
		return GetAgreementTimeOfMngPeriod.get(createRequire(), sids, year)
				.entrySet().stream()
				.collect(Collectors.toMap(c -> c.getKey(), 
										c -> c.getValue().stream()
													.map(x -> AgreementTimeOfManagePeriodExport.copy(x))
													.collect(Collectors.toList())));
	}
	@Override
	public List<AgreementTimeOfManagePeriodExport> get(List<String> sids, YearMonthPeriod ymPeriod) {
		
		return  GetAgreementTimeOfMngPeriod.get(createRequire(), sids, ymPeriod).stream()
				.map(c -> AgreementTimeOfManagePeriodExport.copy(c)).collect(Collectors.toList());
	}
	
	private GetAgreementTimeOfMngPeriod.RequireM1 createRequire() {
		
		return new GetAgreementTimeOfMngPeriod.RequireM1() {
			
			@Override
			public List<AgreementTimeOfManagePeriod> agreementTimeOfManagePeriod(List<String> sids,
					List<YearMonth> yearMonths) {
				
				return agreementTimeOfManagePeriodRepo.findBySidsAndYearMonths(sids, yearMonths);
			}
			
			@Override
			public Optional<AgreementOperationSetting> agreementOperationSetting(String cid) {

				return agreementOperationSettingRepo.find(cid);
			}
		};
	} 
	
}
