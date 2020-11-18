package nts.uk.ctx.at.shared.infra.repository.scherec.dailyattendanceitem;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.ControlOfAttendanceItems;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.repository.ControlOfAttendanceItemsRepository;
import nts.uk.ctx.at.shared.infra.entity.scherec.dailyattendanceitem.KshmtDayAtdCtr;

@Stateless
public class JpaControlOfAttendanceItemsRepository extends JpaRepository implements ControlOfAttendanceItemsRepository {

	private static final String GET_BY_CODE = "SELECT c FROM KshmtDayAtdCtr c "
			+ " WHERE c.kshmtDayAtdCtrPK.companyID = :companyID "
			+ " AND c.kshmtDayAtdCtrPK.itemDailyID = :itemDailyID ";
			
	
	@Override
	public Optional<ControlOfAttendanceItems> getControlOfAttendanceItem(String companyID, int itemDailyID) {
		Optional<ControlOfAttendanceItems> data = this.queryProxy().query(GET_BY_CODE,KshmtDayAtdCtr.class)
				.setParameter("companyID", companyID)
				.setParameter("itemDailyID", itemDailyID)
				.getSingle(c->c.toDomain());
		return data;
	}

	@Override
	public void updateControlOfAttendanceItem(ControlOfAttendanceItems controlOfAttendanceItems) {
		KshmtDayAtdCtr newEntity =KshmtDayAtdCtr.toEntity(controlOfAttendanceItems);
		KshmtDayAtdCtr updateEntity = this.queryProxy().find(newEntity.getKshmtDayAtdCtrPK(), KshmtDayAtdCtr.class).get();
		updateEntity.headerBgColorOfDailyPer = newEntity.headerBgColorOfDailyPer;
		updateEntity.inputUnitOfTimeItem = newEntity.inputUnitOfTimeItem;
		this.commandProxy().update(updateEntity);
	}

	@Override
	public void insertControlOfAttendanceItem(ControlOfAttendanceItems controlOfAttendanceItems) {
		KshmtDayAtdCtr newEntity =KshmtDayAtdCtr.toEntity(controlOfAttendanceItems);
		this.commandProxy().insert(newEntity);
	}


	

}
