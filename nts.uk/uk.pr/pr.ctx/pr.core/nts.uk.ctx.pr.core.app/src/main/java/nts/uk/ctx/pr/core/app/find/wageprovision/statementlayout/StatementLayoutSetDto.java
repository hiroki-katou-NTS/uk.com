package nts.uk.ctx.pr.core.app.find.wageprovision.statementlayout;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementLayoutSet;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Value
public class StatementLayoutSetDto {
    /**
     * 履歴ID
     */
    private String histId;

    /**
     * レイアウトパターン
     */
    private Integer layoutPattern;

    /**
     * カテゴリ別設定
     */
    private List<SettingByCtgDto> listSettingByCtg;

    public StatementLayoutSetDto(StatementLayoutSet domain) {
        this.histId = domain.getHistId();
        this.layoutPattern = domain.getLayoutPattern().value;
        this.listSettingByCtg = domain.getListSettingByCtg().stream().map(x -> new SettingByCtgDto(x)).collect(Collectors.toList());
    }
}
