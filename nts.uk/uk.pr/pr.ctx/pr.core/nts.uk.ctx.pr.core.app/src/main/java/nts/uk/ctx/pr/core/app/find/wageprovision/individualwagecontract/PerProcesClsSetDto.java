package nts.uk.ctx.pr.core.app.find.wageprovision.individualwagecontract;


import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.PerProcessClsSet;

/**
 * 個人処理区分設定
 */
@AllArgsConstructor
@Value
public class PerProcesClsSetDto {
    /**
     * 処理区分NO
     */
    private String processCateNo;

    /**
     * ユーザID
     */
    private String uid;

    /**
     * 会社ID
     */
    private String cid;

    public static PerProcesClsSetDto fromDomain(PerProcessClsSet domain){
        return new PerProcesClsSetDto(domain.getProcessCateNo(),domain.getUid(),domain.getCid());
    }
}
