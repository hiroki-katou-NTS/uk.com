package nts.uk.file.at.app.export.holidaysettingexport;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
@Stateless
public class JpaCompanyStartMonthAdapter extends JpaRepository implements CompanyStartMonthAdapter  {

	@Override
	public Optional<CompanyStartMonthData> getComanyInfoByCid(String cid) {
		
		
		String GET_STARTMONTH= "SELECT a.MONTH_STR FROM BCMMT_COMPANY a WHERE a.ABOLITION_ATR = 0 AND a.CID = ?";

		try (PreparedStatement stmt = this.connection().prepareStatement(GET_STARTMONTH)) {
			stmt.setString(1, cid);

			Optional<CompanyStartMonthData> companyStartMonthData = new NtsResultSet(stmt.executeQuery())
					.getSingle(rec -> {
						CompanyStartMonthData entity = new CompanyStartMonthData();
						entity.setStartMonth(rec.getInt("MONTH_STR"));
						return entity;
					});
			return companyStartMonthData;
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return Optional.ofNullable( new CompanyStartMonthData());
		

	}

}
