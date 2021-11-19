package nts.uk.screen.at.app.ksu008.a.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Form9LayoutDto {

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

    private String templateFileName;
}
