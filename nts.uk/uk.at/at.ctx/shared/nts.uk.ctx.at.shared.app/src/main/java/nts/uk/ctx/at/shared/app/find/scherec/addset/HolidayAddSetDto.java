package nts.uk.ctx.at.shared.app.find.scherec.addset;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HolidayAddSetDto {

    // 参照先.会社一律加算時間.1日
    private Integer oneDay;

    // 参照先.会社一律加算時間.午前
    private Integer morning;

    // 参照先.会社一律加算時間.午後
    private Integer afternoon;

    // 参照先.参照先設定
    private Integer refSet;

    // 参照先.個人別設定参照先
    private Integer personSetRef;

}
