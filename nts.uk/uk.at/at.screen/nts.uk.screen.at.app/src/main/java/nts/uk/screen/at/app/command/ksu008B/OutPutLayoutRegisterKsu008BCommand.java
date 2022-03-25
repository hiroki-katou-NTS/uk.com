package nts.uk.screen.at.app.command.ksu008B;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.screen.at.app.ksu008.a.dto.Form9CoverDto;
import nts.uk.screen.at.app.ksu008.a.dto.Form9NursingAideTableDto;
import nts.uk.screen.at.app.ksu008.a.dto.Form9NursingTableDto;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OutPutLayoutRegisterKsu008BCommand {

    /**
     * コード
     **/
    private String code;

    /**
     * 名称
     **/
    private String name;

    /**
     * システム固定か
     **/
    private boolean isSystemFixed;

    /**
     * 利用区分
     */
    private boolean isUse;

    /**
     * 表紙
     **/
    private Form9CoverDto cover;

    /**
     * 看護職員表
     **/
    private Form9NursingTableDto nursingTable;

    /**
     * 看護補助者表
     **/
    private Form9NursingAideTableDto nursingAideTable;

    /**
     * テンプレート
     **/
    private String templateFileId;
}
