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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
    @Column(name = "GUI_ID")
    public String id;
    
    @Column(name = "ALARM_CHK_ID")
    public String checkConditionId;

    @Column(name = "CID")
    public String cid;

    @Column(name = "EXECUTE_ID")
    public String processingId;

    @Column(name = "WORKPLACE_ID")
    public String workplaceId;

    @Column(name = "WORKPLACE_CD")
    public String workplaceCode;

    @Column(name = "WORKPLACE_NAME")
    public String workplaceName;

    @Column(name = "WPL_HIERARCHY_CD")
    public String hierarchyCode;

    @Column(name = "ALARM_STARTDATE")
    public int startDate;

    @Column(name = "ALARM_ENDDATE")
    public Integer endDate;

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
        return this.id;
    }

    public static List<KfndtAlarmExtractWpl> toEntity(AlarmListExtractInfoWorkplace domain) {

        return domain.getExtractResults().stream().map(x -> {
            KfndtAlarmExtractWpl entity = new KfndtAlarmExtractWpl(
                    IdentifierUtil.randomUniqueId(),
                    domain.getCheckConditionId(),
                    AppContexts.user().companyId(),
                    domain.getProcessingId(),
                    x.getWorkplaceId().orElse(null),
                    x.getWorkplaceCode().orElse(null),
                    x.getWorkplaceName().orElse(null),
                    x.getHierarchyCode().orElse(null),
                    x.getAlarmValueDate().getStartDate(),
                    x.getAlarmValueDate().getEndDate().orElse(null),
                    domain.getCategory().value,
                    domain.getCategoryName(),
                    x.getAlarmItemName().v(),
                    x.getAlarmValueMessage().v(),
                    x.getComment().isPresent() ? x.getComment().get().v() : null,
                    x.getCheckTargetValue()
            );
            entity.contractCd = AppContexts.user().contractCode();
            return entity;
        }).collect(Collectors.toList());
    }

    public static List<AlarmListExtractInfoWorkplace> toDomain(List<KfndtAlarmExtractWpl> lstEntity) {
        Map<Integer, Map<String, List<KfndtAlarmExtractWpl>>> mapData = lstEntity.stream()
                .collect(Collectors.groupingBy(x -> x.category, Collectors.groupingBy(x -> x.checkConditionId, Collectors.toList())));
        List<AlarmListExtractInfoWorkplace> domains = new ArrayList<>();
        for (Map.Entry<Integer, Map<String, List<KfndtAlarmExtractWpl>>> dataByCtg : mapData.entrySet()) {
            for (Map.Entry<String, List<KfndtAlarmExtractWpl>> dataByCond : dataByCtg.getValue().entrySet()) {
                AlarmListExtractInfoWorkplace domain = new AlarmListExtractInfoWorkplace(
                        dataByCond.getKey(),
                        EnumAdaptor.valueOf(dataByCtg.getKey(), WorkplaceCategory.class),
                        dataByCond.getValue().stream().map(x -> new ExtractResult(
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
                        )).collect(Collectors.toList()));
                domains.add(domain);
            }
        }
        return domains;
    }

}
