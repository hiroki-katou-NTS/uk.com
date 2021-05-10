package nts.uk.ctx.at.shared.infra.repository.holidaymanagement.interim;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.holidaymanagement.interim.InterimHolidayMng;
import nts.uk.ctx.at.shared.dom.holidaymanagement.interim.InterimHolidayMngRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainType;
import nts.uk.ctx.at.shared.infra.entity.holidaymanagement.interim.KshdtInterimHolidayMng;
import nts.uk.ctx.at.shared.infra.entity.holidaymanagement.interim.KshdtInterimHolidayMngPK;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author sonnlb
 *
 */
@Stateless
public class JpaInterimHolidayMngRepository extends JpaRepository implements InterimHolidayMngRepository {

	private static final String SELECT_BY_MNG_ID = "SELECT c FROM KshdtInterimHolidayMng c WHERE c.remainMngID = :remainMngID ";

	private static final String DELETE_BY_MNG_ID = "DELETE FROM KshdtInterimHolidayMng c WHERE c.remainMngID = :remainMngID ";
	
	private static final String DELETE_BY_SID_YMD = "DELETE FROM KshdtInterimHolidayMng c WHERE c.pk.sid = :sid AND c.pk.ymd = :ymd ";

	@Override
	public List<InterimHolidayMng> getById(String mngId) {

		return this.queryProxy().query(SELECT_BY_MNG_ID, KshdtInterimHolidayMng.class)
				.setParameter("remainMngID", mngId).getList().stream().map(x -> toDomain(x))
				.collect(Collectors.toList());
	}

	private InterimHolidayMng toDomain(KshdtInterimHolidayMng entity) {

		return new InterimHolidayMng(entity.remainMngID, entity.pk.sid, entity.pk.ymd,
				EnumAdaptor.valueOf(entity.creatorAtr, CreateAtr.class), RemainType.PUBLICHOLIDAY, entity.useDays);
	}

	@Override
	public void deleteById(String mngId) {
		this.getEntityManager().createQuery(DELETE_BY_MNG_ID)
		.setParameter("remainMngID", mngId).executeUpdate();
		this.getEntityManager().flush();
	}

	@Override
	public void add(InterimHolidayMng domain) {

		this.commandProxy().insert(fromDomain(domain));

	}

	private KshdtInterimHolidayMng fromDomain(InterimHolidayMng domain) {
		KshdtInterimHolidayMngPK pk = new KshdtInterimHolidayMngPK(AppContexts.user().companyId(), domain.getSID(),
				domain.getYmd());
		return new KshdtInterimHolidayMng(pk, domain.getRemainManaID(), domain.getCreatorAtr().value, domain.getDays());
	}

	@Override
	public void deleteBySidAndYmd(String sid, GeneralDate ymd) {
		this.getEntityManager().createQuery(DELETE_BY_SID_YMD)
		.setParameter("sid", sid)
		.setParameter("ymd", ymd)
		.executeUpdate();
		this.getEntityManager().flush();
	}

	@Override
	public void persistAndUpdate(InterimHolidayMng domain) {

		KshdtInterimHolidayMngPK pk = new KshdtInterimHolidayMngPK(AppContexts.user().companyId(), domain.getSID(),
				domain.getYmd());

		this.queryProxy().find(pk, KshdtInterimHolidayMng.class).ifPresent(entity -> {
			entity.remainMngID = domain.getRemainManaID();
			entity.creatorAtr = domain.getCreatorAtr().value;
			entity.useDays = domain.getDays();
			this.commandProxy().update(entity);
			this.getEntityManager().flush();
			return;
		});

		KshdtInterimHolidayMng entity = new KshdtInterimHolidayMng();
		entity.pk = pk;
		entity.creatorAtr = domain.getCreatorAtr().value;
		entity.useDays = domain.getDays();
		entity.remainMngID = domain.getRemainManaID();
		
		this.getEntityManager().persist(entity);
		this.getEntityManager().flush();
		
	}

}
