package nts.uk.ctx.at.record.dom.reservation.bentoReservationSetting;

import lombok.AllArgsConstructor;

/**
 * 予約済みの内容変更期限日数
 */

@AllArgsConstructor
public enum ContentChangeDeadlineDay {

    ZERO(0, "1"),
    ONE(1, "2"),
    TWO(2, "3"),
    THREE(3, "4"),
    FOUR(4, "5"),
    FIVE(5, "6"),
    SIX(6, "7"),
    SEVEN(7, "8"),
    EIGHT(8, "9"),
    NINE(9, "10"),
    TEN(10, "11"),
    ELEVEN(11, "12"),
    TWELVE(12, "13"),
    THIRTEEN(13, "14"),
    FOURTEEN(14, "15"),
    FIFTEEN(15, "16"),
    SIXTEEN(16, "17"),
    SEVENTEEN(17, "18"),
    EIGHTEEN(18, "19"),
    NINETEEN(19, "20"),
    TWENTY(20, "21"),
    TWENTY_ONE(21, "22"),
    TWENTY_TWO(22, "23"),
    TWENTY_THREE(23, "24"),
    TWENTY_FOUR(24, "25"),
    TWENTY_FIVE(25, "26"),
    TWENTY_SIX(26, "27"),
    TWENTY_SEVEN(27, "28"),
    TWENTY_EIGHT(28, "29"),
    TWENTY_NINE(29, "30"),
    THIRTY(30, "31");

    public final int value;
    public final String name;

}
