package nts.uk.ctx.pr.proto.infra.repository.paymentdata;

import java.util.Optional;

import javax.enterprise.context.RequestScoped;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.proto.dom.paymentdata.PaymentCalculationBasicInformation;
import nts.uk.ctx.pr.proto.dom.paymentdata.repository.PaymentCalculationBasicInformationRepository;
import nts.uk.ctx.pr.proto.infra.entity.paymentdata.CcastBasicCalc;

@RequestScoped
public class JpaPaymentCalculationBasicInformationRepository extends JpaRepository implements PaymentCalculationBasicInformationRepository {

	@Override
	public Optional<PaymentCalculationBasicInformation> find(CompanyCode companyCode) {
		return this.queryProxy().find(companyCode, CcastBasicCalc.class).map(c -> toDomain(c));
	}

	private static PaymentCalculationBasicInformation toDomain(CcastBasicCalc entity) {
		val domain = PaymentCalculationBasicInformation.createFromJavaType(entity.ccd, entity.baseDays,
				entity.baseHours);
		entity.toDomain(domain);
		return domain;
	}
}
