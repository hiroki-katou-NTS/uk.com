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
import nts.uk.ctx.at.shared.dom.mastercopy.handler.DataCopyHandler;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.KmfmtRetentionYearly;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.KmfmtRetentionYearly_;
import nts.uk.shr.com.context.AppContexts;



/**
 * The Class KmfmtRetentionYearlyDataCopyHandler.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KmfmtRetentionYearlyDataCopyHandler extends JpaRepository implements DataCopyHandler {
	/** The copy method. */
	private CopyMethod copyMethod;

	/** The company Id. */
	private String companyId;

	/** The insert query. */
	private String INSERT_QUERY = "";
	
	/**
	 * Instantiates a new kmfmt retention yearly data copy handler.
	 *
	 * @param copyMethod the copy method
	 * @param companyId the company id
	 */
	public KmfmtRetentionYearlyDataCopyHandler(CopyMethod copyMethod, String companyId) {
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
		List<KmfmtRetentionYearly> entities = this.findAllByCid(sourceCid);
		
		switch (copyMethod) {
		case REPLACE_ALL:
			// Delete all old data
			List<KmfmtRetentionYearly> oldDatas = this.findAllByCid(companyId);
			if(oldDatas != null) {
				this.commandProxy().removeAll(oldDatas);
			}
			this.getEntityManager().flush();
			
		case ADD_NEW:
			// Insert Data
			List<KmfmtRetentionYearly> dataCopy = new ArrayList<>();
			entities.stream().forEach(e -> {
				KmfmtRetentionYearly cloneEntity = SerializationUtils.clone(e);
				cloneEntity.setCid(companyId);
				dataCopy.add(cloneEntity);
			});
			List<KmfmtRetentionYearly> addEntities = dataCopy;
			addEntities = dataCopy.stream().filter(item -> !entities.contains(item)).collect(Collectors.toList());
			this.commandProxy().insertAll(addEntities);
			
		case DO_NOTHING:
			// Do nothing
		default:
			break;
		}
	}
	
	public List<KmfmtRetentionYearly> findAllByCid(String cid){
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<KmfmtRetentionYearly> cq = criteriaBuilder.createQuery(KmfmtRetentionYearly.class);
		Root<KmfmtRetentionYearly> root = cq.from(KmfmtRetentionYearly.class);
		cq.select(root);
		List<Predicate> predicates = new ArrayList<>();
		predicates.add(criteriaBuilder.equal(root.get(KmfmtRetentionYearly_.cid),cid));
		cq.where(predicates.toArray(new Predicate[] {}));
		return em.createQuery(cq).getResultList();
	}
}
