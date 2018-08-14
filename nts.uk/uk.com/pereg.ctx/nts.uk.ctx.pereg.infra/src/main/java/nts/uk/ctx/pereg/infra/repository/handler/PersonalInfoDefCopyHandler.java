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
import org.apache.commons.lang3.SerializationUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author locph
 * <p>
 * Event：個人情報定義の初期値コピー
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
        //Get data company zero
        List<PpemtPerInfoCtg> sPerInfoCtgEntities = findAllPerInfoCtgByCid(sourceCid);
        List<PpemtPerInfoCtgOrder> sPerInfoCtgOrderEntities = findAllPerInfoCtgOrderByCid(sourceCid);

        Set<String> sourcePersonalInfoCatId = sPerInfoCtgEntities.stream()
                .map(ppemtPerInfoCtg -> ppemtPerInfoCtg.ppemtPerInfoCtgPK.perInfoCtgId)
                .collect(Collectors.toSet());

        List<PpemtPerInfoItem> sPerInfoItemEntities = findAllPpemtPerInfoItemByCatId(sourcePersonalInfoCatId);
        List<PpemtPerInfoItemOrder> sPerInfoItemOrderEntities = findAllPerInfoItemOrderByCatId(sourcePersonalInfoCatId);
        List<PpemtDateRangeItem> sDateRangeItemEntities = findAlldateRangeItemByCatId(sourcePersonalInfoCatId);

        //Get data company target
        List<PpemtPerInfoCtg> tPerInfoCtgEntities = findAllPerInfoCtgByCid(sourceCid);
        List<PpemtPerInfoCtgOrder> tPerInfoCtgOrderEntities = findAllPerInfoCtgOrderByCid(sourceCid);

        Set<String> targetPersonalInfoCatId = tPerInfoCtgEntities.stream()
                .map(ppemtPerInfoCtg -> ppemtPerInfoCtg.ppemtPerInfoCtgPK.perInfoCtgId)
                .collect(Collectors.toSet());

        List<PpemtPerInfoItem> tPerInfoItemEntities = findAllPpemtPerInfoItemByCatId(targetPersonalInfoCatId);
        List<PpemtPerInfoItemOrder> tPerInfoItemOrderEntities = findAllPerInfoItemOrderByCatId(targetPersonalInfoCatId);
        List<PpemtDateRangeItem> tPateRangeItemEntities = findAlldateRangeItemByCatId(targetPersonalInfoCatId);

        //filter
        Map<String, List<PpemtPerInfoCtg>> m1 = new HashMap<>();
        Map<String, List<PpemtPerInfoCtgOrder>> m2 = new HashMap<>();
        Map<String, List<PpemtPerInfoItem>> m3 = new HashMap<>();
        Map<String, List<PpemtPerInfoItemOrder>> m4 = new HashMap<>();
        Map<String, List<PpemtDateRangeItem>> m5 = new HashMap<>();
        if (!sPerInfoCtgEntities.isEmpty()) {
            m1 = sPerInfoCtgEntities.stream().collect(Collectors.groupingBy(o -> o.ppemtPerInfoCtgPK.perInfoCtgId));
        }
        if (!sPerInfoCtgOrderEntities.isEmpty()) {
            m2 = sPerInfoCtgOrderEntities.stream().collect(Collectors.groupingBy(o -> o.ppemtPerInfoCtgPK.perInfoCtgId));
        }
        if (!sPerInfoItemEntities.isEmpty()) {
            m3 = sPerInfoItemEntities.stream().collect(Collectors.groupingBy(o -> o.perInfoCtgId));
        }
        if (!sPerInfoItemOrderEntities.isEmpty()) {
            m4 = sPerInfoItemOrderEntities.stream().collect(Collectors.groupingBy(o -> o.perInfoCtgId));
        }
        if (!sDateRangeItemEntities.isEmpty()) {
            m5 = sDateRangeItemEntities.stream().collect(Collectors.groupingBy(o -> o.ppemtPerInfoCtgPK.perInfoCtgId));
        }

        final List<PpemtPerInfoCtg> s1 = new ArrayList<>();
        final List<PpemtPerInfoCtgOrder> s2 = new ArrayList<>();
        final List<PpemtPerInfoItem> s3 = new ArrayList<>();
        final List<PpemtPerInfoItemOrder> s4 = new ArrayList<>();
        final List<PpemtDateRangeItem> s5 = new ArrayList<>();

        //取得できなかった場合（会社ID　＝　Input．会社IDの個人情報定義）//ko lấy được
        if (tPerInfoCtgEntities.isEmpty() && tPerInfoCtgOrderEntities.isEmpty() && tPerInfoItemEntities.isEmpty()
                && tPerInfoItemOrderEntities.isEmpty() && tPateRangeItemEntities.isEmpty()) {
            //create
            for (String catId : sourcePersonalInfoCatId) {
                String newCatId = UUID.randomUUID().toString();
                String newDefId = UUID.randomUUID().toString();
                for (PpemtPerInfoCtg perInfoCtgEntity : m1.get(catId)) {
                    PpemtPerInfoCtg cloneObject = SerializationUtils.clone(perInfoCtgEntity);
                    cloneObject.ppemtPerInfoCtgPK.perInfoCtgId = newCatId;
                    cloneObject.cid = targetCid;
                    s1.add(cloneObject);
                }

                if (m2.get(catId) != null && !m2.get(catId).isEmpty()) {
                    for (PpemtPerInfoCtgOrder perInfoCtgOrderEntity : m2.get(catId)) {
                        PpemtPerInfoCtgOrder cloneObject = SerializationUtils.clone(perInfoCtgOrderEntity);
                        cloneObject.cid = targetCid;
                        cloneObject.ppemtPerInfoCtgPK.perInfoCtgId = newCatId;
                        s2.add(cloneObject);
                    }
                }

                if (m3.get(catId) != null && !m3.get(catId).isEmpty()) {
                    for (PpemtPerInfoItem perInfoItemEntity : m3.get(catId)) {
                        PpemtPerInfoItem cloneObject = SerializationUtils.clone(perInfoItemEntity);
                        cloneObject.perInfoCtgId = newCatId;
                        cloneObject.ppemtPerInfoItemPK.perInfoItemDefId = newDefId;
                        s3.add(cloneObject);
                    }
                }

                if (m4.get(catId) != null && !m4.get(catId).isEmpty()) {
                    for (PpemtPerInfoItemOrder perInfoItemOrderEntity : m4.get(catId)) {
                        PpemtPerInfoItemOrder cloneObject = SerializationUtils.clone(perInfoItemOrderEntity);
                        cloneObject.perInfoCtgId = newCatId;
                        cloneObject.ppemtPerInfoItemPK.perInfoItemDefId = newDefId;
                        s4.add(cloneObject);
                    }
                }

                if (m5.get(catId) != null && !m5.get(catId).isEmpty()) {
                    for (PpemtDateRangeItem dateRangeItem : m5.get(catId)) {
                        PpemtDateRangeItem cloneObject = SerializationUtils.clone(dateRangeItem);
                        cloneObject.ppemtPerInfoCtgPK.perInfoCtgId = newCatId;
                        s5.add(cloneObject);
                    }
                }
            }
        } else {//取得できた場合（会社ID　＝　Input．会社IDの個人情報定義）//Lấy được
            //overwrite

        }

        this.commandProxy().insertAll(s1);
        this.commandProxy().insertAll(s2);
        this.commandProxy().insertAll(s3);
        this.commandProxy().insertAll(s4);
        this.commandProxy().insertAll(s5);
    }
}
