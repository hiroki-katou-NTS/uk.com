package nts.uk.ctx.sys.portal.app.find.toppagesetting;

import lombok.Value;

@Value
public class LayoutAllDto {

	/**my page*/
	private LayoutForMyPageDto myPage;
	/**top page*/
	private LayoutForTopPageDto topPage;
	/**check xem hien thi toppage hay mypage truoc*/
	private Boolean check;
	/**check my page co duoc hien khong*/
	private Boolean checkMyPage;
	
}
