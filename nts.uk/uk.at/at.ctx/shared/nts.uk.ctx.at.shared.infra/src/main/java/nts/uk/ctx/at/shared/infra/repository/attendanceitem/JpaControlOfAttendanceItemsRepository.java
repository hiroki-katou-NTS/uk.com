package nts.uk.ctx.at.shared.infra.repository.attendanceitem;

import java.math.BigDecimal;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.attendanceitem.ControlOfAttendanceItems;
import nts.uk.ctx.at.shared.dom.attendanceitem.repository.ControlOfAttendanceItemsRepository;
import nts.uk.ctx.at.shared.infra.entity.attendanceitem.KdwstControlOfAttendanceItems;
import nts.uk.ctx.at.shared.infra.entity.attendanceitem.KdwstControlOfAttendanceItemsPK;
@Stateless
public class JpaControlOfAttendanceItemsRepository extends JpaRepository implements ControlOfAttendanceItemsRepository {

	@Override
	public Optional<ControlOfAttendanceItems> getControlOfAttendanceItem(String attendanceItemId) {
		Optional<KdwstControlOfAttendanceItems> kdwstControlOfAttendanceItemsOptional = this.queryProxy()
				.find(new KdwstControlOfAttendanceItemsPK(attendanceItemId), KdwstControlOfAttendanceItems.class);
		if (kdwstControlOfAttendanceItemsOptional.isPresent()) {
			return Optional
					.ofNullable(this.toControlOfAttendanceItemsDomain(kdwstControlOfAttendanceItemsOptional.get()));
		}
		return Optional.empty();
	}

	@Override
	public void updateControlOfAttendanceItem(ControlOfAttendanceItems controlOfAttendanceItems) {
		Optional<KdwstControlOfAttendanceItems> kdwstControlOfAttendanceItemsOptional = this.queryProxy().find(
				new KdwstControlOfAttendanceItemsPK(controlOfAttendanceItems.getAttandanceTimeId()),
				KdwstControlOfAttendanceItems.class);
		if (kdwstControlOfAttendanceItemsOptional.isPresent()) {
			KdwstControlOfAttendanceItems kdwstControlOfAttendanceItems = kdwstControlOfAttendanceItemsOptional.get();
			kdwstControlOfAttendanceItems.headerBackgroundColorOfDailyPerformance = controlOfAttendanceItems
					.getHeaderBackgroundColorOfDailyPerformance().v();
			kdwstControlOfAttendanceItems.inputUnitOfTimeItem = new BigDecimal(
					controlOfAttendanceItems.getInputUnitOfTimeItem().value);
			kdwstControlOfAttendanceItems.nameLineFeedPosition = new BigDecimal(controlOfAttendanceItems.getNameLineFeedPosition());
			this.commandProxy().update(kdwstControlOfAttendanceItems);
		} else {
			this.commandProxy().insert(this.toControlOfAttendanceItemsEntity(controlOfAttendanceItems));
		}

	}

	private ControlOfAttendanceItems toControlOfAttendanceItemsDomain(
			KdwstControlOfAttendanceItems kdwstControlOfAttendanceItems) {
		return ControlOfAttendanceItems.createFromJavaType(
				kdwstControlOfAttendanceItems.kdwstControlOfAttendanceItemsPK.attandanceTimeId,
				kdwstControlOfAttendanceItems.inputUnitOfTimeItem.intValue(),
				kdwstControlOfAttendanceItems.headerBackgroundColorOfDailyPerformance,kdwstControlOfAttendanceItems.nameLineFeedPosition.intValue()  );
	}

	private KdwstControlOfAttendanceItems toControlOfAttendanceItemsEntity(
			ControlOfAttendanceItems controlOfAttendanceItems) {
		return new KdwstControlOfAttendanceItems(
				new KdwstControlOfAttendanceItemsPK(controlOfAttendanceItems.getAttandanceTimeId()),
				new BigDecimal(controlOfAttendanceItems.getInputUnitOfTimeItem().value),
				controlOfAttendanceItems.getHeaderBackgroundColorOfDailyPerformance().v(),new BigDecimal(controlOfAttendanceItems.getNameLineFeedPosition()));

	}

}
