package nts.uk.ctx.at.shared.infra.repository.remainingnumber.specialholiday;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UseDay;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UseTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialholidaymng.interim.InterimSpecialHolidayMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialholidaymng.interim.InterimSpecialHolidayMngRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialholidaymng.interim.ManagermentAtr;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.specialholiday.interim.KrcdtInterimHdSpMng;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.specialholiday.interim.KrcmtInterimSpeHolidayPK;

@Stateless
public class JpaInterimSpecialHolidayMngRepo extends JpaRepository implements InterimSpecialHolidayMngRepository {

	private static final String DELETE_BY_ID = "DELETE FROM KrcdtInterimHdSpMng c"
			+ " WHERE c.pk.specialHolidayId = :specialHolidayId";
	private static final String QUERY_BY_ID = "SELECT c FROM KrcdtInterimHdSpMng c"
			+ " WHERE c.pk.specialHolidayId = :specialHolidayId";

	private InterimSpecialHolidayMng toDomain(KrcdtInterimHdSpMng c) {
		return new InterimSpecialHolidayMng(c.pk.specialHolidayId,
				// c.pk.specialHolidayCode,
				c.specialHolidayCode, EnumAdaptor.valueOf(c.mngAtr, ManagermentAtr.class),
				Optional.of(new UseTime(c.usedTime == null ? 0 : c.usedTime)),
				Optional.of(new UseDay(c.usedDays)));
	}

	@Override
	public void persistAndUpdateInterimSpecialHoliday(InterimSpecialHolidayMng domain) {
//		KrcmtInterimSpeHolidayPK key = new KrcmtInterimSpeHolidayPK(domain.getSpecialHolidayId(), domain.getSpecialHolidayCode());
		KrcmtInterimSpeHolidayPK key = new KrcmtInterimSpeHolidayPK(domain.getSpecialHolidayId());
		KrcdtInterimHdSpMng entity = this.getEntityManager().find(KrcdtInterimHdSpMng.class, key);

		if (entity == null) {
			entity = new KrcdtInterimHdSpMng();
			entity.mngAtr = domain.getMngAtr().value;
			entity.usedDays = domain.getUseDays().isPresent() ? domain.getUseDays().get().v() : 0.0;
			entity.usedTime = domain.getUseTimes().isPresent() ? domain.getUseTimes().get().v() : 0;
			key.specialHolidayId = domain.getSpecialHolidayId();
//			key.specialHolidayCode = domain.getSpecialHolidayCode();
			entity.pk = key;
			this.getEntityManager().persist(entity);
		} else {
			entity.mngAtr = domain.getMngAtr().value;
			entity.usedDays = domain.getUseDays().isPresent() ? domain.getUseDays().get().v() : 0.0;
			entity.usedTime = domain.getUseTimes().isPresent() ? domain.getUseTimes().get().v() : 0;
			this.commandProxy().update(entity);
		}
		this.getEntityManager().flush();
	}

	@Override
	public void deleteSpecialHoliday(String specialId) {
		this.getEntityManager().createQuery(DELETE_BY_ID).setParameter("specialHolidayId", specialId).executeUpdate();
	}

	@Override
	public List<InterimSpecialHolidayMng> findById(String mngId) {
		return this.queryProxy().query(QUERY_BY_ID, KrcdtInterimHdSpMng.class)
				.setParameter("specialHolidayId", mngId).getList(c -> toDomain(c));
	}

}
