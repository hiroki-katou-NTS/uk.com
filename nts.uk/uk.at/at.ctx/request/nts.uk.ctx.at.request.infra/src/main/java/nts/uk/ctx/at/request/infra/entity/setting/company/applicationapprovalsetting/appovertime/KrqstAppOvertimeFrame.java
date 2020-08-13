package nts.uk.ctx.at.request.infra.entity.setting.company.applicationapprovalsetting.appovertime;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.appovertime.OvertimeQuotaSetUse;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Entity
@Table(name = "KRQST_APP_OVERTIME_FRAME")
public class KrqstAppOvertimeFrame extends UkJpaEntity {
    @EmbeddedId
    private KrqstAppOvertimeFramePk pk;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CID", insertable = false, updatable = false)
    private KrqstAppOvertime appOvertimeSetting;

    @Override
    protected Object getKey() {
        return this.pk;
    }

    public static List<OvertimeQuotaSetUse> toDomains(List<KrqstAppOvertimeFrame> entities) {
        if (CollectionUtil.isEmpty(entities)) return null;
//        int overtimeAtr = entities.get(0).pk.overtimeAtr;
//        int flexAtr = entities.get(0).pk.flexAtr;
//        return OvertimeQuotaSetUse.create(overtimeAtr, flexAtr, entities.stream().map(e -> e.pk.otFrameNo).collect(Collectors.toList()));
        return new ArrayList<>();
    }
}
