package nts.uk.ctx.at.shared.infra.repository.scherec.dailyattendanceitem;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.DailyServiceTypeControl;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.primitivevalue.BusinessTypeCode;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.repository.DailyServiceTypeControlRepository;
import nts.uk.ctx.at.shared.infra.entity.scherec.dailyattendanceitem.KshstDailyServiceTypeControl;
import nts.uk.ctx.at.shared.infra.entity.scherec.dailyattendanceitem.KshstDailyServiceTypeControlPK;

@Stateless
public class JpaDailyServiceTypeControlRepository extends JpaRepository implements DailyServiceTypeControlRepository {
	private final String SELECT_BY_BUSINESSCODE_AND_COMPANYID = "SELECT c, k.attendanceItemName, k.userCanSet, k.krcmtDailyAttendanceItemPK.attendanceItemId "
			+ "FROM KrcmtDailyAttendanceItem k LEFT JOIN KshstDailyServiceTypeControl c "
			+ "ON c.kshstDailyServiceTypeControlPK.attendanceItemId = k.krcmtDailyAttendanceItemPK.attendanceItemId  "
			+ "AND c.kshstDailyServiceTypeControlPK.businessTypeCode = :businessTypeCode"
			+ " WHERE k.krcmtDailyAttendanceItemPK.companyId = :companyId";

	private final String SELECT_BY_BUSINESSCODE_AND_ATTENDANCEID = "SELECT c FROM KshstDailyServiceTypeControl c "
			+ "WHERE c.kshstDailyServiceTypeControlPK.businessTypeCode = :businessTypeCode "
			+ "AND c.kshstDailyServiceTypeControlPK.attendanceItemId =  :attendanceItemId";

	@Override
	public List<DailyServiceTypeControl> getListDailyServiceTypeControl(BusinessTypeCode businessTypeCode,
			String companyId) {

		return this.queryProxy().query(SELECT_BY_BUSINESSCODE_AND_COMPANYID, Object[].class)
				.setParameter("businessTypeCode", businessTypeCode, BusinessTypeCode.class)
				.setParameter("companyId", companyId).getList(x -> this.toDomain(x, businessTypeCode.v()));
	}

	@Override
	public void updateListDailyServiceTypeControl(List<DailyServiceTypeControl> lstDailyServiceTypeControl) {

		lstDailyServiceTypeControl.forEach(c -> {
			Optional<KshstDailyServiceTypeControl> kshstDailyServiceTypeControlOptional = this.queryProxy()
					.find(new KshstDailyServiceTypeControlPK(c.getBusinessTypeCode().v(),
							new BigDecimal(c.getAttendanceItemId())), KshstDailyServiceTypeControl.class);
			if (kshstDailyServiceTypeControlOptional.isPresent()) {
				KshstDailyServiceTypeControl kshstDailyServiceTypeControl = kshstDailyServiceTypeControlOptional.get();
				if (kshstDailyServiceTypeControl.use.intValue() != (c.isUse() ? 1 : 0)
						|| kshstDailyServiceTypeControl.canBeChangedByOthers
								.intValue() != (c.isCanBeChangedByOthers() ? 1 : 0)
						|| kshstDailyServiceTypeControl.youCanChangeIt.intValue() != (c.isYouCanChangeIt() ? 1 : 0)) {
					kshstDailyServiceTypeControl.use = new BigDecimal(c.isUse() ? 1 : 0);
					if (c.isUse()) {
						kshstDailyServiceTypeControl.canBeChangedByOthers = new BigDecimal(
								c.isCanBeChangedByOthers() ? 1 : 0);
						kshstDailyServiceTypeControl.youCanChangeIt = new BigDecimal(c.isYouCanChangeIt() ? 1 : 0);
					}
					this.commandProxy().update(kshstDailyServiceTypeControl);
				}

			} else {
				this.commandProxy().insert(this.toDailyServiceTypeControlEntity(c));
			}
		});

	}

	private KshstDailyServiceTypeControl toDailyServiceTypeControlEntity(
			DailyServiceTypeControl dailyServiceTypeControl) {
		return new KshstDailyServiceTypeControl(
				new KshstDailyServiceTypeControlPK(dailyServiceTypeControl.getBusinessTypeCode().v(),
						new BigDecimal(dailyServiceTypeControl.getAttendanceItemId())),
				new BigDecimal(dailyServiceTypeControl.isYouCanChangeIt() ? 1 : 0),
				new BigDecimal(dailyServiceTypeControl.isCanBeChangedByOthers() ? 1 : 0),
				new BigDecimal(dailyServiceTypeControl.isUse() ? 1 : 0));
	}

	private DailyServiceTypeControl toDomain(Object[] object, String businessTypeCode) {
		String attendanceItemName = (String) object[1];
		int userCanSet = ((BigDecimal) object[2]).intValue();
		KshstDailyServiceTypeControl kshstDailyServiceTypeControl = (KshstDailyServiceTypeControl) object[0];
		int attendanceId = (int) object[3];
		if (kshstDailyServiceTypeControl == null) {
			return DailyServiceTypeControl.createFromJavaType(attendanceId, businessTypeCode, false, false, true,
					attendanceItemName, userCanSet);
		}
		return DailyServiceTypeControl.createFromJavaType(
				kshstDailyServiceTypeControl.kshstDailyServiceTypeControlPK.attendanceItemId.intValue(),
				kshstDailyServiceTypeControl.kshstDailyServiceTypeControlPK.businessTypeCode,
				kshstDailyServiceTypeControl.youCanChangeIt.intValue() == 1 ? true : false,
				kshstDailyServiceTypeControl.canBeChangedByOthers.intValue() == 1 ? true : false,
				kshstDailyServiceTypeControl.use.intValue() == 1 ? true : false, attendanceItemName, userCanSet);

	}

	@Override
	public Optional<DailyServiceTypeControl> getDailyServiceTypeControl(BusinessTypeCode businessTypeCode,
			int attendanceItemId) {
		Optional<KshstDailyServiceTypeControl> kshstDailyServiceTypeControlOptional = this.queryProxy()
				.query(SELECT_BY_BUSINESSCODE_AND_ATTENDANCEID, KshstDailyServiceTypeControl.class)
				.setParameter("businessTypeCode", businessTypeCode, BusinessTypeCode.class)
				.setParameter("attendanceItemId", attendanceItemId).getSingle();
		if (kshstDailyServiceTypeControlOptional.isPresent()) {
			KshstDailyServiceTypeControl kshstDailyServiceTypeControl = kshstDailyServiceTypeControlOptional.get();
			Optional.ofNullable(DailyServiceTypeControl.createFromJavaType(attendanceItemId, businessTypeCode.v(),
					kshstDailyServiceTypeControl.youCanChangeIt.intValue() == 1 ? true : false,
					kshstDailyServiceTypeControl.canBeChangedByOthers.intValue() == 1 ? true : false,
					kshstDailyServiceTypeControl.use.intValue() == 1 ? true : false, "", 0));
		}
		return Optional.empty();
	}

}
