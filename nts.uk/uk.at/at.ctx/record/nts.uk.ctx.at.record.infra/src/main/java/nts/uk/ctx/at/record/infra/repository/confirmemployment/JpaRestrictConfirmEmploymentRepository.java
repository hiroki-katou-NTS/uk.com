package nts.uk.ctx.at.record.infra.repository.confirmemployment;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.uk.ctx.at.record.dom.confirmemployment.RestrictConfirmEmployment;
import nts.uk.ctx.at.record.dom.confirmemployment.RestrictConfirmEmploymentRepository;

/**
 * refactor 5
 * @author Doan Duy Hung
 *
 */
@Stateless
public class JpaRestrictConfirmEmploymentRepository extends JpaRepository implements RestrictConfirmEmploymentRepository {

	@Override
	public Optional<RestrictConfirmEmployment> findByCompanyID(String companyID) {
		String sql = "select * from KRCMT_WORK_FIXED_CTR where CID = @companyID";
		return new NtsStatement(sql, this.jdbcProxy())
				.paramString("companyID", companyID)
				.getSingle(rec -> {
					return new RestrictConfirmEmployment(
							rec.getString("CID"), 
							rec.getInt("USAGE_ATR") == 0 ? false : true);
		});
	}

}
