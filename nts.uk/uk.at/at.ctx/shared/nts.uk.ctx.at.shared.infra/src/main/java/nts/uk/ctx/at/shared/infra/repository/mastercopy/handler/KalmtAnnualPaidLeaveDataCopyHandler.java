package nts.uk.ctx.at.shared.infra.repository.mastercopy.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.SerializationUtils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.mastercopy.CopyMethod;
import nts.uk.ctx.at.shared.dom.mastercopy.handler.CopyDataHandler;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.annualpaidleave.KalmtAnnualPaidLeave;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.annualpaidleave.KalmtAnnualPaidLeave_;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class KalmtAnnualPaidLeaveDataCopyHandler.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KalmtAnnualPaidLeaveDataCopyHandler extends JpaRepository implements CopyDataHandler {

	/** The copy method. */
	private CopyMethod copyMethod;

	/** The company Id. */
	private String companyId;

	/** The insert query. */
	private String INSERT_QUERY = "";

	/**
	 * Instantiates a new kalmt annual paid leave data copy handler.
	 *
	 * @param copyMethod
	 *            the copy method
	 * @param companyId
	 *            the company id
	 */
	public KalmtAnnualPaidLeaveDataCopyHandler(CopyMethod copyMethod, String companyId) {
		this.copyMethod = copyMethod;
		this.companyId = companyId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.mastercopy.handler.DataCopyHandler#doCopy()
	 */
	@Override
	public void doCopy() {
		// TODO: Get all company zero data

		String sourceCid = AppContexts.user().zeroCompanyIdInContract();
		List<KalmtAnnualPaidLeave> entities = this.findAllByCid(sourceCid);

		switch (copyMethod) {
		case REPLACE_ALL:
			// Delete all old data
			List<KalmtAnnualPaidLeave> oldDatas = this.findAllByCid(companyId);
			if (oldDatas != null) {
				this.commandProxy().removeAll(oldDatas);
			}
			this.getEntityManager().flush();

		case ADD_NEW:
			// Insert Data
			List<KalmtAnnualPaidLeave> dataCopy = new ArrayList<>();
			entities.stream().forEach(e -> {
				KalmtAnnualPaidLeave cloneEntity = SerializationUtils.clone(e);
				cloneEntity.setCid(companyId);
				dataCopy.add(cloneEntity);
			});
			List<KalmtAnnualPaidLeave> addEntities = dataCopy;
			addEntities = dataCopy.stream().filter(item -> !entities.contains(item)).collect(Collectors.toList());
			this.commandProxy().insertAll(addEntities);

		case DO_NOTHING:
			// Do nothing
		default:
			break;
		}
	}

	/**
	 * Find all by cid.
	 *
	 * @param cid
	 *            the cid
	 * @return the list
	 */
	public List<KalmtAnnualPaidLeave> findAllByCid(String cid) {
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<KalmtAnnualPaidLeave> cq = criteriaBuilder.createQuery(KalmtAnnualPaidLeave.class);
		Root<KalmtAnnualPaidLeave> root = cq.from(KalmtAnnualPaidLeave.class);
		cq.select(root);
		List<Predicate> predicates = new ArrayList<>();
		predicates.add(criteriaBuilder.equal(root.get(KalmtAnnualPaidLeave_.cid), cid));
		cq.where(predicates.toArray(new Predicate[] {}));
		return em.createQuery(cq).getResultList();
	}
}
