package nts.uk.ctx.alarm.dom.conditionvalue;

import org.junit.Test;
import static org.assertj.core.api.Assertions.*;
import static nts.uk.ctx.alarm.dom.conditionvalue.ConditionValueComparison.*;

public class ConditionValueComparisonTest {

    @Test
    public void equal() {
        isTrue(EQUAL, 1, 1);
        isFalse(EQUAL, 1, 2);
    }

    @Test
    public void notEqual() {
        isTrue(NOT_EQUAL, 1, 2);
        isFalse(NOT_EQUAL, 1, 1);
    }

    @Test
    public void lessThan() {
        isTrue(LESS_THAN, 1, 2);
        isFalse(LESS_THAN, 2, 2);
        isFalse(LESS_THAN, 3, 2);
    }

    @Test
    public void lessThanOrEqual() {
        isTrue(LESS_THAN_OR_EQUAL, 1, 2);
        isTrue(LESS_THAN_OR_EQUAL, 2, 2);
        isFalse(LESS_THAN_OR_EQUAL, 3, 2);
    }

    @Test
    public void greaterThan() {
        isFalse(GREATER_THAN, 1, 2);
        isFalse(GREATER_THAN, 2, 2);
        isTrue(GREATER_THAN, 3, 2);
    }

    @Test
    public void greaterThanOrEqual() {
        isFalse(GREATER_THAN_OR_EQUAL, 1, 2);
        isTrue(GREATER_THAN_OR_EQUAL, 2, 2);
        isTrue(GREATER_THAN_OR_EQUAL, 3, 2);
    }

    @Test
    public void withinRange() {
        isFalse(WITHIN_RANGE, 0, 1, 3);
        isFalse(WITHIN_RANGE, 1, 1, 3);
        isTrue(WITHIN_RANGE, 2, 1, 3);
        isFalse(WITHIN_RANGE, 3, 1, 3);
        isFalse(WITHIN_RANGE, 4, 1, 3);
    }

    @Test
    public void withinRangeOrEqual() {
        isFalse(WITHIN_RANGE_OR_EQUAL, 0, 1, 3);
        isTrue(WITHIN_RANGE_OR_EQUAL, 1, 1, 3);
        isTrue(WITHIN_RANGE_OR_EQUAL, 2, 1, 3);
        isTrue(WITHIN_RANGE_OR_EQUAL, 3, 1, 3);
        isFalse(WITHIN_RANGE_OR_EQUAL, 4, 1, 3);
    }

    @Test
    public void outOfRange() {
        isTrue(OUT_OF_RANGE, 0, 1, 3);
        isFalse(OUT_OF_RANGE, 1, 1, 3);
        isFalse(OUT_OF_RANGE, 2, 1, 3);
        isFalse(OUT_OF_RANGE, 3, 1, 3);
        isTrue(OUT_OF_RANGE, 4, 1, 3);
    }

    @Test
    public void outOfRangeOrEqual() {
        isTrue(OUT_OF_RANGE_OR_EQUAL, 0, 1, 3);
        isTrue(OUT_OF_RANGE_OR_EQUAL, 1, 1, 3);
        isFalse(OUT_OF_RANGE_OR_EQUAL, 2, 1, 3);
        isTrue(OUT_OF_RANGE_OR_EQUAL, 3, 1, 3);
        isTrue(OUT_OF_RANGE_OR_EQUAL, 4, 1, 3);
    }

    private static void isTrue(ConditionValueComparison comparison, int target, int condition1) {
        isTrue(comparison, target, condition1, null);
    }

    private static void isFalse(ConditionValueComparison comparison, int target, int condition1) {
        isFalse(comparison, target, condition1, null);
    }

    private static void isTrue(ConditionValueComparison comparison, int target, int condition1, Integer condition2) {
        assertThat(comparison.matches(target, condition1, condition2)).isTrue();
    }

    private static void isFalse(ConditionValueComparison comparison, int target, int condition1, Integer condition2) {
        assertThat(comparison.matches(target, condition1, condition2)).isFalse();
    }
}