package nts.uk.ctx.exio.dom.input.setting.copy;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportCode;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportSetting;
import nts.uk.ctx.exio.dom.input.setting.assembly.revise.ReviseItem;
import nts.uk.shr.com.constants.DefaultSettingKeys;

import java.util.List;
import java.util.Optional;

/**
 * 外部受入の初期データを生成する
 */
public class GenerateExternalImportDefaultData {

    public static AtomTask generate(
            Require require,
            String companyId,
            ExternalImportCode generatingCode,
            ExternalImportDefaultDataItem generatingTargetItem) {

        if (require.existsExternalImportSetting(companyId, generatingCode)) {
            throw new BusinessException("Msg_3");
        }

        val setting = require.getExternalImportSetting(
                DefaultSettingKeys.COMPANY_ID,
                generatingTargetItem.getSourceDataCode()).get();

        val reviseItems = require.getReviseItems(
        			DefaultSettingKeys.COMPANY_ID,
        			generatingTargetItem.getSourceDataCode()
        		);
        
        return AtomTask.of(() -> {

            String fileId = generatingTargetItem.storeBaseCsvFile(require);

            setting.changeForCopy(companyId, generatingCode, fileId);

            require.addSetting(setting);
            
            reviseItems.forEach(reviseItem ->{
            	reviseItem.changeForCopy(companyId, generatingCode);
            });
            
            require.addReviseItems(reviseItems);

        });
    }

    public interface Require extends ExternalImportDefaultDataItem.RequireStoreBaseCsvFile {

        boolean existsExternalImportSetting(String companyId, ExternalImportCode code);

        Optional<ExternalImportSetting> getExternalImportSetting(String companyId, ExternalImportCode code);

        void addSetting(ExternalImportSetting setting);
        
        List<ReviseItem> getReviseItems(String companyId, ExternalImportCode code);
        
        void addReviseItems(List<ReviseItem> reviseItems);
    }
}
