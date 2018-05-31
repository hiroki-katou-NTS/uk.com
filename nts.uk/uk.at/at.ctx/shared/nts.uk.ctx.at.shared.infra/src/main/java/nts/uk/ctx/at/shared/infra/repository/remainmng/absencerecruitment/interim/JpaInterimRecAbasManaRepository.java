package nts.uk.ctx.at.shared.infra.repository.remainmng.absencerecruitment.interim;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.remainmng.absencerecruitment.interim.InterimAbsMng;
import nts.uk.ctx.at.shared.dom.remainmng.absencerecruitment.interim.InterimRecAbasMngRepository;
import nts.uk.ctx.at.shared.dom.remainmng.absencerecruitment.interim.InterimRecMng;
import nts.uk.ctx.at.shared.dom.remainmng.interimremain.primitive.OccurrenceDay;
import nts.uk.ctx.at.shared.dom.remainmng.interimremain.primitive.RequiredDay;
import nts.uk.ctx.at.shared.dom.remainmng.interimremain.primitive.StatutoryAtr;
import nts.uk.ctx.at.shared.dom.remainmng.interimremain.primitive.UnOffsetDay;
import nts.uk.ctx.at.shared.dom.remainmng.interimremain.primitive.UnUsedDay;
import nts.uk.ctx.at.shared.infra.entity.remainmng.absencerecruitment.interim.KrcmtInterimAbsMng;
import nts.uk.ctx.at.shared.infra.entity.remainmng.absencerecruitment.interim.KrcmtInterimRecMng;
@Stateless
public class JpaInterimRecAbasManaRepository extends JpaRepository implements InterimRecAbasMngRepository{

	@Override
	public Optional<InterimRecMng> getReruitmentById(String recId) {
		return this.queryProxy().find(recId, KrcmtInterimRecMng.class)
				.map(x -> toDomainRecMana(x));
	}

	private InterimRecMng toDomainRecMana(KrcmtInterimRecMng x) {
		return new InterimRecMng(x.recruitmentMngId, 
				x.expirationDays, 
				new OccurrenceDay(x.occurrenceDays),
				EnumAdaptor.valueOf(x.statutoryAtr, StatutoryAtr.class),
				new UnUsedDay(x.unUsedDays));
	}

	@Override
	public Optional<InterimAbsMng> getAbsById(String absId) {
		return this.queryProxy().find(absId, KrcmtInterimAbsMng.class)
				.map(x -> toDomainAbsMana(x));
	}

	private InterimAbsMng toDomainAbsMana(KrcmtInterimAbsMng x) {		
		return new InterimAbsMng(x.absenceMngId, new RequiredDay(x.requiredDays), new UnOffsetDay(x.unOffsetDay));
	}

}
