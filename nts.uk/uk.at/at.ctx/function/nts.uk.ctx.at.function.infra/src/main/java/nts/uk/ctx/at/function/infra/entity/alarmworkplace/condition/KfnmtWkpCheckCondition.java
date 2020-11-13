package nts.uk.ctx.at.function.infra.entity.alarmworkplace.condition;

import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.AlarmCheckConditionCode;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.EndDate;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.StartDate;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.month.singlemonth.SingleMonth;
import nts.uk.ctx.at.function.dom.alarm.workplace.checkcondition.WorkplaceCategory;
import nts.uk.ctx.at.function.dom.alarmworkplace.CheckCondition;
import nts.uk.ctx.at.function.dom.alarmworkplace.ExtractionPeriodDaily;
import nts.uk.ctx.at.function.dom.alarmworkplace.ExtractionPeriodMonthly;
import nts.uk.ctx.at.function.dom.alarmworkplace.RangeToExtract;
import nts.uk.ctx.at.function.dom.alarmworkplace.checkcondition.WorkplaceCategory;
import nts.uk.ctx.at.function.infra.entity.alarmworkplace.alarmpatternworkplace.KfnmtALstWkpPtn;
import nts.uk.ctx.at.function.infra.entity.alarmworkplace.monthdayperiod.*;
import nts.uk.ctx.at.function.infra.entity.alarmworkplace.singlemonth.KfnmtAssignNumofMon;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Entity
@Table(name = "KFNMT_WRKPCHECK_CONDITION")
public class KfnmtWkpCheckCondition extends UkJpaEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    public KfnmtWkpCheckConditionPK pk;

    @Override
    protected Object getKey() {
        return this.pk;
    }

    @ManyToOne
    @JoinColumns(
        {@JoinColumn(name = "CID", referencedColumnName = "CID", insertable = false, updatable = false),
            @JoinColumn(name = "ALARM_PATTERN_CD", referencedColumnName = "ALARM_PATTERN_CD", insertable = false, updatable = false)})
    public KfnmtALstWkpPtn kfnmtALstWkpPtn;

    @OneToMany(mappedBy = "checkCondition", cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(name = "KFNMT_PTN_MAP_CAT")
    public List<KfnmtPtnMapCat> checkConItems;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumns({
        @JoinColumn(name = "CID", referencedColumnName = "CID", insertable = false, updatable = false),
        @JoinColumn(name = "ALARM_PATTERN_CD", referencedColumnName = "ALARM_PATTERN_CD", insertable = false, updatable = false),
        @JoinColumn(name = "CATEGORY", referencedColumnName = "CATEGORY", insertable = false, updatable = false)})
    public KfnmtAssignNumofMon kfnmtAssignNumofMon;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumns({
        @JoinColumn(name = "CID", referencedColumnName = "CID", insertable = false, updatable = false),
        @JoinColumn(name = "ALARM_PATTERN_CD", referencedColumnName = "ALARM_PATTERN_CD", insertable = false, updatable = false),
        @JoinColumn(name = "CATEGORY", referencedColumnName = "CATEGORY", insertable = false, updatable = false)})
    public KfnmtAssignMonthStart kfnmtAssignMonthStart;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumns({
        @JoinColumn(name = "CID", referencedColumnName = "CID", insertable = false, updatable = false),
        @JoinColumn(name = "ALARM_PATTERN_CD", referencedColumnName = "ALARM_PATTERN_CD", insertable = false, updatable = false),
        @JoinColumn(name = "CATEGORY", referencedColumnName = "CATEGORY", insertable = false, updatable = false)})
    public KfnmtAssignMonthEnd kfnmtAssignMonthEnd;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumns({
        @JoinColumn(name = "CID", referencedColumnName = "CID", insertable = false, updatable = false),
        @JoinColumn(name = "ALARM_PATTERN_CD", referencedColumnName = "ALARM_PATTERN_CD", insertable = false, updatable = false),
        @JoinColumn(name = "CATEGORY", referencedColumnName = "CATEGORY", insertable = false, updatable = false)})
    public KfnmtAssignDayEnd kfnmtAssignDayEnd;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumns({
        @JoinColumn(name = "CID", referencedColumnName = "CID", insertable = false, updatable = false),
        @JoinColumn(name = "ALARM_PATTERN_CD", referencedColumnName = "ALARM_PATTERN_CD", insertable = false, updatable = false),
        @JoinColumn(name = "CATEGORY", referencedColumnName = "CATEGORY", insertable = false, updatable = false)})
    public KfnmtAssignDayStart kfnmtAssignDayStart;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumns({
        @JoinColumn(name = "CID", referencedColumnName = "CID", insertable = false, updatable = false),
        @JoinColumn(name = "ALARM_PATTERN_CD", referencedColumnName = "ALARM_PATTERN_CD", insertable = false, updatable = false),
        @JoinColumn(name = "CATEGORY", referencedColumnName = "CATEGORY", insertable = false, updatable = false)})
    public KfnmtAssignDatelineStart kfnmtAssignDatelineStart;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumns({
        @JoinColumn(name = "CID", referencedColumnName = "CID", insertable = false, updatable = false),
        @JoinColumn(name = "ALARM_PATTERN_CD", referencedColumnName = "ALARM_PATTERN_CD", insertable = false, updatable = false),
        @JoinColumn(name = "CATEGORY", referencedColumnName = "CATEGORY", insertable = false, updatable = false)})
    public KfnmtAssignDatelineEnd kfnmtAssignDatelineEnd;

    public KfnmtWkpCheckCondition(KfnmtWkpCheckConditionPK pk, List<KfnmtPtnMapCat> checkConItems, KfnmtAssignNumofMon assignNumofMon) {
        super();
        this.pk = pk;
        this.checkConItems = checkConItems;
        this.kfnmtAssignNumofMon = assignNumofMon;
    }

    public KfnmtWkpCheckCondition(KfnmtWkpCheckConditionPK pk, List<KfnmtPtnMapCat> checkConItems) {
        super();
        this.pk = pk;
        this.checkConItems = checkConItems;
    }

    public KfnmtWkpCheckCondition(KfnmtWkpCheckConditionPK pk, List<KfnmtPtnMapCat> checkConItems,
                                  KfnmtAssignMonthStart assignMonthStart,
                                  KfnmtAssignMonthEnd assignMonthEnd) {
        super();
        this.pk = pk;
        this.checkConItems = checkConItems;
        this.kfnmtAssignMonthStart = assignMonthStart;
        this.kfnmtAssignMonthEnd = assignMonthEnd;
    }

    public KfnmtWkpCheckCondition(KfnmtWkpCheckConditionPK pk,
                                  List<KfnmtPtnMapCat> checkConItems,
                                  KfnmtAssignDayStart assignDayStart,
                                  KfnmtAssignDatelineStart assignDatelineStart,
                                  KfnmtAssignDayEnd assignDayEnd,
                                  KfnmtAssignDatelineEnd assignDatelineEnd) {
        super();
        this.pk = pk;
        this.checkConItems = checkConItems;
        this.kfnmtAssignDayStart = assignDayStart;
        this.kfnmtAssignDatelineStart = assignDatelineStart;
        this.kfnmtAssignDatelineEnd = assignDatelineEnd;
        this.kfnmtAssignDayEnd = assignDayEnd;
    }

    public CheckCondition toDomain() {

        RangeToExtract extractPeriod = null;
        if (this.pk.category == WorkplaceCategory.MONTHLY.value) {
            extractPeriod = kfnmtAssignNumofMon.toDomain();

        } else if (this.pk.category == WorkplaceCategory.MASTER_CHECK_BASIC.value || this.pk.category == WorkplaceCategory.MASTER_CHECK_WORKPLACE.value) {
            extractPeriod = new ExtractionPeriodMonthly(kfnmtAssignMonthStart.toDomain(), kfnmtAssignMonthEnd.toDomain());

        } else if (this.pk.category == WorkplaceCategory.MASTER_CHECK_DAILY.value || this.pk.category == WorkplaceCategory.SCHEDULE_DAILY.value) {
            extractPeriod = new ExtractionPeriodDaily(
                // Start day
                toStartDate(),
                // End day
                toEndDate()
            );
        }

        List<AlarmCheckConditionCode> checkConList = this.checkConItems.stream().map(c -> new AlarmCheckConditionCode(c.pk.categoryCode))
            .collect(Collectors.toList());
        CheckCondition domain = new CheckCondition(EnumAdaptor.valueOf(this.pk.category, WorkplaceCategory.class),
            checkConList, extractPeriod);
        return domain;
    }

    private StartDate toStartDate() {
        if (kfnmtAssignDayStart != null && kfnmtAssignDatelineStart != null) {
            throw new RuntimeException("There are two start dates");
        } else if (kfnmtAssignDayStart != null) {
            return kfnmtAssignDayStart.toDomain();
        } else if (kfnmtAssignDatelineStart != null) {
            return kfnmtAssignDatelineStart.toDomain();
        } else return null;
    }

    private EndDate toEndDate() {
        if (kfnmtAssignDayEnd != null && kfnmtAssignDatelineEnd != null) {
            throw new RuntimeException("There are two end dates");
        } else if (kfnmtAssignDayEnd != null) {
            return kfnmtAssignDayEnd.toDomain();
        } else if (kfnmtAssignDatelineEnd != null) {
            return kfnmtAssignDatelineEnd.toDomain();
        } else return null;
    }

    public static KfnmtWkpCheckCondition toEntity(CheckCondition domain, String patternCD) {

        String companyId = AppContexts.user().companyId();
        int category = domain.getWorkplaceCategory().value;
        if (category == WorkplaceCategory.MONTHLY.value) {
            return new KfnmtWkpCheckCondition(
                new KfnmtWkpCheckConditionPK(AppContexts.user().companyId(), patternCD, category),
                domain.getCheckConditionLis().stream().map(
                    x -> new KfnmtPtnMapCat(buildCheckConItemPK(domain, x.v(), companyId, patternCD)))
                    .collect(Collectors.toList()),
                KfnmtAssignNumofMon.toEntity((SingleMonth) domain.getRangeToExtract(), patternCD, category)
            );
        } else if (category == WorkplaceCategory.MASTER_CHECK_BASIC.value || domain.getWorkplaceCategory().value == WorkplaceCategory.MASTER_CHECK_WORKPLACE.value) {
            return new KfnmtWkpCheckCondition(
                new KfnmtWkpCheckConditionPK(AppContexts.user().companyId(), patternCD, category),
                domain.getCheckConditionLis().stream().map(
                    x -> new KfnmtPtnMapCat(buildCheckConItemPK(domain, x.v(), companyId, patternCD)))
                    .collect(Collectors.toList()),
                KfnmtAssignMonthStart.toEntity(((ExtractionPeriodMonthly) domain.getRangeToExtract()).getStartMonth(), patternCD, category),
                KfnmtAssignMonthEnd.toEntity(((ExtractionPeriodMonthly) domain.getRangeToExtract()).getEndMonth(), patternCD, category)

            );
        } else if (category == WorkplaceCategory.MASTER_CHECK_DAILY.value || domain.getWorkplaceCategory().value == WorkplaceCategory.SCHEDULE_DAILY.value) {

            return new KfnmtWkpCheckCondition(
                new KfnmtWkpCheckConditionPK(AppContexts.user().companyId(), patternCD, category),
                domain.getCheckConditionLis().stream().map(
                    x -> new KfnmtPtnMapCat(buildCheckConItemPK(domain, x.v(), companyId, patternCD)))
                    .collect(Collectors.toList()),

                ((ExtractionPeriodDaily) domain.getRangeToExtract()).getStartDate().getStrDays().isPresent() ?
                    KfnmtAssignDayStart.toEntity(((ExtractionPeriodDaily) domain.getRangeToExtract()).getStartDate(), patternCD, category) : null,

                ((ExtractionPeriodDaily) domain.getRangeToExtract()).getStartDate().getStrMonth().isPresent() ?
                    KfnmtAssignDatelineStart.toEntity(((ExtractionPeriodDaily) domain.getRangeToExtract()).getStartDate(), patternCD, category) : null,

                ((ExtractionPeriodDaily) domain.getRangeToExtract()).getEndDate().getEndDays().isPresent() ?
                    KfnmtAssignDayEnd.toEntity(((ExtractionPeriodDaily) domain.getRangeToExtract()).getEndDate(), patternCD, category) : null,

                ((ExtractionPeriodDaily) domain.getRangeToExtract()).getEndDate().getEndMonth().isPresent() ?
                    KfnmtAssignDatelineEnd.toEntity(((ExtractionPeriodDaily) domain.getRangeToExtract()).getEndDate(), patternCD, category) : null

            );
        }else {
            return new KfnmtWkpCheckCondition(
                new KfnmtWkpCheckConditionPK(AppContexts.user().companyId(), patternCD, category),
                domain.getCheckConditionLis().stream().map(
                    x -> new KfnmtPtnMapCat(buildCheckConItemPK(domain, x.v(), companyId, patternCD)))
                    .collect(Collectors.toList()));
        }
    }

    private static KfnmtPtnMapCatPk buildCheckConItemPK(CheckCondition domain, String checkConditionCD, String companyId, String alarmPatternCode) {
        return new KfnmtPtnMapCatPk(companyId, alarmPatternCode, domain.getWorkplaceCategory().value, checkConditionCD);
    }


}
