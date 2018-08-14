package nts.uk.ctx.pereg.infra.repository.handler;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pereg.dom.mastercopy.CopyMethod;
import nts.uk.ctx.pereg.dom.mastercopy.DataCopyHandler;
import nts.uk.ctx.pereg.infra.entity.person.info.ctg.PpemtDateRangeItem;
import nts.uk.ctx.pereg.infra.entity.person.info.ctg.PpemtPerInfoCtg;
import nts.uk.ctx.pereg.infra.entity.person.info.ctg.PpemtPerInfoCtgOrder;
import nts.uk.ctx.pereg.infra.entity.person.info.item.PpemtPerInfoItem;
import nts.uk.ctx.pereg.infra.entity.person.info.item.PpemtPerInfoItemOrder;
import nts.uk.shr.com.context.AppContexts;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author locph
 *
 * Event：個人情報定義の初期値コピー
 *
 */
public class PersonalInfoDefCopyHandler extends JpaRepository implements DataCopyHandler {
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
    private static final String FIND_ALL_PERSONAL_INFO_CATEGORY = "SELECT p FROM PpemtPerInfoCtg p WHERE p.cid =:cid;";
    private static final String FIND_ALL_PERSONAL_INFO_CATEGORY_ORDER = "SELECT p FROM PpemtPerInfoCtgOrder p WHERE p.cid =:cid;";
    private static final String FIND_ALL_PERSONAL_INFO_ITEM = "SELECT p FROM PpemtPerInfoItem p WHERE WHERE p.perInfoCtgId IN (:perInfoCtgIdList);";
    private static final String FIND_ALL_PERSONAL_INFO_ITEM_ORDER = "SELECT p FROM PpemtPerInfoItemOrder p WHERE p.perInfoCtgId IN (:perInfoCtgIdList);";
    private static final String FIND_ALL_DATE_RANGE_ITEM = "SELECT d FROM PpemtDateRangeItem d WHERE d.ppemtPerInfoCtgPK.perInfoCtgId IN (:perInfoCtgIdList);";

    /**
     * Instantiates a new kshst overtime frame data copy handler.
     *
     * @param copyMethod the copy method
     * @param companyId  the company cd
     */
    public PersonalInfoDefCopyHandler(CopyMethod copyMethod, String companyId) {
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
        return this.queryProxy().query(FIND_ALL_PERSONAL_INFO_CATEGORY, PpemtPerInfoCtg.class)
                .setParameter("cid", cid).getList();
    }

    //PpemtPerInfoCtgOrder
    private List<PpemtPerInfoCtgOrder> findAllPerInfoCtgOrderByCid(String cid) {
       return this.queryProxy().query(FIND_ALL_PERSONAL_INFO_CATEGORY_ORDER, PpemtPerInfoCtgOrder.class)
                .setParameter("cid", cid).getList();
    }

    //PpemtPerInfoItem
    private List<PpemtPerInfoItem> findAllPpemtPerInfoItemByCatId(Set<String> personalInfoCatId) {
        return this.queryProxy().query(FIND_ALL_PERSONAL_INFO_ITEM, PpemtPerInfoItem.class)
                .setParameter("perInfoCtgIdList", personalInfoCatId).getList();
    }

    //PpemtPerInfoItemOrder
    private List<PpemtPerInfoItemOrder> findAllPerInfoItemOrderByCatId(Set<String> personalInfoCatId) {
        return this.queryProxy().query(FIND_ALL_PERSONAL_INFO_ITEM_ORDER, PpemtPerInfoItemOrder.class)
                .setParameter("perInfoCtgIdList", personalInfoCatId).getList();
    }

    //PpemtDateRangeItem
    private List<PpemtDateRangeItem> findAlldateRangeItemByCatId(Set<String> personalInfoCatId) {
        this.queryProxy().query(FIND_ALL_DATE_RANGE_ITEM, PpemtDateRangeItem.class)
                .setParameter("perInfoCtgIdList", personalInfoCatId).getList();
        return null;
    }

    public void copyMasterData(String sourceCid, String targetCid, boolean isReplace) {
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
