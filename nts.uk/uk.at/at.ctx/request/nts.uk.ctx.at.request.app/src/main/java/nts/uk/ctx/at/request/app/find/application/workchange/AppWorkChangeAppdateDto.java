package nts.uk.ctx.at.request.app.find.application.workchange;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.request.app.command.application.workchange.AppWorkChangeDispInfoCmd;

/**
 * refactor 4
 * @author anhnm
 *
 */
@AllArgsConstructor
@Getter
public class AppWorkChangeAppdateDto {
    private List<String> empLst;

    private List<String> dateLst;

    private AppWorkChangeDispInfoCmd appWorkChangeDispInfo;
}
