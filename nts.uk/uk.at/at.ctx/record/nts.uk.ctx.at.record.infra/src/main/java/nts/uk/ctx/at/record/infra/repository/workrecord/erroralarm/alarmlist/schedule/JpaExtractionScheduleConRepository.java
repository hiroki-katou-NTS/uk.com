package nts.uk.ctx.at.record.infra.repository.workrecord.erroralarm.alarmlist.schedule;

import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.schedule.ExtractionScheduleCon;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.schedule.ExtractionScheduleConRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.schedule.primitivevalue.*;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.alarmlistworkplace.schedule.KrcmtWkpSchedaiExCon;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.attendanceitem.*;
import nts.uk.ctx.at.shared.dom.workrecord.alarm.attendanceitemconditions.CompareRange;
import nts.uk.ctx.at.shared.dom.workrecord.alarm.attendanceitemconditions.CompareSingleValue;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaExtractionScheduleConRepository extends JpaRepository implements ExtractionScheduleConRepository {

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

    private static final String SELECT;

    private static final String FIND_BY_IDS_AND_USEATR;

    public static final String GET_BY_IDS;

    static {
        StringBuilder builderString = new StringBuilder();
        builderString.append(" SELECT a FROM KrcmtWkpSchedaiExCon a ");
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
    public List<ExtractionScheduleCon> getBy(List<String> ids, boolean useAtr) {
        if (CollectionUtil.isEmpty(ids)) return new ArrayList<>();

        Optional<KrcstErAlCompareSingle> krcstErAlCompareSingle = this.queryProxy().query(SELECT_COMPARE_SINGLE, KrcstErAlCompareSingle.class).getSingle();
        Optional<KrcstErAlCompareRange> krcstErAlCompareRange = this.queryProxy().query(SELECT_COMPARE_RANGE, KrcstErAlCompareRange.class).getSingle();
        Optional<KrcstErAlSingleFixed> krcstErAlSingleFixed = this.queryProxy().query(SELECT_SINGLE_FIXED, KrcstErAlSingleFixed.class).getSingle();

        return this.queryProxy().query(FIND_BY_IDS_AND_USEATR, KrcmtWkpSchedaiExCon.class)
            .setParameter("ids", ids)
            .setParameter("useAtr", useAtr)
            .getList(x -> x.toDomain(krcstErAlCompareSingle, krcstErAlCompareRange, krcstErAlSingleFixed));
    }

    private static final String SELECT_COMPARE_SINGLE_BY_ID =
            " SELECT a FROM KrcstErAlCompareSingle a " +
                    " JOIN KrcmtWkpSchedaiExCon b ON a.krcstEralCompareSinglePK.conditionGroupId = b.errorAlarmCheckID " +
                    " WHERE a.krcstEralCompareSinglePK.atdItemConNo = 0 AND a.conditionType = 0 AND b.errorAlarmWorkplaceId IN :ids";

    private static final String SELECT_COMPARE_RANGE_BY_ID =
            " SELECT a FROM KrcstErAlCompareRange a " +
                    " JOIN KrcmtWkpSchedaiExCon b ON a.krcstEralCompareRangePK.conditionGroupId = b.errorAlarmCheckID " +
                    " WHERE a.krcstEralCompareRangePK.atdItemConNo = 0 AND b.errorAlarmWorkplaceId IN :ids";

    private static final String SELECT_SINGLE_FIXED_BY_ID =
            " SELECT a FROM KrcstErAlSingleFixed a " +
                    " JOIN KrcmtWkpSchedaiExCon b ON a.krcstEralSingleFixedPK.conditionGroupId = b.errorAlarmCheckID " +
                    " WHERE a.krcstEralSingleFixedPK.atdItemConNo = 0 AND b.errorAlarmWorkplaceId IN :ids";

    @Override
    public List<ExtractionScheduleCon> getByIds(List<String> ids) {
        if (CollectionUtil.isEmpty(ids)) return new ArrayList<>();

        List<KrcstErAlCompareSingle> krcstErAlCompareSingle = this.queryProxy().query(SELECT_COMPARE_SINGLE_BY_ID, KrcstErAlCompareSingle.class).setParameter("ids", ids).getList();
        List<KrcstErAlCompareRange> krcstErAlCompareRange = this.queryProxy().query(SELECT_COMPARE_RANGE_BY_ID, KrcstErAlCompareRange.class).setParameter("ids", ids).getList();
        List<KrcstErAlSingleFixed> krcstErAlSingleFixed = this.queryProxy().query(SELECT_SINGLE_FIXED_BY_ID, KrcstErAlSingleFixed.class).setParameter("ids", ids).getList();

        List<KrcmtWkpSchedaiExCon> domains = this.queryProxy().query(GET_BY_IDS, KrcmtWkpSchedaiExCon.class)
                .setParameter("ids", ids)
                .getList();
        return domains.stream().map(i -> i.toDomain(
                krcstErAlCompareSingle.stream().filter(x -> x.krcstEralCompareSinglePK.conditionGroupId.equals(i.errorAlarmCheckID)).findFirst(),
                krcstErAlCompareRange.stream().filter(x -> x.krcstEralCompareRangePK.conditionGroupId.equals(i.errorAlarmCheckID)).findFirst(),
                krcstErAlSingleFixed.stream().filter(x -> x.krcstEralSingleFixedPK.conditionGroupId.equals(i.errorAlarmCheckID)).findFirst()
        )).collect(Collectors.toList());
    }

    @Override
    public void register(List<ExtractionScheduleCon> domain) {
        List<KrcmtWkpSchedaiExCon> entities = domain.stream().map(KrcmtWkpSchedaiExCon::fromDomain).collect(Collectors.toList());
        List<KrcstErAlCompareSingle> compareSingles = new ArrayList<>();
        List<KrcstErAlSingleFixed> singleFixeds = new ArrayList<>();
        List<KrcstErAlCompareRange> compareRanges = new ArrayList<>();

        domain.forEach(i -> {
            Double minValue = null;
            Double maxValue = null;
            if (i.getCheckConditions().isSingleValue()) {
                switch (i.getCheckDayItemsType()) {
                    case CONTRAST: {
                        minValue = Double.valueOf(((Comparison) ((CompareSingleValue) i.getCheckConditions()).getValue()).v());
                        break;
                    }
                    case NUMBER_PEOPLE_COMPARISON: {
                        minValue = Double.valueOf(((NumberOfPeople) ((CompareSingleValue) i.getCheckConditions()).getValue()).v());
                        break;
                    }
                    case TIME_COMPARISON: {
                        minValue = Double.valueOf(((Time) ((CompareSingleValue) i.getCheckConditions()).getValue()).v());
                        break;
                    }
                    case AMOUNT_COMPARISON: {
                        minValue = Double.valueOf(((Amount) ((CompareSingleValue) i.getCheckConditions()).getValue()).v());
                        break;
                    }
                    case RATIO_COMPARISON: {
                        minValue = Double.valueOf(((RatioComparison) ((CompareSingleValue) i.getCheckConditions()).getValue()).v());
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
                switch (i.getCheckDayItemsType()) {
                    case CONTRAST: {
                        minValue = Double.valueOf(((Comparison) ((CompareRange) i.getCheckConditions()).getStartValue()).v());
                        maxValue = Double.valueOf(((Comparison) ((CompareRange) i.getCheckConditions()).getEndValue()).v());
                        break;
                    }
                    case NUMBER_PEOPLE_COMPARISON: {
                        minValue = Double.valueOf(((NumberOfPeople) ((CompareRange) i.getCheckConditions()).getStartValue()).v());
                        maxValue = Double.valueOf(((NumberOfPeople) ((CompareRange) i.getCheckConditions()).getEndValue()).v());
                        break;
                    }
                    case TIME_COMPARISON: {
                        minValue = Double.valueOf(((Time) ((CompareRange) i.getCheckConditions()).getStartValue()).v());
                        maxValue = Double.valueOf(((Time) ((CompareRange) i.getCheckConditions()).getEndValue()).v());
                        break;
                    }
                    case AMOUNT_COMPARISON: {
                        minValue = Double.valueOf(((Amount) ((CompareRange) i.getCheckConditions()).getStartValue()).v());
                        maxValue = Double.valueOf(((Amount) ((CompareRange) i.getCheckConditions()).getEndValue()).v());
                        break;
                    }
                    case RATIO_COMPARISON: {
                        minValue = Double.valueOf(((RatioComparison) ((CompareRange) i.getCheckConditions()).getStartValue()).v());
                        maxValue = Double.valueOf(((RatioComparison) ((CompareRange) i.getCheckConditions()).getEndValue()).v());
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
        List<ExtractionScheduleCon> domains = this.getByIds(ids);
        List<String> compareIds = domains.stream().map(ExtractionScheduleCon::getErrorAlarmCheckID).collect(Collectors.toList());
        this.commandProxy().removeAll(KrcstErAlCompareSingle.class, compareIds.stream().map(i -> new KrcstErAlCompareSinglePK(i, 0)).collect(Collectors.toList()));
        this.commandProxy().removeAll(KrcstErAlSingleFixed.class, compareIds.stream().map(i -> new KrcstErAlSingleFixedPK(i, 0)).collect(Collectors.toList()));
        this.commandProxy().removeAll(KrcstErAlCompareRange.class, compareIds.stream().map(i -> new KrcstErAlCompareRangePK(i, 0)).collect(Collectors.toList()));
        this.commandProxy().removeAll(KrcmtWkpSchedaiExCon.class, ids);
    }

}
