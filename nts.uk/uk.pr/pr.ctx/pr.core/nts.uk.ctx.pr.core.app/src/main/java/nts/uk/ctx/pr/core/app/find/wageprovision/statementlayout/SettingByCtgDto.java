package nts.uk.ctx.pr.core.app.find.wageprovision.statementlayout;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.SettingByCtg;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Value
public class SettingByCtgDto {
    /**
     * カテゴリ区分
     */
    private Integer ctgAtr;

    /**
     * 行別設定
     */
    private List<LineByLineSettingDto> listLineByLineSet;

    public SettingByCtgDto(SettingByCtg domain) {
        this.ctgAtr = domain.getCtgAtr().value;
        this.listLineByLineSet = domain.getListLineByLineSet().stream().map(x -> new LineByLineSettingDto(x)).collect(Collectors.toList());
    }
}
