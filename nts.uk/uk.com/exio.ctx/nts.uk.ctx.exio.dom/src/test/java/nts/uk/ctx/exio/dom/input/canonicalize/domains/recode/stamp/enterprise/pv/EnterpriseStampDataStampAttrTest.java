package nts.uk.ctx.exio.dom.input.canonicalize.domains.recode.stamp.enterprise.pv;

import lombok.val;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.SetPreClockArt;
import nts.uk.ctx.at.shared.dom.workrule.goingout.GoingOutReason;
import nts.uk.ctx.office.dom.goout.GoOutReason;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class EnterpriseStampDataStampAttrTest {

    @Test
    public void toChangeClockAtr() {

        val map = new HashMap<String, String>();
        map.put("A", "出勤");
        map.put("B", "出勤");
        map.put("C", "出勤");
        map.put("D", "出勤");
        map.put("E", "出勤");
        map.put("F", "出勤");
        map.put("G", "退勤");
        map.put("H", "退勤");
        map.put("I", "退勤");
        map.put("J", "退勤");
        map.put("K", "退勤");
        map.put("L", "退勤");
        map.put("M", "退勤");
        map.put("N", "退勤");
        map.put("O", "外出");
        map.put("P", "外出");
        map.put("Q", "戻り");
        map.put("R", "戻り");
        map.put("S", "出勤");
        map.put("T", "出勤");
        map.put("U", "出勤");
        map.put("V", "出勤");
        map.put("W", null);
        map.put("X", null);
        map.put("Y", null);
        map.put("Z", null);
        map.put("0", "臨時出勤");
        map.put("1", "応援開始");
        map.put("2", "応援終了");
        map.put("3", "出勤");
        map.put("4", "出勤");
        map.put("5", "出勤");
        map.put("6", "応援開始");
        map.put("7", "臨時出勤");
        map.put("8", "応援開始");
        map.put("9", "臨時退勤");
        map.put("ｱ", "入門");
        map.put("ｲ", "退門");
        map.put("ｳ", "入門");
        map.put("ｴ", "退門");

        for (val e : map.entrySet()) {
            val pv = new EnterpriseStampDataStampAttr(e.getKey());
            if (e.getValue() == null) {
                assertThat(pv.isSupported()).isFalse();
                continue;
            }

            val field = pv.toChangeClockAtr();
            assertThat(field.nameId.trim()).as(pv.v()).isEqualTo(e.getValue());
        }
    }

    @Test
    public void toChangeCalAtr() {

        val map = new HashMap<String, String>();
        map.put("A", "なし");
        map.put("B", "なし");
        map.put("C", "なし");
        map.put("D", "ﾌﾚｯｸｽ");
        map.put("E", "なし");
        map.put("F", "なし");
        map.put("G", "なし");
        map.put("H", "なし");
        map.put("I", "なし");
        map.put("J", "残業");
        map.put("K", "残業");
        map.put("L", "ﾌﾚｯｸｽ");
        map.put("M", "ﾌﾚｯｸｽ");
        map.put("N", "なし");
        map.put("O", "なし");
        map.put("P", "なし");
        map.put("Q", "なし");
        map.put("R", "なし");
        map.put("S", "早出");
        map.put("T", "早出");
        map.put("U", "休出");
        map.put("V", "休出");
        map.put("W", null);
        map.put("X", null);
        map.put("Y", null);
        map.put("Z", null);
        map.put("0", "なし");
        map.put("1", "なし");
        map.put("2", "なし");
        map.put("3", "なし");
        map.put("4", "なし");
        map.put("5", "ﾌﾚｯｸｽ");
        map.put("6", "休出");
        map.put("7", "なし");
        map.put("8", "早出");
        map.put("9", "なし");
        map.put("ｱ", "なし");
        map.put("ｲ", "なし");
        map.put("ｳ", "なし");
        map.put("ｴ", "なし");

        for (val e : map.entrySet()) {
            val pv = new EnterpriseStampDataStampAttr(e.getKey());
            if (e.getValue() == null) {
                assertThat(pv.isSupported()).isFalse();
                continue;
            }

            val field = pv.toChangeCalAtr();
            assertThat(field.nameId.trim()).as(pv.v()).isEqualTo(e.getValue());
        }
    }

    @Test
    public void isHalfDay() {

        val map = new HashMap<String, Boolean>();
        map.put("A", false);
        map.put("B", true);
        map.put("C", true);
        map.put("D", false);
        map.put("E", false);
        map.put("F", false);
        map.put("G", false);
        map.put("H", true);
        map.put("I", true);
        map.put("J", false);
        map.put("K", false);
        map.put("L", false);
        map.put("M", false);
        map.put("N", false);
        map.put("O", false);
        map.put("P", false);
        map.put("Q", false);
        map.put("R", false);
        map.put("S", false);
        map.put("T", false);
        map.put("U", false);
        map.put("V", false);
        map.put("W", null);
        map.put("X", null);
        map.put("Y", null);
        map.put("Z", null);
        map.put("0", false);
        map.put("1", false);
        map.put("2", false);
        map.put("3", false);
        map.put("4", true);
        map.put("5", false);
        map.put("6", false);
        map.put("7", false);
        map.put("8", false);
        map.put("9", false);
        map.put("ｱ", false);
        map.put("ｲ", false);
        map.put("ｳ", false);
        map.put("ｴ", false);

        for (val e : map.entrySet()) {
            val pv = new EnterpriseStampDataStampAttr(e.getKey());
            if (e.getValue() == null) {
                assertThat(pv.isSupported()).isFalse();
                continue;
            }

            val value = pv.isHalfDay();
            assertThat(value).as(pv.v()).isEqualTo(e.getValue());
        }
    }

    private static final List<String> VALUES = Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "ｱ", "ｲ", "ｳ", "ｴ");

    @Test
    public void toGoOutReason() {

        val map = new HashMap<Integer, GoingOutReason>();
        for (val v : GoingOutReason.values()) {
            map.put(v.value, v);
        }

        for (val s : VALUES) {
            val pv = new EnterpriseStampDataStampAttr(s);

            if (s.equals("O") || s.equals("P")) {
                for (val e : map.entrySet()) {
                    val actual = pv.toGoOutReason(new EnterpriseStampDataFunctionNo(e.getKey()));
                    assertThat(actual.get()).as(s).isEqualTo(e.getValue());
                }
            }

            else {
                val actual = pv.toGoOutReason(new EnterpriseStampDataFunctionNo(0));
                assertThat(actual.isPresent()).as(s).isFalse();
            }
        }
    }

    @Test
    public void toSetPreClockAtr() {

        val f99 = new EnterpriseStampDataFunctionNo(99);

        for (val s : VALUES) {
            val pv = new EnterpriseStampDataStampAttr(s);

            if (s.equals("A")) {
                assertThat(pv.toSetPreClockAtr(f99)).as(s).isEqualTo(SetPreClockArt.DIRECT);
            } else if (s.equals("G")) {
                assertThat(pv.toSetPreClockAtr(f99)).as(s).isEqualTo(SetPreClockArt.BOUNCE);
            } else {
                assertThat(pv.toSetPreClockAtr(f99)).as(s).isEqualTo(SetPreClockArt.NONE);
            }
        }

    }

}