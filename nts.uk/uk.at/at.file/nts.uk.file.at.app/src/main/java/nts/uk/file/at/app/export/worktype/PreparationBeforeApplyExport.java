package nts.uk.file.at.app.export.worktype;

import nts.arc.enums.EnumAdaptor;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.masterlist.data.*;
import nts.uk.shr.infra.file.report.masterlist.webservice.MasterListExportQuery;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Stateless
public class PreparationBeforeApplyExport {


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
    private static final String NO_REGIS = "マスタ未登録";
    private static final String NO_HEADER = "HEADER";
    private static final int SIZE_OVER_TIME = 4;

    @Inject
    private PreparationBeforeApplyRepository preparationBeforeApplyRepository;

    public List<MasterHeaderColumn> getHeaderColumnsReaSon() {
        List<MasterHeaderColumn> columns = new ArrayList<>();
        columns.add(new MasterHeaderColumn(KAF022_512, TextResource.localize("KAF022_512"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(KAF022_513, TextResource.localize("KAF022_513"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(KAF022_514, TextResource.localize("KAF022_514"),
                ColumnTextAlign.LEFT, "", true));
        return columns;
    }

    public List<MasterHeaderColumn> getHeaderColumnsJob(){
        List<MasterHeaderColumn> columns = new ArrayList<>();
        columns.add(new MasterHeaderColumn(KAF022_516, TextResource.localize("KAF022_516"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(KAF022_517, TextResource.localize("KAF022_517"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(KAF022_518, TextResource.localize("KAF022_518"),
                ColumnTextAlign.LEFT, "", true));
        return columns;
    }

    public List<MasterHeaderColumn> getHeaderColumnsOverTime(){
        List<MasterHeaderColumn> columns = new ArrayList<>();
        columns.add(new MasterHeaderColumn(KAF022_454, TextResource.localize("KAF022_454"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(NO_HEADER, "",
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(KAF022_455, TextResource.localize("KAF022_455"),
                ColumnTextAlign.LEFT, "", true));
        return columns;
    }


    public List<MasterData> getDataJob(List<Object[]> obj) {
        List<MasterData> datas = new ArrayList<>();
        obj.forEach(j -> {
            if(j[0] != null) {
                Map<String, MasterCellData> data = new HashMap<>();
                data.put(KAF022_516, MasterCellData.builder()
                        .columnId(KAF022_516)
                        .value(j[0])
                        .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                        .build());

                data.put(KAF022_517, MasterCellData.builder()
                        .columnId(KAF022_517)
                        .value(j[1] == null ? NO_REGIS : j[1])
                        .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                        .build());
                data.put(KAF022_518, MasterCellData.builder()
                        .columnId(KAF022_518)
                        .value(j[2])
                        .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                        .build());
                datas.add(MasterData.builder().rowData(data).build());
            }
        });
        return datas;
    }

    public List<MasterData> getDataReaSon(List<Object[]> obj) {
        List<MasterData> datas = new ArrayList<>();
        obj.forEach(r -> {
            Map<String, MasterCellData> data = new HashMap<>();
            if(r[14] != null) {
                data.put(KAF022_512, MasterCellData.builder()
                        .columnId(KAF022_512)
                        .value(EnumAdaptor.valueOf(((BigDecimal) r[14]).intValue(), ApplicationType.class).nameId)
                        .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                        .build());

                data.put(KAF022_513, MasterCellData.builder()
                        .columnId(KAF022_513)
                        .value(((BigDecimal) r[16]).intValue() == 1 ? "○" : "-")
                        .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                        .build());
                data.put(KAF022_513, MasterCellData.builder()
                        .columnId(KAF022_513)
                        .value(r[16])
                        .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                        .build());
                datas.add(MasterData.builder().rowData(data).build());
            }
        });
        return datas;
    }

    public List<MasterData> getDataOverTime(List<Object[]> obj) {
        List<MasterData> datas = new ArrayList<>();
        obj.forEach(ot -> {
            Map<String, MasterCellData> data = new HashMap<>();
            for(int i = 0; i< SIZE_OVER_TIME; i++) {
                data.put(KAF022_512, MasterCellData.builder()
                        .columnId(KAF022_512)
                        .value(this.getTextOverTime(i, 0))
                        .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                        .build());
                data.put(KAF022_513, MasterCellData.builder()
                        .columnId(KAF022_513)
                        .value(this.getTextOverTime(i,1))
                        .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                        .build());
                data.put(KAF022_513, MasterCellData.builder()
                        .columnId(KAF022_513)
                        .value(this.getValueOT(i, ot))
                        .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                        .build());
                datas.add(MasterData.builder().rowData(data).build());
            }
        });
        return datas;
    }

    private String getTextOverTime(int line, int column){
        if (column == 1) {
            switch (line) {
                case 0:
                    return TextResource.localize("KAF022_521");
                case 1:
                    return TextResource.localize("KAF022_522");
                case 2:
                    return TextResource.localize("KAF022_523");
                case 3:
                    return TextResource.localize("KAF022_524");
                case 4:
                    return TextResource.localize("KAF022_525");
                case 5:
                    return TextResource.localize("KAF022_526");
                case 6:
                    return TextResource.localize("KAF022_527");
                case 7:
                    return TextResource.localize("KAF022_528");
                case 8:
                    return TextResource.localize("KAF022_529");
                case 9:
                    return TextResource.localize("KAF022_530");
                case 10:
                    return TextResource.localize("KAF022_531");
                case 11:
                    return TextResource.localize("KAF022_532");
                case 12:
                    return TextResource.localize("KAF022_533");
                case 13:
                    return TextResource.localize("KAF022_534");
            }
        }
        if (column == 0) {
            switch (line) {
                case 0:
                    TextResource.localize("KAF022_520");
                case 5:
                    TextResource.localize("KAF022_526");
                case 9:
                    TextResource.localize("KAF022_531");
            }
        }
        return "";
    }

    private String getValueOT(int line, Object obj[]){
        switch (line) {
            case 0:
                return EnumAdaptor.valueOf(((BigDecimal)obj[0]).intValue(), NotUseAtr.class).nameId;
            case 1:
                return TextResource.localize("KAF022_522");
            case 2:
                return TextResource.localize("KAF022_523");
            case 3:
                return TextResource.localize("KAF022_524");
            case 4:
                return TextResource.localize("KAF022_525");
            case 5:
                return TextResource.localize("KAF022_526");
            case 6:
                return TextResource.localize("KAF022_527");
            case 7:
                return TextResource.localize("KAF022_528");
            case 8:
                return TextResource.localize("KAF022_529");
            case 9:
                return TextResource.localize("KAF022_530");
            case 10:
                return TextResource.localize("KAF022_531");
            case 11:
                return TextResource.localize("KAF022_532");
            case 12:
                return TextResource.localize("KAF022_533");
            case 13:
                return TextResource.localize("KAF022_534");
        }
        return "";
    }

}
