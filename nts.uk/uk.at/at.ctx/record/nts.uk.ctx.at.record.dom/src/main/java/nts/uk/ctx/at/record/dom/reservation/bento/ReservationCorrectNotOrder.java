package nts.uk.ctx.at.record.dom.reservation.bento;

import nts.uk.shr.com.i18n.TextResource;

/**
 * @author anhnm
 * 予約修正抽出条件発注無し
 *
 */
public enum ReservationCorrectNotOrder {
    
    ALL_RESERVE(0, "KMR003_54"),
    
    MORE_THAN_2_ITEMS(1, "KMR003_55");
    
    public int value;
    
    public String name;
    
    ReservationCorrectNotOrder(int type,String name){
        this.value = type;
        this.name = TextResource.localize(name);
    }

}
