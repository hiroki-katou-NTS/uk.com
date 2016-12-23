package nts.uk.ctx.pr.proto.infra.repository.allot;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.proto.dom.allot.CompanyAllotSetting;
import nts.uk.ctx.pr.proto.dom.allot.CompanyAllotSettingRepository;
import nts.uk.ctx.pr.proto.infra.entity.paymentdata.QstmtStmtAllotCp;

@Stateless
public class JpaCompanyAllotSettingRepository extends JpaRepository implements CompanyAllotSettingRepository {

	private final String SEL_1 = "SELECT c FROM QstmtStmtAllotCp c WHERE c.qstmtStmtAllotCpPK.companyCode = :ccd ";

	@Override
	public Optional<CompanyAllotSetting> find(String companyCode) {

		List<QstmtStmtAllotCp> psettings = this.queryProxy().query(SEL_1, QstmtStmtAllotCp.class)
				.setParameter("ccd", companyCode).getList();
		if (psettings.isEmpty()) {
			return Optional.empty();
		}

		return Optional.of(toDomain(psettings.get(0)));
	}

	private static CompanyAllotSetting toDomain(QstmtStmtAllotCp entity) {
		val domain = CompanyAllotSetting.createFromJavaType(entity.qstmtStmtAllotCpPK.companyCode,
				entity.startDate, entity.endDate, entity.bonusDetailCode, entity.paymentDetailCode);

		entity.toDomain(domain);
		return domain;
	}

}
