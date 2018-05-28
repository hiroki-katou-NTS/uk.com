package nts.uk.ctx.sys.auth.pub.user;

public interface CheckBeforePasswordPublisher {
	//パスワード変更前チェック  request list 383
	CheckBeforeChangePassOutput checkBeforeChangePassword(String userId,String currentPass, String newPass, String reNewPass);
}
