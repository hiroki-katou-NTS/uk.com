package nts.uk.ctx.exio.dom.input.setting.copy;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportCode;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportSetting;
import nts.uk.shr.com.constants.DefaultSettingKeys;

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

        return AtomTask.of(() -> {

            String fileId = generatingTargetItem.storeBaseCsvFile(require);

            setting.changeForCopy(companyId, generatingCode, fileId);

            require.add(setting);

        });
    }

    public interface Require extends ExternalImportDefaultDataItem.RequireStoreBaseCsvFile {

        boolean existsExternalImportSetting(String companyId, ExternalImportCode code);

        Optional<ExternalImportSetting> getExternalImportSetting(String companyId, ExternalImportCode code);

        void add(ExternalImportSetting setting);
    }
}
