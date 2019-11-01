package nts.uk.ctx.pr.report.dom.printconfig.empinsreportsetting;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;

/**
* 雇用保険届作成設定
*/
@Getter
public class EmpInsReportSetting extends AggregateRoot {
    
    /**
    * 会社ID
    */
    private String cid;
    
    /**
    * ユーザID
    */
    private String userId;
    
    /**
    * 提出氏名
    */
    private EmpSubNameClass submitNameAtr;
    
    /**
    * 出力順
    */
    private EmpInsOutOrder outputOrderAtr;
    
    /**
    * 事業所区分
    */
    private OfficeCls officeClsAtr;
    
    /**
    * マイナンバー印字区分
    */
    private PrinfCtg myNumberClsAtr;
    
    /**
    * 変更後氏名印字区分
    */
    private PrinfCtg nameChangeClsAtr;
    
    public EmpInsReportSetting(String cid, String userId, int submitNameAtr, int outputOrderAtr, int officeClsAtr, int myNumberClsAtr, int nameChangeClsAtr) {
        this.cid = cid;
        this.userId = userId;
        this.submitNameAtr = EnumAdaptor.valueOf(submitNameAtr, EmpSubNameClass.class);
        this.outputOrderAtr = EnumAdaptor.valueOf(outputOrderAtr, EmpInsOutOrder.class);
        this.officeClsAtr = EnumAdaptor.valueOf(officeClsAtr, OfficeCls.class);
        this.myNumberClsAtr = EnumAdaptor.valueOf(myNumberClsAtr, PrinfCtg.class);
        this.nameChangeClsAtr = EnumAdaptor.valueOf(nameChangeClsAtr, PrinfCtg.class);
    }
    
}
