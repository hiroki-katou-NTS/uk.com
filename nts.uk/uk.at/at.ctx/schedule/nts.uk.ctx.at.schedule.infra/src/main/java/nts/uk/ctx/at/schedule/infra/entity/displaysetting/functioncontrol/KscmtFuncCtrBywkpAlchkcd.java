package nts.uk.ctx.at.schedule.infra.entity.displaysetting.functioncontrol;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.val;
import nts.arc.layer.infra.data.jdbc.map.JpaEntityMapper;
import nts.uk.ctx.at.schedule.dom.displaysetting.functioncontrol.ScheFunctionCtrlByWorkplace;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 	KSCMT_FUNC_CTR_BYWKP_ALCHKCD
 * 		スケジュール修正職場別の機能制御-アラームチェックコードリスト
 * 	@author viet.tx
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSCMT_FUNC_CTR_BYWKP_ALCHKCD")
public class KscmtFuncCtrBywkpAlchkcd extends ContractUkJpaEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final JpaEntityMapper<KscmtFuncCtrBywkpAlchkcd> MAPPER = new JpaEntityMapper<>(KscmtFuncCtrBywkpAlchkcd.class);

    @EmbeddedId
    public KscmtFuncCtrBywkpAlchkcdPk pk;

    @Override
    protected Object getKey() { return this.pk; }

    public String getAlchkCd(){
        return this.pk.alchkCd;
    }

    public static List<KscmtFuncCtrBywkpAlchkcd> toEntities(String companyId, ScheFunctionCtrlByWorkplace domain) {
        if (domain.getCompletionMethodControl().isPresent()
                && !domain.getCompletionMethodControl().get().getAlarmCheckCodeList().isEmpty()) {
            return Collections.emptyList();
        }

        return domain.getCompletionMethodControl().get().getAlarmCheckCodeList().stream().map(x -> {
            KscmtFuncCtrBywkpAlchkcdPk pk = new KscmtFuncCtrBywkpAlchkcdPk(companyId, x);
            return new KscmtFuncCtrBywkpAlchkcd(pk);
        }).collect(Collectors.toList());
    }
}
