package nts.uk.file.at.app.export.supportworklist;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SupportWorkHeaderInfo {
    /** 最初の行のコード */
    private String firstLineCode;

    /** 最後の行の名称*/
    private String firstLineName;

    /** 最後の行のコード*/
    private String lastLineCode;

    /** 最後の行の名称*/
    private String lastLineName;

    /** 選択チェックの個数*/
    private int numberOfSelect;
}
