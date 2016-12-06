package nts.uk.ctx.pr.proto.infra.repository.allot;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.proto.dom.allot.PersonalAllotSetting;
import nts.uk.ctx.pr.proto.dom.allot.PersonalAllotSettingRepository;
import nts.uk.ctx.pr.proto.infra.entity.paymentdata.QstmtStmtAllotPs;

@RequestScoped
public class JpaPersonalAllotSettingRepository extends JpaRepository implements PersonalAllotSettingRepository {

	private final String SEL_1 = "SELECT c FROM QstmtStmtAllotPs c WHERE c.qstmtStmtAllotPsPK.companyCode = :ccd "
			+ "AND c.qstmtStmtAllotPsPK.personId = :pid and c.qstmtStmtAllotPsPK.startDate <= :baseYM and c.endDate >= :baseYM";

	@Override
	public Optional<PersonalAllotSetting> find(String companyCode, String personId, int baseYM) {

		List<QstmtStmtAllotPs> psettings = this.queryProxy().query(SEL_1, QstmtStmtAllotPs.class)
				.setParameter("ccd", companyCode).setParameter("pid", personId).setParameter("baseYM", baseYM)
				.getList();
		if (psettings.isEmpty()) {
			return Optional.empty();
		}

		return Optional.of(toDomain(psettings.get(0)));
	}

	private static PersonalAllotSetting toDomain(QstmtStmtAllotPs entity) {
		val domain = PersonalAllotSetting.createFromJavaType(entity.qstmtStmtAllotPsPK.companyCode,
				entity.qstmtStmtAllotPsPK.personId, entity.qstmtStmtAllotPsPK.startDate, entity.endDate,
				entity.bonusDetailCode, entity.paymentDetailCode);

		return domain;
	}

}