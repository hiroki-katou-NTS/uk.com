package nts.uk.ctx.at.shared.infra.repository.scherec.dailyattendanceitem;

import lombok.SneakyThrows;
import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.ControlOfAttendanceItems;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.enums.TimeInputUnit;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.primitivevalue.HeaderBackgroundColor;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.repository.ControlOfAttendanceItemsRepository;
import nts.uk.ctx.at.shared.infra.entity.scherec.dailyattendanceitem.KshstControlOfAttendanceItems;

import javax.ejb.Stateless;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Stateless
public class JpaControlOfAttendanceItemsRepository extends JpaRepository implements ControlOfAttendanceItemsRepository {

    private static final String GET_BY_CODE = "SELECT c FROM KshstControlOfAttendanceItems c "
            + " WHERE c.kshstControlOfAttendanceItemsPK.companyID = :companyID "
            + " AND c.kshstControlOfAttendanceItemsPK.itemDailyID = :itemDailyID ";


    @Override
    @SneakyThrows
    public List<ControlOfAttendanceItems> getByItemDailyList(String companyID, List<Integer> itemDailyIDList) {
        // Check empty
        if (CollectionUtil.isEmpty(itemDailyIDList)) {
            return Collections.emptyList();
        }
        try (val stmt = this.connection()
                .prepareStatement("SELECT * FROM KSHST_ATD_ITEM_CONTROL"
                        + " WHERE CID = ? AND ITEM_DAILY_ID in ("
                        + NtsStatement.In.createParamsString(itemDailyIDList)
                        + ")")) {
            stmt.setString(1, companyID);
            for (int i = 0; i < itemDailyIDList.size(); i++) {
                stmt.setInt(i + 2, itemDailyIDList.get(i));
            }
            return new NtsResultSet(stmt.executeQuery()).getList(rec -> {
                ControlOfAttendanceItems controlOfAttendanceItems = new ControlOfAttendanceItems(rec.getString("CID"),
                        rec.getInt("ITEM_DAILY_ID"),
                        new HeaderBackgroundColor(rec.getString("HEADER_BACKGROUND_COLOR")),
                        rec.getInt("TIME_INPUT_UNIT") != null ? EnumAdaptor.valueOf(rec.getInt("TIME_INPUT_UNIT"), TimeInputUnit.class) : null);
                return controlOfAttendanceItems;
            });
        }
    }

    @Override
    public Optional<ControlOfAttendanceItems> getControlOfAttendanceItem(String companyID, int itemDailyID) {
        Optional<ControlOfAttendanceItems> data = this.queryProxy().query(GET_BY_CODE, KshstControlOfAttendanceItems.class)
                .setParameter("companyID", companyID)
                .setParameter("itemDailyID", itemDailyID)
                .getSingle(c -> c.toDomain());
        return data;
    }

    @Override
    public void updateControlOfAttendanceItem(ControlOfAttendanceItems controlOfAttendanceItems) {
        KshstControlOfAttendanceItems newEntity = KshstControlOfAttendanceItems.toEntity(controlOfAttendanceItems);
        KshstControlOfAttendanceItems updateEntity = this.queryProxy().find(newEntity.getKshstControlOfAttendanceItemsPK(), KshstControlOfAttendanceItems.class).get();
        updateEntity.headerBgColorOfDailyPer = newEntity.headerBgColorOfDailyPer;
        updateEntity.inputUnitOfTimeItem = newEntity.inputUnitOfTimeItem;
        this.commandProxy().update(updateEntity);
    }

    @Override
    public void insertControlOfAttendanceItem(ControlOfAttendanceItems controlOfAttendanceItems) {
        KshstControlOfAttendanceItems newEntity = KshstControlOfAttendanceItems.toEntity(controlOfAttendanceItems);
        this.commandProxy().insert(newEntity);
    }


}
