package nts.uk.ctx.at.shared.infra.repository.attendanceitem;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.attendanceitem.DailyServiceTypeControl;
import nts.uk.ctx.at.shared.dom.attendanceitem.primitives.BusinessTypeCode;
import nts.uk.ctx.at.shared.dom.attendanceitem.repository.DailyServiceTypeControlRepository;
import nts.uk.ctx.at.shared.infra.entity.attendanceitem.KshstDailyServiceTypeControl;
import nts.uk.ctx.at.shared.infra.entity.attendanceitem.KshstDailyServiceTypeControlPK;

@Stateless
public class JpaDailyServiceTypeControlRepository extends JpaRepository
		implements DailyServiceTypeControlRepository {
	private final String SELECT_BY_WORKTYPECODE = "SELECT c FROM KshstDailyServiceTypeControl c WHERE c.kshstDailyServiceTypeControlPK.workTypeCode = :workTypeCode";

	@Override
	public List<DailyServiceTypeControl> getListDailyServiceTypeControl(BusinessTypeCode workTypeCode) {
		return this.queryProxy().query(SELECT_BY_WORKTYPECODE, KshstDailyServiceTypeControl.class)
				.setParameter("workTypeCode", workTypeCode, BusinessTypeCode.class)
				.getList(x -> this.toDailyServiceTypeControlDomain(x));
	}
	
	
	@Override
	public void updateListDailyServiceTypeControl(
			List<DailyServiceTypeControl> lstDailyServiceTypeControl) {
		lstDailyServiceTypeControl.forEach(c -> {
			Optional<KshstDailyServiceTypeControl> kshstDailyServiceTypeControlOptional = this.queryProxy()
					.find(new KshstDailyServiceTypeControlPK(c.getBusinessTypeCode().v(), c.getAttendanceItemId()),
							KshstDailyServiceTypeControl.class);
			if (kshstDailyServiceTypeControlOptional.isPresent()) {
				KshstDailyServiceTypeControl kshstDailyServiceTypeControl = kshstDailyServiceTypeControlOptional
						.get();
				kshstDailyServiceTypeControl.canBeChangedByOthers = new BigDecimal(
						c.isCanBeChangedByOthers() ? 1 : 0);
				kshstDailyServiceTypeControl.youCanChangeIt = new BigDecimal(c.isYouCanChangeIt() ? 1 : 0);
				kshstDailyServiceTypeControl.use = new BigDecimal(c.isUse() ? 1 : 0);
				this.commandProxy().update(kshstDailyServiceTypeControl);
			} else {
				this.commandProxy().insert(this.toDailyServiceTypeControlEntity(c));
			}
		});

	}

	private DailyServiceTypeControl toDailyServiceTypeControlDomain(
			KshstDailyServiceTypeControl kshstDailyServiceTypeControl) {
		return DailyServiceTypeControl.createFromJavaType(
				kshstDailyServiceTypeControl.kshstDailyServiceTypeControlPK.attendanceItemId,
				kshstDailyServiceTypeControl.kshstDailyServiceTypeControlPK.businessTypeCode,
				kshstDailyServiceTypeControl.youCanChangeIt.intValue() == 1 ? true : false,
				kshstDailyServiceTypeControl.canBeChangedByOthers.intValue() == 1 ? true : false,
				kshstDailyServiceTypeControl.use.intValue() == 1 ? true : false);
	}

	private KshstDailyServiceTypeControl toDailyServiceTypeControlEntity(
			DailyServiceTypeControl dailyServiceTypeControl) {
		return new KshstDailyServiceTypeControl(
				new KshstDailyServiceTypeControlPK(dailyServiceTypeControl.getBusinessTypeCode().v(),
						dailyServiceTypeControl.getAttendanceItemId()),
				new BigDecimal(dailyServiceTypeControl.isYouCanChangeIt() ? 1 : 0),
				new BigDecimal(dailyServiceTypeControl.isCanBeChangedByOthers() ? 1 : 0),
				new BigDecimal(dailyServiceTypeControl.isUse() ? 1 : 0));
	}

}
