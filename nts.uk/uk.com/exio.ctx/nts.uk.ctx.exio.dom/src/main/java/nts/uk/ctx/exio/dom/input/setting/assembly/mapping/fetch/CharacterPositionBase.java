package nts.uk.ctx.exio.dom.input.setting.assembly.mapping.fetch;

import lombok.RequiredArgsConstructor;
import nts.arc.enums.EnumAdaptor;

@RequiredArgsConstructor
public enum CharacterPositionBase {

    /** 先頭から */
    FROM_START(1),

    /** 末尾から */
    FROM_END(2),
    ;

    public final int value;

    public static CharacterPositionBase valueOf(int value) {
        return EnumAdaptor.valueOf(value, CharacterPositionBase.class);
    }
}
