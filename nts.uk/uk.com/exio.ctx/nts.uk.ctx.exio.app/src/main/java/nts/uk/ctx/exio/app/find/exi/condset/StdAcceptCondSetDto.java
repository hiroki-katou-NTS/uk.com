package nts.uk.ctx.exio.app.find.exi.condset;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.exio.dom.exi.condset.StdAcceptCondSet;

/**
* 受入条件設定（定型）
*/
@AllArgsConstructor
@Value
public class StdAcceptCondSetDto
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
    public static StdAcceptCondSetDto fromDomain(StdAcceptCondSet domain)
    {
        return new StdAcceptCondSetDto(domain.getCid(), domain.getConditionSetCd(), domain.getCategoryId(), domain.getCsvDataLineNumber(), domain.getSystemType(), domain.getDeleteExistData(), domain.getCsvDataStartLine(), domain.getAcceptMode(), domain.getConditionSetName(), domain.getCheckCompleted(), domain.getDeleteExtDataMethod(), domain.getVersion());
    }
    
}
