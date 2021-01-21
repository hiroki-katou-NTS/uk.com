package nts.uk.ctx.at.record.pubimp.standardtime;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementOperationSettingRepository;
import nts.uk.ctx.at.record.pub.standardtime.AgreementPeriodByYMDExport;
import nts.uk.ctx.at.record.pub.standardtime.AgreementPeriodByYMDImport;
import nts.uk.ctx.at.record.pub.standardtime.AgreementPeriodByYMDPub;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.AggregatePeriod;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.setting.AgreementOperationSetting;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;

@Stateless
public class AgreementPeriodByYMDImpl implements AgreementPeriodByYMDPub{
	
	@Inject
	AgreementOperationSettingRepository agreementRepository;

	@Inject
	ClosureRepository closureRepository;
	
	@Override
	public AgreementPeriodByYMDExport getAgreementPeriod(AgreementPeriodByYMDImport imp) {
		//DBアクセス
		AgreementOperationSetting agreementSetting = agreementRepository.find(imp.getCompanyId()).get();
		//日から36協定の集計年月を取得
		YearMonth ym = agreementSetting.getAgreementYMBytargetDay(imp.getYmd());
		//年月から集計期間取得
		Optional<Closure> closureOpt = this.closureRepository.findById(imp.getCompanyId(), imp.getClosureId().value);
		
		/** TODO: 36協定時間対応により、コメントアウトされた */
		Optional<AggregatePeriod> period = Optional.of(agreementSetting.getAggregatePeriod(new DatePeriod(GeneralDate.ymd(ym.year(), ym.month(), 1), 
																										ym.lastGeneralDate())));
//		Optional<AggregatePeriod> period = agreementSetting.getAggregatePeriodByYearMonth(ym, closureOpt.get());
		return new AgreementPeriodByYMDExport(period.get().getPeriod(), ym);
		
	}
}
