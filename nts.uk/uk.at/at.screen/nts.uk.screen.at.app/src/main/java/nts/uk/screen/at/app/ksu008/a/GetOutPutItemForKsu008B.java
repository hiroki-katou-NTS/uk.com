package nts.uk.screen.at.app.ksu008.a;

import nts.arc.layer.app.file.storage.StoredFileInfo;
import nts.arc.layer.infra.file.storage.StoredFileInfoRepository;
import nts.arc.primitive.PrimitiveValueBase;
import nts.uk.ctx.at.aggregation.dom.form9.Form9Code;
import nts.uk.ctx.at.aggregation.dom.form9.Form9Layout;
import nts.uk.ctx.at.aggregation.dom.form9.Form9LayoutRepository;
import nts.uk.screen.at.app.ksu008.a.dto.DetailSettingOfForm9Dto;
import nts.uk.screen.at.app.ksu008.a.dto.Form9CoverDto;
import nts.uk.screen.at.app.ksu008.a.dto.Form9LayoutDto;
import nts.uk.screen.at.app.ksu008.a.dto.Form9NursingAideTableDto;
import nts.uk.screen.at.app.ksu008.a.dto.Form9NursingTableDto;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * UKDesign.UniversalK.就業.KSU_スケジュール.KSU008_様式９.Ｂ：様式９_出力項目設定.B：メニュー別OCD.出力項目設定_画面表示
 *
 * @author rafiqul.islam
 */
@Stateless
public class GetOutPutItemForKsu008B {

    @Inject
    private Form9LayoutRepository form9LayoutRepository;

    @Inject
    private StoredFileInfoRepository fileRepo;

    public List<Form9LayoutDto> get(boolean isSystemFixed) {
        List<Form9Layout> form9Layouts = form9LayoutRepository.getLayoutBySystemFixedAttr(AppContexts.user().companyId(), isSystemFixed);
        if (form9Layouts.isEmpty()) {
            return Collections.emptyList();
        }
        Form9Layout.Require require = new Form9Layout.Require() {
            @Override
            public Optional<StoredFileInfo> getInfo(String fileId) {
                return fileRepo.find(fileId);
            }
            @Override
            public StoredFileInfo saveFile(String fileName) {
                return null;
            }
        };
        return form9Layouts.stream().map(
                x -> new Form9LayoutDto(
                        x.getCode().v(),
                        x.getName().v(),
                        x.isSystemFixed(),
                        x.isUse(),
                        new Form9CoverDto(
                                x.getCover().getCellYear().map(PrimitiveValueBase::v).orElse(null),
                                x.getCover().getCellMonth().map(PrimitiveValueBase::v).orElse(null),
                                x.getCover().getCellStartTime().map(PrimitiveValueBase::v).orElse(null),
                                x.getCover().getCellEndTime().map(PrimitiveValueBase::v).orElse(null),
                                x.getCover().getCellTitle().map(PrimitiveValueBase::v).orElse(null),
                                x.getCover().getCellPrintPeriod().map(PrimitiveValueBase::v).orElse(null)
                        ),
                        new Form9NursingTableDto(
                                x.getNursingTable().getFullName().v(),
                                x.getNursingTable().getDay1StartColumn().v(),
                                new DetailSettingOfForm9Dto(
                                        x.getNursingTable().getDetailSetting().getBodyStartRow().v(),
                                        x.getNursingTable().getDetailSetting().getMaxNumerOfPeople().v(),
                                        x.getNursingTable().getDetailSetting().getRowDate().v(),
                                        x.getNursingTable().getDetailSetting().getRowDayOfWeek().v()
                                ),
                                x.getNursingTable().getLicense().map(PrimitiveValueBase::v).orElse(null),
                                x.getNursingTable().getHospitalWardName().map(PrimitiveValueBase::v).orElse(null),
                                x.getNursingTable().getFullTime().map(PrimitiveValueBase::v).orElse(null),
                                x.getNursingTable().getShortTime().map(PrimitiveValueBase::v).orElse(null),
                                x.getNursingTable().getPartTime().map(PrimitiveValueBase::v).orElse(null),
                                x.getNursingTable().getConcurrentPost().map(PrimitiveValueBase::v).orElse(null),
                                x.getNursingTable().getNightShiftOnly().map(PrimitiveValueBase::v).orElse(null)
                        ),
                        new Form9NursingAideTableDto(
                                x.getNursingAideTable().getFullName().v(),
                                x.getNursingAideTable().getDay1StartColumn().v(),
                                new DetailSettingOfForm9Dto(
                                        x.getNursingAideTable().getDetailSetting().getBodyStartRow().v(),
                                        x.getNursingAideTable().getDetailSetting().getMaxNumerOfPeople().v(),
                                        x.getNursingAideTable().getDetailSetting().getRowDate().v(),
                                        x.getNursingAideTable().getDetailSetting().getRowDayOfWeek().v()
                                ),
                                x.getNursingAideTable().getHospitalWardName().map(PrimitiveValueBase::v).orElse(null),
                                x.getNursingAideTable().getFullTime().map(PrimitiveValueBase::v).orElse(null),
                                x.getNursingAideTable().getShortTime().map(PrimitiveValueBase::v).orElse(null),
                                x.getNursingAideTable().getPartTime().map(PrimitiveValueBase::v).orElse(null),
                                x.getNursingAideTable().getOfficeWork().map(PrimitiveValueBase::v).orElse(null),
                                x.getNursingAideTable().getConcurrentPost().map(PrimitiveValueBase::v).orElse(null),
                                x.getNursingAideTable().getNightShiftOnly().map(PrimitiveValueBase::v).orElse(null)
                        ),
                        x.getTemplateFileId().orElse(null),
                        x.getFileName(require)
                )
        ).collect(Collectors.toList());
    }

    public Form9LayoutDto get(String code) {
        Form9Layout.Require require = new Form9Layout.Require() {
            @Override
            public Optional<StoredFileInfo> getInfo(String fileId) {
                return fileRepo.find(fileId);
            }
            @Override
            public StoredFileInfo saveFile(String fileName) {
                return null;
            }
        };
        return form9LayoutRepository.get(AppContexts.user().companyId(), new Form9Code(code)).map(x -> new Form9LayoutDto(
                x.getCode().v(),
                x.getName().v(),
                x.isSystemFixed(),
                x.isUse(),
                new Form9CoverDto(
                        x.getCover().getCellYear().map(PrimitiveValueBase::v).orElse(null),
                        x.getCover().getCellMonth().map(PrimitiveValueBase::v).orElse(null),
                        x.getCover().getCellStartTime().map(PrimitiveValueBase::v).orElse(null),
                        x.getCover().getCellEndTime().map(PrimitiveValueBase::v).orElse(null),
                        x.getCover().getCellTitle().map(PrimitiveValueBase::v).orElse(null),
                        x.getCover().getCellPrintPeriod().map(PrimitiveValueBase::v).orElse(null)
                ),
                new Form9NursingTableDto(
                        x.getNursingTable().getFullName().v(),
                        x.getNursingTable().getDay1StartColumn().v(),
                        new DetailSettingOfForm9Dto(
                                x.getNursingTable().getDetailSetting().getBodyStartRow().v(),
                                x.getNursingTable().getDetailSetting().getMaxNumerOfPeople().v(),
                                x.getNursingTable().getDetailSetting().getRowDate().v(),
                                x.getNursingTable().getDetailSetting().getRowDayOfWeek().v()
                        ),
                        x.getNursingTable().getLicense().map(PrimitiveValueBase::v).orElse(null),
                        x.getNursingTable().getHospitalWardName().map(PrimitiveValueBase::v).orElse(null),
                        x.getNursingTable().getFullTime().map(PrimitiveValueBase::v).orElse(null),
                        x.getNursingTable().getShortTime().map(PrimitiveValueBase::v).orElse(null),
                        x.getNursingTable().getPartTime().map(PrimitiveValueBase::v).orElse(null),
                        x.getNursingTable().getConcurrentPost().map(PrimitiveValueBase::v).orElse(null),
                        x.getNursingTable().getNightShiftOnly().map(PrimitiveValueBase::v).orElse(null)
                ),
                new Form9NursingAideTableDto(
                        x.getNursingAideTable().getFullName().v(),
                        x.getNursingAideTable().getDay1StartColumn().v(),
                        new DetailSettingOfForm9Dto(
                                x.getNursingAideTable().getDetailSetting().getBodyStartRow().v(),
                                x.getNursingAideTable().getDetailSetting().getMaxNumerOfPeople().v(),
                                x.getNursingAideTable().getDetailSetting().getRowDate().v(),
                                x.getNursingAideTable().getDetailSetting().getRowDayOfWeek().v()
                        ),
                        x.getNursingAideTable().getHospitalWardName().map(PrimitiveValueBase::v).orElse(null),
                        x.getNursingAideTable().getFullTime().map(PrimitiveValueBase::v).orElse(null),
                        x.getNursingAideTable().getShortTime().map(PrimitiveValueBase::v).orElse(null),
                        x.getNursingAideTable().getPartTime().map(PrimitiveValueBase::v).orElse(null),
                        x.getNursingAideTable().getOfficeWork().map(PrimitiveValueBase::v).orElse(null),
                        x.getNursingAideTable().getConcurrentPost().map(PrimitiveValueBase::v).orElse(null),
                        x.getNursingAideTable().getNightShiftOnly().map(PrimitiveValueBase::v).orElse(null)
                ),
                x.getTemplateFileId().orElse(null),
                x.getFileName(require)
        )).orElse(null);
    }
}
