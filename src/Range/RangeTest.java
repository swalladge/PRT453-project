package Range;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class RangeTest {

    @Test
    void testUnboundedLowerRange() throws ParseException {
        Range range = new Range("-10");
        Assertions.assertTrue(range.contains(-50));
        Assertions.assertTrue(range.contains(0));
        Assertions.assertTrue(range.contains(9));
        Assertions.assertTrue(range.contains(10));
        Assertions.assertFalse(range.contains(11));
        Assertions.assertFalse(range.contains(50));
    }

    @Test
    void testUnboundedUpperRangeMinus() throws ParseException {
        Range range = new Range("10-");
        Assertions.assertFalse(range.contains(-50));
        Assertions.assertFalse(range.contains(0));
        Assertions.assertFalse(range.contains(9));
        Assertions.assertTrue(range.contains(10));
        Assertions.assertTrue(range.contains(11));
        Assertions.assertTrue(range.contains(50));
    }

    @Test
    void testUnboundedUpperRangePlus() throws ParseException {
        Range range = new Range("10+");
        Assertions.assertFalse(range.contains(-50));
        Assertions.assertFalse(range.contains(0));
        Assertions.assertFalse(range.contains(9));
        Assertions.assertTrue(range.contains(10));
        Assertions.assertTrue(range.contains(11));
        Assertions.assertTrue(range.contains(50));
    }

    @Test
    void testStandardRange() throws ParseException {
        Range range = new Range("10-15");

        Assertions.assertTrue(range.contains(10));
        Assertions.assertTrue(range.contains(15));
        Assertions.assertTrue(range.contains(12));

        Assertions.assertFalse(range.contains(-50));
        Assertions.assertFalse(range.contains(9));
        Assertions.assertFalse(range.contains(16));
        Assertions.assertFalse(range.contains(106));
    }


    @Test
    void testReverseRange() throws ParseException {
        Range range = new Range("15-10");

        Assertions.assertTrue(range.contains(10));
        Assertions.assertTrue(range.contains(15));
        Assertions.assertTrue(range.contains(12));

        Assertions.assertFalse(range.contains(-50));
        Assertions.assertFalse(range.contains(9));
        Assertions.assertFalse(range.contains(16));
        Assertions.assertFalse(range.contains(106));
    }

    @Test
    void testRangeSameNumbers() throws ParseException {
        Range range = new Range("10-10");

        Assertions.assertTrue(range.contains(10));

        Assertions.assertFalse(range.contains(-50));
        Assertions.assertFalse(range.contains(9));
        Assertions.assertFalse(range.contains(11));
        Assertions.assertFalse(range.contains(106));
    }

    @Test
    void testValidNumberLeadingZero() throws ParseException {
        Range range = new Range("0010");

        Assertions.assertTrue(range.contains(10));

        Assertions.assertFalse(range.contains(-50));
        Assertions.assertFalse(range.contains(9));
        Assertions.assertFalse(range.contains(11));
        Assertions.assertFalse(range.contains(106));
    }

    @Test
    void testInvalidRange1() {
        Assertions.assertThrows(ParseException.class, () -> new Range("+10"));
    }

    @Test
    void testInvalidRange2() {
        Assertions.assertThrows(ParseException.class, () -> new Range("a+10"));
    }

    @Test
    void testInvalidRange3() {
        Assertions.assertThrows(ParseException.class, () -> new Range("a-10"));
    }

    @Test
    void testInvalidRange4() {
        Assertions.assertThrows(ParseException.class, () -> new Range("10--"));
    }

    @Test
    void testInvalidRange5() {
        Assertions.assertThrows(ParseException.class, () -> new Range("10--10"));
    }

    @Test
    void testInvalidRange6() {
        Assertions.assertThrows(ParseException.class, () -> new Range("--23"));
    }

    @Test
    void testInvalidRange7() {
        Assertions.assertThrows(ParseException.class, () -> new Range("10+-"));
    }

    @Test
    void testInvalidRange8() {
        Assertions.assertThrows(ParseException.class, () -> new Range("5-5-5"));
    }

    @Test
    void testInvalidRange9() {
        Assertions.assertThrows(ParseException.class, () -> new Range("5-5+"));
    }

    @Test
    void testInvalidRange10() {
        Assertions.assertThrows(ParseException.class, () -> new Range("5,6"));
    }

    @Test
    void testInvalidRange11() {
        Assertions.assertThrows(ParseException.class, () -> new Range("asdf-qwop-45+3-1-et"));
    }

    @Test
    void testEmptyRange() throws ParseException {
        Range range = new Range("");
        Assertions.assertTrue(range.contains(-50));
        Assertions.assertTrue(range.contains(50));
        Assertions.assertTrue(range.contains(0));
        Assertions.assertTrue(range.contains(1));
        Assertions.assertTrue(range.contains(-1));
        Assertions.assertTrue(range.contains(10));
        Assertions.assertTrue(range.contains(1000));
    }
}