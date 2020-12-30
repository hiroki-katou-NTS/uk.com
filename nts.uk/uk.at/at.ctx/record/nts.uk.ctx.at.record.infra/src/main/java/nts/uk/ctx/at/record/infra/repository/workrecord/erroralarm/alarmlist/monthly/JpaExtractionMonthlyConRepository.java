package nts.uk.ctx.at.record.infra.repository.workrecord.erroralarm.alarmlist.monthly;

import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.ExtractionMonthlyCon;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.ExtractionMonthlyConRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.primitivevalue.AverageNumberDays;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.primitivevalue.AverageNumberTimes;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.primitivevalue.AverageRatio;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.primitivevalue.AverageTime;
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

    private static final String SELECT;

    private static final String FIND_BY_IDS_AND_USEATR;

    private static final String GET_BY_IDS;

    private static final String SELECT_COMPARE_SINGLE =
        " SELECT a FROM KrcstErAlCompareSingle a " +
            " JOIN KrcmtWkpMonExtracCon b ON a.krcstEralCompareSinglePK.conditionGroupId = b.errorAlarmCheckID " +
            " WHERE a.krcstEralCompareSinglePK.atdItemConNo = 0 AND a.conditionType = 0 ";

    private static final String SELECT_COMPARE_RANGE =
        " SELECT a FROM KrcstErAlCompareRange a " +
            " JOIN KrcmtWkpMonExtracCon b ON a.krcstEralCompareRangePK.conditionGroupId = b.errorAlarmCheckID " +
            " WHERE a.krcstEralCompareRangePK.atdItemConNo = 0 ";

    private static final String SELECT_SINGLE_FIXED =
        " SELECT a FROM KrcstErAlSingleFixed a " +
            " JOIN KrcmtWkpMonExtracCon b ON a.krcstEralSingleFixedPK.conditionGroupId = b.errorAlarmCheckID " +
            " WHERE a.krcstEralSingleFixedPK.atdItemConNo = 0 ";


    static {
        StringBuilder builderString = new StringBuilder();
        builderString.append(" SELECT a FROM KrcmtWkpMonExtracCon a ");
        SELECT = builderString.toString();

        builderString = new StringBuilder();
        builderString.append(SELECT);
        builderString.append(" WHERE a.errorAlarmWorkplaceId in :ids AND a.useAtr = :useAtr ");
        FIND_BY_IDS_AND_USEATR = builderString.toString();

        builderString = new StringBuilder();
        builderString.append(SELECT);
        builderString.append(" WHERE a.errorAlarmWorkplaceId in :ids ");
        GET_BY_IDS = builderString.toString();

    }

    @Override
    public List<ExtractionMonthlyCon> getBy(List<String> ids, boolean useAtr) {
        if (CollectionUtil.isEmpty(ids)) return new ArrayList<>();

        Optional<KrcstErAlCompareSingle> krcstErAlCompareSingle = this.queryProxy().query(SELECT_COMPARE_SINGLE, KrcstErAlCompareSingle.class).getSingle();
        Optional<KrcstErAlCompareRange> krcstErAlCompareRange = this.queryProxy().query(SELECT_COMPARE_RANGE, KrcstErAlCompareRange.class).getSingle();
        Optional<KrcstErAlSingleFixed> krcstErAlSingleFixed = this.queryProxy().query(SELECT_SINGLE_FIXED, KrcstErAlSingleFixed.class).getSingle();

        return this.queryProxy().query(FIND_BY_IDS_AND_USEATR, KrcmtWkpMonExtracCon.class)
            .setParameter("ids", ids)
            .setParameter("useAtr", useAtr)
            .getList(x -> x.toDomain(krcstErAlCompareSingle, krcstErAlCompareRange, krcstErAlSingleFixed));
    }

    private static final String SELECT_COMPARE_SINGLE_BY_ID =
            " SELECT a FROM KrcstErAlCompareSingle a " +
                    " JOIN KrcmtWkpMonExtracCon b ON a.krcstEralCompareSinglePK.conditionGroupId = b.errorAlarmCheckID " +
                    " WHERE a.krcstEralCompareSinglePK.atdItemConNo = 0 AND a.conditionType = 0 AND b.errorAlarmWorkplaceId IN :ids ";

    private static final String SELECT_COMPARE_RANGE_BY_ID =
            " SELECT a FROM KrcstErAlCompareRange a " +
                    " JOIN KrcmtWkpMonExtracCon b ON a.krcstEralCompareRangePK.conditionGroupId = b.errorAlarmCheckID " +
                    " WHERE a.krcstEralCompareRangePK.atdItemConNo = 0 AND b.errorAlarmWorkplaceId IN :ids ";

    private static final String SELECT_SINGLE_FIXED_BY_ID =
            " SELECT a FROM KrcstErAlSingleFixed a " +
                    " JOIN KrcmtWkpMonExtracCon b ON a.krcstEralSingleFixedPK.conditionGroupId = b.errorAlarmCheckID " +
                    " WHERE a.krcstEralSingleFixedPK.atdItemConNo = 0 AND b.errorAlarmWorkplaceId IN :ids ";

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
        return domains.stream().map(i -> i.toDomain(
                krcstErAlCompareSingle.stream().filter(x -> x.krcstEralCompareSinglePK.conditionGroupId.equals(i.errorAlarmCheckID)).findFirst(),
                krcstErAlCompareRange.stream().filter(x -> x.krcstEralCompareRangePK.conditionGroupId.equals(i.errorAlarmCheckID)).findFirst(),
                krcstErAlSingleFixed.stream().filter(x -> x.krcstEralSingleFixedPK.conditionGroupId.equals(i.errorAlarmCheckID)).findFirst()
        )).collect(Collectors.toList());
    }

    @Override
    public void register(List<ExtractionMonthlyCon> domains) {
        List<KrcmtWkpMonExtracCon> entities = domains.stream().map(KrcmtWkpMonExtracCon::fromDomain).collect(Collectors.toList());
        List<KrcstErAlCompareSingle> compareSingles = new ArrayList<>();
        List<KrcstErAlSingleFixed> singleFixeds = new ArrayList<>();
        List<KrcstErAlCompareRange> compareRanges = new ArrayList<>();

        System.out.println(domains);

        domains.forEach(i -> {
            Double minValue = null;
            Double maxValue = null;
            if (i.getCheckConditions().isSingleValue()) {
                switch (i.getCheckMonthlyItemsType()) {
                    case AVERAGE_TIME:
                    case TIME_FREEDOM:{
                        minValue = Double.valueOf(((AverageTime) ((CompareSingleValue) i.getCheckConditions()).getValue()).v());
                        break;
                    }
                    case AVERAGE_NUMBER_DAY:
                    case AVERAGE_DAY_FREE: {
                        minValue = ((AverageNumberDays) ((CompareSingleValue) i.getCheckConditions()).getValue()).v().doubleValue();
                        break;
                    }
                    case AVERAGE_NUMBER_TIME:
                    case AVERAGE_TIME_FREE: {
                        minValue = Double.valueOf(((AverageNumberTimes) ((CompareSingleValue) i.getCheckConditions()).getValue()).v());
                        break;
                    }
                    case AVERAGE_RATIO:
                    case AVERAGE_RATIO_FREE: {
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
                    case AVERAGE_TIME:
                    case TIME_FREEDOM:{
                        minValue = Double.valueOf(((AverageTime) ((CompareRange) i.getCheckConditions()).getStartValue()).v());
                        maxValue = Double.valueOf(((AverageTime) ((CompareRange) i.getCheckConditions()).getEndValue()).v());
                        break;
                    }
                    case AVERAGE_NUMBER_DAY:
                    case AVERAGE_DAY_FREE: {
                        minValue = ((AverageNumberDays) ((CompareRange) i.getCheckConditions()).getStartValue()).v().doubleValue();
                        maxValue = ((AverageNumberDays) ((CompareRange) i.getCheckConditions()).getEndValue()).v().doubleValue();
                        break;
                    }
                    case AVERAGE_NUMBER_TIME:
                    case AVERAGE_TIME_FREE:{
                        minValue = Double.valueOf(((AverageNumberTimes) ((CompareRange) i.getCheckConditions()).getStartValue()).v());
                        maxValue = Double.valueOf(((AverageNumberTimes) ((CompareRange) i.getCheckConditions()).getEndValue()).v());
                        break;
                    }
                    case AVERAGE_RATIO:
                    case AVERAGE_RATIO_FREE:{
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
        });
        if (!CollectionUtil.isEmpty(compareSingles) && !CollectionUtil.isEmpty(singleFixeds)) {
            this.commandProxy().insertAll(compareSingles);
            this.commandProxy().insertAll(singleFixeds);
        }
        if (!CollectionUtil.isEmpty(compareRanges)) {
            this.commandProxy().insertAll(compareRanges);
        }
        this.commandProxy().insertAll(entities);
    }

    @Override
    public void delete(List<String> ids) {
        List<ExtractionMonthlyCon> domain = this.getByIds(ids);
        List<String> compareIds = domain.stream().map(ExtractionMonthlyCon::getErrorAlarmCheckID).collect(Collectors.toList());
        this.commandProxy().removeAll(KrcstErAlCompareSingle.class, compareIds.stream().map(i -> new KrcstErAlCompareSinglePK(i, 0)).collect(Collectors.toList()));
        this.commandProxy().removeAll(KrcstErAlSingleFixed.class, compareIds.stream().map(i -> new KrcstErAlSingleFixedPK(i, 0)).collect(Collectors.toList()));
        this.commandProxy().removeAll(KrcstErAlCompareRange.class, compareIds.stream().map(i -> new KrcstErAlCompareRangePK(i, 0)).collect(Collectors.toList()));
        this.commandProxy().removeAll(KrcmtWkpMonExtracCon.class, ids);
    }
}
