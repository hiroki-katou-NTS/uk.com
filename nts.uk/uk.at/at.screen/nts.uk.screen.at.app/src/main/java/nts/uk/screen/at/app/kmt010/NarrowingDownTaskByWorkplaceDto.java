package nts.uk.screen.at.app.kmt010;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class NarrowingDownTaskByWorkplaceDto {
    private String workPlaceId;
    // 作業枠NO
    private Integer taskFrameNo;
    // 作業一覧
    private List<String> taskCodeList;
}
