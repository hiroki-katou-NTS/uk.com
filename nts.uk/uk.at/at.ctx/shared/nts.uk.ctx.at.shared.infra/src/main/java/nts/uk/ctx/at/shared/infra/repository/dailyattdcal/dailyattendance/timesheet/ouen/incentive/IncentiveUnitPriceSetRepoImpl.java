package nts.uk.ctx.at.shared.infra.repository.dailyattdcal.dailyattendance.timesheet.ouen.incentive;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkLocationCD;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.incentive.IncentiveUnitPriceSetByCom;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.incentive.IncentiveUnitPriceSetByWkp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.incentive.IncentiveUnitPriceSetByWlc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.incentive.IncentiveUnitPriceSetEachWork;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.incentive.IncentiveUnitPriceSetHis;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.incentive.IncentiveUnitPriceSetRepo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.incentive.IncentiveUnitPriceUsageSet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.work.WorkGroup;
import nts.uk.ctx.at.shared.infra.entity.dailyattdcal.dailyattendance.timesheet.ouen.incentive.KrcmtInsentivePrice;
import nts.uk.ctx.at.shared.infra.entity.dailyattdcal.dailyattendance.timesheet.ouen.incentive.KrcmtInsentivePriceCmp;
import nts.uk.ctx.at.shared.infra.entity.dailyattdcal.dailyattendance.timesheet.ouen.incentive.KrcmtInsentivePriceCmpPK;
import nts.uk.ctx.at.shared.infra.entity.dailyattdcal.dailyattendance.timesheet.ouen.incentive.KrcmtInsentivePricePK;
import nts.uk.ctx.at.shared.infra.entity.dailyattdcal.dailyattendance.timesheet.ouen.incentive.KrcmtInsentivePriceUnt;
import nts.uk.ctx.at.shared.infra.entity.dailyattdcal.dailyattendance.timesheet.ouen.incentive.KrcmtInsentivePriceWkp;
import nts.uk.ctx.at.shared.infra.entity.dailyattdcal.dailyattendance.timesheet.ouen.incentive.KrcmtInsentivePriceWkpPK;
import nts.uk.ctx.at.shared.infra.entity.dailyattdcal.dailyattendance.timesheet.ouen.incentive.KrcmtInsentivePriceWlp;
import nts.uk.ctx.at.shared.infra.entity.dailyattdcal.dailyattendance.timesheet.ouen.incentive.KrcmtInsentivePriceWlpPK;

@Stateless
public class IncentiveUnitPriceSetRepoImpl extends JpaRepository implements IncentiveUnitPriceSetRepo {

	private static String COM = "SELECT i FROM KrcmtInsentivePriceCmp i WHERE i.pk.cid = :cid";
	private static String WKP = "SELECT i FROM KrcmtInsentivePriceWkp i WHERE i.pk.cid = :cid AND i.pk.workplaceId = :id";
	private static String WLP = "SELECT i FROM KrcmtInsentivePriceWlp i WHERE i.pk.cid = :cid AND i.pk.workLocationCD = :id";
	
	@Override
	public Optional<IncentiveUnitPriceUsageSet> findUsageSet(String companyId) {

		return queryProxy().find(companyId, KrcmtInsentivePriceUnt.class)
				.map(e -> e.domain());
	}

	@Override
	public void update(IncentiveUnitPriceUsageSet domain) {
		
		queryProxy().find(domain.getCid(), KrcmtInsentivePriceUnt.class).ifPresent(e -> {
			
			e.unit = domain.getUnit().value;
		});
	}

	@Override
	public void insert(IncentiveUnitPriceUsageSet domain) {
		
		KrcmtInsentivePriceUnt entity = KrcmtInsentivePriceUnt.convert(domain);
		
		commandProxy().insert(entity);
	}

	@Override
	public Optional<IncentiveUnitPriceSetByCom> findComSet(String companyId) {
		
		List<KrcmtInsentivePriceCmp> entity = queryProxy()
				.query(COM, KrcmtInsentivePriceCmp.class)
				.setParameter("cid", companyId)
				.getList();
		
		if (entity.isEmpty()) {

			return Optional.empty();
		}
		
		List<String> ids = entity.stream().map(e -> e.pk.insentiveId).collect(Collectors.toList());
		
		List<KrcmtInsentivePrice> histories = getPriceHis(ids);
		
		IncentiveUnitPriceSetByCom domain = IncentiveUnitPriceSetByCom.create(companyId);
		
		entity.stream().forEach(e -> {

			domain.addUnitPriceSet(createPriceSet(histories, e.pk.insentiveId, WorkGroup
							.create(e.workCd1, e.workCd2, e.workCd3, e.workCd4, e.workCd5)));
		});
		
		return Optional.of(domain);
	}

	@Override
	public void update(IncentiveUnitPriceSetByCom domain) {
		List<KrcmtInsentivePriceCmp> entity = queryProxy()
				.query(COM, KrcmtInsentivePriceCmp.class)
				.setParameter("cid", domain.getCompanyId())
				.getList();
		
		if (entity.isEmpty()) {

			insert(domain);
			return;
		}
		
		List<String> ids = entity.stream().map(e -> e.pk.insentiveId).collect(Collectors.toList());
		
		List<KrcmtInsentivePrice> histories = getPriceHis(ids);
		
		commandProxy().removeAll(entity);
		commandProxy().removeAll(histories);

		insert(domain);
	}

	@Override
	public void insert(IncentiveUnitPriceSetByCom domain) {
		List<KrcmtInsentivePriceCmp> entity = new ArrayList<>();
		List<KrcmtInsentivePrice> hisEntity = new ArrayList<>();
		
		domain.getUnitPriceSet().stream().forEach(s -> {
			
			String insentiveId = IdentifierUtil.randomUniqueId();
			
			KrcmtInsentivePriceCmp e = new KrcmtInsentivePriceCmp(
					new KrcmtInsentivePriceCmpPK(domain.getCompanyId(), insentiveId), 
					s.getWork().getWorkCD1().v(), 
					s.getWork().getWorkCD2().map(w -> w.v()).orElse(null), 
					s.getWork().getWorkCD3().map(w -> w.v()).orElse(null), 
					s.getWork().getWorkCD4().map(w -> w.v()).orElse(null),
					s.getWork().getWorkCD5().map(w -> w.v()).orElse(null));
			
			entity.add(e);
			
			s.getUseHis().stream().forEach(h -> {
				
				KrcmtInsentivePrice p = new KrcmtInsentivePrice(
						new KrcmtInsentivePricePK(insentiveId, h.getStartUseDate()), 
						h.getPriceUnit().v());
				
				hisEntity.add(p);
			});
		});
		
		commandProxy().insertAll(entity);
		commandProxy().insertAll(hisEntity);
	}

	@Override
	public Optional<IncentiveUnitPriceSetByWkp> findWorkPlaceSet(String companyId, String workplaceId) {
		List<KrcmtInsentivePriceWkp> entity = queryProxy()
				.query(WKP, KrcmtInsentivePriceWkp.class)
				.setParameter("cid", companyId)
				.setParameter("id", workplaceId)
				.getList();
		
		if (entity.isEmpty()) {

			return Optional.empty();
		}
		
		List<String> ids = entity.stream().map(e -> e.pk.insentiveId).collect(Collectors.toList());
		
		List<KrcmtInsentivePrice> histories = getPriceHis(ids);
		
		IncentiveUnitPriceSetByWkp domain = IncentiveUnitPriceSetByWkp.create(companyId, workplaceId);
		
		entity.stream().forEach(e -> {

			domain.addUnitPriceSet(createPriceSet(histories, e.pk.insentiveId, WorkGroup
							.create(e.workCd1, e.workCd2, e.workCd3, e.workCd4, e.workCd5)));
		});
		
		return Optional.of(domain);
	}

	@Override
	public void update(IncentiveUnitPriceSetByWkp domain) {
		List<KrcmtInsentivePriceWkp> entity = queryProxy()
				.query(WKP, KrcmtInsentivePriceWkp.class)
				.setParameter("cid", domain.getCompanyId())
				.setParameter("id", domain.getWorkplaceId())
				.getList();
		
		if (entity.isEmpty()) {

			insert(domain);
			return;
		}
		
		List<String> ids = entity.stream().map(e -> e.pk.insentiveId).collect(Collectors.toList());
		
		List<KrcmtInsentivePrice> histories = getPriceHis(ids);
		
		commandProxy().removeAll(entity);
		commandProxy().removeAll(histories);

		insert(domain);
	}

	@Override
	public void insert(IncentiveUnitPriceSetByWkp domain) {
		List<KrcmtInsentivePriceWkp> entity = new ArrayList<>();
		List<KrcmtInsentivePrice> hisEntity = new ArrayList<>();
		
		domain.getUnitPriceSet().stream().forEach(s -> {
			
			String insentiveId = IdentifierUtil.randomUniqueId();
			
			KrcmtInsentivePriceWkp e = new KrcmtInsentivePriceWkp(
					new KrcmtInsentivePriceWkpPK(domain.getCompanyId(), insentiveId, domain.getWorkplaceId()), 
					s.getWork().getWorkCD1().v(), 
					s.getWork().getWorkCD2().map(w -> w.v()).orElse(null), 
					s.getWork().getWorkCD3().map(w -> w.v()).orElse(null), 
					s.getWork().getWorkCD4().map(w -> w.v()).orElse(null),
					s.getWork().getWorkCD5().map(w -> w.v()).orElse(null));
			
			entity.add(e);
			
			s.getUseHis().stream().forEach(h -> {
				
				KrcmtInsentivePrice p = new KrcmtInsentivePrice(
						new KrcmtInsentivePricePK(insentiveId, h.getStartUseDate()), 
						h.getPriceUnit().v());
				
				hisEntity.add(p);
			});
		});
		
		commandProxy().insertAll(entity);
		commandProxy().insertAll(hisEntity);
	}

	@Override
	public Optional<IncentiveUnitPriceSetByWlc> findWorkLocationSet(String companyId, WorkLocationCD locationCd) {
		List<KrcmtInsentivePriceWlp> entity = queryProxy()
				.query(WLP, KrcmtInsentivePriceWlp.class)
				.setParameter("cid", companyId)
				.setParameter("id", locationCd.v())
				.getList();
		
		if (entity.isEmpty()) {

			return Optional.empty();
		}
		
		List<String> ids = entity.stream().map(e -> e.pk.insentiveId).collect(Collectors.toList());
		
		List<KrcmtInsentivePrice> histories = getPriceHis(ids);
		
		IncentiveUnitPriceSetByWlc domain = IncentiveUnitPriceSetByWlc.create(companyId, locationCd);
		
		entity.stream().forEach(e -> {

			domain.addUnitPriceSet(createPriceSet(histories, e.pk.insentiveId, WorkGroup
							.create(e.workCd1, e.workCd2, e.workCd3, e.workCd4, e.workCd5)));
		});
		
		return Optional.of(domain);
	}

	@Override
	public void update(IncentiveUnitPriceSetByWlc domain) {
		List<KrcmtInsentivePriceWlp> entity = queryProxy()
				.query(WLP, KrcmtInsentivePriceWlp.class)
				.setParameter("cid", domain.getCompanyId())
				.setParameter("id", domain.getWorkLocationCD().v())
				.getList();
		
		if (entity.isEmpty()) {

			insert(domain);
			return;
		}
		
		List<String> ids = entity.stream().map(e -> e.pk.insentiveId).collect(Collectors.toList());
		
		List<KrcmtInsentivePrice> histories = getPriceHis(ids);
		
		commandProxy().removeAll(entity);
		commandProxy().removeAll(histories);

		insert(domain);
	}

	@Override
	public void insert(IncentiveUnitPriceSetByWlc domain) {
		List<KrcmtInsentivePriceWlp> entity = new ArrayList<>();
		List<KrcmtInsentivePrice> hisEntity = new ArrayList<>();
		
		domain.getUnitPriceSet().stream().forEach(s -> {
			
			String insentiveId = IdentifierUtil.randomUniqueId();
			
			KrcmtInsentivePriceWlp e = new KrcmtInsentivePriceWlp(
					new KrcmtInsentivePriceWlpPK(domain.getCompanyId(), insentiveId, domain.getWorkLocationCD().v()), 
					s.getWork().getWorkCD1().v(), 
					s.getWork().getWorkCD2().map(w -> w.v()).orElse(null), 
					s.getWork().getWorkCD3().map(w -> w.v()).orElse(null), 
					s.getWork().getWorkCD4().map(w -> w.v()).orElse(null),
					s.getWork().getWorkCD5().map(w -> w.v()).orElse(null));
			
			entity.add(e);
			
			s.getUseHis().stream().forEach(h -> {
				
				KrcmtInsentivePrice p = new KrcmtInsentivePrice(
						new KrcmtInsentivePricePK(insentiveId, h.getStartUseDate()), 
						h.getPriceUnit().v());
				
				hisEntity.add(p);
			});
		});
		
		commandProxy().insertAll(entity);
		commandProxy().insertAll(hisEntity);
	}
	
	private IncentiveUnitPriceSetEachWork createPriceSet(List<KrcmtInsentivePrice> histories,
			String insentiveId, WorkGroup workGroup) {
		IncentiveUnitPriceSetEachWork set = IncentiveUnitPriceSetEachWork
				.create(workGroup);
		
		histories.stream().filter(h -> h.pk.id.equals(insentiveId)).forEach(h -> {
			
			set.addHis(IncentiveUnitPriceSetHis.create(h.pk.startDate, h.price));
		});
		
		return set;
	}

	private List<KrcmtInsentivePrice> getPriceHis(List<String> ids) {
		return queryProxy()
				.query("SELECT i FROM KrcmtInsentivePrice i WHERE i.pk.id in :id", KrcmtInsentivePrice.class)
				.setParameter("id", ids)
				.getList();
	}
}
