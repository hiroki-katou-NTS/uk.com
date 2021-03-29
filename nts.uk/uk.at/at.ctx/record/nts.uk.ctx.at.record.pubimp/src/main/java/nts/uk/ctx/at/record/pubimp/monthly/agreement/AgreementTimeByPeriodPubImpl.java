package nts.uk.ctx.at.record.pubimp.monthly.agreement;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.Year;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.monthly.agreement.export.AgeementTimeCommonSettingService;
import nts.uk.ctx.at.record.dom.require.RecordDomRequireService;
import nts.uk.ctx.at.record.pub.monthly.agreement.AggregateAgreementTimePub;
import nts.uk.ctx.at.record.pub.monthly.agreement.AgreementTimeByPeriodPub;
import nts.uk.ctx.at.record.pub.monthly.agreement.export.AgreMaxAverageTimeMultiExport;
import nts.uk.ctx.at.record.pub.monthly.agreement.export.AgreementTimeYearExport;

/**
 * 指定期間36協定時間の取得
 * @author shuichi_ishida
 */
@Stateless
public class AgreementTimeByPeriodPubImpl implements AgreementTimeByPeriodPub {
	
	@Inject
	private RecordDomRequireService requireService;
	
	@Inject
	private AggregateAgreementTimePub aggregateAgreementTimePub;
	
	/** 指定期間36協定上限複数月平均時間の取得 */
	@Override
	public Optional<AgreMaxAverageTimeMultiExport> maxAverageTimeMulti(String employeeId, GeneralDate criteria, YearMonth yearMonth) {
		
		return Optional.ofNullable(aggregateAgreementTimePub.aggregate(employeeId, criteria, yearMonth, new HashMap<>()));
	}
	
	/** 指定年36協定年間時間の取得 */
	@Override
	public Optional<AgreementTimeYearExport> timeYear(String employeeId, GeneralDate criteria, Year year) {

		return Optional.ofNullable(aggregateAgreementTimePub.aggregate(employeeId, criteria, year, new HashMap<>()));
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Object getCommonSetting(String companyId, List<String> employeeIds, DatePeriod criteria) {
		
		return AgeementTimeCommonSettingService.getCommonService(requireService.createRequire(), companyId, employeeIds, criteria);
	}
}
