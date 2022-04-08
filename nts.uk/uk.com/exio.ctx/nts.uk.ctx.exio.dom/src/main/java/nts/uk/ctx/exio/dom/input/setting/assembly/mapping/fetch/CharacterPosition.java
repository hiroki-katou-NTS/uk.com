package nts.uk.ctx.exio.dom.input.setting.assembly.mapping.fetch;

import java.util.Optional;

import lombok.Value;
import lombok.val;

/**
 * 文字位置
 */
@Value
public class CharacterPosition {

    /** 指定基準 */
    CharacterPositionBase base;

    /** 文字数（Javaの文字列のインデックス仕様と異なり、最初の文字は1とする） */
    ExternalImportFetchPosition count;

    /**
     * 指定文字列における先頭からのインデックスを返す
     * @param source
     * @return
     */
    public int getIndexFromStartOf(String source) {

        switch (base) {
            case FROM_START:
                return count.v() - 1;
            case FROM_END:
        }       return source.length() - count.v();
    }

    /**
     * Integer型による表現から復元する
     * @param value
     * @return
     */
	public static Optional<CharacterPosition> fromIntegerExpression(Integer value) {
		
		if (value == null) {
			return Optional.empty();
		}
		
		int intValue = value;
		
		// あり得ないはず・・・だけどここで落とすと変更できなくなるので
		if (intValue == 0) {
			return Optional.empty();
		}
		
		val base = (intValue > 0) ? CharacterPositionBase.FROM_START : CharacterPositionBase.FROM_END;
		int count = Math.abs(intValue);
		
		return Optional.of(new CharacterPosition(base, new ExternalImportFetchPosition(count)));
	}
	
	/**
	 * Integer型による表現に変換する
	 * @param domain
	 * @return
	 */
	public static Integer toIntegerExpression(Optional<CharacterPosition> domain) {
		
		if (!domain.isPresent()) {
			return null;
		}
		
		return domain.get().toIntegerExpression();
	}
	
	/**
	 * Integer型による表現に変換する
	 * @param domain
	 * @return
	 */
	public Integer toIntegerExpression() {
		int sign = base == CharacterPositionBase.FROM_START ? 1 : -1;
		return sign * count.v();
	}
}
