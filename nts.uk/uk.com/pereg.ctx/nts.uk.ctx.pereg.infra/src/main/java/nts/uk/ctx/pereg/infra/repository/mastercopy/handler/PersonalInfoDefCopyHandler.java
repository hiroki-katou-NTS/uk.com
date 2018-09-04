package nts.uk.ctx.pereg.infra.repository.mastercopy.handler;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.command.CommandProxy;
import nts.arc.layer.infra.data.query.QueryProxy;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.pereg.dom.mastercopy.DataCopyHandler;
import nts.uk.ctx.pereg.infra.entity.person.info.ctg.PpemtDateRangeItem;
import nts.uk.ctx.pereg.infra.entity.person.info.ctg.PpemtPerInfoCtg;
import nts.uk.ctx.pereg.infra.entity.person.info.ctg.PpemtPerInfoCtgOrder;
import nts.uk.ctx.pereg.infra.entity.person.info.ctg.PpemtPerInfoCtgPK;
import nts.uk.ctx.pereg.infra.entity.person.info.item.PpemtPerInfoItem;
import nts.uk.ctx.pereg.infra.entity.person.info.item.PpemtPerInfoItemOrder;
import nts.uk.ctx.pereg.infra.entity.person.info.item.PpemtPerInfoItemPK;
import nts.uk.shr.com.context.AppContexts;
import org.apache.commons.lang3.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author locph
 */
public class PersonalInfoDefCopyHandler extends DataCopyHandler {

	/**
	 * Logger
	 */
    private static final Logger LOGGER = LoggerFactory.getLogger(PersonalInfoDefCopyHandler.class);
    /**
     * The insert query.
     */
    private static final String FIND_ALL_PERSONAL_INFO_CATEGORY = "SELECT p FROM PpemtPerInfoCtg p WHERE p.cid =:cid";
    private static final String FIND_ALL_PERSONAL_INFO_CATEGORY_ORDER = "SELECT p FROM PpemtPerInfoCtgOrder p " +
            "WHERE p.cid =:cid and p.ppemtPerInfoCtgPK.perInfoCtgId IN :perInfoCtgIdList";
    private static final String FIND_ALL_PERSONAL_INFO_ITEM = "SELECT p FROM PpemtPerInfoItem p " +
            "WHERE p.perInfoCtgId IN :perInfoCtgIdList";
    private static final String FIND_ALL_PERSONAL_INFO_ITEM_ORDER = "SELECT p FROM PpemtPerInfoItemOrder p " +
            "WHERE p.perInfoCtgId IN :perInfoCtgIdList";
    private static final String FIND_ALL_DATE_RANGE_ITEM = "SELECT d FROM PpemtDateRangeItem d " +
            "WHERE d.ppemtPerInfoCtgPK.perInfoCtgId IN :perInfoCtgIdList";

    private static final String FIND_ALL_PERSONAL_INFO_ITEM_ON_CID_AND_CCD = "SELECT i FROM PpemtPerInfoItem i " +
            "WHERE i.perInfoCtgId IN (SELECT p.ppemtPerInfoCtgPK.perInfoCtgId FROM PpemtPerInfoCtg p WHERE p.cid =:cid and p.categoryCd=:ccd)";

    private static final String FIND_ALL_DATE_RANGE_ITEM_ON_CID_AND_CCD = "SELECT d FROM PpemtDateRangeItem d " +
            "WHERE d.ppemtPerInfoCtgPK.perInfoCtgId IN (SELECT p.ppemtPerInfoCtgPK.perInfoCtgId FROM PpemtPerInfoCtg p WHERE p.cid =:cid and p.categoryCd=:ccd)";

    /**
     * The copy method.
     */
    private int copyMethod;

    /**
     * The company Id.
     */
    private String companyId;

    QueryProxy queryProxy;
    CommandProxy commandProxy;

    /**
     * Instantiates a new kshst overtime frame data copy handler.
     *
     * @param copyMethod the copy method
     * @param companyId  the company cd
     */
    public PersonalInfoDefCopyHandler(JpaRepository repository, int copyMethod, String companyId) {
        this.copyMethod = copyMethod;
        this.companyId = companyId;
        this.queryProxy = repository.queryProxy();
        this.commandProxy = repository.commandProxy();
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
            default:
                break;
        }
    }

    /**
     * List all LisPpemtPerInfoCtg
     *
     * @param cid
     * @return
     */
    private List<PpemtPerInfoCtg> findAllPerInfoCtgByCid(String cid) {
        return this.queryProxy.query(FIND_ALL_PERSONAL_INFO_CATEGORY, PpemtPerInfoCtg.class)
                .setParameter("cid", cid).getList();
    }

    /**
     * PpemtPerInfoCtgOrder
     *
     * @param cid
     * @param personalInfoCatId
     * @return
     */
    private List<PpemtPerInfoCtgOrder> findAllPerInfoCtgOrderByCid(String cid, Set<String> personalInfoCatId) {
        return this.queryProxy.query(FIND_ALL_PERSONAL_INFO_CATEGORY_ORDER, PpemtPerInfoCtgOrder.class)
                .setParameter("cid", cid).setParameter("perInfoCtgIdList", personalInfoCatId).getList();
    }

    /**
     * PpemtPerInfoItem
     *
     * @param personalInfoCatId
     * @return
     */
    private List<PpemtPerInfoItem> findAllPpemtPerInfoItemByCatId(Set<String> personalInfoCatId) {
        return this.queryProxy.query(FIND_ALL_PERSONAL_INFO_ITEM, PpemtPerInfoItem.class)
                .setParameter("perInfoCtgIdList", personalInfoCatId).getList();
    }

    /**
     * @param cid
     * @param ccd
     * @return
     */
    private List<PpemtPerInfoItem> findAllPpemtPerInfoItemByCidAndCcd(String cid, String ccd) {
        return this.queryProxy.query(FIND_ALL_PERSONAL_INFO_ITEM_ON_CID_AND_CCD, PpemtPerInfoItem.class)
                .setParameter("cid", cid).setParameter("ccd", ccd).getList();
    }

    /**
     * PpemtPerInfoItemOrder
     *
     * @param personalInfoCatId
     * @return
     */
    private List<PpemtPerInfoItemOrder> findAllPerInfoItemOrderByCatId(Set<String> personalInfoCatId) {
        return this.queryProxy.query(FIND_ALL_PERSONAL_INFO_ITEM_ORDER, PpemtPerInfoItemOrder.class)
                .setParameter("perInfoCtgIdList", personalInfoCatId).getList();
    }

    /**
     * PpemtDateRangeItem
     *
     * @param personalInfoCatId
     * @return
     */
    private List<PpemtDateRangeItem> findAlldateRangeItemByCatId(Set<String> personalInfoCatId) {
        return this.queryProxy.query(FIND_ALL_DATE_RANGE_ITEM, PpemtDateRangeItem.class)
                .setParameter("perInfoCtgIdList", personalInfoCatId).getList();
    }

    /**
     * @param cid
     * @param ccd
     * @return
     */
    private PpemtDateRangeItem findAlldateRangeItemByCidAndCcd(String cid, String ccd) {
        return this.queryProxy.query(FIND_ALL_DATE_RANGE_ITEM_ON_CID_AND_CCD, PpemtDateRangeItem.class)
                .setParameter("cid", cid).setParameter("ccd", ccd).getSingleOrNull();
    }

    /**
     * Run algorithm copy
     * Event：個人情報定義の初期値コピー
     *
     * @param sourceCid
     * @param targetCid
     * @param isReplace
     */
    public void copyMasterData(String sourceCid, String targetCid, boolean isReplace) {
        //Get data company zero
        List<PpemtPerInfoCtg> sPerInfoCtgEntities = findAllPerInfoCtgByCid(sourceCid);
        List<PpemtPerInfoCtgOrder> sPerInfoCtgOrderEntities = new ArrayList<>();
        List<PpemtPerInfoItem> sPerInfoItemEntities = new ArrayList<>();
        List<PpemtPerInfoItemOrder> sPerInfoItemOrderEntities = new ArrayList<>();
        List<PpemtDateRangeItem> sDateRangeItemEntities = new ArrayList<>();
        Set<String> sourcePersonalInfoCatId = new TreeSet<String>();
		if (!sPerInfoCtgEntities.isEmpty()) {
			sourcePersonalInfoCatId = sPerInfoCtgEntities.stream()
					.map(ppemtPerInfoCtg -> ppemtPerInfoCtg.ppemtPerInfoCtgPK.perInfoCtgId).collect(Collectors.toSet());
			sPerInfoCtgOrderEntities = findAllPerInfoCtgOrderByCid(sourceCid, sourcePersonalInfoCatId);
	        sPerInfoItemEntities = findAllPpemtPerInfoItemByCatId(sourcePersonalInfoCatId);
	        sPerInfoItemOrderEntities = findAllPerInfoItemOrderByCatId(sourcePersonalInfoCatId);
	        sDateRangeItemEntities = findAlldateRangeItemByCatId(sourcePersonalInfoCatId);
		}

        //Get data company target
        List<PpemtPerInfoCtg> tPerInfoCtgEntities = findAllPerInfoCtgByCid(targetCid);
        List<PpemtPerInfoCtgOrder> tPerInfoCtgOrderEntities = new ArrayList<>();
        List<PpemtPerInfoItem> tPerInfoItemEntities = new ArrayList<>();
        List<PpemtPerInfoItemOrder> tPerInfoItemOrderEntities = new ArrayList<>();
        List<PpemtDateRangeItem> tPateRangeItemEntities = new ArrayList<>();
        if(!tPerInfoCtgEntities.isEmpty()) {
			Set<String> targetPersonalInfoCatId = tPerInfoCtgEntities.stream()
					.map(ppemtPerInfoCtg -> ppemtPerInfoCtg.ppemtPerInfoCtgPK.perInfoCtgId).collect(Collectors.toSet());
			tPerInfoCtgOrderEntities = findAllPerInfoCtgOrderByCid(targetCid,
					targetPersonalInfoCatId);
			tPerInfoItemEntities = findAllPpemtPerInfoItemByCatId(targetPersonalInfoCatId);
			tPerInfoItemOrderEntities = findAllPerInfoItemOrderByCatId(
					targetPersonalInfoCatId);
			tPateRangeItemEntities = findAlldateRangeItemByCatId(targetPersonalInfoCatId);
		}

        //group by personal info item def Id
        final List<PpemtPerInfoCtg> s1 = new ArrayList<>();
        final List<PpemtPerInfoCtgOrder> s2 = new ArrayList<>();
        final List<PpemtPerInfoItem> s3 = new ArrayList<>();
        final List<PpemtPerInfoItemOrder> s4 = new ArrayList<>();
        final List<PpemtDateRangeItem> s5 = new ArrayList<>();

        //取得できなかった場合（会社ID　＝　Input．会社IDの個人情報定義）//ko lấy được
        if (CollectionUtil.isEmpty(tPerInfoCtgEntities) && CollectionUtil.isEmpty(tPerInfoCtgOrderEntities) &&
                CollectionUtil.isEmpty(tPerInfoItemEntities) && CollectionUtil.isEmpty(tPerInfoItemOrderEntities) &&
                CollectionUtil.isEmpty(tPateRangeItemEntities)) {
            //group by personal info category Id
            Map<String, List<PpemtPerInfoCtg>> groupPersonalInfoCatByCatId = new HashMap<>();
            Map<String, List<PpemtPerInfoCtgOrder>> groupPersonalInfoCatOrderByCatId = new HashMap<>();
            Map<String, List<PpemtPerInfoItem>> groupPersonalInfoItemByCatId = new HashMap<>();
            Map<String, List<PpemtPerInfoItem>> groupPersonalInfoItemByDefId = new HashMap<>();
            Map<String, List<PpemtPerInfoItemOrder>> groupPersonalInfoItemOrderByCatId = new HashMap<>();
            Map<String, List<PpemtPerInfoItemOrder>> groupPersonalInfoItemOrderByDefId = new HashMap<>();
            Map<String, List<PpemtDateRangeItem>> groupDateRangeByCatId = new HashMap<>();
            if (!CollectionUtil.isEmpty(sPerInfoCtgEntities)) {
                groupPersonalInfoCatByCatId = sPerInfoCtgEntities.stream().collect(Collectors.groupingBy(o -> o.ppemtPerInfoCtgPK.perInfoCtgId));
            }
            if (!CollectionUtil.isEmpty(sPerInfoCtgOrderEntities)) {
                groupPersonalInfoCatOrderByCatId = sPerInfoCtgOrderEntities.stream().collect(Collectors.groupingBy(o -> o.ppemtPerInfoCtgPK.perInfoCtgId));
            }
            if (!CollectionUtil.isEmpty(sPerInfoItemEntities)) {
                groupPersonalInfoItemByCatId = sPerInfoItemEntities.stream().collect(Collectors.groupingBy(o -> o.perInfoCtgId));
                groupPersonalInfoItemByDefId = sPerInfoItemEntities.stream().collect(Collectors.groupingBy(o -> o.ppemtPerInfoItemPK.perInfoItemDefId));
            }
            if (!CollectionUtil.isEmpty(sPerInfoItemOrderEntities)) {
                groupPersonalInfoItemOrderByCatId = sPerInfoItemOrderEntities.stream().collect(Collectors.groupingBy(o -> o.perInfoCtgId));
                groupPersonalInfoItemOrderByDefId = sPerInfoItemOrderEntities.stream().collect(Collectors.groupingBy(o -> o.ppemtPerInfoItemPK.perInfoItemDefId));
            }
            if (!CollectionUtil.isEmpty(sDateRangeItemEntities)) {
                groupDateRangeByCatId = sDateRangeItemEntities.stream().collect(Collectors.groupingBy(o -> o.ppemtPerInfoCtgPK.perInfoCtgId));
            }

            //create
            for (String catId : sourcePersonalInfoCatId) {
                String newCatId = UUID.randomUUID().toString();
                for (PpemtPerInfoCtg perInfoCtgEntity : groupPersonalInfoCatByCatId.get(catId)) {
                    PpemtPerInfoCtg cloneObject = SerializationUtils.clone(perInfoCtgEntity);
                    cloneObject.ppemtPerInfoCtgPK.perInfoCtgId = newCatId;
                    cloneObject.cid = targetCid;
                    s1.add(cloneObject);
                }

                if (!CollectionUtil.isEmpty(groupPersonalInfoCatOrderByCatId.get(catId))) {
                    for (PpemtPerInfoCtgOrder perInfoCtgOrderEntity : groupPersonalInfoCatOrderByCatId.get(catId)) {
                        PpemtPerInfoCtgOrder cloneObject = SerializationUtils.clone(perInfoCtgOrderEntity);
                        cloneObject.cid = targetCid;
                        cloneObject.ppemtPerInfoCtgPK.perInfoCtgId = newCatId;
                        s2.add(cloneObject);
                    }
                }

                if (!CollectionUtil.isEmpty(groupPersonalInfoItemByCatId.get(catId))) {
                    for (PpemtPerInfoItem perInfoItemEntity : groupPersonalInfoItemByCatId.get(catId)) {
                        PpemtPerInfoItem cloneObject = SerializationUtils.clone(perInfoItemEntity);
                        cloneObject.perInfoCtgId = newCatId;
                        s3.add(cloneObject);
                    }
                }

                if (!CollectionUtil.isEmpty(groupPersonalInfoItemOrderByCatId.get(catId))) {
                    for (PpemtPerInfoItemOrder perInfoItemOrderEntity : groupPersonalInfoItemOrderByCatId.get(catId)) {
                        PpemtPerInfoItemOrder cloneObject = SerializationUtils.clone(perInfoItemOrderEntity);
                        cloneObject.perInfoCtgId = newCatId;
                        s4.add(cloneObject);
                    }
                }

                if (!CollectionUtil.isEmpty(groupDateRangeByCatId.get(catId))) {
                    for (PpemtDateRangeItem dateRangeItem : groupDateRangeByCatId.get(catId)) {
                        PpemtDateRangeItem cloneObject = SerializationUtils.clone(dateRangeItem);
                        cloneObject.ppemtPerInfoCtgPK.perInfoCtgId = newCatId;
                        s5.add(cloneObject);
                    }
                }
            }

            s3.clear();
            s4.clear();
            Set<String> sourcePersonalInfoItemDefId = s3.stream()
                    .map(ppemtPerInfoItem -> ppemtPerInfoItem.ppemtPerInfoItemPK.perInfoItemDefId)
                    .collect(Collectors.toSet());
            for (String defId : sourcePersonalInfoItemDefId) {
                String newDefId = UUID.randomUUID().toString();
                if (!CollectionUtil.isEmpty(groupPersonalInfoItemByDefId.get(defId))) {
                    for (PpemtPerInfoItem perInfoItemEntity : groupPersonalInfoItemByDefId.get(defId)) {
                        PpemtPerInfoItem cloneObject = SerializationUtils.clone(perInfoItemEntity);
                        cloneObject.ppemtPerInfoItemPK.perInfoItemDefId = newDefId;
                        s3.add(cloneObject);
                    }
                }

                if (!CollectionUtil.isEmpty(groupPersonalInfoItemOrderByDefId.get(defId))) {
                    for (PpemtPerInfoItemOrder perInfoItemOrderEntity : groupPersonalInfoItemOrderByDefId.get(defId)) {
                        PpemtPerInfoItemOrder cloneObject = SerializationUtils.clone(perInfoItemOrderEntity);
                        cloneObject.ppemtPerInfoItemPK.perInfoItemDefId = newDefId;
                        s4.add(cloneObject);
                    }
                }
            }

            // insert new
            this.commandProxy.insertAll(s1);
            this.commandProxy.insertAll(s2);
            this.commandProxy.insertAll(s3);
            this.commandProxy.insertAll(s4);
            this.commandProxy.insertAll(s5);

        } else {//取得できた場合（会社ID　＝　Input．会社IDの個人情報定義）//Lấy được
            //group by personal info category Id
            Map<String, PpemtPerInfoCtg> sgroupPersonalInfoCatByCatCd = new HashMap<>();
            if (!CollectionUtil.isEmpty(sPerInfoCtgEntities)) {
                sgroupPersonalInfoCatByCatCd = sPerInfoCtgEntities.stream()
                        .collect(Collectors.toMap(perInfoCtg -> perInfoCtg.categoryCd, perInfoCtg -> perInfoCtg));
            }
            Map<String, PpemtPerInfoCtg> tgroupPersonalInfoCatByCatCd = new HashMap<>();
            if (!CollectionUtil.isEmpty(sPerInfoCtgEntities)) {
                tgroupPersonalInfoCatByCatCd = tPerInfoCtgEntities.stream()
                        .collect(Collectors.toMap(perInfoCtg -> perInfoCtg.categoryCd, perInfoCtg -> perInfoCtg));
            }

            Set<String> sourcePersonalInfoCatCd = sPerInfoCtgEntities.stream()
                    .map(ppemtPerInfoCtg -> ppemtPerInfoCtg.categoryCd)
                    .collect(Collectors.toSet());
            //overwrite
            for (String catCd : sourcePersonalInfoCatCd) {
                String perInfoCtgId = UUID.randomUUID().toString();
                //1 update overwrite for PpemtPerInfoCtg
                PpemtPerInfoCtg src = sgroupPersonalInfoCatByCatCd.get(catCd);
                PpemtPerInfoCtg des = tgroupPersonalInfoCatByCatCd.get(catCd);
                if (src == null) break;
                boolean fl1 = false;
                if (des == null) {
                    des = new PpemtPerInfoCtg();
                    des.ppemtPerInfoCtgPK = new PpemtPerInfoCtgPK();
                    des.ppemtPerInfoCtgPK.perInfoCtgId = perInfoCtgId;
                    fl1 = true;
                }
                des.categoryName = src.categoryName;
                des.abolitionAtr = src.abolitionAtr;
                if (fl1)
                    this.commandProxy.update(des);
                else
                    this.commandProxy.insert(des);

                //2 update overwrite for PpemtPerInfoItem
//                LOGGER.info("Test Event CMM001: " + sourceCid + "-" + catCd);
                Map<String, PpemtPerInfoItem> sourcePerInfoItems = findAllPpemtPerInfoItemByCidAndCcd(sourceCid, catCd)
                        .stream().collect(Collectors.toMap(o -> o.itemCd, o -> o));
                Map<String, PpemtPerInfoItem> destPerInfoItems = findAllPpemtPerInfoItemByCidAndCcd(targetCid, catCd)
                        .stream().collect(Collectors.toMap(o -> o.itemCd, o -> o));

                for (String itemCd : sourcePerInfoItems.keySet()) {
                    PpemtPerInfoItem srcPerInfoItem = sourcePerInfoItems.get(itemCd);
                    PpemtPerInfoItem desPerInfoItem = destPerInfoItems.get(itemCd);
                    if (srcPerInfoItem == null) break;
                    boolean fl2 = false;
                    if (desPerInfoItem == null) {
                        desPerInfoItem = new PpemtPerInfoItem();
                        desPerInfoItem.ppemtPerInfoItemPK = new PpemtPerInfoItemPK();
                        desPerInfoItem.ppemtPerInfoItemPK.perInfoItemDefId = UUID.randomUUID().toString();
                        desPerInfoItem.perInfoCtgId = perInfoCtgId;
                        fl2 = true;
                    }
                    desPerInfoItem.itemCd = itemCd;
                    desPerInfoItem.itemName = srcPerInfoItem.itemName;
                    desPerInfoItem.abolitionAtr = srcPerInfoItem.abolitionAtr;
                    desPerInfoItem.requiredAtr = srcPerInfoItem.requiredAtr;
                    if (fl2)
                        this.commandProxy.insert(desPerInfoItem);
                    else
                        this.commandProxy.update(desPerInfoItem);
                }

                //3
                PpemtDateRangeItem sourceDateRangeItem = findAlldateRangeItemByCidAndCcd(sourceCid, catCd);
                PpemtDateRangeItem destDateRangeItem = findAlldateRangeItemByCidAndCcd(targetCid, catCd);
                if (sourceDateRangeItem == null) break;
                boolean fl3 = false;
                if (destDateRangeItem == null) {
                    destDateRangeItem = new PpemtDateRangeItem();
                    destDateRangeItem.ppemtPerInfoCtgPK = new PpemtPerInfoCtgPK();
                    destDateRangeItem.ppemtPerInfoCtgPK.perInfoCtgId = perInfoCtgId;
                    fl3 = true;
                }
                destDateRangeItem.startDateItemId = sourceDateRangeItem.startDateItemId;
                destDateRangeItem.endDateItemId = sourceDateRangeItem.endDateItemId;
                destDateRangeItem.dateRangeItemId = sourceDateRangeItem.dateRangeItemId;
                if (fl3)
                    this.commandProxy.insert(destDateRangeItem);
                else
                    this.commandProxy.update(destDateRangeItem);
            }

        }
    }
}
