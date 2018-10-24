package nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting;

import java.util.Optional;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.enums.EnumAdaptor;

/**
* 明細書利用単位設定
*/
@Getter
public class StateUseUnitSetting extends AggregateRoot {
    
    /**
    * 会社ID
    */
    private String companyID;
    
    /**
    * マスタ利用区分
    */
    private SettingUseClassification masterUse;
    
    /**
    * 個人利用区分
    */
    private SettingUseClassification individualUse;
    
    /**
    * 利用マスタ
    */
    private Optional<UsageMaster> usageMaster;
    
    public StateUseUnitSetting(String cid, int masterUseClass, int individualUseClass, Integer useMaster) {
        this.companyID = cid;
        this.masterUse = EnumAdaptor.valueOf(masterUseClass, SettingUseClassification.class);
        this.individualUse = EnumAdaptor.valueOf(individualUseClass, SettingUseClassification.class);
        this.usageMaster = useMaster == null ? Optional.empty() : Optional.of(EnumAdaptor.valueOf(useMaster, UsageMaster.class));
    }
    
}
