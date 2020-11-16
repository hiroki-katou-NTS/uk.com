package nts.uk.ctx.at.function.infra.repository.alarmworkplace.checkcondition;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.function.dom.alarmworkplace.checkcondition.AlarmCheckCdtWkpCtgRepository;
import nts.uk.ctx.at.function.dom.alarmworkplace.checkcondition.AlarmCheckCdtWorkplaceCategory;
import nts.uk.ctx.at.function.dom.alarmworkplace.checkcondition.ExtractionCondition;
import nts.uk.ctx.at.function.dom.alarmworkplace.checkcondition.WorkplaceCategory;
import nts.uk.ctx.at.function.dom.alarmworkplace.checkcondition.applicationapproval.AlarmAppApprovalCheckCdt;
import nts.uk.ctx.at.function.dom.alarmworkplace.checkcondition.basic.AlarmMasterBasicCheckCdt;
import nts.uk.ctx.at.function.dom.alarmworkplace.checkcondition.daily.AlarmMasterDailyCheckCdt;
import nts.uk.ctx.at.function.dom.alarmworkplace.checkcondition.schedule.AlarmScheduleCheckCdt;
import nts.uk.ctx.at.function.dom.alarmworkplace.checkcondition.workplace.AlarmMasterWkpCheckCdt;
import nts.uk.ctx.at.function.infra.entity.alarmworkplace.checkcondition.KfnmtCatMapEachType;
import nts.uk.ctx.at.function.infra.entity.alarmworkplace.checkcondition.KfnmtWkpAlstchkConcat;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import java.util.*;
import java.util.stream.Collectors;

@Stateless
public class JpaAlarmWkpAlstchkConcatRepository extends JpaRepository implements AlarmCheckCdtWkpCtgRepository {

    // Get Main Table
    private static final String GET_ALL = "select f from KfnmtWkpAlstchkConcat f";
    private static final String GET_BY_ID = GET_ALL + " where f.pk.companyID = :cid and f.pk.category = :ctg and f.pk.categoryItemCD = :ctgCD";
    private static final String GET_BY_CATEGORY_ID = GET_ALL + " where f.pk.companyID = :cid and f.pk.category = :ctg";

    // Get Sub Table
    private static final String GET_ALL_SUB = "select f from KfnmtCatMapEachType f";
    private static final String GET_SUB_BY_CTG = GET_ALL_SUB + " where f.pk.companyID = :cid and f.pk.category = :ctg";

    @Override
    public Optional<AlarmCheckCdtWorkplaceCategory> getByID(String id, int category, String categoryItemCD) {
        Optional<KfnmtWkpAlstchkConcat> entity = this.queryProxy().query(GET_BY_ID, KfnmtWkpAlstchkConcat.class)
                .setParameter("cid", id)
                .setParameter("ctg", category)
                .setParameter("ctgCD", categoryItemCD)
                .getSingle();
        return entity.map(KfnmtWkpAlstchkConcat::toDomain);
    }

    @Override
    public List<AlarmCheckCdtWorkplaceCategory> getByCategoryID(int categoryID) {
        List<KfnmtWkpAlstchkConcat> entityMain = this.queryProxy().query(GET_BY_CATEGORY_ID, KfnmtWkpAlstchkConcat.class)
                .setParameter("cid", AppContexts.user().companyId())
                .setParameter("ctgID", categoryID)
                .getList();
        List<KfnmtCatMapEachType> entitySub = this.queryProxy().query(GET_SUB_BY_CTG, KfnmtCatMapEachType.class)
                .setParameter("cid", AppContexts.user().companyId())
                .setParameter("ctgID", categoryID)
                .getList();
        return this.toDomain(entityMain, entitySub);
    }

    private List<AlarmCheckCdtWorkplaceCategory> toDomain(List<KfnmtWkpAlstchkConcat> entityMain, List<KfnmtCatMapEachType> entitySub) {
        Map<String, List<KfnmtCatMapEachType>> mapEntitySub = entitySub.stream().collect(Collectors.groupingBy(KfnmtCatMapEachType::getCategoryItemCD));
        List<String> errAlarmCheckIDs = new ArrayList<>();

        return entityMain.stream().map(i -> {
            WorkplaceCategory ctg = EnumAdaptor.valueOf(i.getPk().category, WorkplaceCategory.class);
            ExtractionCondition condition = null;
            List<KfnmtCatMapEachType> subItems = mapEntitySub.get(i.getPk().categoryItemCD);
            List<String> extractionIDs = subItems.stream().filter(x -> x.getPk().fixedOp)
                    .map(x -> x.getPk().wkAlarmCheckId).collect(Collectors.toList());
            List<String> optionalIDs = subItems.stream().filter(x -> !x.getPk().fixedOp)
                    .map(x -> x.getPk().wkAlarmCheckId).collect(Collectors.toList());

            switch (ctg) {
                case MASTER_CHECK_BASIC: {
                    condition = new AlarmMasterBasicCheckCdt(extractionIDs);
                }
                case MASTER_CHECK_WORKPLACE: {
                    condition = new AlarmMasterWkpCheckCdt(extractionIDs);
                }
                case MASTER_CHECK_DAILY: {
                    condition = new AlarmMasterDailyCheckCdt(extractionIDs);
                }
                case SCHEDULE_DAILY: {
                    condition = new AlarmScheduleCheckCdt(extractionIDs, optionalIDs);
                }
                case MONTHLY: {
                    condition = new AlarmScheduleCheckCdt(extractionIDs, optionalIDs);
                }
                case APPLICATION_APPROVAL: {
                    condition = new AlarmAppApprovalCheckCdt(extractionIDs);
                }
            }

            return new AlarmCheckCdtWorkplaceCategory(
                    i.getPk().category,
                    i.getPk().categoryItemCD,
                    i.getAlarmCdtName(),
                    condition
            );
        }).collect(Collectors.toList());

    }

    @Override
    public void register(AlarmCheckCdtWorkplaceCategory domain) {
        this.commandProxy().insert(KfnmtWkpAlstchkConcat.toEntity(domain));
    }

    @Override
    public void update(AlarmCheckCdtWorkplaceCategory domain) {

    }

    @Override
    public void delete(String id) {
        this.commandProxy().remove(KfnmtWkpAlstchkConcat.class, id);
    }
}
