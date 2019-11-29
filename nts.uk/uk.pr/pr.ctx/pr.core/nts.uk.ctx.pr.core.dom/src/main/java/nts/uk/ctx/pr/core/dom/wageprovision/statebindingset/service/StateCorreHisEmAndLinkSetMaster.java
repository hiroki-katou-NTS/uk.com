package nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.StateCorreHisEm;
import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.StateLinkSetMaster;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class StateCorreHisEmAndLinkSetMaster {
    /**
     * 明細書紐付け履歴（雇用）
     */
    List<StateCorreHisEm> historys;

    /**
     * 明細書紐付け設定（マスタ）
     */
    List<StateLinkSetMaster> settings;
}
