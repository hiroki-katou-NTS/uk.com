package nts.uk.ctx.at.shared.infra.repository.attendanceitem;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.attendanceitem.DisplayAndInputControlOfAttendanceItems;
import nts.uk.ctx.at.shared.dom.attendanceitem.primitives.BusinessTypeCode;
import nts.uk.ctx.at.shared.dom.attendanceitem.repository.DAIControlOfAttendanceItemsRepository;
import nts.uk.ctx.at.shared.infra.entity.attendanceitem.KshstDAIControlOfAttendanceItems;
import nts.uk.ctx.at.shared.infra.entity.attendanceitem.KshstDAIControlOfAttendanceItemsPK;

@Stateless
public class JpaDAIControlOfAttendanceItemsRepository extends JpaRepository
		implements DAIControlOfAttendanceItemsRepository {
	private final String SELECT_BY_WORKTYPECODE = "SELECT c FROM KdwstDAIControlOfAttendanceItems c WHERE c.kshstDAIControlOfAttendanceItemsPK.workTypeCode = :workTypeCode";

	@Override
	public List<DisplayAndInputControlOfAttendanceItems> getListControlOfAttendanceItem(BusinessTypeCode workTypeCode) {
		return this.queryProxy().query(SELECT_BY_WORKTYPECODE, KshstDAIControlOfAttendanceItems.class)
				.setParameter("workTypeCode", workTypeCode, BusinessTypeCode.class)
				.getList(x -> this.toDAIControlOfAttendanceItemsDomain(x));
	}

	@Override
	public void updateListControlOfAttendanceItem(
			List<DisplayAndInputControlOfAttendanceItems> lstDisplayAndInputControlOfAttendanceItems) {
		lstDisplayAndInputControlOfAttendanceItems.forEach(c -> {
			Optional<KshstDAIControlOfAttendanceItems> kdwstDAIControlOfAttendanceItemsOptional = this.queryProxy()
					.find(new KshstDAIControlOfAttendanceItemsPK(c.getBusinessTypeCode().v(), c.getAttendanceItemId()),
							KshstDAIControlOfAttendanceItems.class);
			if (kdwstDAIControlOfAttendanceItemsOptional.isPresent()) {
				KshstDAIControlOfAttendanceItems kdwstDAIControlOfAttendanceItems = kdwstDAIControlOfAttendanceItemsOptional
						.get();
				kdwstDAIControlOfAttendanceItems.canBeChangedByOthers = new BigDecimal(
						c.isCanBeChangedByOthers() ? 1 : 0);
				kdwstDAIControlOfAttendanceItems.youCanChangeIt = new BigDecimal(c.isYouCanChangeIt() ? 1 : 0);
				kdwstDAIControlOfAttendanceItems.use = new BigDecimal(c.isUse() ? 1 : 0);
				this.commandProxy().update(kdwstDAIControlOfAttendanceItems);
			} else {
				this.commandProxy().insert(this.toDAIControlOfAttendanceItemsEntity(c));
			}
		});

	}

	private DisplayAndInputControlOfAttendanceItems toDAIControlOfAttendanceItemsDomain(
			KshstDAIControlOfAttendanceItems kdwstDAIControlOfAttendanceItems) {
		return DisplayAndInputControlOfAttendanceItems.createFromJavaType(
				kdwstDAIControlOfAttendanceItems.kshstDAIControlOfAttendanceItemsPK.attendanceItemId,
				kdwstDAIControlOfAttendanceItems.kshstDAIControlOfAttendanceItemsPK.businessTypeCode,
				kdwstDAIControlOfAttendanceItems.youCanChangeIt.intValue() == 1 ? true : false,
				kdwstDAIControlOfAttendanceItems.canBeChangedByOthers.intValue() == 1 ? true : false,
				kdwstDAIControlOfAttendanceItems.use.intValue() == 1 ? true : false);
	}

	private KshstDAIControlOfAttendanceItems toDAIControlOfAttendanceItemsEntity(
			DisplayAndInputControlOfAttendanceItems displayAndInputControlOfAttendanceItems) {
		return new KshstDAIControlOfAttendanceItems(
				new KshstDAIControlOfAttendanceItemsPK(displayAndInputControlOfAttendanceItems.getBusinessTypeCode().v(),
						displayAndInputControlOfAttendanceItems.getAttendanceItemId()),
				new BigDecimal(displayAndInputControlOfAttendanceItems.isYouCanChangeIt() ? 1 : 0),
				new BigDecimal(displayAndInputControlOfAttendanceItems.isCanBeChangedByOthers() ? 1 : 0),
				new BigDecimal(displayAndInputControlOfAttendanceItems.isUse() ? 1 : 0));
	}

}
