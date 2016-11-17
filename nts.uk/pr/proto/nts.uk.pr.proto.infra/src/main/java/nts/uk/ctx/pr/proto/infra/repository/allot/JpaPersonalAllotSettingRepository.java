package nts.uk.ctx.pr.proto.infra.repository.allot;

import java.util.Optional;

import javax.enterprise.context.RequestScoped;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.proto.dom.allot.PersonalAllotSetting;
import nts.uk.ctx.pr.proto.dom.allot.PersonalAllotSettingRepository;
import nts.uk.ctx.pr.proto.infra.entity.paymentdata.QstmtStmtAllotPs;

@RequestScoped
public class JpaPersonalAllotSettingRepository extends JpaRepository implements PersonalAllotSettingRepository {
	
	private final String SELECT_ALL = 	" SELECT ps" +
										" FROM QSTMT_STMT_ALLOT_PS ps" +
										" WHERE ps.CCD = :CCD AND ps.PID = :PID" +
												" AND ps.STR_YM <= :BASE_YM" +
												" AND ps.END_YM >= :BASE_YM";
						
	@Override
	public Optional<PersonalAllotSetting> find(String companyCode, String personId, int startDate) {

		return this.queryProxy()
				.query(SELECT_ALL, QstmtStmtAllotPs.class)
					.setParameter("CCD", companyCode)
					.setParameter("PID", personId)
					.setParameter("BASE_YM", startDate)
				.getSingle(c -> toDomain(c));
	}

	private static PersonalAllotSetting toDomain(QstmtStmtAllotPs entity) {
		val domain = PersonalAllotSetting.createFromJavaType(entity.qstmtStmtAllotPsPK.companyCode,
				entity.qstmtStmtAllotPsPK.personId, entity.qstmtStmtAllotPsPK.startDate, entity.endDate, entity.bonusDetailCode, entity.paymentDetailCode);

		entity.toDomain(domain);
		return domain;
	}

}