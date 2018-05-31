package nts.uk.ctx.at.shared.infra.repository.scherec.dailyattendanceitem;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.ControlOfAttendanceItems;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.repository.ControlOfAttendanceItemsRepository;
import nts.uk.ctx.at.shared.infra.entity.scherec.dailyattendanceitem.KshstControlOfAttendanceItems;

@Stateless
public class JpaControlOfAttendanceItemsRepository extends JpaRepository implements ControlOfAttendanceItemsRepository {

	private static final String GET_BY_CODE = "SELECT c FROM KshstControlOfAttendanceItems c "
			+ " WHERE c.kshstControlOfAttendanceItemsPK.companyID = :companyID "
			+ " AND c.kshstControlOfAttendanceItemsPK.itemDailyID = :itemDailyID ";
			
	
	@Override
	public Optional<ControlOfAttendanceItems> getControlOfAttendanceItem(String companyID, int itemDailyID) {
		Optional<ControlOfAttendanceItems> data = this.queryProxy().query(GET_BY_CODE,KshstControlOfAttendanceItems.class)
				.setParameter("companyID", companyID)
				.setParameter("itemDailyID", itemDailyID)
				.getSingle(c->c.toDomain());
		return data;
	}

	@Override
	public void updateControlOfAttendanceItem(ControlOfAttendanceItems controlOfAttendanceItems) {
		KshstControlOfAttendanceItems newEntity =KshstControlOfAttendanceItems.toEntity(controlOfAttendanceItems);
		KshstControlOfAttendanceItems updateEntity = this.queryProxy().find(newEntity.getKshstControlOfAttendanceItemsPK(), KshstControlOfAttendanceItems.class).get();
		updateEntity.headerBgColorOfDailyPer = newEntity.headerBgColorOfDailyPer;
		updateEntity.inputUnitOfTimeItem = newEntity.inputUnitOfTimeItem;
		this.commandProxy().update(updateEntity);
	}

	@Override
	public void insertControlOfAttendanceItem(ControlOfAttendanceItems controlOfAttendanceItems) {
		KshstControlOfAttendanceItems newEntity =KshstControlOfAttendanceItems.toEntity(controlOfAttendanceItems);
		this.commandProxy().insert(newEntity);
	}


	

}
