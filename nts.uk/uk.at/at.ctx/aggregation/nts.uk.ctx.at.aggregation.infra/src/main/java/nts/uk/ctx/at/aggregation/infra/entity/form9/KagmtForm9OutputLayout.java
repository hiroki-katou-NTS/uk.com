package nts.uk.ctx.at.aggregation.infra.entity.form9;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.primitive.PrimitiveValueBase;
import nts.uk.ctx.at.aggregation.dom.form9.DetailSettingOfForm9;
import nts.uk.ctx.at.aggregation.dom.form9.Form9Code;
import nts.uk.ctx.at.aggregation.dom.form9.Form9Cover;
import nts.uk.ctx.at.aggregation.dom.form9.Form9Layout;
import nts.uk.ctx.at.aggregation.dom.form9.Form9Name;
import nts.uk.ctx.at.aggregation.dom.form9.Form9NursingAideTable;
import nts.uk.ctx.at.aggregation.dom.form9.Form9NursingTable;
import nts.uk.ctx.at.aggregation.dom.form9.OnePageDisplayNumerOfPeople;
import nts.uk.ctx.at.aggregation.dom.form9.OutputColumn;
import nts.uk.ctx.at.aggregation.dom.form9.OutputRow;
import nts.uk.shr.com.primitive.OutputCell;
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
        KagmtForm9OutputLayoutPk pk = new KagmtForm9OutputLayoutPk(companyId, layout.getCode().v());
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
                layout.getTemplateFileId().orElse(null),
                cover.getCellYear().map(PrimitiveValueBase::v).orElse(null),
                cover.getCellMonth().map(PrimitiveValueBase::v).orElse(null),
                cover.getCellStartTime().map(PrimitiveValueBase::v).orElse(null),
                cover.getCellEndTime().map(PrimitiveValueBase::v).orElse(null),
                cover.getCellTitle().map(PrimitiveValueBase::v).orElse(null),
                cover.getCellPrintPeriod().map(PrimitiveValueBase::v).orElse(null),
                nurseDetailSetting.getMaxNumerOfPeople().v(),
                nurseDetailSetting.getBodyStartRow().v(),
                nursingTable.getDay1StartColumn().v(),
                nurseDetailSetting.getRowDate().v(),
                nurseDetailSetting.getRowDayOfWeek().v(),
                nursingTable.getFullName().v(),
                nursingTable.getLicense().map(PrimitiveValueBase::v).orElse(null),
                nursingTable.getHospitalWardName().map(PrimitiveValueBase::v).orElse(null),
                nursingTable.getFullTime().map(PrimitiveValueBase::v).orElse(null),
                nursingTable.getShortTime().map(PrimitiveValueBase::v).orElse(null),
                nursingTable.getPartTime().map(PrimitiveValueBase::v).orElse(null),
                nursingTable.getConcurrentPost().map(PrimitiveValueBase::v).orElse(null),
                nursingTable.getNightShiftOnly().map(PrimitiveValueBase::v).orElse(null),
                nursingAideDetailSetting.getMaxNumerOfPeople().v(),
                nursingAideDetailSetting.getBodyStartRow().v(),
                nursingAideTable.getDay1StartColumn().v(),
                nursingAideDetailSetting.getRowDate().v(),
                nursingAideDetailSetting.getRowDayOfWeek().v(),
                nursingAideTable.getFullName().v(),
                nursingAideTable.getHospitalWardName().map(PrimitiveValueBase::v).orElse(null),
                nursingAideTable.getFullTime().map(PrimitiveValueBase::v).orElse(null),
                nursingAideTable.getShortTime().map(PrimitiveValueBase::v).orElse(null),
                nursingAideTable.getPartTime().map(PrimitiveValueBase::v).orElse(null),
                nursingAideTable.getConcurrentPost().map(PrimitiveValueBase::v).orElse(null),
                nursingAideTable.getOfficeWork().map(PrimitiveValueBase::v).orElse(null),
                nursingAideTable.getNightShiftOnly().map(PrimitiveValueBase::v).orElse(null)
        );
    }

    public static Form9Layout toDomain(KagmtForm9OutputLayout entity) {
        Form9Cover cover = new Form9Cover(
                entity.coverCellYear == null ? Optional.empty() : Optional.of(new OutputCell(entity.coverCellYear)),
                entity.coverCellMonth == null ? Optional.empty() : Optional.of(new OutputCell(entity.coverCellMonth)),
                entity.coverCellNightShiftStart == null ? Optional.empty() : Optional.of(new OutputCell(entity.coverCellNightShiftStart)),
                entity.coverCellNightShiftEnd == null ? Optional.empty() : Optional.of(new OutputCell(entity.coverCellNightShiftEnd)),
                entity.coverCellTitle == null ? Optional.empty() : Optional.of(new OutputCell(entity.coverCellTitle)),
                entity.coverCellPrintingPerIod == null ? Optional.empty() : Optional.of(new OutputCell(entity.coverCellPrintingPerIod))
        );
        Form9NursingTable nursingTable = new Form9NursingTable(
                new OutputColumn(entity.nurseColName),
                new OutputColumn(entity.nurseBodyStartCol),
                new DetailSettingOfForm9(
                        new OutputRow(entity.nursebodyStartRow),
                        new OnePageDisplayNumerOfPeople(entity.nurseMaxNumberOfPeople),
                        new OutputRow(entity.nurseRowDate),
                        new OutputRow(entity.nurseRowDayOfWeek)
                ),
                entity.nurseColLicense == null ? Optional.empty() : Optional.of(new OutputColumn(entity.nurseColLicense)),
                entity.nurseColHospitalWard == null ? Optional.empty() : Optional.of(new OutputColumn(entity.nurseColHospitalWard)),
                entity.nurseColWorkstyleFullTime == null ? Optional.empty() : Optional.of(new OutputColumn(entity.nurseColWorkstyleFullTime)),
                entity.nurseColWorkstyleShortTime == null ? Optional.empty() : Optional.of(new OutputColumn(entity.nurseColWorkstyleShortTime)),
                entity.nurseColWorkstylePartTime == null ? Optional.empty() : Optional.of(new OutputColumn(entity.nurseColWorkstylePartTime)),
                entity.nurseColWorkstyleConpost == null ? Optional.empty() : Optional.of(new OutputColumn(entity.nurseColWorkstyleConpost)),
                entity.nurseColNightShiftOnly == null ? Optional.empty() : Optional.of(new OutputColumn(entity.nurseColNightShiftOnly))
        );
        Form9NursingAideTable nursingAideTable = new Form9NursingAideTable(
                new OutputColumn(entity.nuringAideColName),
                new OutputColumn(entity.nuringAideBodyStartCol),
                new DetailSettingOfForm9(
                        new OutputRow(entity.nuringAideBodyStartRow),
                        new OnePageDisplayNumerOfPeople(entity.nuringAideMaxNumberOfpeople),
                        new OutputRow(entity.nuringAideRowDate),
                        new OutputRow(entity.nuringAideRowDateOfWeek)
                ),
                entity.nuringAideColHospitalWard == null ? Optional.empty() : Optional.of(new OutputColumn(entity.nuringAideColHospitalWard)),
                entity.nuringAideColWorkStyleFullTime == null ? Optional.empty() : Optional.of(new OutputColumn(entity.nuringAideColWorkStyleFullTime)),
                entity.nuringAideColWorkStyleShortTime == null ? Optional.empty() : Optional.of(new OutputColumn(entity.nuringAideColWorkStyleShortTime)),
                entity.nuringAideColWorkStylePartTime == null ? Optional.empty() : Optional.of(new OutputColumn(entity.nuringAideColWorkStylePartTime)),
                entity.nuringAideColRoleIsOfficeWork == null ? Optional.empty() : Optional.of(new OutputColumn(entity.nuringAideColRoleIsOfficeWork)),
                entity.nuringAideColWorkStyleConPost == null ? Optional.empty() : Optional.of(new OutputColumn(entity.nuringAideColWorkStyleConPost)),
                entity.nuringAideColNightShiftOnly == null ? Optional.empty() : Optional.of(new OutputColumn(entity.nuringAideColNightShiftOnly))
        );
        return new Form9Layout(
                new Form9Code(entity.pk.code),
                new Form9Name(entity.name),
                entity.isFixed,
                entity.isUse,
                cover,
                nursingTable,
                nursingAideTable,
                Optional.ofNullable(entity.templateFileId)
        );
    }
}
