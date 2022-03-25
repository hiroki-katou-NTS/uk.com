package nts.uk.ctx.sys.assist.dom.mastercopy.handler;

import nts.gul.text.IdentifierUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 生成したキー値を保持管理する
 */
public class KeyValueHolder {

    private static final String KEY_PREFIX = "%GUID%";

    private final Map<String, String> heldKeys = new HashMap<>();

    public static boolean isKey(Object value) {

        if (value instanceof String) {
            String stringValue = (String) value;
            return stringValue.indexOf(KEY_PREFIX) == 0;
        }

        return false;
    }

    public String getKeyValue(Object value) {

        if (!isKey(value)) {
            throw new RuntimeException("あらかじめisKeyでチェックすること");
        }

        String stringValue = (String) value;
        return heldKeys.computeIfAbsent(stringValue, k -> IdentifierUtil.randomUniqueId());
    }
}
