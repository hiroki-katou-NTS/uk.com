package nts.uk.ctx.at.record.dom.workrecord.remainingnumbermanagement;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.care.interimdata.TempCareManagement;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RemainLongTermCareCheckParam {

 // 会社ID
    String cID;
    
    // 社員ID
    String sID;
    
    // 年月
    YearMonth yearMonth;
    
    // 期間
    DatePeriod period;
    
    // 締めID
    ClosureId closureId;
    
    // 締め日
    ClosureDate closureDate;
    
    // モード
    boolean mode;
    
    // 上書きフラグ
    boolean overwriteFlag;
    
    // 暫定管理データ
    List<TempCareManagement> interimManage;
    
    // 作成元区分<Optional>
    Optional<CreateAtr> createAtr;
    
    // 対象期間<Optional>
    Optional<DatePeriod> targetPeriod;
}
