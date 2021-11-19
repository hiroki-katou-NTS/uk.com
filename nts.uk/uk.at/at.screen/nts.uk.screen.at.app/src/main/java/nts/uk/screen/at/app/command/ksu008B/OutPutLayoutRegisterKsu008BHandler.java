/**
 *
 */
package nts.uk.screen.at.app.command.ksu008B;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.aggregation.dom.form9.*;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.primitive.OutputCell;
import org.apache.commons.lang3.StringUtils;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Optional;

/**
 * @author rafiqul.islam
 * Command: ユーザー定義出力レイアウトを新規登録する
 * Path: UKDesign.UniversalK.就業.KSU_スケジュール.KSU008_様式９.Ｂ：様式９_出力項目設定.B：メニュー別OCD.ユーザー定義出力レイアウトを新規登録する
 */

@Stateless
@Transactional
public class OutPutLayoutRegisterKsu008BHandler extends CommandHandler<OutPutLayoutRegisterKsu008BCommand> {

    @Inject
    private Form9LayoutRepository form9LayoutRepository;

    @Override
    protected void handle(CommandHandlerContext<OutPutLayoutRegisterKsu008BCommand> commandHandlerContext) {
        String loginCompany = AppContexts.user().companyId();
        OutPutLayoutRegisterKsu008BCommand command = commandHandlerContext.getCommand();
        val form9Layout = form9LayoutRepository.get(loginCompany, new Form9Code(command.getCode()));
        if (form9Layout.isPresent()) {
            throw new BusinessException("Msg_3");
        }
        Form9Cover form9Cover = Form9Cover.create(
                StringUtils.isEmpty(command.getCover().getCellYear()) ? Optional.empty() : Optional.of(new OutputCell(command.getCover().getCellYear())),
                StringUtils.isEmpty(command.getCover().getCellMonth()) ? Optional.empty() : Optional.of(new OutputCell(command.getCover().getCellMonth())),
                StringUtils.isEmpty(command.getCover().getCellStartTime()) ? Optional.empty() : Optional.of(new OutputCell(command.getCover().getCellStartTime())),
                StringUtils.isEmpty(command.getCover().getCellEndTime()) ? Optional.empty() : Optional.of(new OutputCell(command.getCover().getCellEndTime())),
                StringUtils.isEmpty(command.getCover().getCellTitle()) ? Optional.empty() : Optional.of(new OutputCell(command.getCover().getCellTitle())),
                StringUtils.isEmpty(command.getCover().getCellPrintPeriod()) ? Optional.empty() : Optional.of(new OutputCell(command.getCover().getCellPrintPeriod()))
        );
        Form9NursingTable nursingTable = Form9NursingTable.create(
                new OutputColumn(command.getNursingTable().getFullName()),
                new OutputColumn(command.getNursingTable().getDay1StartColumn()),
                new DetailSettingOfForm9(
                        new OutputRow(command.getNursingTable().getDetailSetting().getBodyStartRow()),
                        new OnePageDisplayNumerOfPeople(command.getNursingTable().getDetailSetting().getMaxNumerOfPeople()),
                        new OutputRow(command.getNursingTable().getDetailSetting().getRowDate()),
                        new OutputRow(command.getNursingTable().getDetailSetting().getRowDayOfWeek())
                ),
                StringUtils.isEmpty(command.getNursingTable().getLicense()) ? Optional.empty() : Optional.of(new OutputColumn(command.getNursingTable().getLicense())),
                StringUtils.isEmpty(command.getNursingTable().getHospitalWardName()) ? Optional.empty() : Optional.of(new OutputColumn(command.getNursingTable().getHospitalWardName())),
                StringUtils.isEmpty(command.getNursingTable().getFullTime()) ? Optional.empty() : Optional.of(new OutputColumn(command.getNursingTable().getFullTime())),
                StringUtils.isEmpty(command.getNursingTable().getShortTime()) ? Optional.empty() : Optional.of(new OutputColumn(command.getNursingTable().getShortTime())),
                StringUtils.isEmpty(command.getNursingTable().getPartTime()) ? Optional.empty() : Optional.of(new OutputColumn(command.getNursingTable().getPartTime())),
                StringUtils.isEmpty(command.getNursingTable().getConcurrentPost()) ? Optional.empty() : Optional.of(new OutputColumn(command.getNursingTable().getConcurrentPost())),
                StringUtils.isEmpty(command.getNursingTable().getNightShiftOnly()) ? Optional.empty() : Optional.of(new OutputColumn(command.getNursingTable().getNightShiftOnly()))
        );
        Form9NursingAideTable nursingAideTable = Form9NursingAideTable.create(
                new OutputColumn(command.getNursingAideTable().getFullName()),
                new OutputColumn(command.getNursingAideTable().getDay1StartColumn()),
                new DetailSettingOfForm9(
                        new OutputRow(command.getNursingAideTable().getDetailSetting().getBodyStartRow()),
                        new OnePageDisplayNumerOfPeople(command.getNursingAideTable().getDetailSetting().getMaxNumerOfPeople()),
                        new OutputRow(command.getNursingAideTable().getDetailSetting().getRowDate()),
                        new OutputRow(command.getNursingAideTable().getDetailSetting().getRowDayOfWeek())
                ),
                StringUtils.isEmpty(command.getNursingAideTable().getHospitalWardName()) ? Optional.empty() : Optional.of(new OutputColumn(command.getNursingAideTable().getHospitalWardName())),
                StringUtils.isEmpty(command.getNursingAideTable().getFullTime()) ? Optional.empty() : Optional.of(new OutputColumn(command.getNursingAideTable().getFullTime())),
                StringUtils.isEmpty(command.getNursingAideTable().getShortTime()) ? Optional.empty() : Optional.of(new OutputColumn(command.getNursingAideTable().getShortTime())),
                StringUtils.isEmpty(command.getNursingAideTable().getPartTime()) ? Optional.empty() : Optional.of(new OutputColumn(command.getNursingAideTable().getPartTime())),
                StringUtils.isEmpty(command.getNursingAideTable().getOfficeWork()) ? Optional.empty() : Optional.of(new OutputColumn(command.getNursingAideTable().getOfficeWork())),
                StringUtils.isEmpty(command.getNursingAideTable().getConcurrentPost()) ? Optional.empty() : Optional.of(new OutputColumn(command.getNursingAideTable().getConcurrentPost())),
                StringUtils.isEmpty(command.getNursingAideTable().getNightShiftOnly()) ? Optional.empty() : Optional.of(new OutputColumn(command.getNursingAideTable().getNightShiftOnly()))
        );
        Form9Layout form9LayoutNew = Form9Layout.create(
                new Form9Code(command.getCode()),
                new Form9Name(command.getName()),
                command.isSystemFixed(),
                command.isUse(),
                form9Cover,
                nursingTable,
                nursingAideTable,
                Optional.ofNullable(command.getTemplateFileId())
        );
        form9LayoutRepository.insertLayoutOfUser(loginCompany, form9LayoutNew);
    }
}
