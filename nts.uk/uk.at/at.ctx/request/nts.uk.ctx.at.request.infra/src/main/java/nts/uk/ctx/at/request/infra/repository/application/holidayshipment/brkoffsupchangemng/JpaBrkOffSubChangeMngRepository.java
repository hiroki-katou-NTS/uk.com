package nts.uk.ctx.at.request.infra.repository.application.holidayshipment.brkoffsupchangemng;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.request.dom.application.holidayshipment.brkoffsupchangemng.BrkOffSupChangeMng;
import nts.uk.ctx.at.request.dom.application.holidayshipment.brkoffsupchangemng.BrkOffSupChangeMngRepository;
import nts.uk.ctx.at.request.infra.entity.application.holidayshipment.brkoffsupchangemng.KrqdtBrkOffSupChangeMng;
import nts.uk.ctx.at.request.infra.entity.application.holidayshipment.brkoffsupchangemng.KrqdtBrkOffSupChangeMngPK;

@Stateless
public class JpaBrkOffSubChangeMngRepository extends JpaRepository implements BrkOffSupChangeMngRepository {

	@Override
	public void insert(BrkOffSupChangeMng brkOffSupChangeMng) {
		KrqdtBrkOffSupChangeMng entity = new KrqdtBrkOffSupChangeMng();
		entity.setPk(new KrqdtBrkOffSupChangeMngPK(brkOffSupChangeMng.getRecAppID(), brkOffSupChangeMng.getAbsenceLeaveAppID()));
		this.commandProxy().insert(entity);
	}
}
