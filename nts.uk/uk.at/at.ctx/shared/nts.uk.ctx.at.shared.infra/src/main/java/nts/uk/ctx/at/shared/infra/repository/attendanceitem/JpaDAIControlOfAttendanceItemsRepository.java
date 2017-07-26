package nts.uk.ctx.at.shared.infra.repository.attendanceitem;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.attendanceitem.DisplayAndInputControlOfAttendanceItems;
import nts.uk.ctx.at.shared.dom.attendanceitem.primitives.BusinessTypeCode;
import nts.uk.ctx.at.shared.dom.attendanceitem.repository.DAIControlOfAttendanceItemsRepository;
import nts.uk.ctx.at.shared.infra.entity.attendanceitem.KdwstDAIControlOfAttendanceItems;
import nts.uk.ctx.at.shared.infra.entity.attendanceitem.KdwstDAIControlOfAttendanceItemsPK;

@Stateless
public class JpaDAIControlOfAttendanceItemsRepository extends JpaRepository
		implements DAIControlOfAttendanceItemsRepository {
	private final String SELECT_BY_WORKTYPECODE = "SELECT c FROM KdwstDAIControlOfAttendanceItems c WHERE c.kdwstDAIControlOfAttendanceItemsPK.workTypeCode = :workTypeCode";

	@Override
	public List<DisplayAndInputControlOfAttendanceItems> getListControlOfAttendanceItem(BusinessTypeCode workTypeCode) {
		return this.queryProxy().query(SELECT_BY_WORKTYPECODE, KdwstDAIControlOfAttendanceItems.class)
				.setParameter("workTypeCode", workTypeCode, BusinessTypeCode.class)
				.getList(x -> this.toDAIControlOfAttendanceItemsDomain(x));
	}

	@Override
	public void updateListControlOfAttendanceItem(
			List<DisplayAndInputControlOfAttendanceItems> lstDisplayAndInputControlOfAttendanceItems) {
		lstDisplayAndInputControlOfAttendanceItems.forEach(c -> {
			Optional<KdwstDAIControlOfAttendanceItems> kdwstDAIControlOfAttendanceItemsOptional = this.queryProxy()
					.find(new KdwstDAIControlOfAttendanceItemsPK(c.getBusinessTypeCode().v(), c.getAttendanceItemId()),
							KdwstDAIControlOfAttendanceItems.class);
			if (kdwstDAIControlOfAttendanceItemsOptional.isPresent()) {
				KdwstDAIControlOfAttendanceItems kdwstDAIControlOfAttendanceItems = kdwstDAIControlOfAttendanceItemsOptional
						.get();
				kdwstDAIControlOfAttendanceItems.canBeChangedByOthers = new BigDecimal(
						c.isCanBeChangedByOthers() ? 1 : 0);
				kdwstDAIControlOfAttendanceItems.userCanSet = new BigDecimal(c.isUserCanSet() ? 1 : 0);
				kdwstDAIControlOfAttendanceItems.youCanChangeIt = new BigDecimal(c.isYouCanChangeIt() ? 1 : 0);
				kdwstDAIControlOfAttendanceItems.use = new BigDecimal(c.isUse() ? 1 : 0);
				this.commandProxy().update(kdwstDAIControlOfAttendanceItems);
			} else {
				this.commandProxy().insert(this.toDAIControlOfAttendanceItemsEntity(c));
			}
		});

	}

	private DisplayAndInputControlOfAttendanceItems toDAIControlOfAttendanceItemsDomain(
			KdwstDAIControlOfAttendanceItems kdwstDAIControlOfAttendanceItems) {
		return DisplayAndInputControlOfAttendanceItems.createFromJavaType(
				kdwstDAIControlOfAttendanceItems.kdwstDAIControlOfAttendanceItemsPK.attendanceItemId,
				kdwstDAIControlOfAttendanceItems.kdwstDAIControlOfAttendanceItemsPK.businessTypeCode,
				kdwstDAIControlOfAttendanceItems.userCanSet.intValue() == 1 ? true : false,
				kdwstDAIControlOfAttendanceItems.youCanChangeIt.intValue() == 1 ? true : false,
				kdwstDAIControlOfAttendanceItems.canBeChangedByOthers.intValue() == 1 ? true : false,
				kdwstDAIControlOfAttendanceItems.use.intValue() == 1 ? true : false);
	}

	private KdwstDAIControlOfAttendanceItems toDAIControlOfAttendanceItemsEntity(
			DisplayAndInputControlOfAttendanceItems displayAndInputControlOfAttendanceItems) {
		return new KdwstDAIControlOfAttendanceItems(
				new KdwstDAIControlOfAttendanceItemsPK(displayAndInputControlOfAttendanceItems.getBusinessTypeCode().v(),
						displayAndInputControlOfAttendanceItems.getAttendanceItemId()),
				new BigDecimal(displayAndInputControlOfAttendanceItems.isUserCanSet() ? 1 : 0),
				new BigDecimal(displayAndInputControlOfAttendanceItems.isYouCanChangeIt() ? 1 : 0),
				new BigDecimal(displayAndInputControlOfAttendanceItems.isCanBeChangedByOthers() ? 1 : 0),
				new BigDecimal(displayAndInputControlOfAttendanceItems.isUse() ? 1 : 0));
	}

}
