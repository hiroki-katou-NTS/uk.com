package nts.uk.ctx.at.record.dom.reservation.bento;

import nts.uk.shr.com.i18n.TextResource;

/**
 * @author anhnm
 * 予約修正抽出条件
 *
 */
public enum ReservationCorrect {
    
    ALL_RESERVE(0, "KMR003_54"),
    
    MORE_THAN_2_ITEMS(1, "KMR003_55"),
    
    ORDER(2, "KMR003_56"),
    
    NOT_ORDERING(3, "KMR003_57");
    
    public int value;
    
    public String name;
    
    ReservationCorrect(int type,String name){
        this.value = type;
        this.name = TextResource.localize(name);
    }
}
