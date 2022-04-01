package nts.uk.screen.at.app.command.ksu008B;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkScheduleSaveKsu008BCommand {
    // 様式９のコード
    public String form9Code;
    /**
     * 利用区分
     */
    private boolean isUse;
}
