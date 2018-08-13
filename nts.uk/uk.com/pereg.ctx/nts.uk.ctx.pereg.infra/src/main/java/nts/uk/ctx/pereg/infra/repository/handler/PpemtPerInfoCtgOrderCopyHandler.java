package nts.uk.ctx.pereg.infra.repository.handler;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pereg.dom.mastercopy.CopyMethod;
import nts.uk.ctx.pereg.dom.mastercopy.DataCopyHandler;
import nts.uk.ctx.pereg.infra.entity.person.info.ctg.*;
import nts.uk.ctx.pereg.infra.entity.person.info.item.PpemtPerInfoItem;
import nts.uk.ctx.pereg.infra.entity.person.info.item.PpemtPerInfoItemOrder;
import nts.uk.ctx.pereg.infra.entity.person.info.item.PpemtPerInfoItem_;
import nts.uk.shr.com.context.AppContexts;
import org.apache.commons.lang3.SerializationUtils;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author locph
 */
public class PpemtPerInfoCtgOrderCopyHandler extends JpaRepository implements DataCopyHandler {
    /**
     * The copy method.
     */
    private CopyMethod copyMethod;

    /**
     * The company Id.
     */
    private String companyId;

    /**
     * The insert query.
     */
    private String INSERT_QUERY = "";

    /**
     * Instantiates a new kshst overtime frame data copy handler.
     *
     * @param copyMethod the copy method
     * @param companyId  the company cd
     */
    public PpemtPerInfoCtgOrderCopyHandler(CopyMethod copyMethod, String companyId) {
        this.copyMethod = copyMethod;
        this.companyId = companyId;
    }

    /* (non-Javadoc)
     * @see nts.uk.ctx.sys.assist.dom.handler.handler.DataCopyHandler#doCopy()
     */
    @Override
    public void doCopy() {

        String sourceCid = AppContexts.user().zeroCompanyIdInContract();
        String targetCid = companyId;

        switch (copyMethod) {
            case REPLACE_ALL:
                // Delete all old data
                break;
            case ADD_NEW:
                // Insert Data
                copyMasterData(sourceCid, targetCid, false);
                break;
            case DO_NOTHING:
                // Do nothing
                break;
            default:
                break;
        }
    }

    //PpemtPerInfoCtg
    private List<PpemtPerInfoCtg> findAllPerInfoCtgByCid(String cid) {
        EntityManager em = this.getEntityManager();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<PpemtPerInfoCtg> query = builder.createQuery(PpemtPerInfoCtg.class);
        Root<PpemtPerInfoCtg> root = query.from(PpemtPerInfoCtg.class);

        List<Predicate> predicateList = new ArrayList<>();
        predicateList.add(builder.equal(root.get(PpemtPerInfoCtg_.cid), cid));
        query.where(predicateList.toArray(new Predicate[]{}));
        return em.createQuery(query).getResultList();
    }

    //PpemtPerInfoCtgOrder
    private List<PpemtPerInfoCtgOrder> findAllPerInfoCtgOrderByCid(String cid) {
        EntityManager em = this.getEntityManager();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<PpemtPerInfoCtgOrder> query = builder.createQuery(PpemtPerInfoCtgOrder.class);
        Root<PpemtPerInfoCtgOrder> root = query.from(PpemtPerInfoCtgOrder.class);

        List<Predicate> predicateList = new ArrayList<>();
        predicateList.add(builder.equal(root.get(PpemtPerInfoCtgOrder_.cid), cid));
        query.where(predicateList.toArray(new Predicate[]{}));
        return em.createQuery(query).getResultList();
    }

    //PpemtPerInfoItem
    private List<PpemtPerInfoItem> findAllPpemtPerInfoItemByCatId(Set<String> personalInfoCatId) {
        EntityManager em = this.getEntityManager();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<PpemtPerInfoItem> query = builder.createQuery(PpemtPerInfoItem.class);
        Root<PpemtPerInfoItem> root = query.from(PpemtPerInfoItem.class);

        // Build query
        query.select(root);

        // create where conditions
        List<Predicate> predicates = new ArrayList<>();

        // Find by history id
        if (!personalInfoCatId.isEmpty()) {
            predicates.add(root.get(PpemtPerInfoItem_.perInfoCtgId).in(personalInfoCatId));
        }
        // add where to query
        query.where(predicates.toArray(new Predicate[] {}));

        return em.createQuery(query).getResultList();
    }

    //PpemtPerInfoItemOrder
    private List<PpemtPerInfoItemOrder> findAllPerInfoItemOrderByCatId(Set<String> personalInfoCatId) {
//        EntityManager em = this.getEntityManager();
//        CriteriaBuilder builder = em.getCriteriaBuilder();
//        CriteriaQuery<PpemtPerInfoItem> query = builder.createQuery(PpemtPerInfoItem.class);
//        Root<PpemtPerInfoItem> root = query.from(PpemtPerInfoItem.class);
//
//        List<Predicate> predicateList = new ArrayList<>();
//        predicateList.add(builder.equal(root.get(PpemtPerInfoCtgOrder_.cid), cid));
//        query.where(predicateList.toArray(new Predicate[]{}));
//        return em.createQuery(query).getResultList();
        return null;
    }

    //PpemtDateRangeItem
    private List<PpemtDateRangeItem> findAlldateRangeItemByCatId(Set<String> personalInfoCatId) {
//        EntityManager em = this.getEntityManager();
//        CriteriaBuilder builder = em.getCriteriaBuilder();
//        CriteriaQuery<PpemtPerInfoItem> query = builder.createQuery(PpemtPerInfoItem.class);
//        Root<PpemtPerInfoItem> root = query.from(PpemtPerInfoItem.class);
//
//        List<Predicate> predicateList = new ArrayList<>();
//        predicateList.add(builder.equal(root.get(PpemtPerInfoCtgOrder_.cid), cid));
//        query.where(predicateList.toArray(new Predicate[]{}));
//        return em.createQuery(query).getResultList();
        return null;
    }

    public void copyMasterData(String sourceCid, String targetCid, boolean isReplace) {
        //find
        List<PpemtPerInfoCtg> perInfoCtgEntities = findAllPerInfoCtgByCid(sourceCid);
        List<PpemtPerInfoCtgOrder> perInfoCtgOrderEntities = findAllPerInfoCtgOrderByCid(sourceCid);

        Set<String> personalInfoCatId = perInfoCtgEntities.stream()
                .map(ppemtPerInfoCtg -> ppemtPerInfoCtg.getPpemtPerInfoCtgPK().perInfoCtgId)
                .collect(Collectors.toSet());

        List<PpemtPerInfoItem> perInfoItemEntities = findAllPpemtPerInfoItemByCatId(personalInfoCatId);
        List<PpemtPerInfoItemOrder> perInfoItemOrderEntities = findAllPerInfoItemOrderByCatId(personalInfoCatId);
        List<PpemtDateRangeItem> dateRangeItemEntities = findAlldateRangeItemByCatId(personalInfoCatId);


    }
}
