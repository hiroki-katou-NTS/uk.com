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

        if (isOutOfRange(startIndex, source) && isOutOfRange(endIndex, source)) {
            return "";
        }

        int length = endIndex - startIndex + 1;
        if (length <= 0) {
            return "";
        }
        int minIndex = 0;
        startIndex = Math.max(startIndex, minIndex);
        endIndex = Math.max(endIndex, minIndex);

        int maxIndex = source.length() - 1;
        startIndex = Math.min(startIndex, maxIndex);
        endIndex = Math.min(endIndex, maxIndex);


        // String#substringは、endIndexの位置の文字を含めないので +1 が必要
        return source.substring(startIndex, endIndex + 1);
    }

    private static boolean isOutOfRange(int index, String source) {
        return index < 0 || source.length() - 1 < index;
    }
}
