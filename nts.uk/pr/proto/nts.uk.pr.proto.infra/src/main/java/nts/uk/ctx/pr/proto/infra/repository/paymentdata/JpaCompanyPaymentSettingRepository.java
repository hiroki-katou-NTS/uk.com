package nts.uk.ctx.pr.proto.infra.repository.paymentdata;

import java.util.Optional;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.proto.dom.paymentdata.paymentsetting.CompanyPaymentSetting;
import nts.uk.ctx.pr.proto.dom.paymentdata.repository.CompanyPaymentSettingRepository;
import nts.uk.ctx.pr.proto.infra.entity.paymentdata.QstmtStmtAllotCp;
import nts.uk.ctx.pr.proto.infra.entity.paymentdata.QstmtStmtAllotCpPK;

public class JpaCompanyPaymentSettingRepository extends JpaRepository implements CompanyPaymentSettingRepository {
	@Override
	public Optional<CompanyPaymentSetting> find(String companyCode, int startDate){
		
		return this.queryProxy().find(new QstmtStmtAllotCpPK(companyCode, startDate), QstmtStmtAllotCp.class).map(c -> toDomain(c));
	}
	
	private static CompanyPaymentSetting toDomain(QstmtStmtAllotCp entity){
		val domain = CompanyPaymentSetting.createFromJavaType(entity.qstmtStmtAllotCpPK.companyCode, entity.qstmtStmtAllotCpPK.startDate); 
		
		entity.toDomain(domain);
		return domain;
	}
	
}
