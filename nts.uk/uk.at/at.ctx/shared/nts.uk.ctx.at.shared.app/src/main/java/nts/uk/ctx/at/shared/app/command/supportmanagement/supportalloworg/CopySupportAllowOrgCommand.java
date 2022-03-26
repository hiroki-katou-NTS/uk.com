package nts.uk.ctx.at.shared.app.command.supportmanagement.supportalloworg;

import lombok.Data;

import java.util.List;

@Data
public class CopySupportAllowOrgCommand {
    /** 複写先リスト : 職場IDリスト */
    private List<String> copyDestinationWkpIds;
    /** 上書きするか*/
    private boolean overWrite;

    /** 組織選択リスト: 選択職場の職場ID（複写元） */
    private String copySourceWkpId;
}
