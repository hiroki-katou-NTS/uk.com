package nts.uk.ctx.sys.assist.app.command.saveprotection;
import lombok.Value;

@Value
public class SaveProtectionCommand
{
    
    /**
    * カテゴリID
    */
    private int categoryId;
    
    /**
    * 補正区分
    */
    private int correctClasscification;
    
    /**
    * 置き換え列
    */
    private String replaceColumn;
    
    /**
    * テーブルNo
    */
    private int tableNo;
    
    private Long version;

}
