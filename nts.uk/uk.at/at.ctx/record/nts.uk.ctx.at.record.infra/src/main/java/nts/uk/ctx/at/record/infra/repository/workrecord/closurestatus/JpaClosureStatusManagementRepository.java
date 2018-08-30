package nts.uk.ctx.at.record.infra.repository.workrecord.closurestatus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.YearMonth;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.workrecord.closurestatus.ClosureStatusManagement;
import nts.uk.ctx.at.record.dom.workrecord.closurestatus.ClosureStatusManagementRepository;
import nts.uk.ctx.at.record.infra.entity.workrecord.closurestatus.KrcdtClosureSttMng;
import nts.uk.ctx.at.record.infra.entity.workrecord.closurestatus.KrcdtClosureSttMngPk;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureDate;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 
 * @author HungTT
 *
 */

@Stateless
public class JpaClosureStatusManagementRepository extends JpaRepository implements ClosureStatusManagementRepository {

	@Override
	public void add(ClosureStatusManagement domain) {
		this.commandProxy().insert(KrcdtClosureSttMng.fromDomain(domain));
	}

	@Override
	public Optional<ClosureStatusManagement> getById(String employeeId, YearMonth ym, int closureId,
			ClosureDate closureDate) {
		Optional<KrcdtClosureSttMng> opt = this.queryProxy().find(new KrcdtClosureSttMngPk(ym.v(), employeeId,
				closureId, closureDate.getClosureDay().v(), closureDate.getLastDayOfMonth() ? 1 : 0),
				KrcdtClosureSttMng.class);
		if (opt.isPresent())
			return Optional.of(opt.get().toDomain());
		return Optional.empty();
	}

	@Override
	public Optional<ClosureStatusManagement> getLatestByEmpId(String employeeId) {
		String sql = "SELECT a FROM KrcdtClosureSttMng a WHERE a.pk.employeeId = :employeeId ORDER BY a.end DESC";
		List<KrcdtClosureSttMng> lstEntity = this.queryProxy().query(sql, KrcdtClosureSttMng.class)
				.setParameter("employeeId", employeeId).getList();
		if (lstEntity.isEmpty())
			return Optional.empty();
		return Optional.of(lstEntity.get(0).toDomain());
	}
	
	public List<ClosureStatusManagement> getByIdListAndDatePeriod(List<String> employeeIds, DatePeriod span){
		
		List<KrcdtClosureSttMng> results = new ArrayList<>();
		String sql = "SELECT * FROM KrcdtClosureSttMng a WHERE a.SID IN ?employeeId AND"
				+ " (((a.START_DATE <= ?StartSpan ) AND (?StartSpan <= ?a.END_DATE)) OR"//実行期間が開始を含んでいる
				+ "  ((a.START_DATE <= ?EndSpan   ) AND (?EndSpan   <= ?a.END_DATE)) OR"//実行期間が終了を含んでいる
				+ "  ((?StartSpan   <= a.START_DATE) AND (a.END_DATE <= ?a.EndSpan )))";//実行期間がspanの中にある
		
		CollectionUtil.split(employeeIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subEmployeeIds -> {
			@SuppressWarnings("unchecked")
			List<KrcdtClosureSttMng> subResults = this.getEntityManager().createNativeQuery(sql, KrcdtClosureSttMng.class)
					.setParameter("", subEmployeeIds)
					.getResultList();
			results.addAll(subResults);
		});		
		//return results;
		return Collections.emptyList();
	}

}
