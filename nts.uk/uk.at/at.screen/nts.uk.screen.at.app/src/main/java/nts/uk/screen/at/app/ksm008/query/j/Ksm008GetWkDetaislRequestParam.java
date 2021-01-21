package nts.uk.screen.at.app.ksm008.query.j;

import lombok.Data;

@Data
public class Ksm008GetWkDetaislRequestParam {

    /** 職場グループ */
    private int workPlaceUnit;

    /** ID */
    private String workPlaceId;

    /** 入力対象（項目コード） */
    private String workPlaceGroup;

    /**
     * コード
     */
    private String code;
}
