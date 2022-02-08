package nts.uk.ctx.exio.dom.input.canonicalize.domains.recode.stamp.enterprise;

import lombok.RequiredArgsConstructor;
import nts.arc.primitive.PrimitiveValueConstraintException;
import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ChangeCalArt;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ChangeClockAtr;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.SetPreClockArt;
import nts.uk.ctx.at.shared.dom.workrule.goingout.GoingOutReason;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * E版打刻データ打刻区分
 */
@StringCharType(CharType.ANY_HALF_WIDTH)
@StringMaxLength(1)
public class EnterpriseStampDataStampAttr extends StringPrimitiveValue<EnterpriseStampDataStampAttr> {

    public EnterpriseStampDataStampAttr(String rawValue) {
        super(rawValue);
    }

    @Override
    public void validate() {
        super.validate();

        if (!MAP.containsKey(this.v())) {
            throw new PrimitiveValueConstraintException("不正な打刻区分です：" + this.v());
        }
    }

    /**
     * サポート対象の打刻区分か
     * UKではサポートしない（受け入れずに無視する）区分があるので、それの判定をする。
     * 変換系のメソッドを呼ぶ前に必ずこれを呼んでチェックすること。
     * @return
     */
    public boolean isSupported() {
        return convert() != null;
    }

    public ChangeClockAtr toChangeClockAtr() {
        return convert().changeClockAtr;
    }

    public ChangeCalArt toChangeCalAtr() {
        return convert().changeCalAtr;
    }

    public boolean isHalfDay() {
        return convert().isHalfDay;
    }

    public Optional<GoingOutReason> toGoOutReason(EnterpriseStampDataFunctionNo functionNo) {

        if (this.v().equals("O") || this.v().equals("P")) {
            switch (functionNo.v()) {
                case 0: return Optional.of(GoingOutReason.PRIVATE);
                case 1: return Optional.of(GoingOutReason.PUBLIC);
                case 2: return Optional.of(GoingOutReason.COMPENSATION);
                case 3: return Optional.of(GoingOutReason.UNION);
            }
        }

        return Optional.empty();
    }

    public SetPreClockArt toSetPreClockAtr(EnterpriseStampDataFunctionNo functionNo) {

        if (functionNo.v() == 99) {
            switch (this.v()) {
                case "A": return SetPreClockArt.DIRECT;
                case "B": return SetPreClockArt.BOUNCE;
            }
        }

        return SetPreClockArt.NONE;
    }

    private Converted convert() {
        return MAP.get(this.v());
    }

    private static final Map<String, Converted> MAP;
    static {
        MAP = new HashMap<>();
        MAP.put("A", new Converted(ChangeClockAtr.GOING_TO_WORK, ChangeCalArt.NONE, false));
        MAP.put("B", new Converted(ChangeClockAtr.GOING_TO_WORK, ChangeCalArt.NONE, true));
        MAP.put("C", new Converted(ChangeClockAtr.GOING_TO_WORK, ChangeCalArt.NONE, true));
        MAP.put("D", new Converted(ChangeClockAtr.GOING_TO_WORK, ChangeCalArt.FIX, false));
        MAP.put("E", new Converted(ChangeClockAtr.GOING_TO_WORK, ChangeCalArt.NONE, false));
        MAP.put("F", new Converted(ChangeClockAtr.GOING_TO_WORK, ChangeCalArt.NONE, false));
        MAP.put("G", new Converted(ChangeClockAtr.WORKING_OUT, ChangeCalArt.NONE, false));
        MAP.put("H", new Converted(ChangeClockAtr.WORKING_OUT, ChangeCalArt.NONE, true));
        MAP.put("I", new Converted(ChangeClockAtr.WORKING_OUT, ChangeCalArt.NONE, true));
        MAP.put("J", new Converted(ChangeClockAtr.WORKING_OUT, ChangeCalArt.OVER_TIME, false));
        MAP.put("K", new Converted(ChangeClockAtr.WORKING_OUT, ChangeCalArt.OVER_TIME, false));
        MAP.put("L", new Converted(ChangeClockAtr.WORKING_OUT, ChangeCalArt.FIX, false));
        MAP.put("M", new Converted(ChangeClockAtr.WORKING_OUT, ChangeCalArt.FIX, false));
        MAP.put("N", new Converted(ChangeClockAtr.WORKING_OUT, ChangeCalArt.NONE, false));
        MAP.put("O", new Converted(ChangeClockAtr.GO_OUT, ChangeCalArt.NONE, false));
        MAP.put("P", new Converted(ChangeClockAtr.GO_OUT, ChangeCalArt.NONE, false));
        MAP.put("Q", new Converted(ChangeClockAtr.RETURN, ChangeCalArt.NONE, false));
        MAP.put("R", new Converted(ChangeClockAtr.RETURN, ChangeCalArt.NONE, false));
        MAP.put("S", new Converted(ChangeClockAtr.GOING_TO_WORK, ChangeCalArt.EARLY_APPEARANCE, false));
        MAP.put("T", new Converted(ChangeClockAtr.GOING_TO_WORK, ChangeCalArt.EARLY_APPEARANCE, false));
        MAP.put("U", new Converted(ChangeClockAtr.GOING_TO_WORK, ChangeCalArt.BRARK, false));
        MAP.put("V", new Converted(ChangeClockAtr.GOING_TO_WORK, ChangeCalArt.BRARK, false));
        MAP.put("W", null);
        MAP.put("X", null);
        MAP.put("Y", null);
        MAP.put("Z", null);
        MAP.put("0", new Converted(ChangeClockAtr.TEMPORARY_WORK, ChangeCalArt.NONE, false));
        MAP.put("1", new Converted(ChangeClockAtr.START_OF_SUPPORT, ChangeCalArt.NONE, false));
        MAP.put("2", new Converted(ChangeClockAtr.END_OF_SUPPORT, ChangeCalArt.NONE, false));
        MAP.put("3", new Converted(ChangeClockAtr.GOING_TO_WORK, ChangeCalArt.NONE, false));
        MAP.put("4", new Converted(ChangeClockAtr.GOING_TO_WORK, ChangeCalArt.NONE, true));
        MAP.put("5", new Converted(ChangeClockAtr.GOING_TO_WORK, ChangeCalArt.FIX, false));
        MAP.put("6", new Converted(ChangeClockAtr.START_OF_SUPPORT, ChangeCalArt.BRARK, false));
        MAP.put("7", new Converted(ChangeClockAtr.WORKING_OUT, ChangeCalArt.NONE, false));
        MAP.put("8", new Converted(ChangeClockAtr.START_OF_SUPPORT, ChangeCalArt.EARLY_APPEARANCE, false));
        MAP.put("9", new Converted(ChangeClockAtr.TEMPORARY_LEAVING, ChangeCalArt.NONE, false));
        MAP.put("ｱ", new Converted(ChangeClockAtr.OVER_TIME, ChangeCalArt.NONE, false));
        MAP.put("ｲ", new Converted(ChangeClockAtr.BRARK, ChangeCalArt.NONE, false));
        MAP.put("ｳ", new Converted(ChangeClockAtr.OVER_TIME, ChangeCalArt.NONE, false));
        MAP.put("ｴ", new Converted(ChangeClockAtr.BRARK, ChangeCalArt.NONE, false));
    }

    @RequiredArgsConstructor
    private static class Converted {

        /** 時刻変更区分 */
        private final ChangeClockAtr changeClockAtr;

        /** 計算区分変更対象 */
        private final ChangeCalArt changeCalAtr;

        /** 勤務種類を半休に変更する */
        private final boolean isHalfDay;
    }
}
