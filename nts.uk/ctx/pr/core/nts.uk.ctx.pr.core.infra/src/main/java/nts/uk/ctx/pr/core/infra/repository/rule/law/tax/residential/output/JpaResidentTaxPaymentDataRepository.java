package nts.uk.ctx.pr.core.infra.repository.rule.law.tax.residential.output;

import java.util.Optional;

import javax.enterprise.context.RequestScoped;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.rule.law.tax.residential.output.ResidentTaxPaymentData;
import nts.uk.ctx.pr.core.dom.rule.law.tax.residential.output.ResidentTaxPaymentDataRepository;
import nts.uk.ctx.pr.core.infra.entity.rule.law.tax.residential.output.QtxmtResimentTialTaxSlip;
import nts.uk.ctx.pr.core.infra.entity.rule.law.tax.residential.output.QtxmtResimentTialTaxSlipPk;

@RequestScoped
public class JpaResidentTaxPaymentDataRepository extends JpaRepository implements ResidentTaxPaymentDataRepository {

	@Override
	public Optional<ResidentTaxPaymentData> find(String companyCode, String residentTaxCode, int yearMonth) {
		
		QtxmtResimentTialTaxSlipPk primaryKey = new QtxmtResimentTialTaxSlipPk(companyCode, residentTaxCode, yearMonth);
		
		return this.queryProxy().find(primaryKey, QtxmtResimentTialTaxSlip.class)
				.map(x -> ResidentTaxPaymentData.createFromJavaType(
						x.qtxmtResimentTialTaxSlipPk.residentTaxCode, 
						x.taxPayRollMoney, 
						x.taxBonusMoney, 
						x.taxOverDueMoney,
						x.taxDemandChargeMoyney, 
						x.address, 
						x.dueDate, 
						x.headcount, 
						x.retirementBonusAmout, 
						x.cityTaxMoney, 
						x.prefectureTaxMoney,
						x.qtxmtResimentTialTaxSlipPk.yearMonth));
	}

	@Override
	public void add(String companyCode, ResidentTaxPaymentData domain) {
		QtxmtResimentTialTaxSlip entity = toEntity(companyCode, domain);
		this.commandProxy().insert(entity);
	}

	@Override
	public void update(String companyCode, ResidentTaxPaymentData domain) {
		QtxmtResimentTialTaxSlip entity = toEntity(companyCode, domain);
		this.commandProxy().update(entity);
	}
	
	private QtxmtResimentTialTaxSlip toEntity(String companyCode, ResidentTaxPaymentData domain) {
		QtxmtResimentTialTaxSlipPk qtxmtResimentTialTaxSlipPk = new QtxmtResimentTialTaxSlipPk(companyCode, domain.getCode().v(), domain.getYearMonth().v());
		
		QtxmtResimentTialTaxSlip entity = new QtxmtResimentTialTaxSlip(
				qtxmtResimentTialTaxSlipPk, 
				domain.getTaxPayrollMoney().v(), 
				domain.getTaxBonusMoney().v(), 
				domain.getTaxOverdueMoney().v(), 
				domain.getTaxDemandChargeMoney().v(), 
				domain.getAddress().v(), 
				domain.getDueDate(), 
				domain.getStaffNo(),
				domain.getRetirementAmount().v(), 
				domain.getCityTaxMoney().v(), 
				domain.getPrefectureTaxMoney().v());
		return entity;
	}

}
