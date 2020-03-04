package nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset;

import java.util.List;
import java.util.Optional;

/**
* ローマ字氏名届作成設定
*/
public interface RomajiNameNotiCreSetRepository
{

    Optional<RomajiNameNotiCreSetting> getRomajiNameNotiCreSettingById();
    void register(RomajiNameNotiCreSetting domain);


}
