package nts.uk.ctx.at.request.infra.entity.setting.company.applicationapprovalsetting.appovertime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.appovertime.OvertimeQuotaSetUse;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "KRQMT_APP_OVERTIME_FRAME")
public class KrqmtAppOvertimeFrame extends ContractUkJpaEntity {
    @EmbeddedId
    private KrqmtAppOvertimeFramePk pk;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CID", insertable = false, updatable = false)
    private KrqmtAppOvertime appOvertimeSetting;

    public static KrqmtAppOvertimeFrame create(String companyId, int overtimeAtr, int flexAtr, int otFrameNo) {
        KrqmtAppOvertimeFrame entity = new KrqmtAppOvertimeFrame();
        entity.pk = new KrqmtAppOvertimeFramePk(companyId, overtimeAtr, flexAtr, otFrameNo);
        return entity;
    }

    @Override
    protected Object getKey() {
        return this.pk;
    }

    public int getTargetFrame() {
        return pk.otFrameNo;
    }

    public static List<OvertimeQuotaSetUse> toDomains(List<KrqmtAppOvertimeFrame> entities) {
        List<OvertimeQuotaSetUse> domains = new ArrayList<>();
        Map<Object, List<KrqmtAppOvertimeFrame>> group = entities.stream().collect(Collectors.groupingBy(e -> new HashMap() {{
            put(e.pk.overtimeAtr, e.pk.flexAtr);
        }}));
        group.forEach((key, value) -> {
            OvertimeQuotaSetUse quota = OvertimeQuotaSetUse.create((Integer) ((Map)key).keySet().toArray()[0], (Integer) ((Map)key).values().toArray()[0], value.stream().map(KrqmtAppOvertimeFrame::getTargetFrame).collect(Collectors.toList()));
            domains.add(quota);
        });
        return domains;
    }

    public static List<KrqmtAppOvertimeFrame> fromDomains(String companyId, List<OvertimeQuotaSetUse> domains) {
        List<KrqmtAppOvertimeFrame> entities = new ArrayList<>();
        domains.forEach(quota -> {
            quota.getTargetOvertimeLimit().forEach(target -> {
                KrqmtAppOvertimeFrame entity = KrqmtAppOvertimeFrame.create(companyId, quota.getOvertimeAppAtr().value, quota.getFlexWorkAtr().value, target.v());
                entities.add(entity);
            });
        });
        return entities;
    }
}
