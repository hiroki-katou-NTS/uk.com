/**
 *
 */
package nts.uk.screen.at.app.command.ksu008B;

import lombok.experimental.var;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.aggregation.dom.form9.DetailSettingOfForm9;
import nts.uk.ctx.at.aggregation.dom.form9.Form9Code;
import nts.uk.ctx.at.aggregation.dom.form9.Form9Cover;
import nts.uk.ctx.at.aggregation.dom.form9.Form9LayoutRepository;
import nts.uk.ctx.at.aggregation.dom.form9.Form9Name;
import nts.uk.ctx.at.aggregation.dom.form9.Form9NursingAideTable;
import nts.uk.ctx.at.aggregation.dom.form9.Form9NursingTable;
import nts.uk.ctx.at.aggregation.dom.form9.OnePageDisplayNumerOfPeople;
import nts.uk.ctx.at.aggregation.dom.form9.OutputColumn;
import nts.uk.ctx.at.aggregation.dom.form9.OutputRow;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.primitive.OutputCell;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Optional;

/**
 * @author rafiqul.islam
 * Command: ユーザー定義出力レイアウトを更新登録する
 * Path: UKDesign.UniversalK.就業.KSU_スケジュール.KSU008_様式９.Ｂ：様式９_出力項目設定.B：メニュー別OCD.ユーザー定義出力レイアウトを更新登録する
 */

@Stateless
@Transactional
public class OutPutLayoutUpdateKsu008BHandler extends CommandHandler<OutPutLayoutRegisterKsu008BCommand> {

    @Inject
    private Form9LayoutRepository form9LayoutRepository;

    @Override
    protected void handle(CommandHandlerContext<OutPutLayoutRegisterKsu008BCommand> commandHandlerContext) {
        String loginCompany = AppContexts.user().companyId();
        OutPutLayoutRegisterKsu008BCommand command = commandHandlerContext.getCommand();
        var form9LayoutOpt = form9LayoutRepository.get(loginCompany, new Form9Code(command.getCode()));
        if (form9LayoutOpt.isPresent()) {
            var form9Layout = form9LayoutOpt.get();

            Form9Cover form9Cover = new Form9Cover(
                    Optional.of(new OutputCell(command.getCover().getCellYear())),
                    Optional.of(new OutputCell(command.getCover().getCellMonth())),
                    Optional.of(new OutputCell(command.getCover().getCellStartTime())),
                    Optional.of(new OutputCell(command.getCover().getCellEndTime())),
                    Optional.of(new OutputCell(command.getCover().getCellTitle())),
                    Optional.of(new OutputCell(command.getCover().getCellPrintPeriod()))
            );
            Form9NursingTable nursingTable = new Form9NursingTable(
                    new OutputColumn(command.getNursingTable().getFullName()),
                    new OutputColumn(command.getNursingTable().getDay1StartColumn()),
                    new DetailSettingOfForm9(
                            new OutputRow(command.getNursingTable().getDetailSetting().getBodyStartRow()),
                            new OnePageDisplayNumerOfPeople(command.getNursingTable().getDetailSetting().getMaxNumerOfPeople()),
                            new OutputRow(command.getNursingTable().getDetailSetting().getRowDate()),
                            new OutputRow(command.getNursingTable().getDetailSetting().getRowDayOfWeek())
                    ),
                    Optional.of(new OutputColumn(command.getNursingTable().getLicense())),
                    Optional.of(new OutputColumn(command.getNursingTable().getHospitalWardName())),
                    Optional.of(new OutputColumn(command.getNursingTable().getFullTime())),
                    Optional.of(new OutputColumn(command.getNursingTable().getShortTime())),
                    Optional.of(new OutputColumn(command.getNursingTable().getPartTime())),
                    Optional.of(new OutputColumn(command.getNursingTable().getConcurrentPost())),
                    Optional.of(new OutputColumn(command.getNursingTable().getNightShiftOnly()))
            );
            Form9NursingAideTable nursingAideTable = new Form9NursingAideTable(
                    new OutputColumn(command.getNursingAideTable().getFullName()),
                    new OutputColumn(command.getNursingAideTable().getDay1StartColumn()),
                    new DetailSettingOfForm9(
                            new OutputRow(command.getNursingAideTable().getDetailSetting().getBodyStartRow()),
                            new OnePageDisplayNumerOfPeople(command.getNursingAideTable().getDetailSetting().getMaxNumerOfPeople()),
                            new OutputRow(command.getNursingAideTable().getDetailSetting().getRowDate()),
                            new OutputRow(command.getNursingAideTable().getDetailSetting().getRowDayOfWeek())
                    ),
                    Optional.of(new OutputColumn(command.getNursingAideTable().getHospitalWardName())),
                    Optional.of(new OutputColumn(command.getNursingAideTable().getFullTime())),
                    Optional.of(new OutputColumn(command.getNursingAideTable().getShortTime())),
                    Optional.of(new OutputColumn(command.getNursingAideTable().getPartTime())),
                    Optional.of(new OutputColumn(command.getNursingAideTable().getOfficeWork())),
                    Optional.of(new OutputColumn(command.getNursingAideTable().getConcurrentPost())),
                    Optional.of(new OutputColumn(command.getNursingAideTable().getNightShiftOnly()))
            );
            form9Layout.setCover(form9Cover);
            form9Layout.setName(new Form9Name(command.getName()));
            form9Layout.setNursingAideTable(nursingAideTable);
            form9Layout.setNursingTable(nursingTable);
            form9Layout.setTemplateFileId(Optional.of(command.getTemplateFileId()));
            form9LayoutRepository.updateLayoutOfUser(loginCompany, form9Layout);
        }
    }
}
