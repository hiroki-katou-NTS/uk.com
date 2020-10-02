package nts.uk.ctx.at.record.pubimp.monthly.agreement;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.Year;
import nts.uk.ctx.at.record.dom.monthly.agreement.export.AgreementExcessInfo;
import nts.uk.ctx.at.record.dom.monthly.agreement.export.GetExcessTimesYear;
import nts.uk.ctx.at.record.dom.require.RecordDomRequireService;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementDomainService;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementOperationSettingRepository;
import nts.uk.ctx.at.record.pub.monthly.agreement.GetExcessTimesYearPub;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeOfManagePeriod;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeOfManagePeriodRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.setting.AgreementOperationSetting;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.timesetting.BasicAgreementSetting;

/**
 * 実装：年間超過回数の取得
 * @author shuichi_ishida
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class GetExcessTimesYearPubImpl implements GetExcessTimesYearPub {

	/** 年間超過回数の取得 */
	/** TODO: 36協定時間対応により、コメントアウトされた */
//	@Inject
//	private GetExcessTimesYear getExcessTimesYear;
	
	@Inject
	private AgreementOperationSettingRepository agreementOperationSettingRepo;
	
	@Inject
	private AgreementTimeOfManagePeriodRepository agreementTimeOfManagePeriodRepo;
	
	@Inject
	private RecordDomRequireService requireService;
	
	/** 年間超過回数の取得 */
	@Override
	public AgreementExcessInfo algorithm(String employeeId, Year year) {
		return GetExcessTimesYear.get(createRequire(), employeeId, year);
	}
	
	/** 年間超過回数の取得 */
	@Override
	public Map<String,AgreementExcessInfo> algorithm(List<String> employeeIds, Year year) {
		return GetExcessTimesYear.get(createRequire(), employeeIds, year);
	}
	
	/** 年間超過回数と残数の取得 */
	@Override
	public AgreementExcessInfo andRemainTimes(String employeeId, Year year, GeneralDate baseDate) {
		return GetExcessTimesYear.getWithRemainTimes(requireService.createRequire(), employeeId, year, baseDate);
	}
	
	private GetExcessTimesYear.RequireM1 createRequire() {
		
		return new GetExcessTimesYear.RequireM1() {
			
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
