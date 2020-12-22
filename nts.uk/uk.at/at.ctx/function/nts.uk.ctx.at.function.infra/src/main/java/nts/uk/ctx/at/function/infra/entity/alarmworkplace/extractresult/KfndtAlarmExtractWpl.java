package nts.uk.ctx.at.function.infra.entity.alarmworkplace.extractresult;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.function.dom.alarmworkplace.checkcondition.WorkplaceCategory;
import nts.uk.ctx.at.function.dom.alarmworkplace.extractresult.AlarmListExtractInfoWorkplace;
import nts.uk.ctx.at.function.dom.alarmworkplace.extractresult.ExtractResult;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * entity : アラームリスト抽出情報（職場）
 */
@AllArgsConstructor
@Entity
@NoArgsConstructor
@Table(name = "KFNDT_ALARM_EXTRACT_WPL")
public class KfndtAlarmExtractWpl extends ContractUkJpaEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "RECORD_ID")
    public String recordId;

    @Column(name = "ALARM_CHK_ID")
    public String checkConditionId;

    @Column(name = "CID")
    public String cid;

    @Column(name = "PROCESS_ID")
    public String processId;

    @Column(name = "WORKPLACE_ID")
    public String workplaceId;

    @Column(name = "WORKPLACE_CD")
    public String workplaceCode;

    @Column(name = "WORKPLACE_NAME")
    public String workplaceName;

    @Column(name = "WPL_HIERARCHY_CD")
    public String hierarchyCode;

    @Column(name = "ALARM_STARTDATE")
    public String startDate;

    @Column(name = "ALARM_ENDDATE")
    public String endDate;

    @Column(name = "CATEGORY")
    public int category;

    @Column(name = "CATEGORY_NAME")
    public String categoryName;

    @Column(name = "ALARM_ITEM")
    public String alarmItemName;

    @Column(name = "ALARM_MESSAGE")
    public String alarmValueMessage;

    @Column(name = "COMMENT")
    public String comment;

    @Column(name = "CHECKED_VALUE")
    public String checkTargetValue;

    @Override
    protected Object getKey() {
        return null;
    }

    public static List<KfndtAlarmExtractWpl> toEntity(List<AlarmListExtractInfoWorkplace> domains) {
        return domains.stream().map(x -> {
            KfndtAlarmExtractWpl entity = new KfndtAlarmExtractWpl(
                    IdentifierUtil.randomUniqueId(),
                    x.getCheckConditionId(),
                    AppContexts.user().companyId(),
                    x.getProcessId(),
                    x.getExtractResult().getWorkplaceId().orElse(null),
                    x.getExtractResult().getWorkplaceCode().orElse(null),
                    x.getExtractResult().getWorkplaceName().orElse(null),
                    x.getExtractResult().getHierarchyCode().orElse(null),
                    x.getExtractResult().getAlarmValueDate().getStartDate(),
                    x.getExtractResult().getAlarmValueDate().getEndDate().orElse(null),
                    x.getCategory().value,
                    x.getCategoryName(),
                    x.getExtractResult().getAlarmItemName().v(),
                    x.getExtractResult().getAlarmValueMessage().v(),
                    x.getExtractResult().getComment().isPresent() ? x.getExtractResult().getComment().get().v() : null,
                    x.getExtractResult().getCheckTargetValue()
            );
            entity.contractCd = AppContexts.user().contractCode();
            return entity;
        }).collect(Collectors.toList());
    }

    public static List<AlarmListExtractInfoWorkplace> toDomain(List<KfndtAlarmExtractWpl> entities) {
        return entities.stream().map(x -> {
            AlarmListExtractInfoWorkplace domain = new AlarmListExtractInfoWorkplace(
                    x.checkConditionId,
                    EnumAdaptor.valueOf(x.category, WorkplaceCategory.class),
                    new ExtractResult(
                            x.alarmValueMessage,
                            x.startDate,
                            x.endDate,
                            x.alarmItemName,
                            x.checkTargetValue,
                            x.comment,
                            x.workplaceId,
                            x.workplaceCode,
                            x.workplaceName,
                            x.hierarchyCode
                    ));
            domain.setRecordId(x.recordId);
            domain.setProcessId(x.processId);
            domain.setCategoryName(x.categoryName);
            return domain;
        }).collect(Collectors.toList());
    }
}
