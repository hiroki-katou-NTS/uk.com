package nts.uk.ctx.at.shared.repository.vacation.setting.temp.caredata;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.vacation.setting.temp.caredata.TempCareData;
import nts.uk.ctx.at.shared.dom.vacation.setting.temp.caredata.TempCareDataRepository;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.temp.caredata.KrcdtTempCareData;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.temp.caredata.KrcdtTempCareDataPK;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.temp.caredata.KrcdtTempCareDataPK_;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.temp.caredata.KrcdtTempCareData_;

@Stateless
public class JpaTempCareDataRepository extends JpaRepository implements TempCareDataRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.temp.caredata.
	 * TempCareDataRepository#findTempCareDataByEmpId(java.lang.String)
	 */
	@Override
	public List<TempCareData> findTempCareDataByEmpId(String employeeId) {
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<KrcdtTempCareData> cq = criteriaBuilder.createQuery(KrcdtTempCareData.class);
		Root<KrcdtTempCareData> root = cq.from(KrcdtTempCareData.class);
		cq.select(root);
		// create conditions
		List<Predicate> predicates = new ArrayList<>();
		predicates
				.add(criteriaBuilder.equal(root.get(KrcdtTempCareData_.id).get(KrcdtTempCareDataPK_.sid), employeeId));
		cq.where(predicates.toArray(new Predicate[] {}));
		List<KrcdtTempCareData> listKrcdtTempCareData = em.createQuery(cq).getResultList();
		return listKrcdtTempCareData.isEmpty() ? new ArrayList<TempCareData>()
				: listKrcdtTempCareData.stream().map(e -> this.toDomain(e)).collect(Collectors.toList());
	}

	@Override
	public Optional<TempCareData> getTempCareDataInfo(String employeeId, GeneralDate ymd) {
		KrcdtTempCareDataPK pk = new KrcdtTempCareDataPK(employeeId, ymd);
		return this.queryProxy().find(pk, KrcdtTempCareData.class).map(e -> toDomain(e));
	}

	@Override
	public void removeTempCareDate(String employeeId, GeneralDate ymd) {
		Optional<TempCareData> entityOptional = this.getTempCareDataInfo(employeeId, ymd);
		entityOptional.ifPresent(e -> this.commandProxy().remove(e));
	}

	@Override
	public void updateTempCareDate(TempCareData tempCareDataUpdate) {
		this.commandProxy().update(this.toEntity(tempCareDataUpdate));
	}

	/**
	 * To domain.
	 *
	 * @return the temp care data
	 */
	public TempCareData toDomain(KrcdtTempCareData entity) {
		JpaTempCareDataGetMemento getMeneto = new JpaTempCareDataGetMemento(entity);
		return new TempCareData(getMeneto);
	}

	/**
	 * To entity.
	 *
	 * @param domain
	 *            the domain
	 * @return the krcdt temp care data
	 */
	public KrcdtTempCareData toEntity(TempCareData domain) {
		KrcdtTempCareData entity = new KrcdtTempCareData();
		KrcdtTempCareDataPK pk = new KrcdtTempCareDataPK();
		entity.setId(pk);
		domain.saveToMemento(new JpatempCareDataSetMemento(entity));
		return entity;
	}

}
