package nts.uk.ctx.at.record.pubimp.standardtime;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.standardtime.AggregatePeriod;
import nts.uk.ctx.at.record.dom.standardtime.AgreementOperationSetting;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementOperationSettingRepository;
import nts.uk.ctx.at.record.pub.standardtime.AgreementPeriodByYMDExport;
import nts.uk.ctx.at.record.pub.standardtime.AgreementPeriodByYMDImport;
import nts.uk.ctx.at.record.pub.standardtime.AgreementPeriodByYMDPub;
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
		Optional<AggregatePeriod> period = agreementSetting.getAggregatePeriodByYearMonth(ym, closureOpt.get());
		return new AgreementPeriodByYMDExport(period.get().getPeriod(), ym);
		
	}
}
