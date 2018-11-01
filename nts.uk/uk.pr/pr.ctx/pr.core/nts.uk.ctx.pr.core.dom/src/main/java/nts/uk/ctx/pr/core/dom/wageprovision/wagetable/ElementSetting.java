package nts.uk.ctx.pr.core.dom.wageprovision.wagetable;


/**
* 要素設定
*/
public enum ElementSetting {
    
    ONE_DIMENSION(0, "一次元"),
    TWO_DIMENSION(1, "二次元"),
    THREE_DIMENSION(2, "三次元"),
    QUALIFICATION(3, "資格"),
    FINE_WORK(4, "精皆勤");
    
    /** The value. */
    public final int value;
    
    /** The name id. */
    public final String nameId;
    private ElementSetting(int value, String nameId) 
    {
        this.value = value;
        this.nameId = nameId;
    }
}
