package nts.uk.ctx.exio.dom.input.canonicalize.domains.recode.stamp.enterprise;

import lombok.val;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.AuthcMethod;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampMeans;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ChangeCalArt;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ChangeClockAtr;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.SetPreClockArt;
import nts.uk.ctx.at.shared.dom.workrule.goingout.GoingOutReason;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.ItemNoMap;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.recode.stamp.StampCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.recode.stamp.enterprise.pv.EnterpriseStampDataFunctionNo;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.recode.stamp.enterprise.pv.EnterpriseStampDataStampAttr;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.recode.stamp.enterprise.pv.EnterpriseStampDataTerminalAttr;
import nts.uk.ctx.exio.dom.input.canonicalize.result.CanonicalItem;
import nts.uk.ctx.exio.dom.input.canonicalize.result.CanonicalItemList;
import nts.uk.ctx.exio.dom.input.canonicalize.result.IntermediateResult;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class EnterpriseStampCanonicalization extends StampCanonicalization {

    @Override
    public ItemNoMap getItemNoMap() {
        return ItemNoMap.reflection(Items.class);
    }

    public static class Items {
        public static final int カードNO = 1;
        public static final int 年月日 = 2;
        public static final int 時分 = 3;
        public static final int 秒 = 4;
        public static final int 認証方法 = 5;
        public static final int 打刻手段 = 6;
        public static final int 時刻変更区分 = 7;
        public static final int 計算区分変更対象 = 8;
        public static final int 所定時刻セット区分 = 9;
        public static final int 勤務種類を半休に変更する = 10;
        public static final int 外出区分 = 11;
        public static final int 就業時間帯コード = 12;
        public static final int 時間外時間 = 13;
        public static final int 時間外深夜時間 = 14;
        public static final int 職場コード = 15;
        public static final int 勤務場所コード = 16;
        public static final int 応援カードNO = 17;
        public static final int 就業情報端末コード = 18;
        public static final int 打刻日時 = 101;
        public static final int 職場ID = 102;
        public static final int 作業コード1 = 103;
        public static final int 作業コード2 = 104;
        public static final int 作業コード3 = 105;
        public static final int 作業コード4 = 106;
        public static final int 作業コード5 = 107;
        public static final int 緯度 = 108;
        public static final int 経度 = 109;
        public static final int 反映済み区分 = 110;
        public static final int 反映された年月日 = 111;
        public static final int 打刻区分 = 201;
        public static final int ファンクションNO = 202;
        public static final int 端末区分 = 203;
    }

    @Override
    protected IntermediateResult preCanonicalize(IntermediateResult interm) {

        val 打刻区分 = new EnterpriseStampDataStampAttr(interm.getItemByNo(Items.打刻区分).get().getString());
        val ファンクションNO = new EnterpriseStampDataFunctionNo(interm.getItemByNo(Items.ファンクションNO).get().getJavaInt());
        val 端末区分 = new EnterpriseStampDataTerminalAttr(interm.getItemByNo(Items.端末区分).get().getString());

        ChangeClockAtr 時刻変更区分 = 打刻区分.toChangeClockAtr();
        ChangeCalArt 計算区分変更対象 = 打刻区分.toChangeCalAtr();
        boolean 勤務種類を半休に変更する = 打刻区分.isHalfDay();
        SetPreClockArt 所定時刻セット区分 = 打刻区分.toSetPreClockAtr(ファンクションNO);
        Optional<GoingOutReason> 外出区分 = 打刻区分.toGoOutReason(ファンクションNO);
        AuthcMethod 認証方法 = 端末区分.toAuthMethod();
        StampMeans 打刻手段 = 端末区分.toStampMeans();

        interm = interm.addCanonicalized(new CanonicalItemList()
                .add(Items.時刻変更区分, 時刻変更区分.value)
                .add(Items.計算区分変更対象, 計算区分変更対象.value)
                .add(Items.勤務種類を半休に変更する, 勤務種類を半休に変更する ? 1 : 0)
                .add(Items.所定時刻セット区分, 所定時刻セット区分.value)
                .add(Items.認証方法, 認証方法.value)
                .add(Items.打刻手段, 打刻手段.value));

        if (外出区分.isPresent()) {
            interm = interm.addCanonicalized(CanonicalItem.of(Items.外出区分, 外出区分.get().value));
        }

        // Empty扱いとなる値
        for (val e : EMPTY_VALUES.entrySet()) {
            interm = canonicalizeEmpty(interm, e.getKey(), e.getValue());
        }

        return interm;
    }

    private static final Map<Integer, Object> EMPTY_VALUES;
    static {
        EMPTY_VALUES = new HashMap<>();
        EMPTY_VALUES.put(Items.勤務場所コード, "0000");
        EMPTY_VALUES.put(Items.就業時間帯コード, "000");
        EMPTY_VALUES.put(Items.応援カードNO, "    ");
        EMPTY_VALUES.put(Items.時間外時間, -1);
        EMPTY_VALUES.put(Items.時間外深夜時間, -1);
    }

    private static IntermediateResult canonicalizeEmpty(IntermediateResult interm, int itemNo, Object emptyValue) {

        if (!interm.isImporting(itemNo)) {
            return interm;
        }

        Object value = interm.getItemByNo(itemNo).map(item -> item.getValue()).orElse(null);
        if (Objects.equals(value, emptyValue)) {
            return interm.addCanonicalized(CanonicalItem.nullValue(itemNo));
        }

        return interm;
    }
}
