package nts.uk.ctx.sys.assist.dom.mastercopy;

/**
 * @author locph
 */
public interface PersonalInfoDataCopyAdapter {
    void personalInfoDefEvent(String companyId, int value);
    void newLayoutEvent(String companyId, int value);
    void personalInfoItemGroupEvent(String companyId, int value);
    void personalInfoSelectItemEvent(String companyId, int value);
}
