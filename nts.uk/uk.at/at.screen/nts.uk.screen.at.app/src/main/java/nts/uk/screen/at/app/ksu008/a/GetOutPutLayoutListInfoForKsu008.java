package nts.uk.screen.at.app.ksu008.a;

import nts.arc.layer.app.file.storage.StoredFileInfo;
import nts.arc.layer.infra.file.storage.StoredFileInfoRepository;
import nts.uk.ctx.at.aggregation.dom.form9.Form9Layout;
import nts.uk.ctx.at.aggregation.dom.form9.Form9LayoutRepository;
import nts.uk.screen.at.app.ksu008.a.dto.DetailSettingOfForm9Dto;
import nts.uk.screen.at.app.ksu008.a.dto.Form9CoverDto;
import nts.uk.screen.at.app.ksu008.a.dto.Form9LayoutDto;
import nts.uk.screen.at.app.ksu008.a.dto.Form9NursingAideTableDto;
import nts.uk.screen.at.app.ksu008.a.dto.Form9NursingTableDto;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * UKDesign.UniversalK.就業.KSU_スケジュール.KSU008_様式９.Ａ：様式９.Ａ：メニュー別OCD.出力項目設定一覧情報再表示
 *
 * @author rafiqul.islam
 */
@Stateless
public class GetOutPutLayoutListInfoForKsu008 {

    @Inject
    private Form9LayoutRepository form9LayoutRepository;

    @Inject
    private StoredFileInfoRepository fileRepo;

    public List<Form9LayoutDto> get(String companyId) {
        List<Form9Layout> form9Layouts = form9LayoutRepository.getAllLayoutUse(companyId);
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
                                x.getCover().getCellYear().isPresent() ? x.getCover().getCellYear().get().v() : null,
                                x.getCover().getCellMonth().isPresent() ? x.getCover().getCellMonth().get().v() : null,
                                x.getCover().getCellStartTime().isPresent() ? x.getCover().getCellStartTime().get().v() : null,
                                x.getCover().getCellEndTime().isPresent() ? x.getCover().getCellEndTime().get().v() : null,
                                x.getCover().getCellTitle().isPresent() ? x.getCover().getCellTitle().get().v() : null,
                                x.getCover().getCellPrintPeriod().isPresent() ? x.getCover().getCellPrintPeriod().get().v() : null
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
                                x.getNursingTable().getLicense().isPresent() ? x.getNursingTable().getLicense().get().v() : null,
                                x.getNursingTable().getHospitalWardName().isPresent() ? x.getNursingTable().getHospitalWardName().get().v() : null,
                                x.getNursingTable().getFullTime().isPresent() ? x.getNursingTable().getFullTime().get().v() : null,
                                x.getNursingTable().getShortTime().isPresent() ? x.getNursingTable().getShortTime().get().v() : null,
                                x.getNursingTable().getPartTime().isPresent() ? x.getNursingTable().getPartTime().get().v() : null,
                                x.getNursingTable().getConcurrentPost().isPresent() ? x.getNursingTable().getConcurrentPost().get().v() : null,
                                x.getNursingTable().getNightShiftOnly().isPresent() ? x.getNursingTable().getNightShiftOnly().get().v() : null
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
                                x.getNursingAideTable().getHospitalWardName().isPresent() ? x.getNursingAideTable().getHospitalWardName().get().v() : null,
                                x.getNursingAideTable().getFullTime().isPresent() ? x.getNursingAideTable().getFullTime().get().v() : null,
                                x.getNursingAideTable().getShortTime().isPresent() ? x.getNursingAideTable().getShortTime().get().v() : null,
                                x.getNursingAideTable().getPartTime().isPresent() ? x.getNursingAideTable().getPartTime().get().v() : null,
                                x.getNursingAideTable().getOfficeWork().isPresent() ? x.getNursingAideTable().getOfficeWork().get().v() : null,
                                x.getNursingAideTable().getConcurrentPost().isPresent() ? x.getNursingAideTable().getConcurrentPost().get().v() : null,
                                x.getNursingAideTable().getNightShiftOnly().isPresent() ? x.getNursingAideTable().getNightShiftOnly().get().v() : null
                        ),
                        x.getTemplateFileId().isPresent() ? x.getTemplateFileId().get() : null,
                        x.getFileName(require)
                )
        ).collect(Collectors.toList());
    }
}
