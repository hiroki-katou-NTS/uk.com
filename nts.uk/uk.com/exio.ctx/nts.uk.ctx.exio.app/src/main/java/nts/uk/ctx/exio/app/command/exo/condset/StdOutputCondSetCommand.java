package nts.uk.ctx.exio.app.command.exo.condset;

import java.util.List;

import lombok.Value;


@Value
public class StdOutputCondSetCommand {

    /**
     * 会社ID
     */
    private String cId;

    /**
     * 外部出力条件コード
     */
    private String conditionSetCd;

    /**
     * カテゴリID
     */
    private int categoryId;

    /**
     * 区切り文字
     */
    private int delimiter;

    /**
     * するしない区分
     */
    private int itemOutputName;

    /**
     * するしない区分
     */
    private int autoExecution;

    /**
     * 外部出力条件名称
     */
    private String conditionSetName;

    /**
     * するしない区分
     */
    private int conditionOutputName;

    /**
     * 文字列形式
     */
    private int stringFormat;

    
    private Long version;
    
    private String copyDestinationCode;
    
    private boolean overWrite;
    
    private boolean newMode;
    
    private String destinationName;
    
    private int standType;
    
    private List<StdOutItemOrderCommand> listStandardOutputItem;
}
