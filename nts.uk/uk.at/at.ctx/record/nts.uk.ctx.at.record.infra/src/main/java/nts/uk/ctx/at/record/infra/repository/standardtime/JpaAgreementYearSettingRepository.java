package nts.uk.ctx.at.record.infra.repository.standardtime;

import java.util.List;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.standardtime.AgreementTimeOfCompany;
import nts.uk.ctx.at.record.dom.standardtime.AgreementYearSetting;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementYearSettingRepository;
import nts.uk.ctx.at.record.infra.entity.standardtime.KmkmtAgeementTimeCompany;
import nts.uk.ctx.at.record.infra.entity.standardtime.KmkmtAgeementYearSetting;
import nts.uk.ctx.at.record.infra.entity.standardtime.KmkmtAgreementMonthSet;

public class JpaAgreementYearSettingRepository extends JpaRepository implements AgreementYearSettingRepository{
	
	private static final String FIND;

	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT a ");
		builderString.append("FROM KmkmtAgeementTimeCompany a ");
		builderString.append("WHERE a.kmkmtAgeementTimeCompanyPK.companyId = :companyId ");
		builderString.append("AND a.laborSystemAtr = :laborSystemAtr ");
		FIND = builderString.toString();
	}

	@Override
	public List<AgreementYearSetting> find(String agreementYearSetting) {
		return null;
	}

	private static AgreementYearSetting toDomain(KmkmtAgeementYearSetting ageementYearSetting){
		AgreementYearSetting 
	}
	
//	private static AgreementTimeOfCompany toDomain(KmkmtAgeementTimeCompany kmkmtAgeementTimeCompany) {
//		AgreementTimeOfCompany agreementTimeOfCompany = AgreementTimeOfCompany.createFromJavaType(
//				kmkmtAgeementTimeCompany.kmkmtAgeementTimeCompanyPK.companyId,
//				kmkmtAgeementTimeCompany.kmkmtAgeementTimeCompanyPK.basicSettingId,
//				kmkmtAgeementTimeCompany.laborSystemAtr);
//		return agreementTimeOfCompany;
//	}
}
