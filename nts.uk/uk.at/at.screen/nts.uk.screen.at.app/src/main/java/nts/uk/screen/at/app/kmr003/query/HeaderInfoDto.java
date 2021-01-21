package nts.uk.screen.at.app.kmr003.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * ヘッダー情報
 * @author Le Huu Dat
 */
@Setter
@Getter
@AllArgsConstructor
public class HeaderInfoDto {
    /** 単位 */
    private String unit;
    /** 弁当名 */
    private String bentoName;
    /** 枠番 */
    private int frameNo;
}
