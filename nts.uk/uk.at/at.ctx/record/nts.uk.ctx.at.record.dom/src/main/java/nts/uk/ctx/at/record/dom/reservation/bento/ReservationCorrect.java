package nts.uk.ctx.at.record.dom.reservation.bento;

/**
 * @author anhnm
 * 予約修正抽出条件
 *
 */
public enum ReservationCorrect {
    
    ALL_RESERVE(0, "予約した全部"),
    
    MORE_THAN_2_ITEMS(1, "１商品２件以上"),
    
    ORDER(2, "発注済み"),
    
    NOT_ORDERING(3, "未発注");
    
    public int value;
    
    public String name;
    
    ReservationCorrect(int type,String name){
        this.value = type;
        this.name = name;
    }
}
