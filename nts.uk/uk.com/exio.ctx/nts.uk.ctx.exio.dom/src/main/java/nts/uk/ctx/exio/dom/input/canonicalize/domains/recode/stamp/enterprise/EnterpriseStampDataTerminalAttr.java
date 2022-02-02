package nts.uk.ctx.exio.dom.input.canonicalize.domains.recode.stamp.enterprise;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import nts.arc.primitive.PrimitiveValueConstraintException;
import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.AuthcMethod;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampMeans;

import java.util.HashMap;
import java.util.Map;

/**
 * E版打刻データ端末区分
 */
@StringCharType(CharType.ALPHA_NUMERIC)
@StringMaxLength(1)
public class EnterpriseStampDataTerminalAttr extends StringPrimitiveValue<EnterpriseStampDataTerminalAttr> {

    public EnterpriseStampDataTerminalAttr(String rawValue) {
        super(rawValue);
    }

    private static final Map<String, Converted> MAP;
    static {
        MAP = new HashMap<>();
        MAP.put("0", new Converted(AuthcMethod.EXTERNAL_AUTHC, StampMeans.TEXT));
        MAP.put("3", new Converted(AuthcMethod.IC_CARD_AUTHC, StampMeans.TIME_CLOCK));
        MAP.put("4", new Converted(AuthcMethod.ID_AUTHC, StampMeans.INDIVITION));
        MAP.put("5", new Converted(AuthcMethod.ID_AUTHC, StampMeans.SMART_PHONE));
        MAP.put("A", new Converted(AuthcMethod.ID_AUTHC, StampMeans.TIME_CLOCK));
        MAP.put("B", new Converted(AuthcMethod.EXTERNAL_AUTHC, StampMeans.TIME_CLOCK));
        MAP.put("C", new Converted(AuthcMethod.IC_CARD_AUTHC, StampMeans.TIME_CLOCK));
        MAP.put("D", new Converted(AuthcMethod.VEIN_AUTHC, StampMeans.TIME_CLOCK));
        MAP.put("E", new Converted(AuthcMethod.ID_AUTHC, StampMeans.SMART_PHONE));
        MAP.put("F", new Converted(AuthcMethod.IC_CARD_AUTHC, StampMeans.TIME_CLOCK));
        MAP.put("J", new Converted(AuthcMethod.VEIN_AUTHC, StampMeans.FINGER_AUTHC));
    }

    @Override
    public void validate() {
        super.validate();

        if (!MAP.containsKey(this.v())) {
            throw new PrimitiveValueConstraintException("不正な端末区分です: " + this.v());
        }
    }

    /**
     * 認証方法に変換する
     * @return
     */
    public AuthcMethod toAuthMethod() {
        return MAP.get(this.v()).authMethod;
    }

    /**
     * 打刻手段に変換する
     * @return
     */
    public StampMeans toStampMeans() {
        return MAP.get(this.v()).stampMeans;
    }

    @RequiredArgsConstructor
    private static class Converted {
        /** 認証方法 */
        private final AuthcMethod authMethod;

        /** 打刻手段 */
        private final StampMeans stampMeans;
    }
}
