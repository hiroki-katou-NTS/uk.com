package nts.uk.ctx.at.record.dom.reservation.bento;

/**
 * @author anhnm
 * 予約修正抽出条件発注無し
 *
 */
public enum ReservationCorrectNotOrder {
    
    ALL_RESERVE(0, "予約した全部"),
    
    MORE_THAN_2_ITEMS(1, "１商品２件以上");
    
    public int value;
    
    public String name;
    
    ReservationCorrectNotOrder(int type,String name){
        this.value = type;
        this.name = name;
    }

}
