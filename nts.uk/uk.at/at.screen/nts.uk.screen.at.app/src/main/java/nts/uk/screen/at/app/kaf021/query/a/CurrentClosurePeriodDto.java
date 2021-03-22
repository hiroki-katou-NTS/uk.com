package nts.uk.screen.at.app.kaf021.query.a;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CurrentClosurePeriodDto {
    /**
     * 締めＩＤ
     */
    private Integer closureId;

    /**
     * 処理年月
     */
    private Integer processingYm;
}
