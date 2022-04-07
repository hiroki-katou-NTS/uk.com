package nts.uk.ctx.exio.dom.input.setting.assembly.mapping.fetch;

import lombok.val;
import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

public class SubstringFetchTest {

    @Test
    public void fromStart() {
        val target = new SubstringFetch(fromStart(1), fromStart(3));
        String actual = target.fetch("12345");
        assertThat(actual).isEqualTo("123");
    }

    @Test
    public void fromEnd() {
        val target = new SubstringFetch(fromEnd(3), fromEnd(1));
        String actual = target.fetch("12345");
        assertThat(actual).isEqualTo("345");
    }

    @Test
    public void oneChar() {
        val target = new SubstringFetch(fromStart(3), fromEnd(3));
        String actual = target.fetch("12345");
        assertThat(actual).isEqualTo("3");
    }

    @Test
    public void error() {
        val target = new SubstringFetch(fromStart(4), fromEnd(3));
        String actual = target.fetch("12345");
        assertThat(actual).isEqualTo("");
    }

    @Test
    public void japanese() {
        val target = new SubstringFetch(fromStart(3), fromEnd(3));
        String actual = target.fetch("ｱ2う4ｵ");
        assertThat(actual).isEqualTo("う");
    }

    private static Optional<CharacterPosition> fromStart(int count) {
        return pos(CharacterPositionBase.FROM_START, count);
    }

    private static Optional<CharacterPosition> fromEnd(int count) {
        return pos(CharacterPositionBase.FROM_END, count);
    }

    private static Optional<CharacterPosition> pos(CharacterPositionBase base, int count) {
        return Optional.of(new CharacterPosition(
                base, new ExternalImportFetchPosition(count)));
    }
}