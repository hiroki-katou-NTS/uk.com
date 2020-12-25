package nts.uk.file.at.infra.setworkinghoursanddays;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.i18n.I18NText;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.uk.ctx.at.shared.dom.workrule.weekmanage.WeekRuleManagement;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.company.KshmtLegalTimeMCom;
import nts.uk.ctx.at.shared.infra.entity.workrule.week.KsrmtWeekRuleMng;
import nts.uk.file.at.app.export.setworkinghoursanddays.GetKMK004CompanyExportRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.file.report.masterlist.data.MasterData;

/**
 * 
 * @author sonnlb
 *
 */
@Stateless
public class JpaGetKMK004CompanyExportData extends JpaRepository implements GetKMK004CompanyExportRepository {
	

	@Override
	public List<MasterData> getCompanyExportData(int startDate, int endDate) {
		String cid = AppContexts.user().companyId();
		List<MasterData> datas = new ArrayList<>();

		String startOfWeek = getStartOfWeek(cid);
		/*
		 * val legalTimes = this.queryProxy().query(LEGAL_TIME_COM,
		 * KshmtLegalTimeMCom.class).setParameter("cid", cid) .setParameter("start",
		 * startDate * 100 + 1).setParameter("end", endDate * 100 + 12).getList();
		 */

		/*
		 * try (PreparedStatement stmt =
		 * this.connection().prepareStatement(GET_EXPORT_EXCEL.toString())) { //
		 * stmt.setInt(1, startDate); // stmt.setInt(2, endDate); stmt.setString(1,
		 * cid); NtsResultSet result = new NtsResultSet(stmt.executeQuery()); int month
		 * = this.month(); result.forEach(i -> { datas.addAll(buildCompanyRow(i,
		 * legalTimes, startDate, endDate, month, startOfWeek)); }); } catch
		 * (SQLException e) { e.printStackTrace(); }
		 */
		return datas;
	}
	
	private String getStartOfWeek(String cid) {
		
		Optional<WeekRuleManagement> startOfWeek = this.queryProxy().find(cid, KsrmtWeekRuleMng.class)
																	.map(w -> w.toDomain());
		
		return getWeekStart(startOfWeek);
	}
	
	private String getWeekStart(Optional<WeekRuleManagement> startOfWeek) {
		if (!startOfWeek.isPresent()) {
			return null;
		}
		
		switch (startOfWeek.get().getWeekStart()) {
			case Monday:
				return I18NText.getText("Enum_DayOfWeek_Monday");
			case Tuesday:
				return I18NText.getText("Enum_DayOfWeek_Tuesday");
			case Wednesday:
				return I18NText.getText("Enum_DayOfWeek_Wednesday");
			case Thursday:
				return I18NText.getText("Enum_DayOfWeek_Thursday");
			case Friday:
				return I18NText.getText("Enum_DayOfWeek_Friday");
			case Saturday:
				return I18NText.getText("Enum_DayOfWeek_Saturday");
			case Sunday:
				return I18NText.getText("Enum_DayOfWeek_Sunday");
			case TighteningStartDate:
				return "締め開始日";
			default:
				return null;
		}
	}
}
