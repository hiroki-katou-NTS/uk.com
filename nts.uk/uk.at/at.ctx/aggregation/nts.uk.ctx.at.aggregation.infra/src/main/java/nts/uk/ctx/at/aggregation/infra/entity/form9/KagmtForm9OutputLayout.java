package nts.uk.ctx.at.aggregation.infra.entity.form9;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.var;
import nts.uk.ctx.at.aggregation.dom.form9.DetailSettingOfForm9;
import nts.uk.ctx.at.aggregation.dom.form9.Form9Code;
import nts.uk.ctx.at.aggregation.dom.form9.Form9Cover;
import nts.uk.ctx.at.aggregation.dom.form9.Form9Layout;
import nts.uk.ctx.at.aggregation.dom.form9.Form9Name;
import nts.uk.ctx.at.aggregation.dom.form9.Form9NursingAideTable;
import nts.uk.ctx.at.aggregation.dom.form9.Form9NursingTable;
import nts.uk.ctx.at.aggregation.dom.form9.OnePageDisplayNumerOfPeople;
import nts.uk.ctx.at.aggregation.dom.form9.OutputCell;
import nts.uk.ctx.at.aggregation.dom.form9.OutputColumn;
import nts.uk.ctx.at.aggregation.dom.form9.OutputRow;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KAGMT_RPT_FORM9_LAYOUT")
public class KagmtForm9OutputLayout extends ContractUkJpaEntity implements Serializable {

    @EmbeddedId
    public KagmtForm9OutputLayoutPk pk;

    /**
     * 名称
     */
    @Column(name = "NAME")
    public String name;

    /**
     * 利用区分
     */
    @Column(name = "IS_USE")
    public boolean isUse;

    /**
     * システム固定のレイアウトか
     */
    @Column(name = "IS_FIXED")
    public boolean isFixed;

    /**
     * テンプレートファイルID
     */
    @Column(name = "TEMPLATE_FILE_ID")
    public String templateFileId;

    /**
     * 表紙 セル位置 集計年
     */
    @Column(name = "COVER_CELL_YEAR")
    public String coverCellYear;

    /**
     * 表紙 セル位置 集計月
     */
    @Column(name = "COVER_CELL_MONTH")
    public String coverCellMonth;

    /**
     * 表紙 セル位置 夜勤時間帯 開始時刻
     */
    @Column(name = "COVER_CELL_NIGHTSHIFT_START")
    public String coverCellNightShiftStart;

    /**
     * 表紙 セル位置 夜勤時間帯 終了時刻
     */
    @Column(name = "COVER_CELL_NIGHTSHIFT_END")
    public String coverCellNightShiftEnd;

    /**
     * 表紙 セル位置 タイトル
     */
    @Column(name = "COVER_CELL_TITLE")
    public String coverCellTitle;

    /**
     * 表紙 セル位置 出力期間
     */
    @Column(name = "COVER_CELL_PRINTINGPERIOD")
    public String coverCellPrintingPerIod;

    /**
     * 看護職員 印刷可能な人数
     */
    @Column(name = "NURSE_MAX_NUMBER_OF_PEOPLE")
    public int nurseMaxNumberOfPeople;

    /**
     * 看護職員 明細開始位置 行
     */
    @Column(name = "NURSE_BODY_START_ROW")
    public int nursebodyStartRow;

    /**
     * 看護職員 明細開始位置 列
     */
    @Column(name = "NURSE_BODY_START_COL")
    public String nurseBodyStartCol;

    /**
     * 看護職員 行位置 日付
     */
    @Column(name = "NURSE_ROW_DATE")
    public int nurseRowDate;

    /**
     * 看護職員 行位置 曜日
     */
    @Column(name = "NURSE_ROW_DAYOFWEEK")
    public int nurseRowDayOfWeek;

    /**
     * 看護職員 列位置 氏名
     */
    @Column(name = "NURSE_COL_NAME")
    public String nurseColName;

    /**
     * 看護職員 列位置 免許種別
     */
    @Column(name = "NURSE_COL_LICENSE")
    public String nurseColLicense;

    /**
     * 看護職員 列位置 病棟
     */
    @Column(name = "NURSE_COL_HOSPITALWARD")
    public String nurseColHospitalWard;

    /**
     * 看護職員 列位置 雇用形態 常勤
     */
    @Column(name = "NURSE_COL_WORKSTYLE_FULLTIME")
    public String nurseColWorkstyleFullTime;

    /**
     * 看護職員 列位置 雇用形態 短時間
     */
    @Column(name = "NURSE_COL_WORKSTYLE_SHORTTIME")
    public String nurseColWorkstyleShortTime;

    /**
     * 看護職員 列位置 雇用形態 非常勤
     */
    @Column(name = "NURSE_COL_WORKSTYLE_PARTTIME")
    public String nurseColWorkstylePartTime;


    /**
     * 看護職員 列位置 雇用形態 他部署兼務
     */
    @Column(name = "NURSE_COL_WORKSTYLE_CONCURRENT_POST")
    public String nurseColWorkstyleConpost;

    /**
     * 看護職員 列位置 夜勤の有無 夜勤専従
     */
    @Column(name = "NURSE_COL_NIGHTSHIFT_ONLY")
    public String nurseColNightShiftOnly;


    /**
     * 看護補助者 印刷可能な人数
     */
    @Column(name = "NURSINGAIDE_MAX_NUMBER_OF_PEOPLE")
    public int nuringAideMaxNumberOfpeople;


    /**
     * 看護補助者 明細開始位置 行
     */
    @Column(name = "NURSINGAIDE_BODY_START_ROW")
    public int nuringAideBodyStartRow;

    /**
     * 看護補助者 明細開始位置 列
     */
    @Column(name = "NURSINGAIDE_BODY_START_COL")
    public String nuringAideBodyStartCol;

    /**
     * 看護補助者 行位置 日付
     */
    @Column(name = "NURSINGAIDE_ROW_DATE")
    public int nuringAideRowDate;

    /**
     * 看護補助者 行位置 曜日
     */
    @Column(name = "NURSINGAIDE_ROW_DAYOFWEEK")
    public int nuringAideRowDateOfWeek;

    /**
     * 看護補助者 列位置 氏名
     */
    @Column(name = "NURSINGAIDE_COL_NAME")
    public String nuringAideColName;

    /**
     * 看護補助者 列位置 病棟
     */
    @Column(name = "NURSINGAIDE_COL_HOSPITALWARD")
    public String nuringAideColHospitalWard;

    /**
     * 看護補助者 列位置 雇用形態 常勤
     */
    @Column(name = "NURSINGAIDE_COL_WORKSTYLE_FULLTIME")
    public String nuringAideColWorkStyleFullTime;

    /**
     * 看護補助者 列位置 雇用形態 短時間
     */
    @Column(name = "NURSINGAIDE_COL_WORKSTYLE_SHORTTIME")
    public String nuringAideColWorkStyleShortTime;

    /**
     * 看護補助者 列位置 雇用形態 非常勤
     */
    @Column(name = "NURSINGAIDE_COL_WORKSTYLE_PARTTIME")
    public String nuringAideColWorkStylePartTime;

    /**
     * 看護補助者 列位置 雇用形態 他部署兼務
     */
    @Column(name = "NURSINGAIDE_COL_WORKSTYLE_CONCURRENT_POST")
    public String nuringAideColWorkStyleConPost;

    /**
     * 看護補助者 列位置 担当業務 事務的業務従事者
     */
    @Column(name = "NURSINGAIDE_COL_ROLE_IS_OFFICEWORK")
    public String nuringAideColRoleIsOfficeWork;

    /**
     * 看護補助者 列位置 夜勤の有無 夜勤専従
     */
    @Column(name = "NURSINGAIDE_COL_NIGHTSHIFT_ONLY")
    public String nuringAideColNightShiftOnly;

    @Override
    protected Object getKey() {
        return this.pk;
    }

    public static KagmtForm9OutputLayout toEntity(String companyId, Form9Layout layout) {
        var pk = new KagmtForm9OutputLayoutPk(companyId,layout.getCode().v());
        Form9Cover cover = layout.getCover();
        Form9NursingTable nursingTable = layout.getNursingTable();
        DetailSettingOfForm9 nurseDetailSetting = nursingTable.getDetailSetting();
        Form9NursingAideTable nursingAideTable = layout.getNursingAideTable();
        DetailSettingOfForm9 nursingAideDetailSetting = nursingAideTable.getDetailSetting();
        return new KagmtForm9OutputLayout(
                pk,
                layout.getName().v(),
                layout.isUse(),
                layout.isSystemFixed(),
                layout.getTemplateFileId().isPresent() ? layout.getTemplateFileId().get() : null,
                cover.getCellYear().isPresent() ? cover.getCellYear().get().v() : null,
                cover.getCellMonth().isPresent() ? cover.getCellMonth().get().v() : null,
                cover.getCellStartTime().isPresent() ? cover.getCellStartTime().get().v() : null,
                cover.getCellEndTime().isPresent() ? cover.getCellEndTime().get().v() : null,
                cover.getCellTitle().isPresent() ? cover.getCellTitle().get().v() : null,
                cover.getCellPrintPeriod().isPresent() ? cover.getCellPrintPeriod().get().v() : null,
                nurseDetailSetting.getMaxNumerOfPeople().v(),
                nurseDetailSetting.getBodyStartRow().v(),
                null, //TODO map to col
                nurseDetailSetting.getRowDate().v(),
                nurseDetailSetting.getRowDayOfWeek().v(),
                nursingTable.getDay1StartColumn().v(),
                nursingTable.getLicense().isPresent() ? nursingTable.getLicense().get().v() : null,
                nursingTable.getHospitalWardName().isPresent() ? nursingTable.getHospitalWardName().get().v() : null,
                nursingTable.getFullTime().isPresent() ? nursingTable.getFullTime().get().v() : null,
                nursingTable.getShortTime().isPresent() ? nursingTable.getShortTime().get().v() : null,
                nursingTable.getPartTime().isPresent() ? nursingTable.getPartTime().get().v() : null,
                nursingTable.getConcurrentPost().isPresent() ? nursingTable.getConcurrentPost().get().v() : null,
                nursingTable.getNightShiftOnly().isPresent() ? nursingTable.getNightShiftOnly().get().v() : null,
                nursingAideDetailSetting.getMaxNumerOfPeople().v(),
                nursingAideDetailSetting.getBodyStartRow().v(),
                null, //TODO map to col
                nursingAideDetailSetting.getRowDate().v(),
                nursingAideDetailSetting.getRowDayOfWeek().v(),
                nursingAideTable.getDay1StartColumn().v(),
                nursingAideTable.getHospitalWardName().isPresent() ? nursingAideTable.getHospitalWardName().get().v() : null,
                nursingAideTable.getFullTime().isPresent() ? nursingAideTable.getFullTime().get().v() : null,
                nursingAideTable.getShortTime().isPresent() ? nursingAideTable.getShortTime().get().v() : null,
                nursingAideTable.getPartTime().isPresent() ? nursingAideTable.getPartTime().get().v() : null,
                nursingAideTable.getConcurrentPost().isPresent() ? nursingAideTable.getConcurrentPost().get().v() : null,
                nursingAideTable.getOfficeWork().isPresent() ? nursingAideTable.getOfficeWork().get().v() : null,
                nursingAideTable.getNightShiftOnly().isPresent() ? nursingAideTable.getNightShiftOnly().get().v() : null
        );
    }

    public static Form9Layout toDomain(KagmtForm9OutputLayout entity) {
        var cover = Form9Cover.create(
                Optional.of(new OutputCell(entity.coverCellYear)),
                Optional.of(new OutputCell(entity.coverCellMonth)),
                Optional.of(new OutputCell(entity.coverCellNightShiftStart)),
                Optional.of(new OutputCell(entity.coverCellNightShiftEnd)),
                Optional.of(new OutputCell(entity.coverCellTitle)),
                Optional.of(new OutputCell(entity.coverCellPrintingPerIod))
        );

        var nursingTable = Form9NursingTable.create(
                new OutputColumn(entity.nurseColName),
                new OutputColumn(entity.nurseBodyStartCol),
                DetailSettingOfForm9.create(new OutputRow(entity.nursebodyStartRow),
                        new OnePageDisplayNumerOfPeople(entity.nurseMaxNumberOfPeople),
                        new OutputRow(entity.nurseRowDate),
                        new OutputRow(entity.nurseRowDayOfWeek)
                ),
                Optional.of(new OutputColumn(entity.nurseColLicense)),
                Optional.of(new OutputColumn(entity.nurseColHospitalWard)),
                Optional.of(new OutputColumn(entity.nurseColWorkstyleFullTime)),
                Optional.of(new OutputColumn(entity.nurseColWorkstyleShortTime)),
                Optional.of(new OutputColumn(entity.nurseColWorkstylePartTime)),
                Optional.of(new OutputColumn(entity.nurseColWorkstyleConpost)),
                Optional.of(new OutputColumn(entity.nurseColNightShiftOnly))
        );
        var nursingAideTable = Form9NursingAideTable.create(
                new OutputColumn(entity.nuringAideColName),
                new OutputColumn(entity.nuringAideBodyStartCol),
                DetailSettingOfForm9.create(new OutputRow(entity.nuringAideBodyStartRow),
                        new OnePageDisplayNumerOfPeople(entity.nuringAideMaxNumberOfpeople),
                        new OutputRow(entity.nuringAideRowDate),
                        new OutputRow(entity.nuringAideRowDateOfWeek)
                ),
                Optional.of(new OutputColumn(entity.nurseColLicense)),
                Optional.of(new OutputColumn(entity.nuringAideColHospitalWard)),
                Optional.of(new OutputColumn(entity.nuringAideColWorkStyleFullTime)),
                Optional.of(new OutputColumn(entity.nuringAideColWorkStyleShortTime)),
                Optional.of(new OutputColumn(entity.nuringAideColWorkStylePartTime)),
                Optional.of(new OutputColumn(entity.nuringAideColWorkStyleConPost)),
                Optional.of(new OutputColumn(entity.nuringAideColNightShiftOnly))
        );
        return Form9Layout.create(
                new Form9Code(entity.pk.code),
                new Form9Name(entity.name),
                entity.isFixed,
                entity.isUse,
                cover,
                nursingTable,
                nursingAideTable,
                Optional.of(entity.templateFileId)
        );
    }
}
