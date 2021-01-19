package nts.uk.ctx.at.record.pubimp.standardtime;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementMonthSettingRepository;
import nts.uk.ctx.at.record.pub.standardtime.AgreementMonthSettingOutputExport;
import nts.uk.ctx.at.record.pub.standardtime.AgreementMonthSettingPub;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.exceptsetting.AgreementMonthSetting;

@Stateless
public class AgreementMonthSettingPubImpl implements AgreementMonthSettingPub {

	@Inject
	private AgreementMonthSettingRepository repo;

	@Override
	public AgreementMonthSettingOutputExport getExport(String employeeId, YearMonth yearMonth) {
		
		AgreementMonthSettingOutputExport outputExport = new AgreementMonthSettingOutputExport();
		Optional<AgreementMonthSetting> data = repo.getByEmployeeIdAndYm(employeeId, yearMonth);
		outputExport.setOpAgreementMonthSetting(data);
		outputExport.setIsExist(data.isPresent());
		return outputExport;
	}
	

}
