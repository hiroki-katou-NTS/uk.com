package nts.uk.ctx.sys.auth.dom.adapter.checkpassword;

/**
 * The Interface CheckBeforePasswordAdapter.
 */
public interface CheckBeforePasswordAdapter {

    /**
     * Check before change password.
     *
     * @param userId the user id
     * @param currentPass the current pass
     * @param newPass the new pass
     * @param reNewPass the re new pass
     * @return the check before change pass output
     */
    //パスワード変更前チェック  request list 383
    CheckBeforeChangePassImport checkBeforeChangePassword(String userId, String currentPass, String newPass, String reNewPass);
}
