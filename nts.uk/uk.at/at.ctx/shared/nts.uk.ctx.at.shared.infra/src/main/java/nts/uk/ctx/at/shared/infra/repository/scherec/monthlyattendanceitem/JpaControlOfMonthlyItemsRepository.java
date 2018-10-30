package nts.uk.ctx.at.shared.infra.repository.scherec.monthlyattendanceitem;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.enums.TimeInputUnit;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.primitivevalue.HeaderBackgroundColor;
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
		List<ControlOfMonthlyItems> data = new ArrayList<>();
		CollectionUtil.split(itemMonthlyIDs, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			try {
				PreparedStatement statement = this.connection().prepareStatement(
						"SELECT * from KSHST_MON_ITEM_CONTROL h"
						+ " WHERE h.CID = ? AND h.ITEM_MONTHLY_ID IN (" + subList.stream().map(s -> "?").collect(Collectors.joining(",")) + ")");
				statement.setString(1, companyID);
				for (int i = 0; i < subList.size(); i++) {
					statement.setInt(i + 2, subList.get(i));
				}
				data.addAll(new NtsResultSet(statement.executeQuery()).getList(rec -> {
					return new  ControlOfMonthlyItems(
							companyID,
							rec.getInt("ITEM_MONTHLY_ID"),
							rec.getString("HEADER_BACKGROUND_COLOR") != null ? new HeaderBackgroundColor(rec.getString("HEADER_BACKGROUND_COLOR")) : null,
							rec.getInt("TIME_INPUT_UNIT") != null ? EnumAdaptor.valueOf(rec.getInt("TIME_INPUT_UNIT"), TimeInputUnit.class) : null);
				}));
			}catch (Exception e) {
				throw new RuntimeException(e);
			}
		});
		
		return data;
	}

}
