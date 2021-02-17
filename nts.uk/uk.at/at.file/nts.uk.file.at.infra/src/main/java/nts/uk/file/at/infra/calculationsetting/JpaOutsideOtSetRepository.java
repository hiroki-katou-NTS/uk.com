package nts.uk.file.at.infra.calculationsetting;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.uk.file.at.app.export.calculationsetting.OutsideOtSetDto;
import nts.uk.file.at.app.export.calculationsetting.OutsideOtSetRepository;

@Stateless
public class JpaOutsideOtSetRepository extends JpaRepository implements OutsideOtSetRepository {
	

	@Override
	public Optional<OutsideOtSetDto> findById(String companyId) {
		String sqlJdbc = "SELECT * FROM KSHMT_OUTSIDE_SET KOOS "
				+ "WHERE KOOS.CID = ?";
		try (PreparedStatement stmt = this.connection().prepareStatement(sqlJdbc)) {
			stmt.setString(1, companyId);

			Optional<OutsideOtSetDto> outsideOtSetDto = new NtsResultSet(stmt.executeQuery())
					.getSingle(rec -> {
						OutsideOtSetDto entity = new OutsideOtSetDto();
						entity.setCid(rec.getString("CID"));
						entity.setNote(rec.getString("NOTE"));
						entity.setCalculationMethod(rec.getInt("CALCULATION_METHOD"));
						return entity;
					});
			return outsideOtSetDto;
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return Optional.ofNullable( new OutsideOtSetDto());
	}

}
