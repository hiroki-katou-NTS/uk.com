package nts.uk.ctx.pereg.infra.repository.person.setting.selectionitem;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.history.PerInfoHistorySelection;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.history.PerInfoHistorySelectionRepository;
import nts.uk.ctx.pereg.infra.entity.person.setting.selectionitem.PpemtHistorySelection;
import nts.uk.ctx.pereg.infra.entity.person.setting.selectionitem.PpemtHistorySelectionPK;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 
 * @author tuannv
 *
 */
@Stateless
public class JpaPerInfoHistorySelectionRepository extends JpaRepository implements PerInfoHistorySelectionRepository {

	private static final String SELECT_ALL = "SELECT si FROM PpemtHistorySelection si";
	private static final String SELECT_ALL_HISTORY_SELECTION = SELECT_ALL
			+ " WHERE si.selectionItemId = :selectionItemId";

	private static final String SELECT_ALL_HISTORY_STARTDATE_SELECTION = SELECT_ALL
			+ " WHERE si.startDate > :startDate AND si.endDate = :endDate";

	private static final String SELECT_ALL_HISTORY_COMPANYID_SELECTION = SELECT_ALL
			+ " WHERE si.selectionItemId = :selectionItemId AND si.companyId=:companyId"
			+ " ORDER BY si.startDate DESC";

	private static final String SELECT_HISTORY_BY_DATE = "SELECT a" + " FROM PpemtHistorySelection a"
			+ " INNER JOIN PpemtSelItemOrder b" + " ON a.selectionItemId = b.selectionIdPK.selectionId"
			+ " AND a.histidPK.histId = b.histId" + " AND a.selectionItemId IN :lstSelItemId"
			+ " AND a.startDate <= :baseDate" + " AND a.endDate >= :baseDate" + " ORDER BY b.dispOrder";

	private static final String SELECT_ALL_HISTID = SELECT_ALL + " WHERE si.histId = :histId";

	private static final String GET_LAST_HISTORY_BY_SELECTION_ID = SELECT_ALL_HISTORY_SELECTION
			+ " AND si.endDate =:endDate";

	private static final String SELECT_ALL_DATA_BY_COMPANY_ID = SELECT_ALL + " WHERE si.companyId = :companyId";
	private static final String FIND_LAST_HIST_BY_SELID_COMID = SELECT_ALL_HISTORY_SELECTION
			+ " AND si.endDate =:endDate"
			+ " AND si.companyId =:companyId";

	@Override
	public void add(PerInfoHistorySelection domain) {
		this.commandProxy().insert(toHistEntity(domain));

	}

	@Override
	public void update(PerInfoHistorySelection domain) {
		PpemtHistorySelection newEntity = toHistEntity(domain);
		PpemtHistorySelection updateEntity = this.queryProxy().find(newEntity.histidPK, PpemtHistorySelection.class)
				.get();
		updateEntity.companyId = newEntity.companyId;
		updateEntity.selectionItemId = newEntity.selectionItemId;
		updateEntity.startDate = newEntity.startDate;
		updateEntity.endDate = newEntity.endDate;
		this.commandProxy().update(updateEntity);
	}

	@Override
	public void remove(String histId) {
		PpemtHistorySelectionPK pk = new PpemtHistorySelectionPK(histId);
		this.commandProxy().remove(PpemtHistorySelection.class, pk);

	}

	private PerInfoHistorySelection toDomain(PpemtHistorySelection entity) {
		DatePeriod datePeriod = new DatePeriod(entity.startDate, entity.endDate);

		return PerInfoHistorySelection.createHistorySelection(entity.histidPK.histId, entity.selectionItemId,
				entity.companyId, datePeriod);
	}

	@Override
	public List<PerInfoHistorySelection> getAllHistoryBySelectionItemId(String selectionItemId) {

		return this.queryProxy().query(SELECT_ALL_HISTORY_SELECTION, PpemtHistorySelection.class)
				.setParameter("selectionItemId", selectionItemId).getList(c -> toDomain(c));
	}
	
	@Override
	public void removeInSelectionItemId(String selectionItemId) {
		List<PpemtHistorySelection> historyList = this.queryProxy()
				.query(SELECT_ALL_HISTORY_SELECTION, PpemtHistorySelection.class)
				.setParameter("selectionItemId", selectionItemId).getList();
		this.commandProxy().removeAll(historyList);
	}

	public Optional<PerInfoHistorySelection> getAllHistoryByHistId(String histId) {
		PpemtHistorySelectionPK pkHistorySelection = new PpemtHistorySelectionPK(histId);

		return this.queryProxy().find(pkHistorySelection, PpemtHistorySelection.class).map(c -> toDomain(c));
	}

	private static PpemtHistorySelection toHistEntity(PerInfoHistorySelection domain) {
		PpemtHistorySelectionPK key = new PpemtHistorySelectionPK(domain.getHistId());

		return new PpemtHistorySelection(key, domain.getSelectionItemId(), domain.getCompanyId(),
				domain.getPeriod().start(), domain.getPeriod().end());
	}

	@Override
	public List<PerInfoHistorySelection> getHistoryByStartDate(GeneralDate startDate) {
		GeneralDate endDate = GeneralDate.fromString("9999/12/31", "yyyy/MM/dd");
		return this.queryProxy().query(SELECT_ALL_HISTORY_STARTDATE_SELECTION, PpemtHistorySelection.class)
				.setParameter("startDate", startDate).setParameter("endDate", endDate).getList(c -> toDomain(c));
	}

	@Override
	public List<PerInfoHistorySelection> getAllBySelecItemIdAndCompanyId(String selectionItemId, String companyId) {

		return this.queryProxy().query(SELECT_ALL_HISTORY_COMPANYID_SELECTION, PpemtHistorySelection.class)
				.setParameter("selectionItemId", selectionItemId).setParameter("companyId", companyId)
				.getList(c -> toDomain(c));
	}

	// hoatt
	@Override
	public List<PerInfoHistorySelection> getHistorySelItemByDate(GeneralDate baseDate, List<String> lstSelItemId) {
		return this.queryProxy().query(SELECT_HISTORY_BY_DATE, PpemtHistorySelection.class)
				.setParameter("baseDate", baseDate).setParameter("lstSelItemId", lstSelItemId)
				.getList(c -> toDomain(c));
	}

	// Tuannv:
	@Override
	public List<String> getAllHistId(String histId) {
		return queryProxy().query(SELECT_ALL_HISTID, String.class).setParameter("histId", histId).getList();
	}

	@Override
	public Optional<PerInfoHistorySelection> getLastHistoryBySelectioId(String selectionItemId) {
		GeneralDate endDate = GeneralDate.fromString("9999/12/31", "yyyy/MM/dd");
		return this.queryProxy().query(GET_LAST_HISTORY_BY_SELECTION_ID, PpemtHistorySelection.class)
				.setParameter("selectionItemId", selectionItemId).setParameter("endDate", endDate)
				.getSingle(c -> toDomain(c));
	}

	// hoatt
	@Override
	public List<PerInfoHistorySelection> getHistSelByEndDate(String selectionItemId, String companyId, GeneralDate endDate) {
		return this.queryProxy().query(FIND_LAST_HIST_BY_SELID_COMID, PpemtHistorySelection.class)
				.setParameter("selectionItemId", selectionItemId)
				.setParameter("companyId", companyId)
				.setParameter("endDate", endDate)
				.getList(c -> toDomain(c));
	}

	// hoatt
	@Override
	public Optional<PerInfoHistorySelection> getHistSelByHistId(String histId) {
		return this.queryProxy().find(new PpemtHistorySelectionPK(histId), PpemtHistorySelection.class)
				.map(c -> toDomain(c));
	}

	// Tuannv:
	@Override
	public List<PerInfoHistorySelection> getAllHistoryByCompanyID(String companyId) {
		return this.queryProxy().query(SELECT_ALL_DATA_BY_COMPANY_ID, PpemtHistorySelection.class)
				.setParameter("companyId", companyId).getList(c -> toDomain(c));
	}

}
