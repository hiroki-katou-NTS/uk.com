package nts.uk.ctx.at.shared.infra.repository.remainingnumber.specialholiday;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainType;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UseDay;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UseTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialholidaymng.interim.InterimSpecialHolidayMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialholidaymng.interim.InterimSpecialHolidayMngRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialholidaymng.interim.ManagermentAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.AppTimeType;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.DigestionHourlyTimeType;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.specialholiday.interim.KrcdtInterimHdSpMng;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.specialholiday.interim.KrcmtInterimSpeHolidayPK;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class JpaInterimSpecialHolidayMngRepo extends JpaRepository implements InterimSpecialHolidayMngRepository {

	private static final String DELETE_BY_ID = "DELETE FROM KrcdtInterimHdSpMng c"
			+ " WHERE c.remainMngId = :remainMngId";
	private static final String QUERY_BY_ID = "SELECT c FROM KrcdtInterimHdSpMng c"
			+ " WHERE c.remainMngId = :remainMngId";
	
	private static final String DELETE_BY_SID_YMD = "DELETE FROM KrcdtInterimHdSpMng c"
			+ " WHERE c.pk.sid = :sid AND c.pk.ymd = :ymd";

	private InterimSpecialHolidayMng toDomain(KrcdtInterimHdSpMng c) {
		
		return new InterimSpecialHolidayMng(c.remainMngId,
				c.pk.sid,
				c.pk.ymd,
				EnumAdaptor.valueOf(c.createAtr, CreateAtr.class),
				RemainType.SPECIAL,
				c.pk.specialHolidayCode,
				EnumAdaptor.valueOf(c.mngAtr, ManagermentAtr.class),
				Optional.of(new UseTime(c.usedTime == null ? 0 : c.usedTime)),
				Optional.of(new UseDay(c.usedDays)),
				Optional.of(DigestionHourlyTimeType.of(c.pk.timeDigestiveAtr == 1,
						Optional.of(EnumAdaptor.valueOf(c.pk.timeHdType, AppTimeType.class))))
				);
	}

	@Override
	public void persistAndUpdateInterimSpecialHoliday(InterimSpecialHolidayMng domain) {
		
		KrcmtInterimSpeHolidayPK key = new KrcmtInterimSpeHolidayPK(
				AppContexts.user().companyId(),
				domain.getSID(),
				domain.getYmd(),
				domain.getSpecialHolidayCode(),
				domain.getAppTimeType().map(x -> x.isHourlyTimeType() ? 1 : 0).orElse(0),
				domain.getAppTimeType().map(x -> x.getAppTimeType().map(time -> time.value).orElse(0)).orElse(0));
		KrcdtInterimHdSpMng entity = this.getEntityManager().find(KrcdtInterimHdSpMng.class, key);

		if (entity == null) {
			entity = new KrcdtInterimHdSpMng();
			entity.createAtr = domain.getCreatorAtr().value;
			entity.mngAtr = domain.getMngAtr().value;
			entity.usedDays = domain.getUseDays().map(x -> x.v().equals(0d) ? null : x.v()).orElse(null);
			entity.usedTime = domain.getUseTimes().map(x -> x.v() == 0 ? null : x.v()).orElse(null);
			entity.remainMngId = domain.getRemainManaID();
			entity.pk = key;
			this.getEntityManager().persist(entity);
		} else {
			entity.createAtr = domain.getCreatorAtr().value;
			entity.mngAtr = domain.getMngAtr().value;
			entity.usedDays = domain.getUseDays().map(x -> x.v().equals(0d) ? null : x.v()).orElse(null);
			entity.usedTime = domain.getUseTimes().map(x -> x.v() == 0 ? null : x.v()).orElse(null);
			this.commandProxy().update(entity);
		}
		this.getEntityManager().flush();
	}

	@Override
	public void deleteSpecialHoliday(String specialId) {
		this.getEntityManager().createQuery(DELETE_BY_ID).setParameter("remainMngId", specialId).executeUpdate();
	}

	@Override
	public List<InterimSpecialHolidayMng> findById(String mngId) {
		return this.queryProxy().query(QUERY_BY_ID, KrcdtInterimHdSpMng.class)
				.setParameter("remainMngId", mngId).getList(c -> toDomain(c));
	}

	@Override
	public void deleteSpecialHolidayBySidAndYmd(String sId, GeneralDate ymd) {
		this.getEntityManager().createQuery(DELETE_BY_SID_YMD)
		.setParameter("sid", sId)
		.setParameter("ymd", ymd)
		.executeUpdate();
	}

}
