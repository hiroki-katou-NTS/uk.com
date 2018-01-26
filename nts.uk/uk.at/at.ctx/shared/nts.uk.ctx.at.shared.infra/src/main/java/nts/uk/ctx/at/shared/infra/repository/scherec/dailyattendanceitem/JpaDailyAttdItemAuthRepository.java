package nts.uk.ctx.at.shared.infra.repository.scherec.dailyattendanceitem;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.DailyAttendanceItemAuthority;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.repository.DailyAttdItemAuthRepository;
import nts.uk.ctx.at.shared.infra.entity.scherec.dailyattendanceitem.KshstDailyAttdItemAuth;
import nts.uk.ctx.at.shared.infra.entity.scherec.dailyattendanceitem.KshstDailyAttdItemAuthPK;

@Stateless
public class JpaDailyAttdItemAuthRepository extends JpaRepository implements DailyAttdItemAuthRepository {
	private final String SELECT_BY_AUTHORITYID_AND_COMPANYID = "SELECT c, k.userCanSet, k.krcmtDailyAttendanceItemPK.attendanceItemId "
			+ "FROM KrcmtDailyAttendanceItem k LEFT JOIN KshstDailyAttdItemAuth c "
			+ "ON c.kshstDailyAttdItemAuthPK.attendanceItemId = k.krcmtDailyAttendanceItemPK.attendanceItemId  "
			+ "AND c.kshstDailyAttdItemAuthPK.authorityId = :authorityId"
			+ " WHERE k.krcmtDailyAttendanceItemPK.companyId = :companyId";

	@Override
	public List<DailyAttendanceItemAuthority> getListDailyAttendanceItemAuthority(String authorityId,
			String companyId) {
		return this.queryProxy().query(SELECT_BY_AUTHORITYID_AND_COMPANYID, Object[].class)
				.setParameter("authorityId", authorityId).setParameter("companyId", companyId)
				.getList(x -> this.toDomain(x, authorityId));
	}

	@Override
	public void updateListDailyAttendanceItemAuthority(
			List<DailyAttendanceItemAuthority> lstDailyAttendanceItemAuthority) {

		lstDailyAttendanceItemAuthority.forEach(c -> {
			Optional<KshstDailyAttdItemAuth> kshstDailyAttdItemAuthOptional = this.queryProxy().find(
					new KshstDailyAttdItemAuthPK(c.getAuthorityId(), new BigDecimal(c.getAttendanceItemId())),
					KshstDailyAttdItemAuth.class);
			if (kshstDailyAttdItemAuthOptional.isPresent()) {
				KshstDailyAttdItemAuth kshstDailyAttdItemAuth = kshstDailyAttdItemAuthOptional.get();
				kshstDailyAttdItemAuth.use = new BigDecimal(c.isUse() ? 1 : 0);
				this.commandProxy().update(kshstDailyAttdItemAuth);
				if (kshstDailyAttdItemAuth.use.intValue() != (c.isUse() ? 1 : 0)
						|| kshstDailyAttdItemAuth.canBeChangedByOthers
								.intValue() != (c.isCanBeChangedByOthers() ? 1 : 0)
						|| kshstDailyAttdItemAuth.youCanChangeIt.intValue() != (c.isYouCanChangeIt() ? 1 : 0)) {

					kshstDailyAttdItemAuth.use = new BigDecimal(c.isUse() ? 1 : 0);
					if (c.isUse()) {
						kshstDailyAttdItemAuth.canBeChangedByOthers = new BigDecimal(
								c.isCanBeChangedByOthers() ? 1 : 0);
						kshstDailyAttdItemAuth.youCanChangeIt = new BigDecimal(c.isYouCanChangeIt() ? 1 : 0);
					}
					this.commandProxy().insert(kshstDailyAttdItemAuth);
				}

			} else {
				this.commandProxy().insert(this.toEntity(c));
			}

		});

	}

	private DailyAttendanceItemAuthority toDomain(Object[] object, String authorityId) {

		KshstDailyAttdItemAuth kshstDailyAttdItemAuth = (KshstDailyAttdItemAuth) object[0];
		int userCanSet = ((BigDecimal) object[1]).intValue();
		int attendanceId = (int) object[2];
		if (kshstDailyAttdItemAuth == null) {
			return DailyAttendanceItemAuthority.createFromJavaType(attendanceId, authorityId, false, false, true,
					userCanSet);
		}
		return DailyAttendanceItemAuthority.createFromJavaType(
				kshstDailyAttdItemAuth.kshstDailyAttdItemAuthPK.attendanceItemId.intValue(),
				kshstDailyAttdItemAuth.kshstDailyAttdItemAuthPK.authorityId,
				kshstDailyAttdItemAuth.youCanChangeIt.intValue() == 1 ? true : false,
				kshstDailyAttdItemAuth.canBeChangedByOthers.intValue() == 1 ? true : false,
				kshstDailyAttdItemAuth.use.intValue() == 1 ? true : false, userCanSet);

	}

	private KshstDailyAttdItemAuth toEntity(DailyAttendanceItemAuthority dailyAttendanceItemAuthority) {
		return new KshstDailyAttdItemAuth(
				new KshstDailyAttdItemAuthPK(dailyAttendanceItemAuthority.getAuthorityId(),
						new BigDecimal(dailyAttendanceItemAuthority.getAttendanceItemId())),
				new BigDecimal(dailyAttendanceItemAuthority.isYouCanChangeIt() ? 1 : 0),
				new BigDecimal(dailyAttendanceItemAuthority.isCanBeChangedByOthers() ? 1 : 0),
				new BigDecimal(dailyAttendanceItemAuthority.isUse() ? 1 : 0));

	}

}
