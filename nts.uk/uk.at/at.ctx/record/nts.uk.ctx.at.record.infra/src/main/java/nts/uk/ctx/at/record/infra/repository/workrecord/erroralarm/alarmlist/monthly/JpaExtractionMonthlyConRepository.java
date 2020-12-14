package nts.uk.ctx.at.record.infra.repository.workrecord.erroralarm.alarmlist.monthly;

import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.ExtractionMonthlyCon;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.ExtractionMonthlyConRepository;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.alarmlistworkplace.monthly.KrcmtWkpMonExtracCon;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.attendanceitem.KrcstErAlCompareRange;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.attendanceitem.KrcstErAlCompareSingle;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.attendanceitem.KrcstErAlSingleFixed;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaExtractionMonthlyConRepository extends JpaRepository implements ExtractionMonthlyConRepository {

    private static final String SELECT;

    private static final String FIND_BY_IDS_AND_USEATR;

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
}
