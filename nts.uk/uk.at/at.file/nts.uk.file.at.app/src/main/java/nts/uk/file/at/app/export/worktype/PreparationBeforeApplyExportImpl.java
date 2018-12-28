package nts.uk.file.at.app.export.worktype;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.apptypesetting.AppAcceptLimitDay;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.apptypesetting.BeforeAddCheckMethod;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.masterlist.annotation.DomainID;
import nts.uk.shr.infra.file.report.masterlist.data.*;
import nts.uk.shr.infra.file.report.masterlist.webservice.MasterListExportQuery;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.*;

@Stateless
@DomainID(value ="PreparationBeforeApply")
public class PreparationBeforeApplyExportImpl implements MasterListData{


    private static final String A4_2 = "A4_2";
    private static final String A4_3 = "A4_3";
    private static final String A7_4 = "A7_4";
    private static final String A7_5 = "A7_5";
    private static final String KAF022_454 = "項目";
    private static final String KAF022_455 = "値";
    private static final String KAF022_456 = "申請締切設定";
    private static final String KAF022_457 = "締め日";
    private static final String KAF022_458 = "利用";
    private static final String KAF022_459 = "基準日から期限までの日数";
    private static final String KAF022_460 = "＜承認設定＞";
    private static final String KAF022_461 = "承認ルートの基準日";
    private static final String KAF022_462 = "本人による承認";
    private static final String KAF022_463 = "承認者による申請内容の変更";
    private static final String KAF022_464 = "＜申請の種類＞";
    private static final String KAF022_465 = "申請表示名";
    //private static final String KAF022_466 = "残業申請";
    private static final String KAF022_467 = "事前の受付制限";
    private static final String KAF022_468 = "利用する";
    private static final String KAF022_469 = "利用区分";
    private static final String KAF022_470 = "申請対象日";
    private static final String KAF022_471 = "区分";
    private static final String KAF022_472 = "当日";
    private static final String KAF022_473 = "早出";
    private static final String KAF022_474 = "普通";
    private static final String KAF022_475 = "早出・普通";
    private static final String KAF022_476 = "事後の受付制限";
    private static final String KAF022_477 = "未来日を許可しない";
    private static final String KAF022_478 = "定型理由の表示";
    private static final String KAF022_479 = "申請理由の表示";
    private static final String KAF022_480 = "新規登録時に自動でメールを送信する";
    private static final String KAF022_481 = "する";
    private static final String KAF022_482 = "承認処理時に自動でメールを送信する";
    private static final String KAF022_483 = "事前事後区分の初期表示";
    private static final String KAF022_484 = "事前事後区分を変更できる";
    private static final String KAF022_485 = "＜申請制限設定＞";
    private static final String KAF022_486 = "申請理由が必須";
    private static final String KAF022_487 = "時間外深夜の申請を利用する";
    private static final String KAF022_488 = "日別実績確認後（確認済み）の申請";
    private static final String KAF022_489 = "月別実績確認後（確認済み）の申請";
    private static final String KAF022_490 = "就業確定後（確定済み）の申請";
    private static final String KAF022_491 = "実績修正がロック状態の申請";
    private static final String KAF022_492 = "＜申請表示設定＞";
    private static final String KAF022_493 = "事前事後区分表示";
    private static final String KAF022_494 = "登録時の手動メール送信の初期値";
    private static final String KAF022_495 = "＜代行申請の設定＞";
    private static final String KAF022_496 = "代行申請で利用できる申請設定申請種類";
    private static final String KAF022_497 = "＜職位指定の設定＞";
    private static final String KAF022_498 = "兼務者を含める";
    private static final String KAF022_499 = "＜メール設定＞";
    private static final String KAF022_500 = "休出指示のメール件名";
    private static final String KAF022_501 = "休出指示のメール本文";
    private static final String KAF022_502 = "残業指示のメール件名";
    private static final String KAF022_503 = "残業指示のメール本文";
    private static final String KAF022_504 = "申請承認のメール本文";
    private static final String KAF022_505 = "差し戻しのメール件名";
    private static final String KAF022_506 = "差し戻しのメール本文";
    private static final String KAF022_507 = "メール送信時にシングルサインオンURLを利用するかの設定";
    private static final String KAF022_508 = "で";
    private static final String KAF022_509 = "まで";
    private static final String KAF022_510 = "まで可能";
    private static final String KAF022_512 = "設定対象";
    private static final String KAF022_513 = "既定の理由に設定する";
    private static final String KAF022_514 = "定型理由";
    private static final String KAF022_516 = "コード";
    private static final String KAF022_517 = "職場名";
    private static final String KAF022_518 = "上位職場にサーチ設定";
    private static final String KAF022_520 = "＜申請項目の設定＞";
    private static final String KAF022_521 = "勤務種類、就業時間帯の変更";
    private static final String KAF022_522 = "フレックス超過時間";
    private static final String KAF022_523 = "加給時間";
    private static final String KAF022_524 = "乖離定型理由（乖離時間の登録にて設定）";
    private static final String KAF022_525 = "乖離理由入力";
    private static final String KAF022_526 = "＜参考値表示の設定＞";
    private static final String KAF022_527 = "実績状況";
    private static final String KAF022_528 = "事前申請時間";
    private static final String KAF022_529 = "実績時間";
    private static final String KAF022_530 = "３６協定時間";
    private static final String KAF022_531 = "＜エラーチェック設定＞";
    private static final String KAF022_532 = "事前申請超過チェック";
    private static final String KAF022_533 = "実績超過チェック";
    private static final String KAF022_534 = "実績時間の超過をどの退勤時刻でチェックするか（1日の打刻反映時間帯内）";
    private static final String KAF022_535 = "３６協定時間超過チェック";
    private static final String KAF022_536 = "申請日の矛盾";
    private static final String KAF022_538 = "就業時間帯";
    private static final String KAF022_539 = "勤務種類の未選択";
    private static final String KAF022_540 = "勤務時間の変更";
    private static final String KAF022_541 = "時間年休";
    private static final String KAF022_542 = "６０H超休";
    private static final String KAF022_543 = "時間代休";
    private static final String KAF022_544 = "申請日の矛盾　（休日への休暇申請等）";
    private static final String KAF022_545 = "法定休日、法定外休日の矛盾";
    private static final String KAF022_546 = "振出日への申請";
    private static final String KAF022_547 = "半日年休の使用上限";
    private static final String KAF022_548 = "残数チェック";
    private static final String KAF022_549 = "【年休】";
    private static final String KAF022_550 = "【代休】";
    private static final String KAF022_551 = "【積立年休】";
    private static final String KAF022_552 = "【公休】";
    private static final String KAF022_553 = "【振休】";
    private static final String KAF022_554 = "年休より優先消化チェック区分";
    private static final String KAF022_555 = "＜休暇分類名称＞";
    private static final String KAF022_556 = "年休名称";
    private static final String KAF022_557 = "代休名称";
    private static final String KAF022_558 = "欠勤名称";
    private static final String KAF022_559 = "特別休暇名称";
    private static final String KAF022_560 = "積立年休名称";
    private static final String KAF022_561 = "休日名称";
    private static final String KAF022_562 = "時間消化名称";
    private static final String KAF022_563 = "振休名称";
    private static final String KAF022_565 = "勤務時間の初期表示";
    private static final String KAF022_566 = "スケジュールが休日の日を除いて申請";
    private static final String KAF022_567 = "コメント1";
    private static final String KAF022_568 = "色";
    private static final String KAF022_569 = "太字";
    private static final String KAF022_570 = "コメント2";
    private static final String KAF022_572 = "申請可能な勤務種類";
    private static final String KAF022_573 = "勤務の変更";
    private static final String KAF022_574 = "遅刻・早退になる時刻";
    private static final String KAF022_576 = "休出申請勤務種類";
    private static final String KAF022_577 = "勤務変更";
    private static final String KAF022_578 = "勤務時間の初期表示";
    private static final String KAF022_579 = "休出時間の未入力チェック";
    private static final String KAF022_580 = "打刻漏れ時のチェック";
    private static final String KAF022_582 = "コメント（振休）";
    private static final String KAF022_583 = "コメント（振出）";
    private static final String KAF022_584 = "振出・振休の同時申請";
    private static final String KAF022_585 = "振休先取り";
    private static final String KAF022_587 = "＜申請一覧共通設定＞";
    private static final String KAF022_588 = "所属職場名表示";
    private static final String KAF022_589 = "＜承認一覧設定＞";
    private static final String KAF022_590 = "申請理由";
    private static final String KAF022_591 = "申請対象日に対して警告表示";
    private static final String KAF022_592 = "事後申請の事前比較表示";
    private static final String KAF022_593 = "残業の事前申請";
    private static final String KAF022_594 = "休出の事前申請";
    private static final String KAF022_595 = "事前申請の超過メッセージ";
    private static final String KAF022_596 = "実績超過の表示設定";
    private static final String KAF022_597 = "残業の実績";
    private static final String KAF022_598 = "休出の実績";
    private static final String KAF022_599 = "実績超過メッセージ";
    private static final String KAF022_600 = "日前に警告表示する（0日前は、警告表示しません）";
    private static final String KAF022_602 = "＜申請反映設定＞";
    private static final String KAF022_603 = "事前申請のスケジュールへの反映";
    private static final String KAF022_604 = "日別実績の予定欄";
    private static final String KAF022_605 = "スケジュールが確定されている場合";
    private static final String KAF022_606 = "実績が確定されている場合";
    private static final String KAF022_607 = "＜残業申請反映設定＞";
    private static final String KAF022_608 = "＜事前申請＞";
    private static final String KAF022_609 = "日別実績の予定勤務種類・就業時間帯への反映";
    private static final String KAF022_610 = "残業時間への反映";
    private static final String KAF022_611 = "＜事後申請＞";
    private static final String KAF022_612 = "日別実績の実績勤務種類・就業時間帯への反映（事前もこの設定に従う）";
    private static final String KAF022_613 = "出勤・退勤時刻への反映";
    private static final String KAF022_614 = "休憩時刻への反映";
    private static final String KAF022_615 = "反映しない";
    private static final String KAF022_616 = "＜直行直帰申請反映設定＞";
    private static final String KAF022_617 = "申請対象日が振出・休出の勤務変更";
    private static final String KAF022_618 = "予定時刻の優先順序";
    private static final String KAF022_619 = "実績時刻の優先順序";
    private static final String KAF022_620 = "外出・戻りが打刻漏れの場合";
    private static final String KAF022_621 = "＜休日出勤申請反映設定＞";
    private static final String KAF022_622 = "＜事前申請＞";
    private static final String KAF022_623 = "休出時間への反映";
    private static final String KAF022_624 = "＜事後申請＞";
    private static final String KAF022_625 = "出勤・退勤時刻への反映";
    private static final String KAF022_626 = "休憩時刻への反映";
    private static final String KAF022_628 = "コード";
    private static final String KAF022_629 = "名称";
    private static final String KAF022_630 = "申請の種類";
    private static final String KAF022_631 = "申請内容";
    private static final String KAF022_632 = "利用しない";
    private static final String KAF022_633 = "対象勤務種類";
    private static final String KAF022_635 = "コード";
    private static final String KAF022_636 = "名称";
    private static final String KAF022_637 = "申請の種類";
    private static final String KAF022_638 = "利用設定";
    private static final String KAF022_639 = "指示が必須";
    private static final String KAF022_640 = "事前必須の設定";
    private static final String KAF022_641 = "時刻計算";
    private static final String KAF022_642 = "外出入力欄を表示する";
    private static final String KAF022_643 = "実績から外出を初期表示する";
    private static final String KAF022_644 = "出退勤時刻の初期表示";
    private static final String KAF022_645 = "残業時間を入力";
    private static final String KAF022_646 = "休出時間を入力";
    private static final String KAF022_647 = "実績を取り消す";
    private static final String KAF022_648 = "申請時に選択";
    private static final int ROW_SIZE_A4 = 4;
    private static final int ROW_SIZE_A5 = 3;
    private static final int ROW_SIZE_A7 = 7;
    private static final int ROW_SIZE_A7_BOTTOM = 2;
    private static final int ROW_SIZE_A8_TOP = 8;
    private static final int ROW_SIZE_A8_CENTER = 2;
    private static final int ROW_SIZE_A9 = 6;
    private static final int ROW_SIZE_A10 = 2;
    private static final int ROW_SIZE_A13 = 8;

    @Inject
    private PreparationBeforeApplyRepository preparationBeforeApplyRepository;

    @Inject
    private PreparationBeforeApplyExport preparationBeforeApply;

    @Override
    public List<MasterHeaderColumn> getHeaderColumns(MasterListExportQuery arg0){
        List <MasterHeaderColumn> columns = new ArrayList<>();
        columns.add(new MasterHeaderColumn(KAF022_454, TextResource.localize("KAF022_454"),
        ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(A4_2, TextResource.localize(""),
        ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(A4_3, TextResource.localize(""),
        ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(A7_4, TextResource.localize(""),
        ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(A7_5, TextResource.localize(""),
        ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(KAF022_455, TextResource.localize("KAF022_455"),
        ColumnTextAlign.LEFT, "", true));
        return columns;
    }
	
	private  List<MasterData> putDatasA4(Object[] export ) {
        List<MasterData> datasA4 = new ArrayList<>();
        for(int i= 0 ; i< ROW_SIZE_A4; i++) {
            Map<String, MasterCellData> dataA4 = new HashMap<>();
            dataA4.put(KAF022_454, MasterCellData.builder()
                    .columnId(KAF022_454)
                    .value(((Long) export[0]).intValue() == 1 && i == 0 ? TextResource.localize("KAF022_456") : "")
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            dataA4.put(A4_2, MasterCellData.builder()
                    .columnId(A4_2)
                    .value( i== 0 ? export[0] : "")
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            dataA4.put(A4_3, MasterCellData.builder()
                    .columnId(A4_3)
                    .value(this.getTextA4(i))
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            dataA4.put(A7_4, MasterCellData.builder()
                    .columnId(A7_4)
                    .value("")
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            dataA4.put(A7_5, MasterCellData.builder()
                    .columnId(A7_5)
                    .value("")
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            dataA4.put(KAF022_455, MasterCellData.builder()
                    .columnId(KAF022_455)
                    .value(getValueA4(i, export))
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
          datasA4.add(MasterData.builder().rowData(dataA4).build());
        }
        return datasA4;
    }

    private  List<MasterData> putDatasA5(Object[] export ) {
        List<MasterData> datasA5 = new ArrayList<>();

        for(int i= 0 ; i< ROW_SIZE_A5; i++) {
            Map<String, MasterCellData> dataA5 = new HashMap<>();
            dataA5.put(KAF022_454, MasterCellData.builder()
                    .columnId(KAF022_454)
                    .value(((Long) export[0]).intValue() == 1 && i == 0 ? TextResource.localize("KAF022_460") : "")
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            dataA5.put(A4_2, MasterCellData.builder()
                    .columnId(A4_2)
                    .value(this.getTextA5(i))
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            dataA5.put(A4_3, MasterCellData.builder()
                    .columnId(A4_3)
                    .value(getTextA5(i))
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            dataA5.put(A7_4, MasterCellData.builder()
                    .columnId(A7_4)
                    .value("")
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            dataA5.put(A7_5, MasterCellData.builder()
                    .columnId(A7_5)
                    .value("")
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            dataA5.put(KAF022_455, MasterCellData.builder()
                    .columnId(KAF022_455)
                    .value(getValueA5(i, export))
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            datasA5.add(MasterData.builder().rowData(dataA5).build());
        }
        return datasA5;
    }

    private  List<MasterData> putDatasA6(Object[] export ) {
        List<MasterData> datasA6 = new ArrayList<>();
        Map<String, MasterCellData> dataA6 = new HashMap<>();
        dataA6.put(KAF022_454, MasterCellData.builder()
                .columnId(KAF022_454)
                .value(((Long) export[35]).intValue() == 1 ? TextResource.localize("KAF022_464") : "")
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
        dataA6.put(A4_2, MasterCellData.builder()
                .columnId(A4_2)
                .value(((Long) export[35]).intValue() == 1 ? TextResource.localize("KAF022_465") : "")
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
        dataA6.put(A4_3, MasterCellData.builder()
                .columnId(A4_3)
                .value(EnumAdaptor.valueOf(((BigDecimal) export[36]).intValue(), ApplicationType.class).nameId)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
        dataA6.put(A7_4, MasterCellData.builder()
                .columnId(A7_4)
                .value("")
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
        dataA6.put(A7_5, MasterCellData.builder()
                .columnId(A7_4)
                .value("")
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
        dataA6.put(KAF022_455, MasterCellData.builder()
                .columnId(KAF022_455)
                .value(export[37])
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
        datasA6.add(MasterData.builder().rowData(dataA6).build());
        return datasA6;
    }

    private  List<MasterData> putDatasA7(Object[] export ) {
        List<MasterData> datasA7 = new ArrayList<>();
        for(int i = 0; i< ROW_SIZE_A7; i++) {
            Map<String, MasterCellData> dataA7 = new HashMap<>();
            dataA7.put(KAF022_454, MasterCellData.builder()
                    .columnId(KAF022_454)
                    .value(i == 0 ? TextResource.localize("KAF022_464") : "")
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            dataA7.put(A4_2, MasterCellData.builder()
                    .columnId(A4_2)
                    .value(export[20])
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            dataA7.put(A4_3, MasterCellData.builder()
                    .columnId(A4_3)
                    .value(getTextA7Top(i,2))
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            dataA7.put(A7_4, MasterCellData.builder()
                    .columnId(A7_4)
                    .value(getTextA7Top(i,0))
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            dataA7.put(A7_5, MasterCellData.builder()
                    .columnId(A7_5)
                    .value(getTextA7Top(i,1))
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            dataA7.put(KAF022_455, MasterCellData.builder()
                    .columnId(KAF022_455)
                    .value(getValueA7Top(i, export))
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            datasA7.add(MasterData.builder().rowData(dataA7).build());
        }
        return datasA7;
    }

    private  List<MasterData> putDatasA7Bottom(Object[] export ) {
        List<MasterData> datasA7 = new ArrayList<>();
        for(int i = 0;i< ROW_SIZE_A7_BOTTOM ; i++) {
            Map<String, MasterCellData> dataA7 = new HashMap<>();
            dataA7.put(KAF022_454, MasterCellData.builder()
                    .columnId(KAF022_454)
                    .value("")
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            dataA7.put(A4_2, MasterCellData.builder()
                    .columnId(A4_2)
                    .value(EnumAdaptor.valueOf(((BigDecimal) export[27]).intValue(), ApplicationType.class).nameId )
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            dataA7.put(A4_3, MasterCellData.builder()
                    .columnId(A4_3)
                    .value(i == 0 ? TextResource.localize("KAF022_467") : "")
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            dataA7.put(A7_4, MasterCellData.builder()
                    .columnId(A7_4)
                    .value(i== 0 ? TextResource.localize("KAF022_467") : "")
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            dataA7.put(A7_5, MasterCellData.builder()
                    .columnId(A7_5)
                    .value(i == 1 ? TextResource.localize("KAF022_468") : "")
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            dataA7.put(KAF022_455, MasterCellData.builder()
                    .columnId(KAF022_455)
                    .value(getValueA7Bottom(i, export))
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            datasA7.add(MasterData.builder().rowData(dataA7).build());
        }
        return datasA7;
    }

    private  List<MasterData> putDatasA8Top(Object[] export ) {
        List<MasterData> datasA8Top = new ArrayList<>();
        for(int i = 0; i< ROW_SIZE_A8_TOP; i++) {
            Map<String, MasterCellData> dataA8Top = new HashMap<>();
            dataA8Top.put(KAF022_454, MasterCellData.builder()
                    .columnId(KAF022_454)
                    .value( i == 0 && ((Long)export[21]).intValue() == 1 ? TextResource.localize("KAF022_464")  : "")
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            dataA8Top.put(A4_2, MasterCellData.builder()
                    .columnId(A4_2)
                    .value( i == 0 ? EnumAdaptor.valueOf(((BigDecimal) export[27]).intValue(), ApplicationType.class).nameId : "")
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            dataA8Top.put(A4_3, MasterCellData.builder()
                    .columnId(A4_3)
                    .value(this.getTextA8Top(i,0))
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            dataA8Top.put(A7_4, MasterCellData.builder()
                    .columnId(A7_4)
                    .value(getTextA8Top(i, 1))
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            dataA8Top.put(A7_5, MasterCellData.builder()
                    .columnId(A7_5)
                    .value(TextResource.localize("KAF022_468"))
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            dataA8Top.put(KAF022_455, MasterCellData.builder()
                    .columnId(KAF022_455)
                    .value(getValueA8Top(i, export))
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            datasA8Top.add(MasterData.builder().rowData(dataA8Top).build());
        }
        return datasA8Top;
    }



    private  List<MasterData> putDatasA8Center(Object[] export ) {
        List<MasterData> datasA8Center = new ArrayList<>();
        for(int i = 0; i< ROW_SIZE_A8_CENTER; i++) {
            Map<String, MasterCellData> dataA8Center = new HashMap<>();
            dataA8Center.put(KAF022_454, MasterCellData.builder()
                    .columnId(KAF022_454)
                    .value(i == 0 ? TextResource.localize("KAF022_464") : "")
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            dataA8Center.put(A4_2, MasterCellData.builder()
                    .columnId(A4_2)
                    .value("")
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            dataA8Center.put(A4_3, MasterCellData.builder()
                    .columnId(A4_3)
                    .value(this.getLeaveType(((BigDecimal)export[39]).intValue()))
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            dataA8Center.put(A7_4, MasterCellData.builder()
                    .columnId(A7_4)
                    .value(i == 0 ? TextResource.localize(KAF022_478) : TextResource.localize(KAF022_479))
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            dataA8Center.put(A7_5, MasterCellData.builder()
                    .columnId(A7_5)
                    .value(TextResource.localize(KAF022_468))
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            dataA8Center.put(KAF022_455, MasterCellData.builder()
                    .columnId(KAF022_455)
                    .value(getValueA8Center(i, export))
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            datasA8Center.add(MasterData.builder().rowData(dataA8Center).build());
        }
        return datasA8Center;
    }

    private  List<MasterData> putDatasA9(Object[] export ) {
        List<MasterData> datasA9 = new ArrayList<>();
        for(int i = 0; i< ROW_SIZE_A9; i++) {
            Map<String, MasterCellData> dataA9 = new HashMap<>();
            dataA9.put(KAF022_454, MasterCellData.builder()
                    .columnId(KAF022_454)
                    .value(i == 0 ? TextResource.localize("KAF022_485") : "")
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            dataA9.put(A4_2, MasterCellData.builder()
                    .columnId(A4_2)
                    .value(this.getTextA9(i))
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            dataA9.put(A4_3, MasterCellData.builder()
                    .columnId(A4_3)
                    .value("")
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            dataA9.put(A7_4, MasterCellData.builder()
                    .columnId(A7_4)
                    .value("")
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            dataA9.put(A7_5, MasterCellData.builder()
                    .columnId(A7_5)
                    .value("")
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            dataA9.put(KAF022_455, MasterCellData.builder()
                    .columnId(KAF022_455)
                    .value(this.getValueA9(i, export))
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            datasA9.add(MasterData.builder().rowData(dataA9).build());
        }
        return datasA9;
    }

    private  List<MasterData> putDatasA10(Object[] export ) {
        List<MasterData> datasA10 = new ArrayList<>();
        for(int i = 0; i< ROW_SIZE_A10; i++) {
            Map<String, MasterCellData> dataA10 = new HashMap<>();
            dataA10.put(KAF022_454, MasterCellData.builder()
                    .columnId(KAF022_454)
                    .value(i == 0 ? TextResource.localize("KAF022_492") : "")
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            dataA10.put(A4_2, MasterCellData.builder()
                    .columnId(A4_2)
                    .value(i == 0 ? TextResource.localize("KAF022_493") : TextResource.localize("KAF022_494"))
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            dataA10.put(A4_3, MasterCellData.builder()
                    .columnId(A4_3)
                    .value("")
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            dataA10.put(A7_4, MasterCellData.builder()
                    .columnId(A7_4)
                    .value("")
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            dataA10.put(A7_5, MasterCellData.builder()
                    .columnId(A7_5)
                    .value("")
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            dataA10.put(KAF022_455, MasterCellData.builder()
                    .columnId(KAF022_455)
                    .value(i == 0 ? this.getValueEnum(((BigDecimal)export[18]).intValue()) : this.getValueEnum(((BigDecimal)export[48]).intValue()))
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            datasA10.add(MasterData.builder().rowData(dataA10).build());
        }
        return datasA10;
    }

    private  List<MasterData> putDatasA11(Object[] export ) {
        List<MasterData> datasA11 = new ArrayList<>();
        Map<String, MasterCellData> dataA11 = new HashMap<>();
        dataA11.put(KAF022_454, MasterCellData.builder()
                .columnId(KAF022_454)
                .value(TextResource.localize("KAF022_495"))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
        dataA11.put(A4_2, MasterCellData.builder()
                .columnId(A4_2)
                .value(TextResource.localize("KAF022_496"))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
        dataA11.put(A4_3, MasterCellData.builder()
                .columnId(A4_3)
                .value("")
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
        dataA11.put(A7_4, MasterCellData.builder()
                .columnId(A7_4)
                .value("")
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
        dataA11.put(A7_5, MasterCellData.builder()
                .columnId(A7_5)
                .value("")
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
        dataA11.put(KAF022_455, MasterCellData.builder()
                .columnId(KAF022_455)
                .value(EnumAdaptor.valueOf(((BigDecimal) export[20]).intValue(), ApplicationType.class).nameId)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
        datasA11.add(MasterData.builder().rowData(dataA11).build());
        return datasA11;
    }

    private  List<MasterData> putDatasA12(Object[] export ) {
        List<MasterData> datasA12 = new ArrayList<>();
        Map<String, MasterCellData> dataA12 = new HashMap<>();
        dataA12.put(KAF022_454, MasterCellData.builder()
                .columnId(KAF022_454)
                .value(TextResource.localize("KAF022_497"))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
        dataA12.put(A4_2, MasterCellData.builder()
                .columnId(A4_2)
                .value(TextResource.localize("KAF022_498"))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
        dataA12.put(A4_3, MasterCellData.builder()
                .columnId(A4_3)
                .value("")
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
        dataA12.put(A7_4, MasterCellData.builder()
                .columnId(A7_4)
                .value("")
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
        dataA12.put(A7_5, MasterCellData.builder()
                .columnId(A7_5)
                .value("")
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
        dataA12.put(KAF022_455, MasterCellData.builder()
                .columnId(KAF022_455)
                .value(this.getValueEnum(((BigDecimal)export[12]).intValue()))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
        datasA12.add(MasterData.builder().rowData(dataA12).build());
        return datasA12;
    }

    private  List<MasterData> putDatasA13(Object[] export ) {
        List<MasterData> datasA13 = new ArrayList<>();
        for(int i = 0; i< ROW_SIZE_A13; i++) {
            Map<String, MasterCellData> dataA13 = new HashMap<>();
            dataA13.put(KAF022_454, MasterCellData.builder()
                    .columnId(KAF022_454)
                    .value(i == 0 ? TextResource.localize("KAF022_499") : "")
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            dataA13.put(A4_2, MasterCellData.builder()
                    .columnId(A4_2)
                    .value(this.getTextA13(i))
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            dataA13.put(A4_3, MasterCellData.builder()
                    .columnId(A4_3)
                    .value("")
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            dataA13.put(A7_4, MasterCellData.builder()
                    .columnId(A7_4)
                    .value("")
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            dataA13.put(A7_5, MasterCellData.builder()
                    .columnId(A7_5)
                    .value("")
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            dataA13.put(KAF022_455, MasterCellData.builder()
                    .columnId(KAF022_455)
                    .value(this.getValueA13(i, export))
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            datasA13.add(MasterData.builder().rowData(dataA13).build());
        }
        return datasA13;
    }

    private String getTextA4(int i){
        if(i == 0) {
            return KAF022_456;
        }
        if(i == 1) {
            return KAF022_458;
        }
        if(i == 2) {
            return KAF022_459;
        }
        return "";
    }

    private String getValueA4(int i, Object[] obj){
        if(i == 0) {
            return obj[1].toString();
        }
        if(i == 1) {
            return ((BigDecimal)obj[3]).intValue() == 1 ? "○" : "";
        }
        if(i == 2) {
            return ((BigDecimal) obj[4]).intValue() == 0 ? TextResource.localize("KAF022_321") : TextResource.localize("KAF022_322");
        }
        return getTextDeadLine(((BigDecimal) obj[5]).intValue());
    }
    
    private String getTextDeadLine(int value){
        if(value == 0 ) {
            return TextResource.localize("KAF022_321");
        }
        int text = 323 + value;
        String resource = "KAF022_" + text;
        return TextResource.localize(resource) + TextResource.localize("KAF022_509");
    }

    private String getTextA5(int i){
        if(i == 0) {
            return TextResource.localize("KAF022_461");
        }
        if(i == 1) {
            return TextResource.localize("KAF022_462");
        }
        return TextResource.localize("KAF022_463");
    }

    private String getValueA5(int i, Object[] obj){
        if(i == 0) {
            if(((BigDecimal)obj[6]).intValue() == 0) {
                return TextResource.localize("KAF022_403");
            }
            return TextResource.localize("KAF022_404");
        }
        if(i == 1) {
            if(((BigDecimal)obj[13]).intValue() == 1) {
                return TextResource.localize("KAF022_272");
            }
            return TextResource.localize("KAF022_273");
        }
        if(i == 2) {
            if(((BigDecimal)obj[7]).intValue() == 1) {
                return TextResource.localize("KAF022_389");
            }
            return TextResource.localize("KAF022_390");
        }
        return "";
    }

    private String getTextA7Top(int i, int type){
        if(i == 0 && type == 0) {
            return TextResource.localize("KAF022_468");
        }
        if(i == 1 && type == 0) {
            return TextResource.localize("KAF022_469");
        }
        if(i == 2 && type == 0) {
            return TextResource.localize("KAF022_470");
        }
        if(i == 3 && type == 0) {
            return TextResource.localize("KAF022_472");
        }
        if(i == 6 && type == 0) {
            return TextResource.localize("KAF022_477");
        }
        if(i == 2 && type == 1) {
            return TextResource.localize("KAF022_471");
        }
        if(i == 3 && type == 1) {
            return TextResource.localize("KAF022_473");
        }
        if(i == 4 && type == 1) {
            return TextResource.localize("KAF022_474");
        }
        if(i == 5 && type == 1) {
            return TextResource.localize("KAF022_475");
        }
        if(i == 0 && type == 2) {
            return TextResource.localize("KAF022_467");
        }
        if(i == 6 && type == 2) {
            return TextResource.localize("KAF022_476");
        }
        return "";
    }

    private String getTextA8Top(int i, int type){
        if(i == 0 && type == 0) {
            return TextResource.localize("KAF022_478");
        }
        if(i == 1 && type == 0) {
            return TextResource.localize("KAF022_479");
        }
        if(i == 2 && type == 0) {
            return TextResource.localize("KAF022_480");
        }
        if(i == 3 && type == 0) {
            return TextResource.localize("KAF022_482");
        }
        if(i == 4 && type == 0) {
            return TextResource.localize("KAF022_483");
        }
        if(i == 5 && type == 0) {
            return TextResource.localize("KAF022_484");
        }
        if((i == 0 && type == 1) || (i == 1 && type == 1)) {
            return TextResource.localize("KAF022_468");
        }
        if((i == 2 && type == 1) || (i == 3 && type == 1) || (i == 4 && type == 1)) {
            return TextResource.localize("KAF022_481");
        }
        return "";
    }

    private String getTextA9(int i){
        if(i == 0 ) {
            return TextResource.localize("KAF022_471");
        }
        if(i == 1) {
            return TextResource.localize("KAF022_473");
        }
        if(i == 2) {
            return TextResource.localize("KAF022_474");
        }
        if(i == 3) {
            return TextResource.localize("KAF022_475");
        }
        if(i == 4) {
            return TextResource.localize("KAF022_467");
        }
        return TextResource.localize("KAF022_476");
    }

    private String getValueA9(int i, Object[] obj){
        if(i == 0 && obj[42] != null) {
            return ((BigDecimal)obj[42]).intValue() == 1 ? TextResource.localize("KAF022_100") : TextResource.localize("KAF022_101");
        }
        if(i == 2 && obj[43] != null) {
            return ((BigDecimal)obj[42]).intValue() == 1 ? TextResource.localize("KAF022_100") : TextResource.localize("KAF022_101");
        }
        if(i == 3 && obj[44] != null) {
            return ((BigDecimal)obj[44]).intValue() == 1 ? "申請できる" : "申請できない";
        }
        if(i == 4 && obj[45] != null) {
            return ((BigDecimal)obj[45]).intValue() == 1 ? "申請できる" : "申請できない";
        }
        if(i == 5 && obj[30] != null) {
            return ((BigDecimal)obj[30]).intValue() == 1 ? "申請できる" : "申請できない";

        }
        return "";
    }

    private String getTextA13(int i){
        if(i == 0 ) {
            return TextResource.localize("KAF022_500");
        }
        if(i == 1) {
            return TextResource.localize("KAF022_501");
        }
        if(i == 2) {
            return TextResource.localize("KAF022_502");
        }
        if(i == 3) {
            return TextResource.localize("KAF022_503");
        }
        if(i == 4) {
            return TextResource.localize("KAF022_504");
        }
        if(i == 5) {
            return TextResource.localize("KAF022_505");
        }
        if(i == 6) {
            return TextResource.localize("KAF022_506");
        }
        if(i == 7) {
            return TextResource.localize("KAF022_507");
        }
        return "";
    }

    private String getValueA13(int i, Object[] obj){
        if(i == 0 && obj[8] != null) {
            return obj[8].toString();
        }
        if(i == 1 && obj[9] != null) {
            return obj[9].toString();
        }
        if(i == 2 && obj[10] != null) {
            return obj[10].toString();
        }
        if(i == 3 && obj[11] != null) {
            return obj[11].toString();
        }
        if(i == 4 && obj[12] != null) {
            return obj[12].toString();

        }
        if(i == 5 && obj[13] != null) {
            return obj[13].toString();

        }
        if(i == 6 && obj[14] != null) {
            return obj[14].toString();

        }
        if(i == 7 && obj[15] != null) {
            return obj[15].toString();

        }
        return "";
    }


    private String getValueA7Top(int i, Object[] obj){
        if(i == 0 && obj[28] != null ) {
            return ((BigDecimal)obj[28]).intValue() == 1 ? "○" : "-";
        }
        if(i == 1 && obj[23] != null ) {
            return EnumAdaptor.valueOf(((BigDecimal) obj[23]).intValue(), BeforeAddCheckMethod.class).name;
        }
        if(i == 2 && obj[29] != null ) {
            return EnumAdaptor.valueOf(((BigDecimal) obj[29]).intValue(), AppAcceptLimitDay.class).name + TextResource.localize("KAF022_510");
        }
        if(i == 3 && obj[24] != null ) {
            return obj[24].toString() + TextResource.localize("KAF022_510");
        }
        if(i == 4 && obj[25] != null ) {
            return obj[25].toString() + TextResource.localize("KAF022_510");
        }
        if(i == 5 && obj[26] != null ) {
            return obj[26].toString() + TextResource.localize("KAF022_510");
        }
        if(i == 6 && obj[30] != null) {
            return ((BigDecimal)obj[30]).intValue() == 1 ? "○" : "-";
        }
        return "";
    }

    private String getValueA8Top(int i, Object[] obj){
        if(i == 0 && obj[33] != null ) {
            return ((BigDecimal)obj[33]).intValue() == 1 ? "○" : "-";
        }
        if(i == 1 && obj[17] != null ) {
            return ((BigDecimal)obj[17]).intValue() == 1 ? "○" : "-";
        }
        if(i == 2 && obj[31] != null ) {
            return ((BigDecimal)obj[31]).intValue() == 1 ? "○" : "-";
        }
        if(i == 3 && obj[32] != null ) {
            return ((BigDecimal)obj[32]).intValue() == 1 ? "○" : "-";
        }
        if(i == 4 && obj[18] != null ) {
            return ((BigDecimal)obj[18]).intValue() == 1 ? "○" : "-";
        }
        if(i == 5 && obj[34] != null ) {
            return ((BigDecimal)obj[34]).intValue() == 1 ? "○" : "-";
        }
        return "";
    }
    private String getValueA8Center(int i, Object[] obj){
        if(i == 0 && obj[42] != null ) {
            return this.getLeaveType(((BigDecimal)obj[42]).intValue());
        }
        if(i == 1 && obj[41] != null ) {
            return ((BigDecimal)obj[41]).intValue() == 1 ? "○" : "-";
        }
        return "";
    }

    //Enum
    private String getValueEnum(int i) {

        //表示する
        if(i == 1) {
            return TextResource.localize("KAF022_36");
        }
        //表示する
        return TextResource.localize("KAF022_37");
    }

    private String getLeaveType(int i){
        if(i == 0) {
            return TextResource.localize("KAF022_323");
        }
        if(i == 1) {
            return TextResource.localize("KAF022_357");
        }
        if(i == 2) {
            return TextResource.localize("KAF022_358");
        }
        if(i == 3) {
            return TextResource.localize("KAF022_359");
        }
        if(i == 4) {
            return TextResource.localize("KAF022_360");
        }
        if(i == 5) {
            return TextResource.localize("KAF022_361");
        }
        if(i == 6) {
            return TextResource.localize("KAF022_362");
        }
        if(i == 7) {
            return TextResource.localize("KAF022_363");
        }
        return "";

    }

    private String getValueA7Bottom(int i, Object[] obj){
        if(i == 0 && obj[28] != null) {
            return ((BigDecimal)obj[28]).intValue() == 1 ? "○" : "-";
        }
        if(i== 1 && obj[29] != null) {
            return EnumAdaptor.valueOf(((BigDecimal) obj[29]).intValue(), AppAcceptLimitDay.class).name + TextResource.localize("KAF022_510");
        }
        if(i == 0 && obj[30] != null) {
            return ((BigDecimal)obj[30]).intValue() == 1 ? "○" : "-";
        }
        return "";
    }

    public List<MasterHeaderColumn> getHeaderColumns(Sheet sheet) {
        List <MasterHeaderColumn> columns = new ArrayList<>();
        switch (sheet) {
            case JOB:
                columns.addAll(preparationBeforeApply.getHeaderColumnsJob());
                break;
            case REASON:
                columns.addAll(preparationBeforeApply.getHeaderColumnsReaSon());
                break;
        }
        return columns;
    }

    private List<MasterData> getData(Sheet sheet) {
        String companyId = AppContexts.user().companyId();
        String baseDate = "9999-12-31";
        List <MasterData> datas = new ArrayList<>();
        switch (sheet){
            case REASON:
                List<Object[]> extraDate = preparationBeforeApplyRepository.getExtraData(companyId);
                datas.addAll(preparationBeforeApply.getDataReaSon(extraDate));
                break;
            case JOB:
                List<Object[]> preparationBefore = preparationBeforeApplyRepository.getJob(companyId, baseDate);
                datas.addAll(preparationBeforeApply.getDataJob(preparationBefore));
                break;
        }
        return datas;
    }



    @Override
    public List<MasterData> getMasterDatas(MasterListExportQuery query) {
        String companyId = AppContexts.user().companyId();
        List<MasterData> datas = new ArrayList<>();
        List<MasterData> datasA4 = new ArrayList<>();
        List<MasterData> datasA5 = new ArrayList<>();
        List<MasterData> datasA6 = new ArrayList<>();
        List<MasterData> datasA7 = new ArrayList<>();
        List<MasterData> datasA7Bottom = new ArrayList<>();
        List<MasterData> datasA8Top = new ArrayList<>();
        List<MasterData> datasA8Center = new ArrayList<>();
        List<MasterData> datasA8Bottom = new ArrayList<>();
        List<MasterData> datasA9 = new ArrayList<>();
        List<MasterData> datasA10 = new ArrayList<>();
        List<MasterData> datasA11 = new ArrayList<>();
        List<MasterData> datasA12 = new ArrayList<>();
        List<MasterData> datasA13 = new ArrayList<>();
        List<Object[]> preparationBefore = preparationBeforeApplyRepository.getChangePerInforDefinitionToExport(companyId);
        preparationBefore.forEach(item->{
            if(item[0] != null) {
                datasA4.addAll(this.putDatasA4(item));
            }
            if(item[6] != null && ((Long)item[0]).intValue() == 1) {
                datasA5.addAll(this.putDatasA5(item));
            }
            if(item[35] != null) {
                datasA6.addAll(this.putDatasA6(item));
            }
            if(item[21] != null && ((BigDecimal)item[27]).intValue() == 0) {
                datasA7.addAll(this.putDatasA7(item));
            }
            if(item[21] != null && ((BigDecimal)item[27]).intValue() != 0) {
                datasA7Bottom.addAll(this.putDatasA7Bottom(item));
            }
            if(item[27] != null) {
                if (((BigDecimal) item[27]).intValue() == 1 || ((BigDecimal) item[27]).intValue() == 0) {
                    datasA8Top.addAll(this.putDatasA8Top(item));
                } else {
                    datasA8Bottom.addAll(this.putDatasA8Top(item));
                }
            }
            if(item[38] != null) {
                datasA8Center.addAll(this.putDatasA8Center(item));
            }
            if(item[6] != null) {
                datasA9.addAll(this.putDatasA9(item));
            }
            if(item[6] != null) {
                datasA9.addAll(this.putDatasA9(item));
                datasA10.addAll(this.putDatasA10(item));
            }
            if(item[20] != null) {
                datasA11.addAll(this.putDatasA11(item));
            }
            if(item[12] != null) {
                datasA12.addAll(this.putDatasA12(item));
            }
            if(item[8] != null) {
                datasA13.addAll(this.putDatasA13(item));
            }

        });
        datas.addAll(datasA4);
        datas.addAll(datasA5);
        datas.addAll(datasA6);
        datas.addAll(datasA7);
        datas.addAll(datasA7Bottom);
        datas.addAll(datasA8Top);
        datas.addAll(datasA8Center);
        datas.addAll(datasA8Bottom);
        datas.addAll(datasA9);
        datas.addAll(datasA10);
        datas.addAll(datasA11);
        datas.addAll(datasA12);
        datas.addAll(datasA13);
        return datas;
    }

    @Override
    public List<SheetData> extraSheets(MasterListExportQuery query){
        List<SheetData> sheetData = new ArrayList<>();
       /* EnumSet.allOf(Sheet.class)
                .forEach( i ->{
                    SheetData job = new SheetData(this.getData(i),
                            getHeaderColumns(i), null, null, getSheetName(i));
                    sheetData.add(job);
                });*/
        SheetData reson = new SheetData(this.getData(Sheet.REASON),
                getHeaderColumns(Sheet.REASON), null, null, getSheetName(Sheet.REASON));
        sheetData.add(reson);
        SheetData job = new SheetData(this.getData(Sheet.JOB),
                getHeaderColumns(Sheet.JOB), null, null, getSheetName(Sheet.JOB));
        sheetData.add(job);
        return sheetData;
    }

    @Override
    public String mainSheetName() {
        return TextResource.localize("KAF022_453");
    }

    private String getSheetName(Sheet sheet){
        switch (sheet) {
            case JOB: return TextResource.localize("KAF022_515");
            case REASON: return TextResource.localize("KAF022_511");
        }

        return "";
    }
}
