package nts.uk.ctx.at.schedule.infra.entity.displaysetting.functioncontrol;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.val;
import nts.arc.layer.infra.data.jdbc.map.JpaEntityMapper;
import nts.uk.ctx.at.schedule.dom.displaysetting.functioncontrol.ScheFunctionControl;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * KSCMT_FUNC_CTR_USE_WKTP
 * @author viet.tx
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSCMT_FUNC_CTR_USE_WKTP")
public class KscmtFuncCtrUseWktp extends ContractUkJpaEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final JpaEntityMapper<KscmtFuncCtrUseWktp> MAPPER = new JpaEntityMapper<>(KscmtFuncCtrUseWktp.class);

    @EmbeddedId
    public KscmtFuncCtrUseWktpPk kscmtFuncCtrUseWktpPk;

    @Override
    protected Object getKey() {
        return this.kscmtFuncCtrUseWktpPk;
    }

    /**
     * Convert to entity
     * @param companyId
     * @param domain
     * @return
     */
    public static List<KscmtFuncCtrUseWktp> toEntity(String companyId, ScheFunctionControl domain) {
        return domain.getDisplayableWorkTypeCodeList().stream().map(x -> {
            KscmtFuncCtrUseWktpPk pk = new KscmtFuncCtrUseWktpPk(companyId, x.v());
            return new KscmtFuncCtrUseWktp(pk);
        }).collect(Collectors.toList());
    }

    public WorkTypeCode toWorkTypeCode() {
        return new WorkTypeCode(this.kscmtFuncCtrUseWktpPk.wktpCd);
    }
}
