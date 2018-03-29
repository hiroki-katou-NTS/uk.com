package nts.uk.ctx.exio.dom.exi.opmanage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;

/**
* 外部受入動作管理
*/
@AllArgsConstructor
@Getter
@Setter
public class ExAcOpManage extends AggregateRoot
{
    
    /**
    * 会社ID
    */
    private String cid;
    
    /**
    * 外部受入処理ID
    */
    private String processId;
    
    /**
    * エラー件数
    */
    private int errorCount;
    
    /**
    * 中断するしない
    */
    private int interruption;
    
    /**
    * 処理カウント
    */
    private int processCount;
    
    /**
    * 処理トータルカウント
    */
    private int processTotalCount;
    
    /**
    * 動作状態
    */
    private int stateBehavior;
    
    public static ExAcOpManage createFromJavaType(Long version, String cid, String processId, int errorCount, int interruption, int processCount, int processTotalCount, int stateBehavior)
    {
        ExAcOpManage  exAcOpManage =  new ExAcOpManage(cid, processId, errorCount, interruption, processCount, processTotalCount,  stateBehavior);
        exAcOpManage.setVersion(version);
        return exAcOpManage;
    }
    
}
