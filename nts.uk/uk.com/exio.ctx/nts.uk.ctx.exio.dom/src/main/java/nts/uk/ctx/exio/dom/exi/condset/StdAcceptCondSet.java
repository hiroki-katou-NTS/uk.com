package nts.uk.ctx.exio.dom.exi.condset;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

/**
* 受入条件設定（定型）
*/
@AllArgsConstructor
@Getter
@Setter
public class StdAcceptCondSet extends AggregateRoot
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
    
    public static StdAcceptCondSet createFromJavaType(Long version, String cid, String conditionSetCd, String categoryId, int csvDataLineNumber, int systemType, int deleteExistData, int csvDataStartLine, int acceptMode, String conditionSetName, int checkCompleted, int deleteExtDataMethod)
    {
        StdAcceptCondSet  stdAcceptCondSet =  new StdAcceptCondSet(cid, conditionSetCd, categoryId, csvDataLineNumber, systemType, deleteExistData, csvDataStartLine, acceptMode, conditionSetName, checkCompleted,  deleteExtDataMethod);
        stdAcceptCondSet.setVersion(version);
        return stdAcceptCondSet;
    }
    
}
