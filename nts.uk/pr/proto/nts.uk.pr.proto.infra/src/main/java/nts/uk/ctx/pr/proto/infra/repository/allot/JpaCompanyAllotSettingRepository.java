package nts.uk.ctx.pr.proto.infra.repository.allot;

import java.util.Optional;

import javax.enterprise.context.RequestScoped;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.proto.dom.allot.CompanyAllotSetting;
import nts.uk.ctx.pr.proto.dom.allot.CompanyAllotSettingRepository;
import nts.uk.ctx.pr.proto.infra.entity.paymentdata.QstmtStmtAllotCp;
import nts.uk.ctx.pr.proto.infra.entity.paymentdata.QstmtStmtAllotCpPK;
@RequestScoped
public class JpaCompanyAllotSettingRepository extends JpaRepository implements CompanyAllotSettingRepository {
	@Override
	public Optional<CompanyAllotSetting> find(String companyCode, int startDate){
		
		return this.queryProxy().find(new QstmtStmtAllotCpPK(companyCode, startDate), QstmtStmtAllotCp.class).map(c -> toDomain(c));
	}
	
	private static CompanyAllotSetting toDomain(QstmtStmtAllotCp entity){
		val domain = CompanyAllotSetting.createFromJavaType(entity.qstmtStmtAllotCpPK.companyCode, entity.qstmtStmtAllotCpPK.startDate,
				entity.endDate, entity.bonusDetailCode, entity.paymentDetailCode); 
		
		entity.toDomain(domain);
		return domain;
	}
	
}
