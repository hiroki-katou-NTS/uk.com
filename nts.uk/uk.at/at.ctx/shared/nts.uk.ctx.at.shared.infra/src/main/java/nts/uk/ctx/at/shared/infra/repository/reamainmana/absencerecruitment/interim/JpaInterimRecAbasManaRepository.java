package nts.uk.ctx.at.shared.infra.repository.reamainmana.absencerecruitment.interim;

import java.util.Optional;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.reamainmana.absencerecruitment.interim.InterimAbsMana;
import nts.uk.ctx.at.shared.dom.reamainmana.absencerecruitment.interim.InterimRecAbasManaRepository;
import nts.uk.ctx.at.shared.dom.reamainmana.absencerecruitment.interim.InterimRecMana;
import nts.uk.ctx.at.shared.dom.reamainmana.interimremain.primitive.OccurrenceDay;
import nts.uk.ctx.at.shared.dom.reamainmana.interimremain.primitive.RequiredDay;
import nts.uk.ctx.at.shared.dom.reamainmana.interimremain.primitive.StatutoryAtr;
import nts.uk.ctx.at.shared.dom.reamainmana.interimremain.primitive.UnOffsetDay;
import nts.uk.ctx.at.shared.dom.reamainmana.interimremain.primitive.UnUsedDay;
import nts.uk.ctx.at.shared.infra.entity.reamainmana.absencerecruitment.interim.KrcmtInterimAbsMana;
import nts.uk.ctx.at.shared.infra.entity.reamainmana.absencerecruitment.interim.KrcmtInterimRecMana;

public class JpaInterimRecAbasManaRepository extends JpaRepository implements InterimRecAbasManaRepository{

	@Override
	public Optional<InterimRecMana> getReruitmentById(String recId) {
		return this.queryProxy().find(recId, KrcmtInterimRecMana.class)
				.map(x -> toDomainRecMana(x));
	}

	private InterimRecMana toDomainRecMana(KrcmtInterimRecMana x) {
		return new InterimRecMana(x.recruitmentManaId, 
				x.expirationDays, 
				new OccurrenceDay(x.occurrenceDays),
				EnumAdaptor.valueOf(x.statutoryAtr, StatutoryAtr.class),
				new UnUsedDay(x.unUsedDays));
	}

	@Override
	public Optional<InterimAbsMana> getAbsById(String absId) {
		return this.queryProxy().find(absId, KrcmtInterimAbsMana.class)
				.map(x -> toDomainAbsMana(x));
	}

	private InterimAbsMana toDomainAbsMana(KrcmtInterimAbsMana x) {		
		return new InterimAbsMana(x.absenceManaId, new RequiredDay(x.requiredDays), new UnOffsetDay(x.unOffsetDay));
	}

}
