package nts.uk.file.at.infra.vacation.set.annualpaidleave;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import nts.arc.i18n.I18NText;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.file.at.app.export.vacation.set.annualpaidleave.AnnPaidLeaveImpl;
import nts.uk.file.at.app.export.vacation.set.annualpaidleave.AnnPaidLeaveRepository;
import nts.uk.shr.infra.file.report.masterlist.data.MasterData;

@Stateless
public class JpaAnnPaidLeaveRepository extends JpaRepository implements AnnPaidLeaveRepository {

	private static final String GET_PAID_LEA_EXPORT = "SELECT  pl.MANAGE_ATR, " +
			" mas.IS_WORK_DAY_CAL, " +
			" mas.HALF_MAX_GRANT_DAY, " +
			" mas.REMAINING_MAX_DAY, " +
			" mas.RETENTION_YEAR, " +
			" mas.REMAINING_MAX_DAY, " +
			" mas.HALF_MANAGE_ATR, " +
			" mas.HALF_MAX_REFERENCE, " +
			" mas.HALF_MAX_UNIFORM_COMP, " +
			" mas.ROUND_PRO_CLA, " +
			" pl.PRIORITY_TYPE, " +
			" tas.TIME_MANAGE_ATR, " +
			" tas.TIME_UNIT, " +
			" tas.TIME_MAX_DAY_MANAGE_ATR, " +
			" tas.TIME_MAX_DAY_REFERENCE, " +
			" tas.TIME_MAX_DAY_UNIF_COMP, " +
			" tas.ROUND_PRO_CLA " +
			"FROM (SELECT MANAGE_ATR,PRIORITY_TYPE,CID FROM KALMT_ANNUAL_PAID_LEAVE WHERE KALMT_ANNUAL_PAID_LEAVE.CID = ? )  as pl " +
			"INNER JOIN KMAMT_MNG_ANNUAL_SET mas on pl.CID = mas.CID " +
			"INNER JOIN KTVMT_TIME_ANNUAL_SET tas on mas.CID = tas.CID ";

	@Override
	public List<MasterData> getAnPaidLea(String cid) {

		List<MasterData> datas = new ArrayList<>();
		try (PreparedStatement stmt = this.connection().prepareStatement(GET_PAID_LEA_EXPORT)) {
			stmt.setString(1, cid);

			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return buildMasterListData(rs);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return datas;
	}

	private List<MasterData> buildMasterListData(ResultSet rs) {
		List<MasterData> datas = new ArrayList<>();
		try {

			// Row 1
			datas.add(buildARow(I18NText.getText("KMF001_176"), "", "", "", rs.getInt(1) == 0 ? "管理する" : "管理しない"));
			// Row 2
			datas.add(buildARow("", I18NText.getText("KMF001_177"), I18NText.getText("KMF001_178"), "", rs.getInt(2) == 0 ? "管理する" : "管理しない"));
			// Row 3
			datas.add(buildARow("", "", I18NText.getText("KMF001_179"), I18NText.getText("KMF001_180"), rs.getString(3)));
			// Row 4
			datas.add(buildARow("", "", "", I18NText.getText("KMF001_181"), rs.getString(4)));
			// Row 5
			datas.add(buildARow("", "", I18NText.getText("KMF001_182"), "", rs.getString(5)));
			// Row 6
			datas.add(buildARow("", "",I18NText.getText("KMF001_183"), "", rs.getString(6)));
			// Row 7
			datas.add(buildARow("", "", I18NText.getText("KMF001_184"), "", rs.getInt(7) == 0 ? "管理する" : "管理しない"));
			// Row 8
			datas.add(buildARow("", "",I18NText.getText("KMF001_185"), "", rs.getInt(8) == 0 ? "会社一律" : "年休付与テーブルを参照"));
			// Row 9
			datas.add(buildARow("", "", "", I18NText.getText("KMF001_186"), rs.getString(9)));
			datas.add(buildARow("", "", I18NText.getText("KMF001_187"), "", rs.getString(10)));
			datas.add(buildARow("", I18NText.getText("KMF001_188"),I18NText.getText("KMF001_189"), "", rs.getString(11)));
			datas.add(buildARow("",I18NText.getText("KMF001_190"),I18NText.getText("KMF001_191"), "", rs.getString(12)));
			datas.add(buildARow("", "", I18NText.getText("KMF001_192"), "", rs.getString(13)));
			datas.add(buildARow("", "","", I18NText.getText("KMF001_193"), rs.getString(14)));
			datas.add(buildARow("", "",I18NText.getText("KMF001_194"), "", rs.getString(15)));
			datas.add(buildARow("", "", "", I18NText.getText("KMF001_195"), rs.getString(16)+rs.getString(18)));
			datas.add(buildARow("", "",I18NText.getText("KMF001_196"), "", rs.getString(17)));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return datas;
	}

	private MasterData buildARow(String value1, String value2, String value3, String value4, String value5) {
		Map<String, Object> data = new HashMap<>();
		data.put(AnnPaidLeaveImpl.KMF001_1, value1);
		data.put(AnnPaidLeaveImpl.KMF001_2, value2);
		data.put(AnnPaidLeaveImpl.KMF001_3, value3);
		data.put(AnnPaidLeaveImpl.KMF001_4, value4);
		data.put(AnnPaidLeaveImpl.KMF001_5, value5);
		return new MasterData(data, null, "");
	}
}
