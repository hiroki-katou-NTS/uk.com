package nts.uk.ctx.sys.assist.dom.salary;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;

/**
* 給与支払日設定
*/
@AllArgsConstructor
@Getter
public class SetDaySupport extends AggregateRoot
{
    
    /**
    * 会社ID
    */
    private String cid;
    
    /**
    * 処理区分NO
    */
    private int processCateNo;
    
    /**
    * 勤怠締め日
    */
    private GeneralDate closeDateTime;
    
    /**
    * 雇用保険基準日
    */
    private GeneralDate empInsurdStanDate;
    
    /**
    * 経理締め年月日
    */
    private GeneralDate closureDateAccounting;
    
    /**
    * 支払年月日
    */
    private GeneralDate paymentDate;
    
    /**
    * 社員抽出基準日
    */
    private GeneralDate empExtraRefeDate;
    
    /**
    * 社会保険基準日
    */
    private GeneralDate socialInsurdStanDate;
    
    /**
    * 社会保険徴収年月
    */
    private int socialInsurdCollecMonth;
    
    /**
    * 処理年月
    */
    private int processDate;
    
    /**
    * 所得税基準日
    */
    private GeneralDate incomeTaxDate;
    
    /**
    * 要勤務日数
    */
    private String numberWorkDay;

	public static SetDaySupport createFromJavaType(String cid2, int processCateNo2, GeneralDate closeDateTime2,
			GeneralDate empInsurdStanDate2, GeneralDate closureDateAccounting2, GeneralDate paymentDate2,
			GeneralDate empExtraRefeDate2, GeneralDate socialInsurdStanDate2, int socialInsurdCollecMonth2,
			int processDate2, GeneralDate incomeTaxDate2, String numberWorkDay2) {
		// TODO Auto-generated method stub
		return null;
	}
 
    
}
