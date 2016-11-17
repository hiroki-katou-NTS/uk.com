package nts.uk.ctx.pr.proto.infra.repository.allot;

import java.util.Optional;

import javax.enterprise.context.RequestScoped;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.proto.dom.allot.PersonalAllotSetting;
import nts.uk.ctx.pr.proto.dom.allot.PersonalAllotSettingRepository;
import nts.uk.ctx.pr.proto.infra.entity.paymentdata.QstmtStmtAllotPs;
import nts.uk.ctx.pr.proto.infra.entity.paymentdata.QstmtStmtAllotPsPK;

@RequestScoped
public class JpaPersonalAllotSettingRepository extends JpaRepository implements PersonalAllotSettingRepository {

	@Override
	public Optional<PersonalAllotSetting> find(String companyCode, String personId, int startDate) {

		return this.queryProxy().find(new QstmtStmtAllotPsPK(companyCode, personId, startDate), QstmtStmtAllotPs.class)
				.map(c -> toDomain(c));
	}

	private static PersonalAllotSetting toDomain(QstmtStmtAllotPs entity) {
		val domain = PersonalAllotSetting.createFromJavaType(entity.qstmtStmtAllotPsPK.companyCode,
				entity.qstmtStmtAllotPsPK.personId, entity.qstmtStmtAllotPsPK.startDate, entity.endDate, entity.bonusDetailCode, entity.paymentDetailCode);

		entity.toDomain(domain);
		return domain;
	}

}