package nts.uk.ctx.pr.core.infra.repository.rule.law.tax.residential;

import java.util.List;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.rule.law.tax.residential.ResidentalTax;
import nts.uk.ctx.pr.core.dom.rule.law.tax.residential.ResidentalTaxRepository;
import nts.uk.ctx.pr.core.infra.entity.rule.law.tax.residential.QtxmtResidentialTax;
import nts.uk.ctx.pr.core.infra.entity.rule.law.tax.residential.QtxmtResidentialTaxPk;

public class JpaResidentialTaxRepository extends JpaRepository implements ResidentalTaxRepository {
    private final String SEL_1 = "SELECT c FROM QtxmtResidentialTax c WHERE c.qtxmtResidentialTaxPk.companyCd =: companyCd";
	private static ResidentalTax toDomain(QtxmtResidentialTax entity){
		val domain = ResidentalTax.createFromJavaType(entity.qtxmtResidentialTaxPk.companyCd, entity.companyAccountNo, entity.companySpecifiedNo, 
				entity.cordinatePostOffice, entity.cordinatePostalCode, entity.memo, entity.prefectureCode, entity.registeredName, entity.resiTaxAutonomy, entity.qtxmtResidentialTaxPk.resiTaxCode, entity.resiReportCode);
		return domain;
	}
	
	private static QtxmtResidentialTax toEntity(ResidentalTax domain){
		QtxmtResidentialTax entity = new QtxmtResidentialTax();
		entity.qtxmtResidentialTaxPk = new QtxmtResidentialTaxPk(domain.getCompanyCode().v(), domain.getResiTaxCode().v());
		entity.companyAccountNo = domain.getCompanyAccountNo().v();
		entity.companySpecifiedNo = domain.getCompanySpecifiedNo().v();
		entity.cordinatePostalCode = domain.getCordinatePostalCode().v();
		entity.cordinatePostOffice = domain.getCordinatePostOffice().v();
		entity.memo = domain.getMemo().v();
		entity.prefectureCode = domain.getPrefectureCode().v();
		entity.registeredName = domain.getRegisteredName().v();
		entity.resiReportCode = domain.getResiTaxReportCode().v();
		entity.resiTaxAutonomy = domain.getResiTaxAutonomy().v();
		return entity;
	}
	@Override
	public List<ResidentalTax> getAllResidentalTax(String companyCode) {
		
		return this.queryProxy().query(SEL_1, QtxmtResidentialTax.class).setParameter("companyCd", companyCode)
				.getList(c->toDomain(c));
	}

	@Override
	public void add(ResidentalTax residentalTax) {
		this.commandProxy().insert(toEntity(residentalTax));
		
	}

	@Override
	public void update(String companyCode,String resiTaxCode) {
		try{
			//this.commandProxy().update(toEntity());
			
		}catch(Exception e){
			throw e;
		}
		
		
	}

	@Override
	public void delele(String companyCode,String resiTaxCode) {
		val objectKey = new QtxmtResidentialTaxPk();
		objectKey.companyCd = companyCode;
		objectKey.resiTaxCode = resiTaxCode;
		this.commandProxy().remove(QtxmtResidentialTax.class, objectKey);
		
	}

}
