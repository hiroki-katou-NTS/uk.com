package nts.uk.ctx.office.infra.repository.status;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.office.dom.status.ActivityStatus;
import nts.uk.ctx.office.dom.status.ActivityStatusRepository;
import nts.uk.ctx.office.infra.entity.status.OfidtPresentStatus;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class ActivityStatusRepositoryImpl extends JpaRepository implements ActivityStatusRepository {

	private static final String SELECT_BY_DATE_AND_IDS = "SELECT a From OfidtPresentStatus a WHERE a.pk.sid IN :listId and a.date =:date";
	
	private static final String SELECT_BY_DATE_AND_ID = "SELECT a From OfidtPresentStatus a WHERE a.pk.sid = :sId and a.date = :date";

	@Override
	public void insert(ActivityStatus domain) {
		OfidtPresentStatus entity = new OfidtPresentStatus();
		domain.setMemento(entity);
		entity.setVersion(0);
		entity.setContractCd(AppContexts.user().contractCode());
		this.commandProxy().insert(entity);
	}

	@Override
	public void update(ActivityStatus domain) {
		OfidtPresentStatus entity = new OfidtPresentStatus();
        domain.setMemento(entity);
	    Optional<OfidtPresentStatus> oldEntity = this.queryProxy().find(entity.getPk(), OfidtPresentStatus.class);
	    if(oldEntity.isPresent()) {
	    	OfidtPresentStatus updateEntity = oldEntity.get();
	    	updateEntity.setActivity(entity.getActivity());
	    	updateEntity.setDate(entity.getDate());
	    	this.commandProxy().update(updateEntity);
	    } else {
	    	entity.setVersion(0);
	    	entity.setContractCd(AppContexts.user().contractCode());
			this.commandProxy().insert(entity);
	    }
	}

	@Override
	public List<ActivityStatus> getByListSidAndDate(List<String> sids, GeneralDate date) {
		return this.queryProxy().query(SELECT_BY_DATE_AND_IDS, OfidtPresentStatus.class)
				.setParameter("listId", sids)
				.setParameter("date", date)
				.getList(ActivityStatus::createFromMemento);
	}

	@Override
	public Optional<ActivityStatus> getBySidAndDate(String sid, GeneralDate date) {
		return this.queryProxy().query(SELECT_BY_DATE_AND_ID, OfidtPresentStatus.class)
				.setParameter("sId", sid)
				.setParameter("date", date)
				.getSingle(ActivityStatus::createFromMemento);
	}
}
