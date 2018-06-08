package nts.uk.ctx.at.shared.infra.repository.remainingnumber.absencerecruitment.interim;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimAbsMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimRecAbasMngRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimRecAbsMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimRecMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.DataManagementAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.OccurrenceDay;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RequiredDay;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.SelectedAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.StatutoryAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UnOffsetDay;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UnUsedDay;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UseDay;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.absencerecruitment.interim.KrcmtInterimAbsMng;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.absencerecruitment.interim.KrcmtInterimRecAbs;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.absencerecruitment.interim.KrcmtInterimRecMng;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
@Stateless
public class JpaInterimRecAbasMngRepository extends JpaRepository implements InterimRecAbasMngRepository{

	private String QUERY_REC_BY_ID = "SELECT c FROM KrcmtInterimRecAbs c"
			+ " WHERE c.recAbsPk.recruitmentMngId = :remainID"
			+ " AND c.recruitmentMngAtr = :mngAtr";
	private String QUERY_ABS_BY_ID = "SELECT c FROM KrcmtInterimRecAbs c"
			+ " WHERE c.recAbsPk.absenceMngID = :remainID"
			+ " AND c.recruitmentMngAtr = :mngAtr";
	private String QUERY_REC_BY_DATEPERIOD = "SELECT c FROM KrcmtInterimRecMng c"
			+ " WHERE c.recruitmentMngId in :mngIds"
			+ " AND c.unUsedDays > :unUsedDays"
			+ " AND c.expirationDate >= :startDate"
			+ " AND c.expirationDate <= :endDate";
	
	private String QUERY_ABS_BY_SID_MNGID = "SELECT c FROM KrcmtInterimRecAbs c"
			+ " WHERE c.recAbsPk.absenceMngID = :absenceMngID"
			+ " AND c.absenceMngAtr = :absenceMngAtr"
			+ " AND c.recruitmentMngAtr = :recruitmentMngAtr";
	private String DELETE_ABS_BY_MNGID = "SELECT c FROM KrcmtInterimRecAbs c "
			+ " WHERE c.recAbsPk.absenceMngID := mngId";
	private String DELETE_REC_BY_MNGID = "SELECT c FROM KrcmtInterimRecAbs c "
			+ " WHERE c.recAbsPk.recruitmentMngId := mngId";
	private String DELETE_BY_ID_ATR = "SELECT c FROM KrcmtInterimRecAbs c"
			+ " WHERE c.recAbsPk.absenceMngID = :absId"
			+ " AND c.recAbsPk.recruitmentMngId = :recId"
			+ " AND c.absenceMngAtr = absAtr"
			+ " AND c.recruitmentMngAtr = recAtr";
			
	@Override
	public Optional<InterimRecMng> getReruitmentById(String recId) {
		return this.queryProxy().find(recId, KrcmtInterimRecMng.class)
				.map(x -> toDomainRecMng(x));
	}

	private InterimRecMng toDomainRecMng(KrcmtInterimRecMng x) {
		return new InterimRecMng(x.recruitmentMngId, 
				x.expirationDate, 
				new OccurrenceDay(x.occurrenceDays),
				EnumAdaptor.valueOf(x.statutoryAtr, StatutoryAtr.class),
				new UnUsedDay(x.unUsedDays));
	}

	@Override
	public Optional<InterimAbsMng> getAbsById(String absId) {
		return this.queryProxy().find(absId, KrcmtInterimAbsMng.class)
				.map(x -> toDomainAbsMng(x));
	}

	private InterimAbsMng toDomainAbsMng(KrcmtInterimAbsMng x) {		
		return new InterimAbsMng(x.absenceMngId, new RequiredDay(x.requiredDays), new UnOffsetDay(x.unOffsetDay));
	}

	@Override
	public List<InterimRecAbsMng> getRecOrAbsMng(String interimId, boolean isRec, DataManagementAtr mngAtr) {
		return this.queryProxy().query(isRec ? QUERY_REC_BY_ID : QUERY_ABS_BY_ID, KrcmtInterimRecAbs.class)
				.setParameter("remainID", interimId)
				.setParameter("mngAtr", mngAtr.values)
				.getList(x -> toDomainRecAbs(x));
	}

	private InterimRecAbsMng toDomainRecAbs(KrcmtInterimRecAbs x) {
		return new InterimRecAbsMng(x.recAbsPk.absenceMngID, 
				EnumAdaptor.valueOf(x.absenceMngAtr, DataManagementAtr.class),
				x.recAbsPk.recruitmentMngId,
				EnumAdaptor.valueOf(x.recruitmentMngAtr,DataManagementAtr.class),
				new UseDay(x.useDays),
				EnumAdaptor.valueOf(x.selectedAtr, SelectedAtr.class));
	}

	@Override
	public List<InterimRecMng> getRecByIdPeriod(List<String> recId, Double unUseDays, DatePeriod dateData) {
		if(recId.isEmpty()) {
			return Collections.emptyList();
		}
		return this.queryProxy().query(QUERY_REC_BY_DATEPERIOD, KrcmtInterimRecMng.class)
				.setParameter("mngIds", recId)
				.setParameter("unUsedDays", unUseDays)
				.setParameter("startDate", dateData.start())
				.setParameter("endDate", dateData.end())
				.getList(c -> toDomainRecMng(c));
	}

	@Override
	public List<InterimRecAbsMng> getBySidMng(DataManagementAtr recAtr, DataManagementAtr absAtr,
			String absId) {
		return this.queryProxy().query(QUERY_ABS_BY_SID_MNGID, KrcmtInterimRecAbs.class)
				.setParameter("absenceMngID", absId)
				.setParameter("absenceMngAtr", absAtr.values)
				.setParameter("recruitmentMngAtr", recAtr.values)
				.getList(x -> toDomainRecAbs(x));
	}

	@Override
	public void createInterimRecMng(InterimRecMng domain) {
		this.commandProxy().insert(toEntityInterimRecMng(domain));
	}

	private KrcmtInterimRecMng toEntityInterimRecMng(InterimRecMng domain) {
		KrcmtInterimRecMng entity = new KrcmtInterimRecMng();
		entity.recruitmentMngId = domain.getRecruitmentMngId();
		entity.expirationDate = domain.getExpirationDate();
		entity.occurrenceDays = domain.getOccurrenceDays().v();
		entity.statutoryAtr = domain.getStatutoryAtr().value;
		entity.unUsedDays = domain.getUnUsedDays().v();
		return entity;
	}

	@Override
	public void createInterimAbsMng(InterimAbsMng domain) {
		this.commandProxy().insert(toEntityInterimAbsMng(domain));
	}

	private KrcmtInterimAbsMng toEntityInterimAbsMng(InterimAbsMng domain) {
		KrcmtInterimAbsMng entity = new KrcmtInterimAbsMng();
		entity.absenceMngId = domain.getAbsenceMngId();
		entity.requiredDays = domain.getRequeiredDays().v();
		entity.unOffsetDay = domain.getUnOffsetDays().v();
		return entity;
	}

	@Override
	public void createInterimRecAbsMng(InterimRecAbsMng domain) {
		this.commandProxy().insert(toEntityInterimRecAbsMng(domain));
	}

	private KrcmtInterimRecAbs toEntityInterimRecAbsMng(InterimRecAbsMng domain) {
		KrcmtInterimRecAbs entity = new KrcmtInterimRecAbs();
		entity.recAbsPk.absenceMngID = domain.getAbsenceMngId();
		entity.recAbsPk.recruitmentMngId = domain.getRecruitmentMngId();
		entity.absenceMngAtr = domain.getAbsenceMngAtr().values;
		entity.recruitmentMngAtr = domain.getRecruitmentMngAtr().values;
		entity.useDays = domain.getUseDays().v();
		entity.selectedAtr = domain.getSelectedAtr().value;
		return entity;
	}

	@Override
	public void deleteInterimRecMng(String recruitmentMngId) {
		Optional<KrcmtInterimRecMng> entityOpt = this.queryProxy().find(recruitmentMngId, KrcmtInterimRecMng.class);
		entityOpt.ifPresent(x -> {
			this.commandProxy().remove(KrcmtInterimRecMng.class, recruitmentMngId);
		});		
	}

	@Override
	public void deleteInterimAbsMng(String absenceMngId) {
		Optional<KrcmtInterimAbsMng> entityOpt = this.queryProxy().find(absenceMngId, KrcmtInterimAbsMng.class);
		entityOpt.ifPresent(x -> {
			this.commandProxy().remove(KrcmtInterimAbsMng.class, absenceMngId);
		});
	}

	@Override
	public void deleteInterimRecAbsMng(String mndId, boolean isRec) {
		List<KrcmtInterimRecAbs> lstData = this.queryProxy().query(isRec ? DELETE_REC_BY_MNGID : DELETE_ABS_BY_MNGID, KrcmtInterimRecAbs.class)
				.setParameter("mngId", mndId)
				.getList();
		lstData.stream().forEach(x -> {
			this.commandProxy().remove(x);
		});		
	}

	@Override
	public void deleteRecAbsMngByIdAndAtr(String recId, String absId, DataManagementAtr recAtr,
			DataManagementAtr absAtr) {
		List<KrcmtInterimRecAbs> lstEntity = this.queryProxy().query(DELETE_BY_ID_ATR, KrcmtInterimRecAbs.class)
				.setParameter("absId", absId)
				.setParameter("recId", recId)
				.setParameter("absAtr", absAtr)
				.setParameter("recAtr", recAtr)
				.getList();
		lstEntity.stream().forEach(x -> {
			this.commandProxy().remove(x);
		});
	}

	@Override
	public void updateInterimRecMng(InterimRecMng domain) {
		this.commandProxy().update(toEntityInterimRecMng(domain));
	}

	@Override
	public void updateInterimAbsMng(InterimAbsMng domain) {
		this.commandProxy().update(toEntityInterimAbsMng(domain));
	}

	@Override
	public void updateInterimRecAbsMng(InterimRecAbsMng domain) {
		this.commandProxy().update(toEntityInterimRecAbsMng(domain));
	}

	@Override
	public void deleteRecAbsMngByIDAtr(String mngId, DataManagementAtr mngAtr, boolean isRec) {
		List<KrcmtInterimRecAbs> lstEntity = this.queryProxy().query(isRec ? QUERY_REC_BY_ID : QUERY_ABS_BY_ID, KrcmtInterimRecAbs.class)
			.setParameter("remainID", mngId)
			.setParameter("mngAtr", mngAtr.values)			
			.getList();
		lstEntity.stream().forEach(x -> {
			this.commandProxy().remove(x);
		});
		
	}
		
}
