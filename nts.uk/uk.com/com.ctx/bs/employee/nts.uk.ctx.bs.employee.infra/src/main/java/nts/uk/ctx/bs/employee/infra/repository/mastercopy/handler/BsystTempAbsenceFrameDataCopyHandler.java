package nts.uk.ctx.bs.employee.infra.repository.mastercopy.handler;

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
import nts.uk.ctx.bs.employee.dom.mastercopy.handler.CopyMethod;
import nts.uk.ctx.bs.employee.dom.mastercopy.handler.DataCopyHandler;
import nts.uk.ctx.bs.employee.infra.entity.temporaryabsence.frame.BsystTempAbsenceFrame;
import nts.uk.ctx.bs.employee.infra.entity.temporaryabsence.frame.BsystTempAbsenceFramePK_;
import nts.uk.ctx.bs.employee.infra.entity.temporaryabsence.frame.BsystTempAbsenceFrame_;
import nts.uk.shr.com.context.AppContexts;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BsystTempAbsenceFrameDataCopyHandler extends JpaRepository implements DataCopyHandler {

	/** The copy method. */
	private CopyMethod copyMethod;
	
	/** The company Id. */
	private String companyId;
	
	/** The insert query. */
	private String INSERT_QUERY = "";
	
	/**
	 * Instantiates a new kshst overtime frame data copy handler.
	 *
	 * @param copyMethod the copy method
	 * @param companyCd the company cd
	 */
	public BsystTempAbsenceFrameDataCopyHandler(CopyMethod copyMethod, String companyId) {
		this.copyMethod = copyMethod;
		this.companyId = companyId;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.assist.dom.mastercopy.handler.DataCopyHandler#doCopy()
	 */
	@Override
	public void doCopy() {
		//Get all company zero data
		String zeroCid = AppContexts.user().zeroCompanyIdInContract();
		List<BsystTempAbsenceFrame> zeroCidEntities = this.findAllByCid(zeroCid);
		List<BsystTempAbsenceFrame> oldDatas = this.findAllByCid(companyId);
		switch (copyMethod) {
			case REPLACE_ALL:
				// Delete all old data
				if(oldDatas!=null) this.commandProxy().removeAll(oldDatas);
				this.getEntityManager().flush();
			case ADD_NEW:
				// Insert Data
				List<BsystTempAbsenceFrame> dataCopy = new ArrayList<>();
				zeroCidEntities.stream().forEach(e-> {
					BsystTempAbsenceFrame cloneEntity = SerializationUtils.clone(e);
					cloneEntity.getBsystTempAbsenceFramePK().setCid(companyId);
					dataCopy.add(cloneEntity);
				});
				List<BsystTempAbsenceFrame> addEntites = dataCopy;
				addEntites = dataCopy.stream()
		                .filter(item -> !oldDatas.contains(item))
		                .collect(Collectors.toList());
				this.commandProxy().insertAll(addEntites);
			case DO_NOTHING:
				// Do nothing
			default: 
				break;
		}
	}
	
	/**
	 * Find all by cid.
	 *
	 * @param cid the cid
	 * @return the list
	 */
	public List<BsystTempAbsenceFrame> findAllByCid(String cid){
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<BsystTempAbsenceFrame> cq = criteriaBuilder.createQuery(BsystTempAbsenceFrame.class);
		Root<BsystTempAbsenceFrame> root = cq.from(BsystTempAbsenceFrame.class);
		cq.select(root);
		List<Predicate> predicates = new ArrayList<>();
		predicates.add(criteriaBuilder.equal(root.get(BsystTempAbsenceFrame_.bsystTempAbsenceFramePK).get(BsystTempAbsenceFramePK_.cid), cid));
		cq.where(predicates.toArray(new Predicate[] {}));
		return em.createQuery(cq).getResultList();
	}

}
