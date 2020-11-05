package nts.uk.ctx.at.request.dom.application.optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.optionalitemappsetting.OptionalItemApplicationTypeCode;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.optionalitemvalue.AnyItemValue;

import java.util.List;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.申請承認.申請.任意項目申請.任意項目申請
 *
 * @author thanh.hc
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OptionalItemApplication extends Application {
    /**
     * コード
     */

    private OptionalItemApplicationTypeCode code;
    /**
     * 申請値
     */
    private List<AnyItemValue> optionalItems;
}
