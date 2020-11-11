package nts.uk.ctx.at.function.infra.entity.alarmworkplace.condition;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.function.dom.alarm.AlarmCategory;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.AlarmCheckConditionCode;
import nts.uk.ctx.at.function.dom.alarm.workplace.checkcondition.WorkplaceCategory;
import nts.uk.ctx.at.function.dom.alarmworkplace.CheckCondition;
import nts.uk.ctx.at.function.dom.alarmworkplace.ExtractionPeriodDaily;
import nts.uk.ctx.at.function.dom.alarmworkplace.ExtractionPeriodMonthly;
import nts.uk.ctx.at.function.dom.alarmworkplace.RangeToExtract;
import nts.uk.ctx.at.function.infra.entity.alarmworkplace.alarmpatternworkplace.KfnmtALstWkpPtn;
import nts.uk.ctx.at.function.infra.entity.alarmworkplace.monthdayperiod.*;
import nts.uk.ctx.at.function.infra.entity.alarmworkplace.singlemonth.KfnmtAssignNumofMon;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KFNMT_WKPCHECK_CONDITION")
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

    public CheckCondition toDomain() {

        RangeToExtract extractPeriodList = new RangeToExtract() {
        };
        if (this.pk.category == WorkplaceCategory.MONTHLY.value) {
            extractPeriodList = kfnmtAssignNumofMon.toDomain();

        } else if (this.pk.category == WorkplaceCategory.MASTER_CHECK_BASIC.value || this.pk.category == WorkplaceCategory.MASTER_CHECK_WORKPLACE.value) {
            extractPeriodList = new ExtractionPeriodMonthly();

        } else if (this.pk.category == WorkplaceCategory.MASTER_CHECK_DAILY.value || this.pk.category == WorkplaceCategory.SCHEDULE_DAILY.value) {
            extractPeriodList = new ExtractionPeriodDaily();
        }

        List<AlarmCheckConditionCode> checkConList = this.checkConItems.stream().map(c -> new AlarmCheckConditionCode(c.pk.categoryCode))
            .collect(Collectors.toList());
        CheckCondition domain = new CheckCondition(EnumAdaptor.valueOf(this.pk.category, WorkplaceCategory.class),
            checkConList, extractPeriodList);
        return domain;
    }

}
