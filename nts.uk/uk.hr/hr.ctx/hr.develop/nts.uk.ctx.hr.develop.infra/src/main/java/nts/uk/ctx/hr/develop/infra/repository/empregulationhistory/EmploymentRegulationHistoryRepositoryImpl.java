package nts.uk.ctx.hr.develop.infra.repository.empregulationhistory;

import java.util.List;
import java.util.Optional;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.hr.develop.dom.empregulationhistory.EmploymentRegulationHistoryRepository;
import nts.uk.ctx.hr.develop.dom.empregulationhistory.dto.RegulationHistoryDto;
import nts.uk.ctx.hr.develop.infra.entity.empregulationhistory.JshmtEmpRegHistory;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

public class EmploymentRegulationHistoryRepositoryImpl extends JpaRepository implements EmploymentRegulationHistoryRepository{

	private static final String SELECT_LIST_DATE_HIS_ID = "SELECT c FROM JshmtEmpRegHistory c "
			+ "WHERE c.cId = :cId "
			+ "ORDER BY c.startDate ASC";
	
	@Override
	public Optional<RegulationHistoryDto> getLatestEmpRegulationHist(String cId) {
		List<DateHistoryItem> lis = this.queryProxy().query(SELECT_LIST_DATE_HIS_ID, JshmtEmpRegHistory.class)
				.setParameter("cId", cId)
				.getList(c->c.toDomain());
		if(lis.isEmpty()) {
			return Optional.empty();
		}else {
			DateHistoryItem item = lis.stream().max((d1, d2) -> d1.start().compareTo(d2.start())).get();
			return Optional.of(new RegulationHistoryDto(item.identifier(), item.start()));
		}
	}

	@Override
	public List<DateHistoryItem> getEmpRegulationHistList(String cId) {
		return this.queryProxy().query(SELECT_LIST_DATE_HIS_ID, JshmtEmpRegHistory.class)
				.setParameter("cId", cId)
				.getList(c->c.toDomain());
	}

	@Override
	public String addEmpRegulationHist(String cId, GeneralDate startDate) {
		String historyId = IdentifierUtil.randomUniqueId();
		this.commandProxy().insert(new JshmtEmpRegHistory(cId, new DateHistoryItem(historyId, new DatePeriod(startDate, GeneralDate.ymd(9999, 12, 31)))));
		return historyId;
	}

	@Override
	public void updateEmpRegulationHist(String cId, String historyId, GeneralDate startDate) {
		Optional<JshmtEmpRegHistory> entity = this.queryProxy().find(historyId , JshmtEmpRegHistory.class);
		if(entity.isPresent()) {
			entity.get().startDate = startDate;
			this.commandProxy().update(entity.get());
		}
	}

	@Override
	public void removeEmpRegulationHist(String cId, String historyId) {
		this.commandProxy().remove(JshmtEmpRegHistory.class, historyId);
	}

}
