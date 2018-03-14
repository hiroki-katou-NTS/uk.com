package nts.uk.ctx.at.record.infra.repository.divergence.time.setting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceReason;
import nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceReasonInputMethod;
import nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceReasonInputMethodGetMemento;
import nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceTime;
import nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceTimeGetMemento;
import nts.uk.ctx.at.record.dom.divergence.time.setting.*;
import nts.uk.ctx.at.record.infra.entity.divergence.time.KrcstDvgcTime;
import nts.uk.ctx.at.record.infra.entity.divergence.time.KrcstDvgcTimePK_;
import nts.uk.ctx.at.record.infra.entity.divergence.time.KrcstDvgcTime_;
import nts.uk.ctx.at.record.infra.entity.divergencetime.KmkmtDivergenceTime;
import nts.uk.ctx.at.shared.dom.attendance.AttendanceItem;

public class JpaDivergenceTimeRepository extends JpaRepository implements DivergenceTimeRepository{
	
	/**
	 * get all divergence time
	 * 
	 * @param companyId
	 * @return
	 */
	
	@Override
	public List<DivergenceTime> getAllDivTime(String companyId) {
		return this.findByCompanyId(companyId);
	}

	@Override
	public List<DivergenceReason> getDivReasonByCode(String companyId, int divTimeId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AttendanceItem> getallItembyCode(String companyId, int divTimeId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateDivTime(DivergenceTime divTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addDivReason(int divTimeId, String company, DivergenceReason divReason) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateDivReason(int divTimeId, String company, DivergenceReason divReason) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteDivReason(String companyId, int divTimeId, String divReasonCode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Optional<DivergenceReason> getDivReason(String companyId, int divTimeId, String divReasonCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addAttendanceItemId(String companyId, int divTimeId, Double AttendanceId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAttendItemId(String companyId, int divTimeId, Double AttendanceId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<DivergenceTime> getDivergenceTimeName(String companyId, List<Integer> divTimeIds) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Double> getAttendanceIdByDivNo(String companyId,int divTimeId){
		return null;
	}
	
	private DivergenceTime toDomain(KrcstDvgcTime entities) {
		DivergenceTimeGetMemento memento = new JpaDivergenceTimeRepositoryGetMemento(entities);
		return new DivergenceTime(memento);
	}
	private List<DivergenceTime> findByCompanyId(String companyId) {
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<KrcstDvgcTime> cq = criteriaBuilder.createQuery(KrcstDvgcTime.class);
		Root<KrcstDvgcTime> root = cq.from(KrcstDvgcTime.class);

		// Build query
		cq.select(root);

		// create where conditions
		List<Predicate> predicates = new ArrayList<>();
		predicates.add(criteriaBuilder.equal(root.get( KrcstDvgcTime_.id).get(KrcstDvgcTimePK_.cid), companyId));

		// add where to query
		cq.where(predicates.toArray(new Predicate[] {}));

		// query data
		List<KrcstDvgcTime> KrcstDvgcTime = em.createQuery(cq).getResultList();

		// return
		return KrcstDvgcTime.isEmpty() ? new ArrayList<DivergenceTime>()
				: KrcstDvgcTime.stream().map(item -> this.toDomain(item)).collect(Collectors.toList());
	}

	
	
}
