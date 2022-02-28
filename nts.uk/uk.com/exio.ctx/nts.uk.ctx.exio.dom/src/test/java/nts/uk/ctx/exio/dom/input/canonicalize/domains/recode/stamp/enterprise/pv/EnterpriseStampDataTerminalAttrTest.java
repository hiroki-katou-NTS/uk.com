package nts.uk.ctx.exio.dom.input.canonicalize.domains.recode.stamp.enterprise.pv;

import lombok.val;
import org.junit.Test;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.*;

public class EnterpriseStampDataTerminalAttrTest {

    @Test
    public void toAuthMethod() {

        val map = new HashMap<String, String>();
        map.put("0", "外部認証");
        map.put("3", "ICカード認証");
        map.put("4", "ID認証");
        map.put("5", "ID認証");
        map.put("A", "ID認証");
        map.put("B", "外部認証");
        map.put("C", "ICカード認証");
        map.put("D", "静脈認証");
        map.put("E", "ID認証");
        map.put("F", "ICカード認証");
        map.put("J", "静脈認証");

        for (val e : map.entrySet()) {
            val field = new EnterpriseStampDataTerminalAttr(e.getKey()).toAuthMethod();
            assertThat(field.name).isEqualTo(e.getValue());
        }
    }

    @Test
    public void toStampMeans() {

        val map = new HashMap<String, String>();
        map.put("0", "テキスト受入");
        map.put("3", "タイムレコーダー打刻");
        map.put("4", "個人打刻");
        map.put("5", "スマホ打刻");
        map.put("A", "タイムレコーダー打刻");
        map.put("B", "タイムレコーダー打刻");
        map.put("C", "タイムレコーダー打刻");
        map.put("D", "タイムレコーダー打刻");
        map.put("E", "スマホ打刻");
        map.put("F", "タイムレコーダー打刻");
        map.put("J", "指認証打刻");

        for (val e : map.entrySet()) {
            val field = new EnterpriseStampDataTerminalAttr(e.getKey()).toStampMeans();
            assertThat(field.name).isEqualTo(e.getValue());
        }
    }
}