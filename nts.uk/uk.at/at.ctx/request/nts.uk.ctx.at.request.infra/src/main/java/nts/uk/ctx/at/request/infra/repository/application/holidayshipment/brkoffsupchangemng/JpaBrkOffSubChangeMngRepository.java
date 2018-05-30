package nts.uk.ctx.at.request.infra.repository.application.holidayshipment.brkoffsupchangemng;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.request.dom.application.holidayshipment.brkoffsupchangemng.BrkOffSupChangeMng;
import nts.uk.ctx.at.request.dom.application.holidayshipment.brkoffsupchangemng.BrkOffSupChangeMngRepository;
import nts.uk.ctx.at.request.infra.entity.application.holidayshipment.brkoffsupchangemng.KrqdtBrkOffSupChangeMng;
import nts.uk.ctx.at.request.infra.entity.application.holidayshipment.brkoffsupchangemng.KrqdtBrkOffSupChangeMngPK;

@Stateless
public class JpaBrkOffSubChangeMngRepository extends JpaRepository implements BrkOffSupChangeMngRepository {
	 private static final String FINDER_LEAVE_APPID = "Select e from KrqdtBrkOffSupChangeMng e where e.pk.leaveAppID = :holidayWorkAppID";
	
	@Override
	public void insert(BrkOffSupChangeMng brkOffSupChangeMng) {
		KrqdtBrkOffSupChangeMng entity = new KrqdtBrkOffSupChangeMng();
		entity.setPk(new KrqdtBrkOffSupChangeMngPK(brkOffSupChangeMng.getAbsenceLeaveAppID(),brkOffSupChangeMng.getRecAppID()));
		this.commandProxy().insert(entity);
	}

	@Override
	public Optional<BrkOffSupChangeMng> findHolidayAppID(String holidayWorkAppID) {
		Optional<KrqdtBrkOffSupChangeMng> opt = this.queryProxy().query(FINDER_LEAVE_APPID, KrqdtBrkOffSupChangeMng.class).setParameter("holidayWorkAppID", holidayWorkAppID).getSingle();
		if(!opt.isPresent()){
			return Optional.empty();
		}
		KrqdtBrkOffSupChangeMng krqdtBrkOffSupChangeMng = opt.get();
		BrkOffSupChangeMng brkOffSupChangeMng = krqdtBrkOffSupChangeMng.toDomain();
		return Optional.of(brkOffSupChangeMng);
	}

	@Override
	public void remove(String leaveAppID, String absenceLeaveAppID) {
		Optional<KrqdtBrkOffSupChangeMng> opt = this.queryProxy().find(new KrqdtBrkOffSupChangeMngPK(absenceLeaveAppID,leaveAppID), KrqdtBrkOffSupChangeMng.class);
		if(!opt.isPresent()){
			throw new RuntimeException("Not find KrqdtBrkOffSupChangeMng");
		}
		this.commandProxy().remove(KrqdtBrkOffSupChangeMng.class, new KrqdtBrkOffSupChangeMngPK(absenceLeaveAppID,leaveAppID));
	}
}
