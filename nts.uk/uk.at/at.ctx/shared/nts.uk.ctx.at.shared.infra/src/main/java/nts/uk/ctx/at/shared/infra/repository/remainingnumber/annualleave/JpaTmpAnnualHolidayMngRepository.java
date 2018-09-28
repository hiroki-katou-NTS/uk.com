package nts.uk.ctx.at.shared.infra.repository.remainingnumber.annualleave;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim.TmpAnnualHolidayMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim.TmpAnnualHolidayMngRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UseDay;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.annlea.KrcmtInterimAnnualMng;

@Stateless
public class JpaTmpAnnualHolidayMngRepository extends JpaRepository implements TmpAnnualHolidayMngRepository{

	@Override
	public Optional<TmpAnnualHolidayMng> getById(String mngId) {
		Optional<TmpAnnualHolidayMng> optTmpAnnualHolidayMng = this.queryProxy().find(mngId, KrcmtInterimAnnualMng.class)
				.map(x -> toDomain(x));
		return optTmpAnnualHolidayMng;
	}

	private TmpAnnualHolidayMng toDomain(KrcmtInterimAnnualMng x) {
		return new TmpAnnualHolidayMng(x.annualMngId, x.workTypeCode, new UseDay(x.useDays));
	}

	@Override
	public void deleteById(String mngId) {
		Optional<KrcmtInterimAnnualMng> optTmpAnnualHolidayMng = this.queryProxy().find(mngId, KrcmtInterimAnnualMng.class);
		optTmpAnnualHolidayMng.ifPresent(x -> {
			this.commandProxy().remove(x);
		});
		
	}

	@Override
	public void persistAndUpdate(TmpAnnualHolidayMng dataMng) {
		Optional<KrcmtInterimAnnualMng> optTmpAnnualHolidayMng = this.queryProxy().find(dataMng.getAnnualId(), KrcmtInterimAnnualMng.class);
		if(optTmpAnnualHolidayMng.isPresent()) {
			KrcmtInterimAnnualMng entity = optTmpAnnualHolidayMng.get();
			entity.useDays = dataMng.getUseDays().v();
			entity.workTypeCode = dataMng.getWorkTypeCode();
			this.commandProxy().update(entity);
		} else {
			KrcmtInterimAnnualMng entity = new KrcmtInterimAnnualMng();
			entity.annualMngId = dataMng.getAnnualId();
			entity.useDays = dataMng.getUseDays().v();
			entity.workTypeCode = dataMng.getWorkTypeCode();
			this.getEntityManager().persist(entity);
		}
		this.getEntityManager().flush();
	}

}
