package nts.uk.ctx.at.shared.infra.repository.remainmng.absencerecruitment.interim;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.remainmng.absencerecruitment.interim.InterimAbsMng;
import nts.uk.ctx.at.shared.dom.remainmng.absencerecruitment.interim.InterimRecAbasMngRepository;
import nts.uk.ctx.at.shared.dom.remainmng.absencerecruitment.interim.InterimRecAbsMng;
import nts.uk.ctx.at.shared.dom.remainmng.absencerecruitment.interim.InterimRecMng;
import nts.uk.ctx.at.shared.dom.remainmng.interimremain.primitive.DataManagementAtr;
import nts.uk.ctx.at.shared.dom.remainmng.interimremain.primitive.OccurrenceDay;
import nts.uk.ctx.at.shared.dom.remainmng.interimremain.primitive.RequiredDay;
import nts.uk.ctx.at.shared.dom.remainmng.interimremain.primitive.SelectedAtr;
import nts.uk.ctx.at.shared.dom.remainmng.interimremain.primitive.StatutoryAtr;
import nts.uk.ctx.at.shared.dom.remainmng.interimremain.primitive.UnOffsetDay;
import nts.uk.ctx.at.shared.dom.remainmng.interimremain.primitive.UnUsedDay;
import nts.uk.ctx.at.shared.dom.remainmng.interimremain.primitive.UseDay;
import nts.uk.ctx.at.shared.infra.entity.remainmng.absencerecruitment.interim.KrcmtInterimAbsMng;
import nts.uk.ctx.at.shared.infra.entity.remainmng.absencerecruitment.interim.KrcmtInterimRecAbs;
import nts.uk.ctx.at.shared.infra.entity.remainmng.absencerecruitment.interim.KrcmtInterimRecMng;
@Stateless
public class JpaInterimRecAbasMngRepository extends JpaRepository implements InterimRecAbasMngRepository{

	private String QUERY_REC_BY_ID = "SELECT c FROM KrcmtInterimRecAbs c"
			+ " WHERE c.recAbsPk.recruitmentMngId = :remainID"
			+ " AND c.recruitmentMngAtr = 0";
	private String QUERY_ABS_BY_ID = "SELECT c FROM KrcmtInterimRecAbs c"
			+ " WHERE c.recAbsPk.absenceMngID = :remainID"
			+ " AND c.recruitmentMngAtr = 0";
	
	@Override
	public Optional<InterimRecMng> getReruitmentById(String recId) {
		return this.queryProxy().find(recId, KrcmtInterimRecMng.class)
				.map(x -> toDomainRecMng(x));
	}

	private InterimRecMng toDomainRecMng(KrcmtInterimRecMng x) {
		return new InterimRecMng(x.recruitmentMngId, 
				x.expirationDays, 
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
	public Optional<InterimRecAbsMng> getRecOrAbsMng(String interimId, boolean isRec) {
		return this.queryProxy().query(isRec ? QUERY_REC_BY_ID : QUERY_ABS_BY_ID, KrcmtInterimRecAbs.class)
				.setParameter("remainID", interimId)
				.getSingle(x -> toDomainRecAbs(x));
	}

	private InterimRecAbsMng toDomainRecAbs(KrcmtInterimRecAbs x) {
		return new InterimRecAbsMng(x.recAbsPk.absenceMngID, 
				EnumAdaptor.valueOf(x.absenceMngAtr, DataManagementAtr.class),
				x.recAbsPk.recruitmentMngId,
				EnumAdaptor.valueOf(x.recruitmentMngAtr,DataManagementAtr.class),
				new UseDay(x.useDays),
				EnumAdaptor.valueOf(x.selectedAtr, SelectedAtr.class));
	}

}
