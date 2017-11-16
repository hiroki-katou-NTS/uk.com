package nts.uk.ctx.at.shared.infra.repository.scherec.dailyattendanceitem;

import java.math.BigDecimal;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.ControlOfAttendanceItems;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.repository.ControlOfAttendanceItemsRepository;
import nts.uk.ctx.at.shared.infra.entity.scherec.dailyattendanceitem.KshstControlOfAttendanceItems;
import nts.uk.ctx.at.shared.infra.entity.scherec.dailyattendanceitem.KshstControlOfAttendanceItemsPK;

@Stateless
public class JpaControlOfAttendanceItemsRepository extends JpaRepository implements ControlOfAttendanceItemsRepository {

	@Override
	public Optional<ControlOfAttendanceItems> getControlOfAttendanceItem(BigDecimal attendanceItemId) {
		Optional<KshstControlOfAttendanceItems> kdwstControlOfAttendanceItemsOptional = this.queryProxy()
				.find(new KshstControlOfAttendanceItemsPK(attendanceItemId), KshstControlOfAttendanceItems.class);
		if (kdwstControlOfAttendanceItemsOptional.isPresent()) {
			return Optional
					.ofNullable(this.toControlOfAttendanceItemsDomain(kdwstControlOfAttendanceItemsOptional.get()));
		}
		return Optional.empty();
	}

	

	private ControlOfAttendanceItems toControlOfAttendanceItemsDomain(
			KshstControlOfAttendanceItems kdwstControlOfAttendanceItems) {
		return ControlOfAttendanceItems.createFromJavaType(
				kdwstControlOfAttendanceItems.kshstControlOfAttendanceItemsPK.attandanceTimeId,
				kdwstControlOfAttendanceItems.inputUnitOfTimeItem == null ? -1
						: kdwstControlOfAttendanceItems.inputUnitOfTimeItem.intValue(),
				kdwstControlOfAttendanceItems.headerBackgroundColorOfDailyPerformance == null ? ""
						: kdwstControlOfAttendanceItems.headerBackgroundColorOfDailyPerformance,
				kdwstControlOfAttendanceItems.nameLineFeedPosition.intValue());
	}
	
	@Override
	public void updateControlOfAttendanceItem(ControlOfAttendanceItems controlOfAttendanceItems) {
		Optional<KshstControlOfAttendanceItems> kdwstControlOfAttendanceItemsOptional = this.queryProxy().find(
				new KshstControlOfAttendanceItemsPK(controlOfAttendanceItems.getAttandanceTimeId()),
				KshstControlOfAttendanceItems.class);
		if (kdwstControlOfAttendanceItemsOptional.isPresent()) {
			KshstControlOfAttendanceItems kdwstControlOfAttendanceItems = kdwstControlOfAttendanceItemsOptional.get();
			kdwstControlOfAttendanceItems.headerBackgroundColorOfDailyPerformance = controlOfAttendanceItems
					.getHeaderBackgroundColorOfDailyPerformance() == null ? ""
							: controlOfAttendanceItems.getHeaderBackgroundColorOfDailyPerformance().v();
			kdwstControlOfAttendanceItems.inputUnitOfTimeItem = new BigDecimal(
					controlOfAttendanceItems.getInputUnitOfTimeItem() == null ? -1
							: controlOfAttendanceItems.getInputUnitOfTimeItem().value);
			this.commandProxy().update(kdwstControlOfAttendanceItems);
		} else {
			this.commandProxy().insert(this.toControlOfAttendanceItemsEntity(controlOfAttendanceItems));
		}

	}

	private KshstControlOfAttendanceItems toControlOfAttendanceItemsEntity(
			ControlOfAttendanceItems controlOfAttendanceItems) {
		BigDecimal inputUnitOfTimeItem;
		if (controlOfAttendanceItems.getInputUnitOfTimeItem() == null) {
			inputUnitOfTimeItem = null;
		} else {
			inputUnitOfTimeItem = new BigDecimal(controlOfAttendanceItems.getInputUnitOfTimeItem().value);
		}
		String headerColor;
		if ("".equals(controlOfAttendanceItems.getHeaderBackgroundColorOfDailyPerformance().v())) {
			headerColor = null;
		} else {
			headerColor = controlOfAttendanceItems.getHeaderBackgroundColorOfDailyPerformance().v();
		}

		return new KshstControlOfAttendanceItems(
				new KshstControlOfAttendanceItemsPK(controlOfAttendanceItems.getAttandanceTimeId()),
				inputUnitOfTimeItem, headerColor, new BigDecimal(controlOfAttendanceItems.getNameLineFeedPosition()));

	}

}
