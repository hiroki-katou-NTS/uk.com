package nts.uk.ctx.pr.proto.infra.repository.paymentdata;

import java.util.Optional;

import javax.enterprise.context.RequestScoped;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.proto.dom.paymentdata.paymentsetting.PersonalPaymentSetting;
import nts.uk.ctx.pr.proto.dom.paymentdata.repository.PersonalPaymentSettingRepository;
import nts.uk.ctx.pr.proto.infra.entity.paymentdata.QstmtStmtAllotPs;
import nts.uk.ctx.pr.proto.infra.entity.paymentdata.QstmtStmtAllotPsPK;

@RequestScoped
public class JpaPersonalPaymentSettingRepository extends JpaRepository implements PersonalPaymentSettingRepository {
	
	@Override
	public Optional<PersonalPaymentSetting> find(String companyCode, String personId, int startDate){
		
		return this.queryProxy().find(new QstmtStmtAllotPsPK(companyCode, personId, startDate), QstmtStmtAllotPs.class).map(c -> toDomain(c));
	}
	
	private static PersonalPaymentSetting toDomain(QstmtStmtAllotPs entity){
		val domain = PersonalPaymentSetting.createFromJavaType(entity.qstmtStmtAllotPsPK.companyCode, entity.qstmtStmtAllotPsPK.personId, 
																entity.qstmtStmtAllotPsPK.startDate); 
		
		entity.toDomain(domain);
		return domain;
	}
	
	
}