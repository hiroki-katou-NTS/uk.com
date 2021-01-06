package nts.uk.ctx.at.record.infra.repository.workrecord.erroralarm.alarmlist.monthly;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.ExtractionMonthlyCon;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.ExtractionMonthlyConRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.primitivevalue.AverageNumberDays;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.primitivevalue.AverageNumberTimes;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.primitivevalue.AverageRatio;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.primitivevalue.AverageTime;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.CountableTarget;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.alarmlistworkplace.monthly.KrcmtWkpMonExtracCon;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.attendanceitem.*;
import nts.uk.ctx.at.shared.dom.workrecord.alarm.attendanceitemconditions.CompareRange;
import nts.uk.ctx.at.shared.dom.workrecord.alarm.attendanceitemconditions.CompareSingleValue;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaExtractionMonthlyConRepository extends JpaRepository implements ExtractionMonthlyConRepository {

    private static final String SELECT = "SELECT a FROM KrcmtWkpMonExtracCon a ";
    private static final String FIND_BY_IDS_AND_USEATR;
    private static final String GET_BY_IDS;

    private static final String SELECT_COMPARE_SINGLE = " SELECT a FROM KrcstErAlCompareSingle a " +
            " JOIN KrcmtWkpMonExtracCon b ON a.krcstEralCompareSinglePK.conditionGroupId = b.errorAlarmCheckID ";
    private static final String SELECT_COMPARE_SINGLE_BY_ID;

    private static final String SELECT_COMPARE_RANGE = " SELECT a FROM KrcstErAlCompareRange a " +
            " JOIN KrcmtWkpMonExtracCon b ON a.krcstEralCompareRangePK.conditionGroupId = b.errorAlarmCheckID ";
    private static final String SELECT_COMPARE_RANGE_BY_ID;

    private static final String SELECT_SINGLE_FIXED = " SELECT a FROM KrcstErAlSingleFixed a " +
            " JOIN KrcmtWkpMonExtracCon b ON a.krcstEralSingleFixedPK.conditionGroupId = b.errorAlarmCheckID ";
    private static final String SELECT_SINGLE_FIXED_BY_ID;

    private static final String SELECT_TARGET = " SELECT a FROM KrcstErAlAtdTarget a " +
            " JOIN KrcmtWkpMonExtracCon b ON a.krcstErAlAtdTargetPK.conditionGroupId = b.errorAlarmCheckID ";
    private static final String SELECT_TARGET_BY_ID;


    private static final String DELETE_TARGET = " DELETE FROM KrcstErAlAtdTarget a " +
            " WHERE a.krcstErAlAtdTargetPK.conditionGroupId in :ids ";

    static {
        StringBuilder builderString = new StringBuilder();
        builderString.append(SELECT);
        builderString.append(" WHERE a.errorAlarmWorkplaceId in :ids AND a.useAtr = :useAtr ");
        FIND_BY_IDS_AND_USEATR = builderString.toString();

        builderString = new StringBuilder();
        builderString.append(SELECT);
        builderString.append(" WHERE a.errorAlarmWorkplaceId in :ids ");
        GET_BY_IDS = builderString.toString();

        builderString = new StringBuilder();
        builderString.append(SELECT_COMPARE_SINGLE);
        builderString.append(" WHERE a.krcstEralCompareSinglePK.atdItemConNo = 0 AND a.conditionType = 0 ");
        builderString.append(" AND b.errorAlarmWorkplaceId IN :ids ");
        SELECT_COMPARE_SINGLE_BY_ID = builderString.toString();

        builderString = new StringBuilder();
        builderString.append(SELECT_COMPARE_RANGE);
        builderString.append(" WHERE a.krcstEralCompareRangePK.atdItemConNo = 0 ");
        builderString.append(" AND b.errorAlarmWorkplaceId IN :ids ");
        SELECT_COMPARE_RANGE_BY_ID = builderString.toString();

        builderString = new StringBuilder();
        builderString.append(SELECT_SINGLE_FIXED);
        builderString.append(" WHERE a.krcstEralSingleFixedPK.atdItemConNo = 0 ");
        builderString.append(" AND b.errorAlarmWorkplaceId IN :ids ");
        SELECT_SINGLE_FIXED_BY_ID = builderString.toString();

        builderString = new StringBuilder();
        builderString.append(SELECT_TARGET);
        builderString.append(" WHERE b.errorAlarmWorkplaceId IN :ids ");
        SELECT_TARGET_BY_ID = builderString.toString();
    }

    @Override
    public List<ExtractionMonthlyCon> getBy(List<String> ids, boolean useAtr) {
        if (CollectionUtil.isEmpty(ids)) return new ArrayList<>();

        List<KrcstErAlCompareSingle> krcstErAlCompareSingle = this.queryProxy().query(SELECT_COMPARE_SINGLE_BY_ID, KrcstErAlCompareSingle.class).setParameter("ids", ids).getList();
        List<KrcstErAlCompareRange> krcstErAlCompareRange = this.queryProxy().query(SELECT_COMPARE_RANGE_BY_ID, KrcstErAlCompareRange.class).setParameter("ids", ids).getList();
        List<KrcstErAlSingleFixed> krcstErAlSingleFixed = this.queryProxy().query(SELECT_SINGLE_FIXED_BY_ID, KrcstErAlSingleFixed.class).setParameter("ids", ids).getList();
        List<KrcmtWkpMonExtracCon> domains = this.queryProxy().query(FIND_BY_IDS_AND_USEATR, KrcmtWkpMonExtracCon.class)
                .setParameter("ids", ids)
                .setParameter("useAtr", useAtr)
                .getList();

        List<KrcstErAlAtdTarget> krcstErAlAtdTarget = this.queryProxy().query(SELECT_TARGET_BY_ID, KrcstErAlAtdTarget.class).setParameter("ids", ids).getList();

        return domains.stream().map(i -> i.toDomain(
                krcstErAlCompareSingle.stream().filter(x -> x.krcstEralCompareSinglePK.conditionGroupId.equals(i.errorAlarmCheckID)).findFirst(),
                krcstErAlCompareRange.stream().filter(x -> x.krcstEralCompareRangePK.conditionGroupId.equals(i.errorAlarmCheckID)).findFirst(),
                krcstErAlSingleFixed.stream().filter(x -> x.krcstEralSingleFixedPK.conditionGroupId.equals(i.errorAlarmCheckID)).findFirst(),
                krcstErAlAtdTarget.stream().filter(x -> x.krcstErAlAtdTargetPK.conditionGroupId.equals(i.errorAlarmCheckID)).collect(Collectors.toList())
        )).collect(Collectors.toList());
    }


    @Override
    public List<ExtractionMonthlyCon> getByIds(List<String> ids) {
        if (ids.isEmpty()) {
            return Collections.emptyList();
        }

        List<KrcstErAlCompareSingle> krcstErAlCompareSingle = this.queryProxy().query(SELECT_COMPARE_SINGLE_BY_ID, KrcstErAlCompareSingle.class).setParameter("ids", ids).getList();
        List<KrcstErAlCompareRange> krcstErAlCompareRange = this.queryProxy().query(SELECT_COMPARE_RANGE_BY_ID, KrcstErAlCompareRange.class).setParameter("ids", ids).getList();
        List<KrcstErAlSingleFixed> krcstErAlSingleFixed = this.queryProxy().query(SELECT_SINGLE_FIXED_BY_ID, KrcstErAlSingleFixed.class).setParameter("ids", ids).getList();
        List<KrcmtWkpMonExtracCon> domains = this.queryProxy().query(GET_BY_IDS, KrcmtWkpMonExtracCon.class)
                .setParameter("ids", ids)
                .getList();
        List<KrcstErAlAtdTarget> krcstErAlAtdTarget = this.queryProxy().query(SELECT_TARGET_BY_ID, KrcstErAlAtdTarget.class).setParameter("ids", ids).getList();

        return domains.stream().map(i -> i.toDomain(
                krcstErAlCompareSingle.stream().filter(x -> x.krcstEralCompareSinglePK.conditionGroupId.equals(i.errorAlarmCheckID)).findFirst(),
                krcstErAlCompareRange.stream().filter(x -> x.krcstEralCompareRangePK.conditionGroupId.equals(i.errorAlarmCheckID)).findFirst(),
                krcstErAlSingleFixed.stream().filter(x -> x.krcstEralSingleFixedPK.conditionGroupId.equals(i.errorAlarmCheckID)).findFirst(),
                krcstErAlAtdTarget.stream().filter(x -> x.krcstErAlAtdTargetPK.conditionGroupId.equals(i.errorAlarmCheckID)).collect(Collectors.toList())
        )).collect(Collectors.toList());
    }

    @Override
    public void register(List<ExtractionMonthlyCon> domains) {
        List<KrcmtWkpMonExtracCon> entities = domains.stream().map(KrcmtWkpMonExtracCon::fromDomain).collect(Collectors.toList());
        List<KrcstErAlCompareSingle> compareSingles = new ArrayList<>();
        List<KrcstErAlSingleFixed> singleFixeds = new ArrayList<>();
        List<KrcstErAlCompareRange> compareRanges = new ArrayList<>();
        List<KrcstErAlAtdTarget> targetList = new ArrayList<>();

        System.out.println(domains);

        domains.forEach((ExtractionMonthlyCon i) -> {
            Double minValue = null;
            Double maxValue = null;
            if (i.getCheckConditions().isSingleValue()) {
                switch (i.getCheckMonthlyItemsType()) {
                    case AVERAGE_TIME:{
                        minValue = Double.valueOf(((AverageTime) ((CompareSingleValue) i.getCheckConditions()).getValue()).v());
                        break;
                    }
                    case AVERAGE_NUMBER_DAY:{
                        minValue = ((AverageNumberDays) ((CompareSingleValue) i.getCheckConditions()).getValue()).v().doubleValue();
                        break;
                    }
                    case AVERAGE_NUMBER_TIME:{
                        minValue = Double.valueOf(((AverageNumberTimes) ((CompareSingleValue) i.getCheckConditions()).getValue()).v());
                        break;
                    }
                    case AVERAGE_RATIO:{
                        minValue = Double.valueOf(((AverageRatio) ((CompareSingleValue) i.getCheckConditions()).getValue()).v());
                        break;
                    }
                }
                KrcstErAlCompareSingle single = new KrcstErAlCompareSingle(
                        new KrcstErAlCompareSinglePK(i.getErrorAlarmCheckID(), 0),
                        ((CompareSingleValue) i.getCheckConditions()).getCompareOpertor().value,
                        ((CompareSingleValue) i.getCheckConditions()).getConditionType().value
                );
                KrcstErAlSingleFixed singleFixed = new KrcstErAlSingleFixed(
                        new KrcstErAlSingleFixedPK(i.getErrorAlarmCheckID(), 0),
                        minValue
                );
                compareSingles.add(single);
                singleFixeds.add(singleFixed);
            } else {
                switch (i.getCheckMonthlyItemsType()) {
                    case AVERAGE_TIME: {
                        minValue = Double.valueOf(((AverageTime) ((CompareRange) i.getCheckConditions()).getStartValue()).v());
                        maxValue = Double.valueOf(((AverageTime) ((CompareRange) i.getCheckConditions()).getEndValue()).v());
                        break;
                    }
                    case AVERAGE_NUMBER_DAY:{
                        minValue = ((AverageNumberDays) ((CompareRange) i.getCheckConditions()).getStartValue()).v().doubleValue();
                        maxValue = ((AverageNumberDays) ((CompareRange) i.getCheckConditions()).getEndValue()).v().doubleValue();
                        break;
                    }
                    case AVERAGE_NUMBER_TIME:{
                        minValue = Double.valueOf(((AverageNumberTimes) ((CompareRange) i.getCheckConditions()).getStartValue()).v());
                        maxValue = Double.valueOf(((AverageNumberTimes) ((CompareRange) i.getCheckConditions()).getEndValue()).v());
                        break;
                    }
                    case AVERAGE_RATIO:{
                        minValue = Double.valueOf(((AverageRatio) ((CompareRange) i.getCheckConditions()).getStartValue()).v());
                        maxValue = Double.valueOf(((AverageRatio) ((CompareRange) i.getCheckConditions()).getEndValue()).v());
                        break;
                    }
                }
                KrcstErAlCompareRange range = new KrcstErAlCompareRange(
                        new KrcstErAlCompareRangePK(i.getErrorAlarmCheckID(), 0),
                        ((CompareRange) i.getCheckConditions()).getCompareOperator().value,
                        minValue,
                        maxValue
                );
                compareRanges.add(range);
            }

            // Insert target
            if (i.getCheckedTarget().isPresent()){
                val value =  (CountableTarget) i.getCheckedTarget().get();
                List<Integer> listImtem = new ArrayList<Integer>();
                listImtem = value.getAddSubAttendanceItems().getAdditionAttendanceItems();
                List<Integer> listSub = new ArrayList<>();
                listSub = value.getAddSubAttendanceItems().getSubstractionAttendanceItems();
                listImtem.forEach(item -> {
                    val key = new KrcstErAlAtdTargetPK(i.getErrorAlarmCheckID(),0,item.intValue());
                    targetList.add(new KrcstErAlAtdTarget(key,0));
                });
                listSub.forEach(item -> {
                    val key = new KrcstErAlAtdTargetPK(i.getErrorAlarmCheckID(),0,item.intValue());
                    targetList.add(new KrcstErAlAtdTarget(key,1));
                });
            }

        });
        if (!CollectionUtil.isEmpty(compareSingles) && !CollectionUtil.isEmpty(singleFixeds)) {
            this.commandProxy().insertAll(compareSingles);
            this.commandProxy().insertAll(singleFixeds);
        }
        if (!CollectionUtil.isEmpty(compareRanges)) {
            this.commandProxy().insertAll(compareRanges);
        }
        if (!CollectionUtil.isEmpty(targetList)) {
            this.commandProxy().insertAll(targetList);
        }
        this.commandProxy().insertAll(entities);

    }

    @Override
    public void delete(List<String> ids) {
        List<ExtractionMonthlyCon> domain = this.getByIds(ids);
        List<String> compareIds = domain.stream().map(ExtractionMonthlyCon::getErrorAlarmCheckID).collect(Collectors.toList());
        if (CollectionUtil.isEmpty(compareIds)){
            return;
        }
        this.commandProxy().removeAll(KrcstErAlCompareSingle.class, compareIds.stream().map(i -> new KrcstErAlCompareSinglePK(i, 0)).collect(Collectors.toList()));
        this.commandProxy().removeAll(KrcstErAlSingleFixed.class, compareIds.stream().map(i -> new KrcstErAlSingleFixedPK(i, 0)).collect(Collectors.toList()));
        this.commandProxy().removeAll(KrcstErAlCompareRange.class, compareIds.stream().map(i -> new KrcstErAlCompareRangePK(i, 0)).collect(Collectors.toList()));

        this.getEntityManager().createQuery(DELETE_TARGET)
                .setParameter("ids", compareIds)
                .executeUpdate();

        this.commandProxy().removeAll(KrcmtWkpMonExtracCon.class, ids);
    }
}
