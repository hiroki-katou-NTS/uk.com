package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.optionalitemappsetting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Optional;

/**
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.設定.会社別.申請承認設定.任意項目申請設定
 * 任意項目申請設定
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class OptionalItemApplicationSetting extends AggregateRoot {
    /**
     * 会社ID
     */
    private String companyId;

    /**
     * コード
     */
    private OptionalItemApplicationTypeCode code;

    /**
     * 申請種類名
     */
    private OptionalItemApplicationTypeName name;

    /**
     * 利用区分
     */
    private boolean useAtr;

    /**
     * 任意項目申請の説明文
     */
    private Optional<OptionalItemApplicationDescription> description;

    /**
     * 申請設定項目
     */
    private List<ApplicationSettingItem> settingItems;

    public static OptionalItemApplicationSetting create(String companyId, String code, String name, int useAtr, String note, List<ApplicationSettingItem> settingItems) {
        return new OptionalItemApplicationSetting(
                companyId,
                new OptionalItemApplicationTypeCode(code),
                new OptionalItemApplicationTypeName(name),
                BooleanUtils.toBoolean(useAtr),
                StringUtils.isEmpty(note) ? Optional.empty() : Optional.of(new OptionalItemApplicationDescription(note)),
                settingItems
        );
    }
}
