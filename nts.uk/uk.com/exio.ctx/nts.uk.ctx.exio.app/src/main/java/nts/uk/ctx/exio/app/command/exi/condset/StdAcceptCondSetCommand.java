package nts.uk.ctx.exio.app.command.exi.condset;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

@Value
public class StdAcceptCondSetCommand
{
    
    /**
    * 会社ID
    */
    private String cid;
    
    /**
    * 外部受入条件コード
    */
    private String conditionSetCd;
    
    /**
    * 外部受入カテゴリID
    */
    private String categoryId;
    
    /**
    * CSVデータの項目名行
    */
    private int csvDataLineNumber;
    
    /**
    * システム種類
    */
    private int systemType;
    
    /**
    * 既存データの削除
    */
    private int deleteExistData;
    
    /**
    * CSVデータの取込開始行
    */
    private int csvDataStartLine;
    
    /**
    * 受入モード
    */
    private int acceptMode;
    
    /**
    * 外部受入条件名称
    */
    private String conditionSetName;
    
    /**
    * チェック完了
    */
    private int checkCompleted;
    
    /**
    * 既存データの削除方法
    */
    private int deleteExtDataMethod;
    
    private Long version;

}
