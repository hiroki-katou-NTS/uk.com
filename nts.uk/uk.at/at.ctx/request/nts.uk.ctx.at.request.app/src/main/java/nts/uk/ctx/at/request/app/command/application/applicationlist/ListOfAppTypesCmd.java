package nts.uk.ctx.at.request.app.command.application.applicationlist;

import java.util.Optional;

import lombok.Data;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.applist.service.ApplicationTypeDisplay;
import nts.uk.ctx.at.request.dom.application.applist.service.ListOfAppTypes;
import org.apache.commons.lang3.StringUtils;

/**
 * refactor 4
 *
 * @author Doan Duy Hung
 */
@Data
public class ListOfAppTypesCmd {
    /**
     * 申請種類
     */
    private int appType;

    /**
     * 申請名称
     */
    private String appName;

    /**
     * 選択
     */
    private boolean choice;

    /**
     * プログラムID
     */
    private String opProgramID;

    /**
     * 申請種類表示
     */
    private Integer opApplicationTypeDisplay;

    /**
     * 文字列
     */
    private String opString;

    public ListOfAppTypes toDomain() {
        return new ListOfAppTypes(
                EnumAdaptor.valueOf(appType, ApplicationType.class),
                appName,
                choice,
                !StringUtils.isEmpty(opProgramID) ? Optional.of(opProgramID) : Optional.empty(),
                opApplicationTypeDisplay != null
                        ? (appType == ApplicationType.OVER_TIME_APPLICATION.value ? Optional.of(EnumAdaptor.valueOf(opApplicationTypeDisplay, ApplicationTypeDisplay.class)) : Optional.of(opApplicationTypeDisplay == 0 ? ApplicationTypeDisplay.STAMP_ADDITIONAL : ApplicationTypeDisplay.STAMP_ONLINE_RECORD))
                        : Optional.empty(),
                !StringUtils.isEmpty(opString) ? Optional.of(opString) : Optional.empty());
    }
}
