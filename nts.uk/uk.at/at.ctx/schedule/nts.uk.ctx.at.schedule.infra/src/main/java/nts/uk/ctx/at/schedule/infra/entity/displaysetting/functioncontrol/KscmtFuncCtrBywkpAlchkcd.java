package nts.uk.ctx.at.schedule.infra.entity.displaysetting.functioncontrol;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

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

    @EmbeddedId
    public KscmtFuncCtrBywkpAlchkcdPk pk;

    @Override
    protected Object getKey() { return this.pk; }
}
