package nts.uk.ctx.at.function.app.query.outputworkstatustable;

import lombok.val;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.WorkStatusOutputSettings;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.WorkStatusOutputSettingsRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

/**
 * Query:勤務状況表の出力設定の詳細を取得する
 * @author chinh.hm
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class GetDetailOutputSettingWorkStatusQuery {

    @Inject
    private WorkStatusOutputSettingsRepository outputSettingsRepository;
    public WorkStatusOutputSettings getDetail(String settingId){
        val cid = AppContexts.user().companyId();
      return outputSettingsRepository.getWorkStatusOutputSettings(cid,settingId);
    }
}
