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
    public void emptyStart() {
        val target = new SubstringFetch(Optional.empty(), fromStart(3));
        String actual = target.fetch("12345");
        assertThat(actual).isEqualTo("123");
    }

    @Test
    public void emptyEnd() {
        val target = new SubstringFetch(fromStart(3), Optional.empty());
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
    public void error_reverse() {
        val target = new SubstringFetch(fromStart(4), fromEnd(3));
        String actual = target.fetch("12345");
        assertThat(actual).isEqualTo("");
    }

    @Test
    public void startIndexIsOutOfRange1() {
        val target = new SubstringFetch(fromStart(100), Optional.empty());
        String actual = target.fetch("12345");
        assertThat(actual).isEqualTo("");
    }

    @Test
    public void startIndexIsOutOfRange2() {
        val target = new SubstringFetch(fromEnd(100), Optional.empty());
        String actual = target.fetch("12345");
        assertThat(actual).isEqualTo("12345");
    }

    @Test
    public void startIndexIsOutOfRange3() {
        val target = new SubstringFetch(fromEnd(100), fromStart(2));
        String actual = target.fetch("12345");
        assertThat(actual).isEqualTo("12");
    }

    @Test
    public void endIndexIsOutOfRange1() {
        val target = new SubstringFetch(Optional.empty(), fromStart(100));
        String actual = target.fetch("12345");
        assertThat(actual).isEqualTo("12345");
    }

    @Test
    public void endIndexIsOutOfRange2() {
        val target = new SubstringFetch(fromStart(3), fromStart(100));
        String actual = target.fetch("12345");
        assertThat(actual).isEqualTo("345");
    }

    @Test
    public void endIndexIsOutOfRange3() {
        val target = new SubstringFetch(Optional.empty(), fromEnd(100));
        String actual = target.fetch("12345");
        assertThat(actual).isEqualTo("");
    }

    @Test
    public void startEndIndexIsOutOfRange1() {
        val target = new SubstringFetch(fromStart(100), fromStart(200));
        String actual = target.fetch("12345");
        assertThat(actual).isEqualTo("");
    }

    @Test
    public void startEndIndexIsOutOfRange2() {
        val target = new SubstringFetch(fromEnd(200), fromEnd(100));
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