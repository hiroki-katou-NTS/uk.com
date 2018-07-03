package nts.uk.ctx.exio.dom.exo.executionlog;

import lombok.AllArgsConstructor;
import lombok.Getter;

import nts.arc.layer.dom.AggregateRoot;


/**
* 外部出力動作管理
*/
@AllArgsConstructor
@Getter
public class ExOutOpMng extends AggregateRoot
{
    
    


	/**
    * 外部出力処理ID
    */
    private ExOutProcessingId exOutProId;
    
    /**
    * 処理カウント
    */
    private int proCnt;
    
    /**
    * エラー件数
    */
    private int errCnt;
    
    /**
    * 処理トータルカウント
    */
    private int totalProCnt;
    
    /**
    * 中断するしない
    */
    private NotUseAtr doNotInterrupt;
    
    /**
    * 動作状態
    */
    private String opCond;
    
    /**
    * 処理単位
    */
    private ExIoOperationState proUnit;
    
    
}
