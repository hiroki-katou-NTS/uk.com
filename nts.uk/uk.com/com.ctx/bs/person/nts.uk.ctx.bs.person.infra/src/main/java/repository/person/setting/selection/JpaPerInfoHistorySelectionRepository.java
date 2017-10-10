package repository.person.setting.selection;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import entity.person.setting.selection.PpemtHistorySelection;
import entity.person.setting.selection.PpemtHistorySelectionPK;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.person.dom.person.setting.selectionitem.PerInfoHistorySelection;
import nts.uk.ctx.bs.person.dom.person.setting.selectionitem.PerInfoHistorySelectionRepository;

@Stateless
public class JpaPerInfoHistorySelectionRepository extends JpaRepository implements PerInfoHistorySelectionRepository {

	private static final String SELECT_ALL = "SELECT si FROM PpemtHistorySelection si";
	private static final String SELECT_ALL_HISTORY_SELECTION = SELECT_ALL
			+ " WHERE si.selectionItemId = :selectionItemId";
	
	private static final String SELECT_ALL_HISTORY_STARTDATE_SELECTION = SELECT_ALL
			+ " WHERE si.startDate < :startDate";
	
	
	@Override
	public void add(PerInfoHistorySelection domain) {
		this.commandProxy().insert(toHistEntity(domain));

	}

	@Override
	public void remove(String histId) {
		PpemtHistorySelectionPK pk = new PpemtHistorySelectionPK(histId);
		this.commandProxy().remove(PpemtHistorySelection.class, pk);

	}

	private PerInfoHistorySelection toDomain(PpemtHistorySelection entity) {
		return PerInfoHistorySelection.createHistorySelection(entity.histidPK.histidPK, entity.selectionItemId,
				entity.companyCode, entity.endDate, entity.startDate);
	}
	
	@Override
	public List<PerInfoHistorySelection> historySelection(String selectionItemId) {
		return this.queryProxy().query(SELECT_ALL_HISTORY_SELECTION, PpemtHistorySelection.class)
				.setParameter("selectionItemId", selectionItemId).getList(c -> toDomain(c));
	}
	
	public Optional<PerInfoHistorySelection> getHistorySelectionItem(String histId) {
		PpemtHistorySelectionPK pkHistorySelection = new PpemtHistorySelectionPK(histId);
		return this.queryProxy().find(pkHistorySelection, PpemtHistorySelection.class).map(c -> toDomain(c));
	}

	private static PpemtHistorySelection toHistEntity(PerInfoHistorySelection domain) {
		PpemtHistorySelectionPK key = new PpemtHistorySelectionPK(domain.getHistId());
		return new PpemtHistorySelection(key, domain.getSelectionItemId(), domain.getCompanyCode(), domain.getEndDate(),
				domain.getStartDate());
	}

	// historyStartDateSelection
	@Override
	public List<PerInfoHistorySelection> historyStartDateSelection(GeneralDate startDate) {
		// TODO Auto-generated method stub
		return this.queryProxy().query(SELECT_ALL_HISTORY_STARTDATE_SELECTION, PpemtHistorySelection.class)
				.setParameter("startDate", startDate).getList(c -> toDomain(c));
	}
}
