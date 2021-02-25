package nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums;

/**
 * 	Enumeration: 帳票共通の演算子
 * 	@author chinh.hm
 */
public enum OperatorsCommonToForms {
    //1	加算
    ADDITION(1),
    //2	減算
    SUBTRACTION(2);

    public final int value;

    private OperatorsCommonToForms(int value){
        this.value = value;
    }
}
