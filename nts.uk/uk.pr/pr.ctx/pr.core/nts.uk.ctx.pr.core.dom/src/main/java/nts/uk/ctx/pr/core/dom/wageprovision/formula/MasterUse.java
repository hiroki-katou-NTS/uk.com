package nts.uk.ctx.pr.core.dom.wageprovision.formula;


import nts.arc.i18n.I18NText;

/**
* 使用マスタ
*/
public enum MasterUse {
    
    EMPLOYMENT(0, I18NText.getText("Enum_MasterUse_EMPLOYMENT")),
    DEPARTMENT(1, I18NText.getText("Enum_MasterUse_DEPARTMENT")),
    CLASSIFICATION(2, I18NText.getText("Enum_MasterUse_CLASSIFICATION")),
    JOB_TITLE(3, I18NText.getText("Enum_MasterUse_JOB_TITLE")),
    SALARY_CLASSIFICATION(4, I18NText.getText("Enum_MasterUse_SALARY_CLS")),
    SALARY_FORM(5, I18NText.getText("Enum_MasterUse_SALARY_FORM"));
    
    /** The value. */
    public final int value;
    
    /** The name id. */
    public final String nameId;
    private MasterUse(int value, String nameId) 
    {
        this.value = value;
        this.nameId = nameId;
    }

    public static void main(String[] args) {

    }
}
