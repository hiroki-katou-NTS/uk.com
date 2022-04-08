package nts.uk.ctx.exio.dom.input.setting.assembly.mapping.fetch;

import lombok.Value;

import java.util.Optional;

/**
 * 部分文字列の読み取り
 */
@Value
public class SubstringFetch {

    /** 開始位置 */
    Optional<CharacterPosition> start;

    /** 終了位置 */
    Optional<CharacterPosition> end;

    /**
     * 開始位置の文字から終了位置の文字までを読み取る
     * @param source
     * @return
     */
    public String fetch(String source) {

        int startIndex = start.map(cp -> cp.getIndexFromStartOf(source)).orElse(0);
        int endIndex = end.map(cp -> cp.getIndexFromStartOf(source)).orElse(source.length() - 1);

        int length = endIndex - startIndex + 1;
        if (length <= 0) {
            return "";
        }

        if (startIndex < 0) {
            startIndex = 0;
        }
        if (endIndex > source.length() - 1) {
            endIndex = source.length() - 1;
        }

        // String#substringは、endIndexの位置の文字を含めないので +1 が必要
        return source.substring(startIndex, endIndex + 1);
    }
}
