package nts.uk.ctx.at.shared.infra.repository.remainingnumber.reserveleave;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UseDay;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.interim.TmpResereLeaveMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.interim.TmpResereLeaveMngRepository;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.reserveleave.KrcmtInterimReserveMng;
@Stateless
public class JpaTmpResereLeaveMngRepository extends JpaRepository implements TmpResereLeaveMngRepository{

	@Override
	public Optional<TmpResereLeaveMng> getById(String resereMngId) {
		Optional<TmpResereLeaveMng> optKrcmtInterimReserveMng = this.queryProxy().find(resereMngId, KrcmtInterimReserveMng.class)
				.map(x -> toDomain(x));
		
		return optKrcmtInterimReserveMng;
	}

	private TmpResereLeaveMng toDomain(KrcmtInterimReserveMng x) {
		return new TmpResereLeaveMng(x.reserveMngId, new UseDay(x.useDays));
	}

	@Override
	public void deleteById(String resereMngId) {
		Optional<KrcmtInterimReserveMng> optKrcmtInterimReserveMng = this.queryProxy().find(resereMngId, KrcmtInterimReserveMng.class);
		optKrcmtInterimReserveMng.ifPresent(x -> {
			this.commandProxy().remove(x);
		});
		
	}

	@Override
	public void persistAndUpdate(TmpResereLeaveMng dataMng) {
		Optional<KrcmtInterimReserveMng> optKrcmtInterimReserveMng = this.queryProxy().find(dataMng.getResereId(), KrcmtInterimReserveMng.class);
		if(optKrcmtInterimReserveMng.isPresent()) {
			KrcmtInterimReserveMng entity = optKrcmtInterimReserveMng.get();
			entity.useDays = dataMng.getUseDays().v();
			this.commandProxy().update(entity);
		} else {
			KrcmtInterimReserveMng entity = new KrcmtInterimReserveMng();
			entity.reserveMngId = dataMng.getResereId();
			entity.useDays = dataMng.getUseDays().v();
			this.getEntityManager().persist(entity);
		}
		this.getEntityManager().flush();
	}

}
