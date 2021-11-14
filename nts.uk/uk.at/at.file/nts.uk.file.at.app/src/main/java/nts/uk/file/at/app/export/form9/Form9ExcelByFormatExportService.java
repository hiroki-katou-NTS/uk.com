package nts.uk.file.at.app.export.form9;

import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.aggregation.dom.form9.*;
import nts.uk.ctx.at.shared.dom.common.timerounding.Rounding;
import nts.uk.file.at.app.export.form9.dto.DisplayInfoRelatedToWorkplaceGroupDto;
import nts.uk.file.at.app.export.form9.dto.Form9ColorSettingsDTO;
import nts.uk.file.at.app.export.form9.dto.WorkplaceGroupInfoDto;
import nts.uk.shr.com.primitive.OutputCell;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.*;

/**
 * Excel形式で出力
 * UKDesign.UniversalK.就業.KSU_スケジュール.KSU008_様式９.Ａ：様式９.Ａ：メニュー別OCD.Excel形式で印刷
 */
@Stateless
public class Form9ExcelByFormatExportService extends ExportService<Form9ExcelByFormatQuery> {

    @Inject
    private CreateForm9FileQuery createForm9Query;

    @Inject
    private Form9ExcelByFormatExportGenerator exportGenerator;

    @Override
    protected void handle(ExportServiceContext<Form9ExcelByFormatQuery> exportServiceContext) {
        Form9ExcelByFormatQuery query = exportServiceContext.getQuery();

         // 1.1. 出力する(対象期間, 年月, int, 様式９のコード)
        Form9ExcelByFormatDataSource dataSource = this.createForm9Query.get(query);

//        List<DisplayInfoRelatedToWorkplaceGroupDto> infoRelatedWkpGroups = new ArrayList<>();
//        for (int i = 1; i <= 2; i++) {
//            infoRelatedWkpGroups.add(new DisplayInfoRelatedToWorkplaceGroupDto(
//                    Collections.emptyMap(),
//                    Optional.empty(),
//                    "workplace-group-id-" + i,
//                    "workplace-group-code-" + i,
//                    "workplace-group-name-" + i,
//                    Collections.emptyList()
//            ));
//        }
//        Form9ExcelByFormatDataSource dataSource = new Form9ExcelByFormatDataSource(
//                new Form9Layout(
//                        new Form9Code("form9-code"),
//                        new Form9Name("form9-name"),
//                        true,
//                        true,
//                        new Form9Cover(
//                                Optional.of(new OutputCell("A")),
//                                Optional.of(new OutputCell("A")),
//                                Optional.of(new OutputCell("A")),
//                                Optional.of(new OutputCell("A")),
//                                Optional.of(new OutputCell("A")),
//                                Optional.of(new OutputCell("A"))
//                                ),
//                        new Form9NursingTable(
//                                new OutputColumn("B"),
//                                new OutputColumn("C"),
//                                null,      //new DetailSettingOfForm9()
//                                Optional.of(new OutputColumn("D")),
//                                Optional.of(new OutputColumn("D")),
//                                Optional.of(new OutputColumn("D")),
//                                Optional.of(new OutputColumn("D")),
//                                Optional.of(new OutputColumn("D")),
//                                Optional.of(new OutputColumn("D")),
//                                Optional.of(new OutputColumn("D"))
//                        ),
//                        new Form9NursingAideTable(
//                                new OutputColumn("B"),
//                                new OutputColumn("C"),
//                                null,      //new DetailSettingOfForm9()
//                                Optional.of(new OutputColumn("D")),
//                                Optional.of(new OutputColumn("D")),
//                                Optional.of(new OutputColumn("D")),
//                                Optional.of(new OutputColumn("D")),
//                                Optional.of(new OutputColumn("D")),
//                                Optional.of(new OutputColumn("D")),
//                                Optional.of(new OutputColumn("D"))
//                        ),
//                        Optional.empty()
//
//
//                ),
//                "",
//                new Form9DetailOutputSetting(
//                        new Form9TimeRoundingSetting(
//                                RoundingUnit.THREE_DIGIT,
//                                Rounding.ROUNDING_DOWN
//                        ),
//                        true
//                ),
//                infoRelatedWkpGroups
//        );
//
//        List<WorkplaceGroupInfoDto> wkpGrs = new ArrayList<>();
//        for (int i = 1; i <= 2; i++) {
//            wkpGrs.add(
//                    new WorkplaceGroupInfoDto(
//                            "workplace-group-id-" + i,
//                            "workplace-group-code-" + i,
//                            "workplace-group-name-" + i
//                    )
//            );
//        }
//        Form9ExcelByFormatQuery query = new Form9ExcelByFormatQuery(
//                GeneralDate.fromString("2021/11/01", "yyyy/MM/dd"),
//                GeneralDate.fromString("2021/11/10", "yyyy/MM/dd"),
//                wkpGrs,
//                "form9-code-1",
//                2,
//                new Form9ColorSettingsDTO()
//        );

        // 1.2. create report Form9
        this.exportGenerator.generate(exportServiceContext.getGeneratorContext(), dataSource, query);
    }
}
