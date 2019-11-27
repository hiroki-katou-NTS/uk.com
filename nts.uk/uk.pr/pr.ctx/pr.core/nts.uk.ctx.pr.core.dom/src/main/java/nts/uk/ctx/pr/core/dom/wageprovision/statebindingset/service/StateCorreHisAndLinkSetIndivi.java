package nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.StateCorreHisIndivi;
import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.StateLinkSetIndivi;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class StateCorreHisAndLinkSetIndivi {
    /**
     * 明細書紐付け履歴（個人）
     */
    List<StateCorreHisIndivi> historys;
    /**
     * 明細書紐付け設定（個人）
     */
    List<StateLinkSetIndivi> settings;
}
