package nts.uk.ctx.at.shared.infra.repository.monthlyattdcal.ouen.aggframe;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.ouen.aggframe.AggregateFrameTargetWork;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.ouen.aggframe.AggregateFrameTargetWorkLocation;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.ouen.aggframe.AggregateFrameTargetWorkplace;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.ouen.aggframe.OuenAggregateFrameOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.ouen.aggframe.OuenAggregateFrameSetOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.ouen.aggframe.OuenAggregateFrameSetOfMonthlyRepo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.ouen.aggframe.OuenAggregateFrameSetOfMonthly.AggregateFrameUnit;
import nts.uk.ctx.at.shared.infra.entity.monthlyattdcal.ouen.aggframe.KrcmtMonOuenFramePK;
import nts.uk.ctx.at.shared.infra.entity.monthlyattdcal.ouen.aggframe.KrcmtMonOuenFrameSet;
import nts.uk.ctx.at.shared.infra.entity.monthlyattdcal.ouen.aggframe.KrcmtMonOuenFrameWkp;
import nts.uk.ctx.at.shared.infra.entity.monthlyattdcal.ouen.aggframe.KrcmtMonOuenFrameWlc;
import nts.uk.ctx.at.shared.infra.entity.monthlyattdcal.ouen.aggframe.KrcmtMonOuenFrameWork;

@Stateless
public class OuenAggregateFrameSetOfMonthlyRepoImpl extends JpaRepository 
						implements OuenAggregateFrameSetOfMonthlyRepo {

	@Override
	public Optional<OuenAggregateFrameSetOfMonthly> find(String companyId) {
		
		return queryProxy().find(companyId, KrcmtMonOuenFrameSet.class).map(entity -> {
			List<OuenAggregateFrameOfMonthly> frames;
			
			if (entity.frameUnit == AggregateFrameUnit.WORKPLACE.value) {
				frames = getEntityWkp(companyId, e -> e.domain());
			} else if (entity.frameUnit == AggregateFrameUnit.WORKLOCATION.value) {
				frames = getEntityWlc(companyId, e -> e.domain());
			} else {
				frames = getEntityWork(companyId, e -> e.domain());
			}
			
			return OuenAggregateFrameSetOfMonthly.create(companyId, entity.frameUnit, frames);
		});
	}

	@Override
	public void update(OuenAggregateFrameSetOfMonthly domain) {
		
		internalPersist(domain, e -> commandProxy().update(e));
	}

	@Override
	public void insert(OuenAggregateFrameSetOfMonthly domain) {
		
		internalPersist(domain, e -> commandProxy().insert(e));
	}
	
	private void internalPersist(OuenAggregateFrameSetOfMonthly domain, Consumer<Object> persist) {
		
		KrcmtMonOuenFrameSet entity = new KrcmtMonOuenFrameSet(domain.getCompanyId(), domain.getUnit().value);
		persist.accept(entity);
		
		if (domain.getUnit() == AggregateFrameUnit.WORK) {
			internalUpdateWork(domain.getCompanyId(), domain.getFrames(), persist);
		} else if (domain.getUnit() == AggregateFrameUnit.WORKPLACE) {
			internalUpdateWkp(domain.getCompanyId(), domain.getFrames(), persist);
		} else {
			internalUpdateWlc(domain.getCompanyId(), domain.getFrames(), persist);
		}
	}
	
	private void internalUpdateWork(String companyId, 
			List<OuenAggregateFrameOfMonthly> frames, Consumer<Object> persist) {
		
		commandProxy().removeAll(getEntityWlc(companyId, e -> e));
		
		commandProxy().removeAll(getEntityWkp(companyId, e -> e));
		
		frames.stream().map(f -> {
			AggregateFrameTargetWork work = (AggregateFrameTargetWork) f.getTarget();
			
			return new KrcmtMonOuenFrameWork(new KrcmtMonOuenFramePK(companyId, f.getFrameNo()), 
					f.getName().v(), work.getWorkGroupCD().v(), 
					work.getWorkTarget().getWorkCD1().v(), 
					work.getWorkTarget().getWorkCD2().map(w -> w.v()).orElse(null), 
					work.getWorkTarget().getWorkCD3().map(w -> w.v()).orElse(null), 
					work.getWorkTarget().getWorkCD4().map(w -> w.v()).orElse(null),
					work.getWorkTarget().getWorkCD5().map(w -> w.v()).orElse(null));
		}).forEach(e -> {
			persist.accept(e);
		});
	}
	
	private void internalUpdateWkp(String companyId, 
			List<OuenAggregateFrameOfMonthly> frames, Consumer<Object> persist) {
		
		commandProxy().removeAll(getEntityWlc(companyId, e -> e));
		
		commandProxy().removeAll(getEntityWork(companyId, e -> e));
		
		frames.stream().map(f -> {
			AggregateFrameTargetWorkplace work = (AggregateFrameTargetWorkplace) f.getTarget();
			
			return new KrcmtMonOuenFrameWkp(new KrcmtMonOuenFramePK(companyId, f.getFrameNo()), 
					f.getName().v(), work.getWorkplaceID());
		}).forEach(e -> {
			persist.accept(e);
		});
	}
	
	private void internalUpdateWlc(String companyId, 
			List<OuenAggregateFrameOfMonthly> frames, Consumer<Object> persist) {
		
		commandProxy().removeAll(getEntityWork(companyId, e -> e));
		
		commandProxy().removeAll(getEntityWkp(companyId, e -> e));
		
		frames.stream().map(f -> {
			AggregateFrameTargetWorkLocation work = (AggregateFrameTargetWorkLocation) f.getTarget();
			
			return new KrcmtMonOuenFrameWlc(new KrcmtMonOuenFramePK(companyId, f.getFrameNo()), 
					f.getName().v(), work.getWorkLocationCD().v());
		}).forEach(e -> {
			persist.accept(e);
		});
	}

	private <T> List<T> getEntityWlc(String companyId, Function<KrcmtMonOuenFrameWlc, T> converter) {
		return queryProxy().query("SELECT f FROM KrcmtMonOuenFrameWlc f WHERE f.pk.cid = :cid",
									KrcmtMonOuenFrameWlc.class)
						.setParameter("cid", companyId)
						.getList(converter);
	}

	private <T> List<T> getEntityWork(String companyId, Function<KrcmtMonOuenFrameWork, T> converter) {
		
		return queryProxy().query("SELECT f FROM KrcmtMonOuenFrameWork f WHERE f.pk.cid = :cid",
									KrcmtMonOuenFrameWork.class)
						.setParameter("cid", companyId)
						.getList(converter);
	}

	private <T> List<T> getEntityWkp(String companyId, Function<KrcmtMonOuenFrameWkp, T> converter) {
		
		return queryProxy().query("SELECT f FROM KrcmtMonOuenFrameWkp f WHERE f.pk.cid = :cid",
									KrcmtMonOuenFrameWkp.class)
						.setParameter("cid", companyId)
						.getList(converter);
	}

}
