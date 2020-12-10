package nts.uk.screen.at.app.kmk.kmk008.unitofapprove;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.approveregister.UnitOfApprover;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnitOfApproveDto {

    /**
     * 職場を利用する
     */
    private int useWorkplace;

    public static UnitOfApproveDto setData(UnitOfApprover data){
        if (data == null){
            return new UnitOfApproveDto();
        }
        return new UnitOfApproveDto(data.getUseWorkplace().value);
    }
}
