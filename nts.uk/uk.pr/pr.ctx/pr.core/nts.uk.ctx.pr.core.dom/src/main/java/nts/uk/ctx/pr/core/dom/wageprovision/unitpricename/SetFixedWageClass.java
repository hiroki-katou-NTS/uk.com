package nts.uk.ctx.pr.core.dom.wageprovision.unitpricename;


/**
* 固定的賃金の設定区分
*/
public enum SetFixedWageClass
{
    /*
    * 全員一律で指定する
    *
    *  */
    DES_FOR_EACH_SALARY_CON_TYPE(0),
    /*
    * 給与契約形態ごとに指定する
    * */
    DES_BY_ALL_MEMBERS(1);

    /** The value. */
    public final int value;
    private SetFixedWageClass(int value)
    {
        this.value = value;
    }
}
