package nts.uk.ctx.at.shared.infra.repository.scherec.monthlyattendanceitem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattendanceitem.ControlOfMonthlyItems;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattendanceitem.ControlOfMonthlyItemsRepository;
import nts.uk.ctx.at.shared.infra.entity.scherec.monthlyattendanceitem.KrcmtControlOfMonthlyItems;

@Stateless
public class JpaControlOfMonthlyItemsRepository extends JpaRepository implements ControlOfMonthlyItemsRepository {

	private static final String GET_BY_CODE = "SELECT c FROM KrcmtControlOfMonthlyItems c "
			+ " WHERE c.krcmtControlOfMonthlyItemsPK.companyID = :companyID "
			+ " AND c.krcmtControlOfMonthlyItemsPK.itemMonthlyID = :itemMonthlyID ";

	@Override
	public Optional<ControlOfMonthlyItems> getControlOfMonthlyItem(String companyID, int itemMonthlyID) {
		Optional<ControlOfMonthlyItems> data = this.queryProxy().query(GET_BY_CODE, KrcmtControlOfMonthlyItems.class)
				.setParameter("companyID", companyID).setParameter("itemMonthlyID", itemMonthlyID)
				.getSingle(c -> c.toDomain());
		return data;
	}

	@Override
	public void updateControlOfMonthlyItem(ControlOfMonthlyItems controlOfMonthlyItems) {
		KrcmtControlOfMonthlyItems newEntity = KrcmtControlOfMonthlyItems.toEntity(controlOfMonthlyItems);
		KrcmtControlOfMonthlyItems updateEntity = this.queryProxy()
				.find(newEntity.getKrcmtControlOfMonthlyItemsPK(), KrcmtControlOfMonthlyItems.class).get();
		updateEntity.headerBgColorOfMonthlyPer = newEntity.headerBgColorOfMonthlyPer;
		updateEntity.inputUnitOfTimeItem = newEntity.inputUnitOfTimeItem;
		this.commandProxy().update(updateEntity);

	}

	@Override
	public void addControlOfMonthlyItem(ControlOfMonthlyItems controlOfMonthlyItems) {
		KrcmtControlOfMonthlyItems newEntity = KrcmtControlOfMonthlyItems.toEntity(controlOfMonthlyItems);
		this.commandProxy().insert(newEntity);

	}

	@Override
	public List<ControlOfMonthlyItems> getListControlOfMonthlyItem(String companyID, List<Integer> itemMonthlyIDs) {
		if (CollectionUtil.isEmpty(itemMonthlyIDs)) 
			return Collections.emptyList();
		String sql = "SELECT c FROM KrcmtControlOfMonthlyItems c "
				+ " WHERE c.krcmtControlOfMonthlyItemsPK.companyID = :companyID "
				+ " AND c.krcmtControlOfMonthlyItemsPK.itemMonthlyID IN :itemMonthlyIDs ";
		
		List<ControlOfMonthlyItems> data = new ArrayList<>();
		CollectionUtil.split(itemMonthlyIDs, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			data.addAll(this.queryProxy().query(sql, KrcmtControlOfMonthlyItems.class)
							.setParameter("companyID", companyID)
							.setParameter("itemMonthlyIDs", subList)
							.getList(c -> c.toDomain()));
		});
		return data;
	}

}
